package com.agritrading.AgritradingApplication.controller;

import com.agritrading.AgritradingApplication.model.Products;
import com.agritrading.AgritradingApplication.model.Users;
import com.agritrading.AgritradingApplication.repo.UserRepo;
import com.agritrading.AgritradingApplication.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/farmers")
public class FarmersController{
    private final UserRepo userRepo;
    @Autowired
    private ProductsService productsService;
    public FarmersController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/product")
    public Products addProducts(@RequestBody Products products , Authentication authentication) {
        String username = authentication.getName();

        Users user = userRepo.findByUsername(username);
        products.setFarmer_id(user.getFarmerId());
        return productsService.addProduct(products);
    }
    @GetMapping("/product/all")
    public List<Products> getProducts( Authentication authentication)  throws Exception {
        String username = authentication.getName();

        Users user = userRepo.findByUsername(username);
        int farmerId = user.getFarmerId();
        return productsService.getAllProducts(farmerId);
    }

    @GetMapping("/product/by-category")
    public List<Products> getProductsByCategory(@RequestParam("category") Optional<String> category,  Authentication authentication) throws Exception {
        String username = authentication.getName();
        Users user = userRepo.findByUsername(username);
        int farmerId = user.getFarmerId();
        if(category.isPresent()) {
            String categoryName = category.get();
            return productsService.getProductsByCategory(categoryName , farmerId);
        }
        throw new Exception();
    }
    @GetMapping("/product")
    public Products getProducts(@RequestParam("id") Optional<Integer> id ,  Authentication authentication) throws Exception {
        if(id.isPresent()) {
            return productsService.getProductById(id.get());
        }
        else{
            throw new Exception();
        }
    }




}
