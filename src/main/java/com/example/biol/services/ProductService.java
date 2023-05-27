package com.example.biol.services;

import com.example.biol.models.Image;
import com.example.biol.models.Product;
import com.example.biol.models.User;
import com.example.biol.repositories.ProductRepository;
import com.example.biol.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    @Value("${upload.path}")
    private String uploadPath;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void saveFile(MultipartFile file, Image image) throws IOException {
        if (!file.isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            image.setFilePath(uploadPath + "/" + resultFilename);
        }
    }

    public List<Product> FindProducts(String title, String city) {
        if (StringUtils.hasText(title) && StringUtils.hasText(city)) {
            return productRepository.findByTitleAndCity(title, city);
        }

        if (StringUtils.hasText(title)) {
            return productRepository.findByTitle(title);
        }

        if (StringUtils.hasText(city)) {
            return productRepository.findByCity(city);
        }

        return productRepository.findAll();
    }


    public void saveProduct(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws Exception {
        product.setUser(getUserByPrincipal(principal));
        MultipartFile[] files = {file1, file2, file3};

        for (int i = 0; i < files.length; i++) {
            MultipartFile file = files[i];
            if (file.getSize() != 0) {
                Image image = toImageEntity(file);
                if (i == 0) {
                    image.setPreviewImage(true);
                }
                saveFile(file, image);
                product.addImageToProduct(image);
            }
        }

        log.info("Saving new Product. Title: {}; Author email: {}", product.getTitle(), product.getUser().getEmail());
        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImageList().get(0).getId());
        productRepository.save(product);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByUsername(principal.getName());
    }

    private Image toImageEntity(MultipartFile file) throws Exception{
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        return image;
    }

    public void deleteProduct(User user, Long id) {
        Product product = productRepository.findById(id)
                .orElse(null);
        if (product != null) {
            if (product.getUser().getId().equals(user.getId())) {
                productRepository.delete(product);
                log.info("Product with id = {} was deleted", id);
            } else {
                log.error("User: {} haven't this product with id = {}", user.getEmail(), id);
            }
        } else {
            log.error("Product with id = {} is not found", id);
        }
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

}
