package com.example.biol.services;

import com.example.biol.models.Image;
import com.example.biol.models.Product;
import com.example.biol.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public List<Product> listProducts(String title) {
        if (title != null && !title.isEmpty()) return productRepository.findByTitle(title);
        return productRepository.findAll();
    }

    public void saveProduct(Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws Exception {
        MultipartFile[] files = {file1, file2, file3};

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.getSize() != 0) {
                Image image = toImageEntity(file);
                if (i == 0) {
                    image.setPreviewImage(true);
                }
                product.addImageToProduct(image);
            }
        }

        log.info("Saving new Product. Tittle: {}; Author: {}", product.getTitle(), product.getAuthor());
        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImageList().get(0).getId());
        productRepository.save(product);
    }

    private Image toImageEntity(MultipartFile file) throws Exception{
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

}
