package com.example.biol.services;

import com.example.biol.models.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private List<Product> productList = new ArrayList<>();
    private long ID = 0;
    {
        productList.add(new Product(++ID, "P", "r", 1, "d", "r"));
        productList.add(new Product(++ID, "P2", "r", 2, "d", "r"));

    }

    public List<Product> listProducts() { return productList; }

    public void saveProduct(Product product) {
        product.setId(++ID);
        productList.add(product);
    }

    public void deleteProduct(Long id) {
        productList.removeIf(product -> product.getId().equals(id));
    }

    public Product getProductById(Long id) {
        for (Product product : productList) {
            if (product.getId().equals(id)) return product;
        }
        return null;
    }
}
