package com.ouichousoft.serviceproduct.repository;

import com.ouichousoft.serviceproduct.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
