package com.quo.quotation2.service.serviceImpl;

import com.quo.quotation2.dto.requestdto.ProductDto;
import com.quo.quotation2.entity.Product;
import com.quo.quotation2.exception.ProductNotFoundException;
import com.quo.quotation2.repository.ProductRepository;
import com.quo.quotation2.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(ProductDto productDto, MultipartFile imageFile) throws IOException {
        Product product = new Product();
        product.setProductCode(productDto.getProductCode());
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setSectionCode(productDto.getSectionCode());
        product.setHsnCode(productDto.getHsnCode());
        product.setGstRate(productDto.getGstRate());
        product.setPrice(productDto.getPrice());
        product.setPowerHP(productDto.getPowerHP());
        product.setPowerKW(productDto.getPowerKW());
        product.setShedSize(productDto.getShedSize());
        product.setLabor(productDto.getLabor());
        product.setProduction(productDto.getProduction());
        product.setPower(productDto.getPower());

        // ✅ Set image if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImage(imageFile.getBytes());
        }

        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, ProductDto productDto, MultipartFile imageFile) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        product.setProductCode(productDto.getProductCode());
        product.setName(productDto.getName());
        product.setCategory(productDto.getCategory());
        product.setSectionCode(productDto.getSectionCode());
        product.setHsnCode(productDto.getHsnCode());
        product.setGstRate(productDto.getGstRate());
        product.setPrice(productDto.getPrice());
        product.setPowerHP(productDto.getPowerHP());
        product.setPowerKW(productDto.getPowerKW());
        product.setShedSize(productDto.getShedSize());
        product.setLabor(productDto.getLabor());
        product.setProduction(productDto.getProduction());
        product.setPower(productDto.getPower());

        // ✅ Update image if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImage(imageFile.getBytes());
        }

        product.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findByDeletedAtIsNull();
    }

    @Override
    public byte[] getProductImage(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        if (product.getImage() == null) {
            throw new RuntimeException("No image found for product: " + id);
        }

        return product.getImage();
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + id));

        product.setDeletedAt(LocalDateTime.now());
        productRepository.save(product);
    }
}