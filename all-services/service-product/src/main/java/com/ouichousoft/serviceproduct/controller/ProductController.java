package com.ouichousoft.serviceproduct.controller;

import com.ouichousoft.serviceproduct.model.Product;
import com.ouichousoft.serviceproduct.service.JwtService;
import com.ouichousoft.serviceproduct.service.ProductService;
import com.ouichousoft.serviceproduct.service.impl.ProductServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/products")
@Tag(name = "Product Management", description = "API pour gérer les produits")
public class ProductController {
    private final ProductService service;
    @Autowired
    private JwtService jwtService;
    public ProductController(ProductServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les produits", description = "Renvoie la liste de tous les produits.")
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un produit par ID", description = "Renvoie les détails d'un produit spécifique.")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un produit", description = "Ajoute un nouveau produit à la base de données.")
    public ResponseEntity<?> createProduct(@RequestHeader("Authorization") String token,@RequestBody Product product) {
        String email = jwtService.extractEmail(token.substring(7));
        if (!email.equals("admin@admin.com")) {
            return ResponseEntity.status(403).body("Access denied: Only admin can create products.");
        }
        return ResponseEntity.ok(service.createProduct(product));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Mettre à jour un produit", description = "Met à jour les détails d'un produit existant.")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return ResponseEntity.ok(service.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un produit", description = "Supprime un produit de la base de données.")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}