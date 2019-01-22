package com.example.ProductsMicroService.Controller;

import com.example.ProductsMicroService.Entity.*;
import com.example.ProductsMicroService.Services.ProductService;
import com.example.ProductsMicroService.dto.MerchantDTO;
import com.example.ProductsMicroService.dto.MerchantDetailsDTO;
import com.example.ProductsMicroService.dto.ProductDTO;
import com.example.ProductsMicroService.dto.ShortListingDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
public class ProductController {

//    public static final String BASE_URL="products";

    @Autowired
    ProductService productService;

    public static final String BASE_URL="products";

    @RequestMapping(value = BASE_URL+"/addProduct",method = RequestMethod.POST)
    public ResponseEntity<String> add(@RequestBody ProductDTO productDTO)
    {
        Product product=new Product();
        BeanUtils.copyProperties(productDTO,product);

        Product productCreated=productService.addProduct(product);


        List<String> subCategoriesForSolr=new ArrayList<>();
        List<String> categorySolr=new ArrayList<>();
        List<String> brandSolr=new ArrayList<>();

        for(int i=0;i<productCreated.subCategory.size();i++)
        {
            subCategoriesForSolr.add(product.subCategory.get(i).getSubcategoryName());
        }
        categorySolr.add(productCreated.subCategory.get(0).category.getCategoryName());
        brandSolr.add(productCreated.brand.getBrandName());

        /*Api Call from Solr Post*/
        String postUrl="http://localhost:8080/addProductsToSearch";

        SolrProduct solrProduct=new SolrProduct();
        //Setting up solr member variables
        solrProduct.setProductId(productCreated.getProductId());
        solrProduct.setProductName(productCreated.getProductName());
        solrProduct.setDescription(productCreated.getDescription());
        solrProduct.setUsp(productCreated.getUsp());
        solrProduct.setProductImage(productCreated.getProductImage());
        solrProduct.setSubCategories(subCategoriesForSolr);
        solrProduct.setCategory(categorySolr);
        solrProduct.setBrand(brandSolr);

        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<String> postResponse=restTemplate.postForEntity(postUrl,solrProduct,String.class);

        return new ResponseEntity<String>(productCreated.getProductName(),HttpStatus.CREATED);
    }

    @RequestMapping(value = BASE_URL+"/allProducts",method = RequestMethod.GET)
    public List<Product> getProducts()
    {
        return productService.getAllProducts();
    }

    @RequestMapping(value = BASE_URL+"/deleteProduct/{id}",method = RequestMethod.GET)
    public String deleteProduct(@PathVariable String id)
    {
        productService.deleteProduct(id);
        return "Deleted";
    }
    @RequestMapping(value = BASE_URL+"/getProduct/{id}",method = RequestMethod.GET)
    public Product getProduct(@PathVariable String id)
    {

        return productService.findProduct(id);
    }

    @RequestMapping(value = BASE_URL+"/updateProduct/{id}",method = RequestMethod.PUT)
    public Product updateProduct(@PathVariable String id, @RequestParam String comment,@RequestParam int userRating)
    {
            Product product=productService.findOneProduct(id);
            List<UserReview> allUserReviews;
           if(product.getUserReviews()==null) {
                allUserReviews = new ArrayList<>();
           }
           else
           {
                allUserReviews=product.getUserReviews();
           }
        UserReview userReview=new UserReview();
        userReview.setProductId(id);
        userReview.setUserComment(comment);
        userReview.setUserRatingOnProduct(userRating);
        allUserReviews.add(userReview);
        product.setUserReviews(allUserReviews);
        return productService.update(product);
    }

    @RequestMapping(value = BASE_URL+"/productByName/{productName}",method = RequestMethod.GET)
    public List<Product> findByName(@PathVariable String productName)
    {
        return productService.findByProductName(productName);
    }
//
    @RequestMapping(value = BASE_URL+"/getAllCategories",method = RequestMethod.GET)
    public List<Category> getCategories()
    {
        List<Product> productList=productService.findAllCategories();
        List<SubCategory> subCategories=new ArrayList<>();
        List<Category> categoriesList=new ArrayList<>();
        Set<String> categories=new HashSet<>();

        int count=0;
        System.out.println(productList.size());

        for(int i=0;i<productList.size();i++)
        {

            subCategories=productList.get(i).subCategory;
           // System.out.println(subCategories.size());
            for(int j=0;j<subCategories.size();j++)
            {
                if(!(categories.contains(subCategories.get(j).getCategory().getCategoryName())))
                {
                    categoriesList.add(subCategories.get(j).getCategory());
                }
                categories.add(subCategories.get(j).getCategory().getCategoryName());
            }
            count++;
        }
        //System.out.println(count);
        return categoriesList;
    }

    @RequestMapping(value = BASE_URL+"/getSubCategoriesByCategory/{categoryName}",method = RequestMethod.GET)
    public List<SubCategory> getSubCategories(@PathVariable String categoryName)
    {
        int count=0;
        List<Product> productsByCategories=productService.findByCategoryName(categoryName);
        List<SubCategory> subCategories=new ArrayList<>();
        List<SubCategory> subCategorySet=new ArrayList<>();
        Set<String> subCategString=new HashSet<>();
        for(int i=0;i<productsByCategories.size();i++)
        {
            subCategories=productsByCategories.get(i).getSubCategory();
            for(int j=0;j<subCategories.size();j++)
            {
                if(subCategories.get(j).category.getCategoryName().equalsIgnoreCase(categoryName))
                {
                    if(!(subCategString.contains(subCategories.get(j).getSubcategoryName())))
                    {
                        subCategorySet.add(subCategories.get(j));
                    }
                    subCategString.add(subCategories.get(j).getSubcategoryName());


                }
            }
        }
        return subCategorySet;
    }

    @RequestMapping(value = BASE_URL+"/getAllProductsFromSubCategories/{subCategoryName}",method = RequestMethod.GET)
    public List<Product> getProductsBySubCategory(@PathVariable String subCategoryName)
    {

        List<Product> list=productService.findBySubCategory(subCategoryName);
        return productService.findBySubCategory(subCategoryName);
    }

    @RequestMapping(value = "/shortListing",method = RequestMethod.GET)
    public List<ShortListingDTO> shortListing(@RequestParam String subCategoryName)
    {
        RestTemplate restTemplate=new RestTemplate();
        String getUrl = "http://localhost:8003/"+BASE_URL+"/getAllProductsFromSubCategories/"+subCategoryName;

        ResponseEntity<List<Product>> responseEntity=restTemplate.exchange(getUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
        });

        List<Product> shortListings=responseEntity.getBody();
        List<ShortListingDTO> shortListingDTOS=new ArrayList<>();

        for(Product productShortList:shortListings)
        {
                RestTemplate restTemplate1=new RestTemplate();
                String getURLMerchant="http://10.177.7.120:8080/getMerchantFromProductId/"+productShortList.getProductId();
                ResponseEntity<List<MerchantDetailsDTO>> responseEntity1=restTemplate1.exchange(getURLMerchant, HttpMethod.GET, null, new ParameterizedTypeReference<List<MerchantDetailsDTO>>() {
                });
              List<MerchantDetailsDTO> merchantDetailsDTO=responseEntity1.getBody();
            for (int i=0;i<merchantDetailsDTO.size();i++)
            {
                                                                                  
                if (productShortList.getProductId().equalsIgnoreCase(merchantDetailsDTO.get(i).getProductId())) {
                                                        

                    ShortListingDTO shortListingDTO=new ShortListingDTO();

                    shortListingDTO.setProductName(productShortList.getProductName());
                    shortListingDTO.setProductId(merchantDetailsDTO.get(i).getProductId());
                    shortListingDTO.setDiscountedPrice(merchantDetailsDTO.get(i).getSalePrice());
                    shortListingDTO.setMarketRetailPrice(merchantDetailsDTO.get(i).getPrice());
                    shortListingDTO.setProductImage(productShortList.getProductImage());
                    shortListingDTOS.add(shortListingDTO);
                }
            }
        }

        return shortListingDTOS;
    }
//    @RequestMapping(value = BASE_URL+"/addReviewToProduct",method = RequestMethod.PUT)
//    public Product updateProductFromReview(@PathVariable ProductDTO productDTO)
//    {
//        return productService.update()
//    }
}
