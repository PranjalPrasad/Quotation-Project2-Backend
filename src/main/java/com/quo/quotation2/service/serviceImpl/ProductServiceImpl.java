package com.quo.quotation2.service.serviceImpl;
import com.quo.quotation2.dto.requestdto.ProductRequestDto;
import com.quo.quotation2.dto.responsedto.ProductResponseDto;
import com.quo.quotation2.entity.ProductEntity;
import com.quo.quotation2.exception.ProductNotFoundException;
import com.quo.quotation2.repository.ProductRepository;
import com.quo.quotation2.service.ProductService;
import com.quo.quotation2.exception.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private static final long MAX_FILE_SIZE = 1024 * 1024;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        if (productRepository.existsBySku(requestDto.getSku())) {
            throw new BadRequestException("Product with SKU " + requestDto.getSku() + " already exists");
        }

        ProductEntity product = new ProductEntity();
        mapDtoToEntity(requestDto, product);
        handleImageUploads(requestDto, product);

        ProductEntity savedProduct = productRepository.save(product);
        return convertToResponseDto(savedProduct);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto requestDto) {
        ProductEntity existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        if (!existingProduct.getSku().equals(requestDto.getSku()) &&
                productRepository.existsBySku(requestDto.getSku())) {
            throw new BadRequestException("Product with SKU " + requestDto.getSku() + " already exists");
        }

        mapDtoToEntity(requestDto, existingProduct);
        handleImageUploads(requestDto, existingProduct);

        ProductEntity updatedProduct = productRepository.save(existingProduct);
        return convertToResponseDto(updatedProduct);
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return convertToResponseDto(product);
    }

    @Override
    public ProductResponseDto getProductBySku(String sku) {
        ProductEntity product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with SKU: " + sku));
        return convertToResponseDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<ProductEntity> products = productRepository.findAll();
        return products.stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> getProductsByCategory(String category) {
        List<ProductEntity> products = productRepository.findByCategory(category);
        return products.stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> getLowStockProducts() {
        List<ProductEntity> products = productRepository.findLowStockProducts();
        return products.stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> searchProducts(String keyword) {
        List<ProductEntity> products = productRepository.searchByKeyword(keyword);
        return products.stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        productRepository.delete(product);
    }

    @Override
    public ProductResponseDto updateProductStatus(Long id, String status) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        product.setStatus(status);
        ProductEntity updatedProduct = productRepository.save(product);
        return convertToResponseDto(updatedProduct);
    }

    private void mapDtoToEntity(ProductRequestDto dto, ProductEntity entity) {
        entity.setName(dto.getName());
        entity.setSku(dto.getSku());
        entity.setModelCode(dto.getModelCode());
        entity.setBrand(dto.getBrand());
        entity.setType(dto.getType());
        entity.setCategory(dto.getCategory());
        entity.setSubCategory(dto.getSubCategory());
        entity.setHsn(dto.getHsn());
        entity.setGst(dto.getGst());
        entity.setMrp(dto.getMrp());
        entity.setDiscountType(dto.getDiscountType());
        entity.setDiscountValue(dto.getDiscountValue());
        entity.setCalculatedPrice(dto.getCalculatedPrice());
        entity.setFinalPrice(dto.getFinalPrice());
        entity.setStock(dto.getStock());
        entity.setThreshold(dto.getThreshold());
        entity.setReorderQuantity(dto.getReorderQuantity());
        entity.setLeadTimeDays(dto.getLeadTimeDays());
        entity.setStatus(dto.getStatus());
        entity.setPowerConsumptionKw(dto.getPowerConsumptionKw());
        entity.setWeightKg(dto.getWeightKg());

        if (dto.getDimensions() != null) {
            entity.setLengthCm(dto.getDimensions().get("lengthCm"));
            entity.setWidthCm(dto.getDimensions().get("widthCm"));
            entity.setHeightCm(dto.getDimensions().get("heightCm"));
        }

        if (dto.getWarranty() != null) {
            entity.setWarrantyPeriodYears((Integer) dto.getWarranty().get("periodYears"));
            entity.setWarrantyType((String) dto.getWarranty().get("type"));
            entity.setWarrantyPartsCovered((String) dto.getWarranty().get("partsCovered"));
        }

        // ===== FIX START =====
        // Previously this block built a brand-new ArrayList and called
        // entity.setFeatures(features), which replaces the Hibernate-managed
        // collection on `existingProduct` during updateProduct(). Because
        // `features` has orphanRemoval = true, Hibernate loses track of the
        // original list it was managing and throws:
        //   "A collection with orphan deletion was no longer referenced by
        //    the owning entity instance: ProductEntity.features"
        //
        // Fix: clear the SAME collection instance that's already on the
        // entity, then add the new items into it. This keeps Hibernate's
        // internal tracking intact for both create (where the list starts
        // empty anyway) and update (where it's the real managed list).
        if (dto.getFeatures() != null) {
            entity.getFeatures().clear();
            for (Map<String, String> featureMap : dto.getFeatures()) {
                ProductEntity.Feature feature = new ProductEntity.Feature();
                feature.setFeatureId(featureMap.get("id"));
                feature.setLabel(featureMap.get("label"));
                feature.setValue(featureMap.get("value"));
                feature.setProduct(entity);
                entity.getFeatures().add(feature);
            }
        } else {
            entity.getFeatures().clear();
        }
        // ===== FIX END =====

        entity.setDescription(dto.getDescription());
    }

    private void handleImageUploads(ProductRequestDto dto, ProductEntity entity) {
        if (dto.getThumbnailFile() != null && !dto.getThumbnailFile().isEmpty()) {
            try {
                validateFileSize(dto.getThumbnailFile());
                validateImageType(dto.getThumbnailFile());
                entity.setThumbnailImage(dto.getThumbnailFile().getBytes());
            } catch (IOException e) {
                throw new BadRequestException("Failed to process thumbnail: " + e.getMessage());
            }
        }

        if (dto.getGalleryFiles() != null && !dto.getGalleryFiles().isEmpty()) {
            List<byte[]> galleryBytes = new ArrayList<>();
            for (MultipartFile file : dto.getGalleryFiles()) {
                if (!file.isEmpty()) {
                    try {
                        validateFileSize(file);
                        validateImageType(file);
                        galleryBytes.add(file.getBytes());
                    } catch (IOException e) {
                        throw new BadRequestException("Failed to process gallery image: " + e.getMessage());
                    }
                }
            }
            entity.setGalleryImages(galleryBytes);
        }

        if (dto.getBrochurePdfFile() != null && !dto.getBrochurePdfFile().isEmpty()) {
            try {
                validateFileSize(dto.getBrochurePdfFile());
                validatePdfType(dto.getBrochurePdfFile());
                entity.setBrochurePdf(dto.getBrochurePdfFile().getBytes());
            } catch (IOException e) {
                throw new BadRequestException("Failed to process PDF: " + e.getMessage());
            }
        }
    }

    private void validateFileSize(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BadRequestException("File exceeds 1MB: " + (file.getSize() / 1024) + "KB");
        }
    }

    private void validateImageType(MultipartFile file) {
        String ct = file.getContentType();
        if (ct == null || !(ct.equals("image/jpeg") || ct.equals("image/png") || ct.equals("image/jpg") || ct.equals("image/webp"))) {
            throw new BadRequestException("Invalid image format. Allowed: JPEG, PNG, JPG, WEBP");
        }
    }

    private void validatePdfType(MultipartFile file) {
        String ct = file.getContentType();
        if (ct == null || !ct.equals("application/pdf")) {
            throw new BadRequestException("Invalid file format. Only PDF allowed");
        }
    }

    private ProductResponseDto convertToResponseDto(ProductEntity entity) {
        ProductResponseDto dto = new ProductResponseDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSku(entity.getSku());
        dto.setModelCode(entity.getModelCode());
        dto.setBrand(entity.getBrand());
        dto.setType(entity.getType());
        dto.setCategory(entity.getCategory());
        dto.setSubCategory(entity.getSubCategory());
        dto.setHsn(entity.getHsn());
        dto.setGst(entity.getGst());
        dto.setMrp(entity.getMrp());
        dto.setDiscountType(entity.getDiscountType());
        dto.setDiscountValue(entity.getDiscountValue());
        dto.setCalculatedPrice(entity.getCalculatedPrice());
        dto.setFinalPrice(entity.getFinalPrice());
        dto.setStock(entity.getStock());
        dto.setThreshold(entity.getThreshold());
        dto.setReorderQuantity(entity.getReorderQuantity());
        dto.setLeadTimeDays(entity.getLeadTimeDays());
        dto.setStatus(entity.getStatus());
        dto.setPowerConsumptionKw(entity.getPowerConsumptionKw());
        dto.setWeightKg(entity.getWeightKg());

        Map<String, Double> dims = new HashMap<>();
        dims.put("lengthCm", entity.getLengthCm());
        dims.put("widthCm", entity.getWidthCm());
        dims.put("heightCm", entity.getHeightCm());
        dto.setDimensions(dims);

        Map<String, Object> warranty = new HashMap<>();
        warranty.put("periodYears", entity.getWarrantyPeriodYears());
        warranty.put("type", entity.getWarrantyType());
        warranty.put("partsCovered", entity.getWarrantyPartsCovered());
        dto.setWarranty(warranty);

        if (entity.getFeatures() != null) {
            List<Map<String, String>> features = new ArrayList<>();
            for (ProductEntity.Feature f : entity.getFeatures()) {
                Map<String, String> map = new HashMap<>();
                map.put("id", f.getFeatureId());
                map.put("label", f.getLabel());
                map.put("value", f.getValue());
                features.add(map);
            }
            dto.setFeatures(features);
        }

        if (entity.getThumbnailImage() != null && entity.getThumbnailImage().length > 0) {
            dto.setThumbnail("data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(entity.getThumbnailImage()));
        }

        if (entity.getGalleryImages() != null && !entity.getGalleryImages().isEmpty()) {
            List<String> gallery = new ArrayList<>();
            for (byte[] img : entity.getGalleryImages()) {
                if (img != null && img.length > 0) {
                    gallery.add("data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(img));
                }
            }
            dto.setGallery(gallery);
        }

        if (entity.getBrochurePdf() != null && entity.getBrochurePdf().length > 0) {
            dto.setBrochurePdf("data:application/pdf;base64," + java.util.Base64.getEncoder().encodeToString(entity.getBrochurePdf()));
        }

        dto.setDescription(entity.getDescription());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }
}