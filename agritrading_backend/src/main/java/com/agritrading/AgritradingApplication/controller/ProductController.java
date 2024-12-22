package com.agritrading.AgritradingApplication.controller;

import com.agritrading.AgritradingApplication.dto.AddProductResponseDTO;
import com.agritrading.AgritradingApplication.dto.ProductDTO;
import com.agritrading.AgritradingApplication.dto.Response;
import com.agritrading.AgritradingApplication.model.Products;
import com.agritrading.AgritradingApplication.model.Users;
import com.agritrading.AgritradingApplication.repo.FarmersRepository;
import com.agritrading.AgritradingApplication.repo.ProductsRepository;
import com.agritrading.AgritradingApplication.repo.UserRepo;
import com.agritrading.AgritradingApplication.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/farmers")
public class ProductController {
    private final UserRepo userRepo;
    @Autowired
    private ProductsService productsService;
    @Autowired
    private FarmersRepository farmersRepository;
    @Autowired
    private ProductsRepository productsRepository;

    public ProductController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/product")
    public ResponseEntity<Response> addProducts(@RequestBody Products products , Authentication authentication) {
        String username = authentication.getName();

        Users user = userRepo.findByUsername(username);
        products.setFarmer(farmersRepository.findById(user.getFarmerId()).orElse(null));
        ProductDTO productResponse = productsService.addProduct(products);

        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Product created successfully")
                .product(productResponse).build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
    @GetMapping("/product/all")
    public ResponseEntity<Response> getProducts( Authentication authentication)  throws Exception {
        String username = authentication.getName();

        Users user = userRepo.findByUsername(username);
        int farmerId = user.getFarmerId();
        List<ProductDTO> productsList =  productsService.getAllProducts(farmerId);
        Response response = Response.builder()
                .status(HttpStatus.CREATED.value())
                .message("Product created successfully")
                .productList(productsList).build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/product/by-category")
    public ResponseEntity<Response> getProductsByCategory(@RequestParam("category") Optional<String> category,  Authentication authentication) throws Exception {
        String username = authentication.getName();
        Users user = userRepo.findByUsername(username);
        int farmerId = user.getFarmerId();
        if(category.isPresent()) {
            String categoryName = category.get();
            List<ProductDTO> productsList =  productsService.getProductsByCategory(categoryName , farmerId);
            Response response = Response.builder()
                    .status(HttpStatus.CREATED.value())
                    .message("Product created successfully")
                    .productList(productsList).build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        }
        throw new Exception();
    }
    @GetMapping("/product")
    public ResponseEntity<Response> getProducts(@RequestParam("id") Optional<Integer> id ,  Authentication authentication) throws Exception {
        if(id.isPresent()) {
            ProductDTO productResponse =  productsService.getProductById(id.get());
            Response response = Response.builder()
                    .status(HttpStatus.CREATED.value())
                    .message("Product created successfully")
                    .product(productResponse).build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        else{
            throw new Exception();
        }
    }

    @PutMapping("/product")
    public ResponseEntity<Response> updateProduct(@RequestBody Products products , @RequestParam("id") int id, Authentication authentication) throws Exception {
        String username = authentication.getName();
        Users user = userRepo.findByUsername(username);
        int farmerId = user.getFarmerId();

        if(productsRepository.getById(id).getFarmer().getFarmerId()==farmerId) {
            productsRepository.deleteById(id);
            products.setFarmer(farmersRepository.findById(farmerId).orElse(null));
            ProductDTO productResponse =  productsService.addProduct(products);

            Response response = Response.builder()
                    .status(HttpStatus.CREATED.value())
                    .message("Order created successfully")
                    .product(productResponse).build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        throw  new Exception("Not Allowed to delete the product");
    }

}
