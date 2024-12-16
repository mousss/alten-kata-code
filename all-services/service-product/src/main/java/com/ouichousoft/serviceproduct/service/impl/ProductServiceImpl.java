package com.ouichousoft.serviceproduct.service.impl;

import com.ouichousoft.serviceproduct.model.Product;
import com.ouichousoft.serviceproduct.repository.ProductRepository;
import com.ouichousoft.serviceproduct.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Product createProduct(Product product) {
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        return repository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);
        updatedProduct.setId(id);
        updatedProduct.setCreatedAt(existingProduct.getCreatedAt());
        updatedProduct.setUpdatedAt(Instant.now());
        return repository.save(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}
