package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.dtos.ProductModel.ProductDTO;
import com.cookswp.milkstore.pojo.entities.Product;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.firebase.FirebaseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/firebase")
public class FirebaseController {

    private final FirebaseService firebaseService;

    public FirebaseController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @PostMapping("/")
    public ResponseData<?> createProduct(@RequestParam("productImage") MultipartFile productImage) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "New Milk Product create successfully", firebaseService.upload(productImage));
    }
}
