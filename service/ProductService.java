package com.ms.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ms.entity.Product;
import com.ms.exception.ProductNotFoundException;
import com.ms.exception.ProductAlreadyExistsException;
import com.ms.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;

    public Product createProduct(Product product) {
        Optional<Product> existingProduct = productRepo.findByName(product.getName());
        if (existingProduct.isPresent()) {
            throw new ProductAlreadyExistsException("Product with name '" + product.getName() + "' already exists");
        }
              
        return productRepo.save(product);  
    }
    
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
    
    public List<Product> getProductsWithLowStock() {
        return productRepo.findAll().stream()
                .filter(product -> product.getStockQuantity() < product.getLeastQuantityRequired())
                .collect(Collectors.toList());
    }
    
    public void deleteProductById(Long id) {
        if (!productRepo.existsById(id)) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        productRepo.deleteById(id);
    }
    
    public Product updateProductById(Long id, Product updatedProduct) {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        
        // Update the existing product with new values
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
        existingProduct.setLeastQuantityRequired(updatedProduct.getLeastQuantityRequired());
        existingProduct.setUnitPrice(updatedProduct.getUnitPrice());
        
        return productRepo.save(existingProduct);
    }
}