package com.quo.quotation2.service;

import com.quo.quotation2.dto.requestdto.ProductDto;
import com.quo.quotation2.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    Product createProduct(ProductDto productDto, MultipartFile imageFile) throws IOException;
    Product updateProduct(Long id, ProductDto productDto, MultipartFile imageFile) throws IOException;
    Product getProduct(Long id);
    List<Product> getAllProducts();
    byte[] getProductImage(Long id);
    void deleteProduct(Long id);
}
