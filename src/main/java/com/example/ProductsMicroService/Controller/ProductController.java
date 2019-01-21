package com.example.ProductsMicroService.Controller;

import com.example.ProductsMicroService.Entity.Category;
import com.example.ProductsMicroService.Entity.Product;
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
    public Set<String> getCategories()
    {
        List<Product> productList=productService.findAllCategories();
        List<SubCategory> subCategories=new ArrayList<>();
        Set<String> categories=new HashSet<>();


        for(int i=0;i<productList.size();i++)
        {
            subCategories=productList.get(i).subCategory;
            for(int j=0;j<subCategories.size();j++)
            {
                categories.add(subCategories.get(j).getCategory().getCategoryName());
            }

        }

        return categories;
    }

    @RequestMapping(value = BASE_URL+"/getSubCategoriesByCategory/{categoryName}",method = RequestMethod.GET)
    public Set<String> getSubCategories(@PathVariable String categoryName)
    {
        List<Product> productsByCategories=productService.findByCategoryName(categoryName);
        List<SubCategory> subCategories=new ArrayList<>();
        Set<String> subCategString=new HashSet<>();
        for(int i=0;i<productsByCategories.size();i++)
        {
            subCategories=productsByCategories.get(i).getSubCategory();
            for(int j=0;j<subCategories.size();j++)
            {
                if(subCategories.get(j).category.getCategoryName().equalsIgnoreCase(categoryName))
                {
                    subCategString.add(subCategories.get(j).getSubcategoryName());
                }
            }
        }
        return subCategString;
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
