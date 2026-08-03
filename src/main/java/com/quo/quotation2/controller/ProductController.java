package com.quo.quotation2.controller;

import com.quo.quotation2.dto.requestdto.ProductRequestDto;
import com.quo.quotation2.dto.responsedto.ApiResponseDto;
import com.quo.quotation2.dto.responsedto.ProductResponseDto;
import com.quo.quotation2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    // ✅ @PostMapping ki jagah @RequestMapping use kiya
    @RequestMapping(value = "/create-product", method = RequestMethod.POST)
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> createProduct(
            @RequestPart("product") ProductRequestDto productRequestDto,
            @RequestPart(value = "thumbnailFile", required = false) MultipartFile thumbnailFile,
            @RequestPart(value = "galleryFiles", required = false) List<MultipartFile> galleryFiles,
            @RequestPart(value = "brochurePdfFile", required = false) MultipartFile brochurePdfFile) {

        productRequestDto.setThumbnailFile(thumbnailFile);
        productRequestDto.setGalleryFiles(galleryFiles);
        productRequestDto.setBrochurePdfFile(brochurePdfFile);

        ProductResponseDto response = productService.createProduct(productRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Product created successfully", response));
    }

    @GetMapping("/products")
    public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getAllProducts() {
        List<ProductResponseDto> responses = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponseDto.success("Products fetched successfully", responses));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> getProductById(@PathVariable Long id) {
        ProductResponseDto response = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponseDto.success("Product fetched successfully", response));
    }

    @GetMapping("/products/sku/{sku}")
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> getProductBySku(@PathVariable String sku) {
        ProductResponseDto response = productService.getProductBySku(sku);
        return ResponseEntity.ok(ApiResponseDto.success("Product fetched successfully", response));
    }

    @GetMapping("/products/category/{category}")
    public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getProductsByCategory(@PathVariable String category) {
        List<ProductResponseDto> responses = productService.getProductsByCategory(category);
        return ResponseEntity.ok(ApiResponseDto.success("Products fetched successfully", responses));
    }

    @GetMapping("/products/low-stock")
    public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getLowStockProducts() {
        List<ProductResponseDto> responses = productService.getLowStockProducts();
        return ResponseEntity.ok(ApiResponseDto.success("Low stock products fetched successfully", responses));
    }

    @GetMapping("/products/search")
    public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> searchProducts(@RequestParam String keyword) {
        List<ProductResponseDto> responses = productService.searchProducts(keyword);
        return ResponseEntity.ok(ApiResponseDto.success("Search results fetched successfully", responses));
    }

    // ✅ PUT bhi change karo
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") ProductRequestDto productRequestDto,
            @RequestPart(value = "thumbnailFile", required = false) MultipartFile thumbnailFile,
            @RequestPart(value = "galleryFiles", required = false) List<MultipartFile> galleryFiles,
            @RequestPart(value = "brochurePdfFile", required = false) MultipartFile brochurePdfFile) {

        productRequestDto.setThumbnailFile(thumbnailFile);
        productRequestDto.setGalleryFiles(galleryFiles);
        productRequestDto.setBrochurePdfFile(brochurePdfFile);

        ProductResponseDto response = productService.updateProduct(id, productRequestDto);
        return ResponseEntity.ok(ApiResponseDto.success("Product updated successfully", response));
    }

    @PatchMapping("/products/{id}/status")
    public ResponseEntity<ApiResponseDto<ProductResponseDto>> updateProductStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        ProductResponseDto response = productService.updateProductStatus(id, status);
        return ResponseEntity.ok(ApiResponseDto.success("Product status updated successfully", response));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponseDto.success("Product deleted successfully", null));
    }
}
