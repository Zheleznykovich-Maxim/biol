package com.example.biol.controllers;

import com.example.biol.models.Product;
import com.example.biol.models.User;
import com.example.biol.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Value("${upload.path}")
    private String uploadPath;
    @GetMapping("/")
    public String products(@RequestParam(name = "searchCity", required = false) String city,
            @RequestParam(name = "searchWord", required = false) String title,
                           Principal principal,
                           Model model, HttpSession session) {
        model.addAttribute("products", productService.FindProducts(title, city));
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("searchWord", title);
        model.addAttribute("searchCity", city);
        return "products";
    }

    @GetMapping("/product/{id}")
    public String productInfo(@PathVariable Long id, Model model, Principal principal) {
        Product product = productService.getProductById(id);
        model.addAttribute("user", productService.getUserByPrincipal(principal));
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImageList());
        model.addAttribute("authorProduct", product.getUser());
        return "product-info";
    }

    @PostMapping("/product/create")
    public String createProduct(@RequestParam("file1") MultipartFile file1,
                                @RequestParam("file2") MultipartFile file2,
                                @RequestParam("file3") MultipartFile file3,
                                Product product,
                                Principal principal) throws Exception {
//        ProductService.saveFile()
//
        productService.saveProduct(principal, product, file1, file2, file3);
        return "redirect:/my/products";
    }

    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Principal principal) {
        productService.deleteProduct(productService.getUserByPrincipal(principal), id);
        return "redirect:/my/products";
    }

    @GetMapping("/my/products")
    public String userProducts(Principal principal, Model model) {
        User user = productService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        model.addAttribute("products", user.getProductList());
        return "my-products";
    }
}
