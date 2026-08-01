package com.quo.quotation2.controller;

import com.quo.quotation2.dto.requestdto.ProductDto;
import com.quo.quotation2.dto.responsedto.ApiResponseDto;
import com.quo.quotation2.entity.Product;
import com.quo.quotation2.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ✅ CREATE Product with Image using RequestPart
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDto<Product>> createProduct(
            @RequestPart("product") ProductDto productDto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {

        Product product = productService.createProduct(productDto, imageFile);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Product created successfully", product));
    }

    // ✅ UPDATE Product with Image using RequestPart
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDto<Product>> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") ProductDto productDto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws IOException {

        Product product = productService.updateProduct(id, productDto, imageFile);
        return ResponseEntity.ok(ApiResponseDto.success("Product updated successfully", product));
    }

    // GET Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Product>> getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        return ResponseEntity.ok(ApiResponseDto.success("Product retrieved successfully", product));
    }

    // GET All Products
    @GetMapping
    public ResponseEntity<ApiResponseDto<List<Product>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponseDto.success("Products retrieved successfully", products));
    }

    // GET Product Image
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        byte[] image = productService.getProductImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    // DELETE Product (Soft Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponseDto.success("Product deleted successfully", null));
    }
}
