package com.rohit.ecom.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rohit.ecom.Repository.ProductRepo;
import com.rohit.ecom.model.Product;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageDate(imageFile.getBytes());
        return repo.save(product);
    }

    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        Product existingProduct = repo.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setCategory(product.getCategory());
            existingProduct.setProductAvailable(product.isProductAvailable());
            
            if (imageFile != null && !imageFile.isEmpty()) {
                existingProduct.setImageName(imageFile.getOriginalFilename());
                existingProduct.setImageType(imageFile.getContentType());
                existingProduct.setImageDate(imageFile.getBytes());
            }
            
            return repo.save(existingProduct);
        }
        return null;
    }

    public boolean deleteProduct(int id) {
        Product product = repo.findById(id).orElse(null);
        if (product != null) {
            repo.delete(product);
            return true;
        }
        return false;
    }
}