package com.ms.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.dto.ProductResponseDTO;
import com.ms.entity.Product;
import com.ms.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/newProduct")
    public ResponseEntity<Product> register(@RequestBody Product product) throws Exception {
    	
    	Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);  
    }
    
    @GetMapping("/allProducts")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        
        List<ProductResponseDTO> productDTOs = products.stream()
                .map(product -> new ProductResponseDTO(product.getName(), product.getUnitPrice()))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(productDTOs);
    }
    
    @GetMapping("/lowStock")
    public ResponseEntity<List<Product>> getProductsWithLowStock() {
        List<Product> productsWithLowStock = productService.getProductsWithLowStock();
        return ResponseEntity.ok(productsWithLowStock);
    }
    
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return ResponseEntity.ok("Product with id " + id + " deleted successfully");
    }
    
    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product updated = productService.updateProductById(id, updatedProduct);
        return ResponseEntity.ok(updated);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProductsInfo() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

}
