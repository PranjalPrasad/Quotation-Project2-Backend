package com.quo.quotation2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quo.quotation2.dto.requestdto.ProductRequestDto;
import com.quo.quotation2.dto.responsedto.ApiResponseDto;
import com.quo.quotation2.dto.responsedto.ProductResponseDto;
import com.quo.quotation2.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping(value = "/create-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> createProduct(
            @RequestPart("product") String productJson,
            @RequestPart(value = "thumbnailFile", required = false) MultipartFile thumbnailFile,
            @RequestPart(value = "galleryFiles", required = false) List<MultipartFile> galleryFiles,
            @RequestPart(value = "brochurePdfFile", required = false) MultipartFile brochurePdfFile) {

        try {
            log.info("Received product JSON: {}", productJson);

            ProductRequestDto productRequestDto = objectMapper.readValue(productJson, ProductRequestDto.class);

            productRequestDto.setThumbnailFile(thumbnailFile);
            productRequestDto.setGalleryFiles(galleryFiles);
            productRequestDto.setBrochurePdfFile(brochurePdfFile);

            ProductResponseDto response = productService.createProduct(productRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponseDto.success("Product created successfully", response));
        } catch (Exception e) {
            log.error("Error creating product: ", e);
            return ResponseEntity.badRequest()
                    .body(ApiResponseDto.error("Failed to create product: " + e.getMessage(), null));
        }
    }

    @GetMapping("/products/get-all-products")
    public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getAllProducts() {
        List<ProductResponseDto> responses = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponseDto.success("Products fetched successfully", responses));
    }

    @GetMapping("/products/get-product-by-id/{id}")
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> getProductById(@PathVariable Long id) {
        ProductResponseDto response = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponseDto.success("Product fetched successfully", response));
    }

    @GetMapping("/products/get-product-by-sku/sku/{sku}")
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> getProductBySku(@PathVariable String sku) {
        ProductResponseDto response = productService.getProductBySku(sku);
        return ResponseEntity.ok(ApiResponseDto.success("Product fetched successfully", response));
    }

    @GetMapping("/products/category/get-products-by-category/{category}")
    public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getProductsByCategory(@PathVariable String category) {
        List<ProductResponseDto> responses = productService.getProductsByCategory(category);
        return ResponseEntity.ok(ApiResponseDto.success("Products fetched successfully", responses));
    }

    @GetMapping("/products/low-stock/get-low-stock-products")
    public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getLowStockProducts() {
        List<ProductResponseDto> responses = productService.getLowStockProducts();
        return ResponseEntity.ok(ApiResponseDto.success("Low stock products fetched successfully", responses));
    }

    @GetMapping("/products/search/search-products")
    public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> searchProducts(@RequestParam String keyword) {
        List<ProductResponseDto> responses = productService.searchProducts(keyword);
        return ResponseEntity.ok(ApiResponseDto.success("Search results fetched successfully", responses));
    }

    @PutMapping(value = "/products/update-product/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") String productJson,
            @RequestPart(value = "thumbnailFile", required = false) MultipartFile thumbnailFile,
            @RequestPart(value = "galleryFiles", required = false) List<MultipartFile> galleryFiles,
            @RequestPart(value = "brochurePdfFile", required = false) MultipartFile brochurePdfFile) {

        try {
            log.info("Received update product JSON: {}", productJson);

            ProductRequestDto productRequestDto = objectMapper.readValue(productJson, ProductRequestDto.class);

            productRequestDto.setThumbnailFile(thumbnailFile);
            productRequestDto.setGalleryFiles(galleryFiles);
            productRequestDto.setBrochurePdfFile(brochurePdfFile);

            ProductResponseDto response = productService.updateProduct(id, productRequestDto);
            return ResponseEntity.ok(ApiResponseDto.success("Product updated successfully", response));
        } catch (Exception e) {
            log.error("Error updating product: ", e);
            return ResponseEntity.badRequest()
                    .body(ApiResponseDto.error("Failed to update product: " + e.getMessage(), null));
        }
    }

    @PatchMapping("/products/update-product-status/{id}/status")
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> updateProductStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        ProductResponseDto response = productService.updateProductStatus(id, status);
        return ResponseEntity.ok(ApiResponseDto.success("Product status updated successfully", response));
    }

    @DeleteMapping("/products/delete-product/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponseDto.success("Product deleted successfully", null));
    }
}