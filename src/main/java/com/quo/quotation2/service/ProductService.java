package com.quo.quotation2.service;

import com.quo.quotation2.dto.requestdto.ProductRequestDto;
import com.quo.quotation2.dto.responsedto.ProductResponseDto;

import java.util.List;

public interface ProductService {

    ProductResponseDto createProduct(ProductRequestDto requestDto);

    ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto);

    ProductResponseDto getProductById(Long id);

    ProductResponseDto getProductBySku(String sku);

    List<ProductResponseDto> getAllProducts();

    List<ProductResponseDto> getProductsByCategory(String category);

    List<ProductResponseDto> getLowStockProducts();

    List<ProductResponseDto> searchProducts(String keyword);

    void deleteProduct(Long id);

    ProductResponseDto updateProductStatus(Long id, String status);
}