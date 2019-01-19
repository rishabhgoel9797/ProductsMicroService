package com.example.ProductsMicroService.Controller;

import com.example.ProductsMicroService.Entity.Product;
import com.example.ProductsMicroService.Services.ProductService;
import com.example.ProductsMicroService.dto.ProductDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
}
