package com.example.ProductsMicroService.Controller;

import com.example.ProductsMicroService.Entity.Category;
import com.example.ProductsMicroService.Entity.Product;
import com.example.ProductsMicroService.Entity.SolrProduct;
import com.example.ProductsMicroService.Entity.SubCategory;
import com.example.ProductsMicroService.Services.ProductService;
import com.example.ProductsMicroService.dto.ProductDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        String categorySolr;
        String brandSolr;

        for(int i=0;i<productCreated.subCategory.size();i++)
        {
            subCategoriesForSolr.add(product.subCategory.get(i).getSubcategoryName());
        }
        categorySolr=productCreated.subCategory.get(0).category.getCategoryName();
        brandSolr=productCreated.brand.getBrandName();

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

    @RequestMapping(value = BASE_URL+"/updateProduct",method = RequestMethod.PUT)
    public Product updateProduct(@RequestBody Product product)
    {
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


        for(int i=0;i<productList.size();i++)
        {
            subCategories=productList.get(i).subCategory;
            for(int j=0;j<subCategories.size();j++)
            {
                if(!(categories.contains(subCategories.get(j).getCategory().getCategoryName())))
                {
                    categoriesList.add(subCategories.get(j).getCategory());
                }
                categories.add(subCategories.get(j).getCategory().getCategoryName());
            }

        }

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

    @RequestMapping(value = "/apiCall")
    private String getEmployees()
    {
        final String api="http://localhost:8003/products/allProducts";
        RestTemplate restTemplate=new RestTemplate();
        String list=restTemplate.getForObject(api,String.class);

        return list;

    }
}
