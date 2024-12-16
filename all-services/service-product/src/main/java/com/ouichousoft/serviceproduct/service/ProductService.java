package com.ouichousoft.serviceproduct.service;

import com.ouichousoft.serviceproduct.model.Product;
import com.ouichousoft.serviceproduct.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public interface ProductService {
    public List<Product> getAllProducts() ;
    public Product getProductById(Long id) ;
    public Product createProduct(Product product) ;
    public Product updateProduct(Long id, Product updatedProduct) ;
    public void deleteProduct(Long id) ;
}