package vn.fis.training.ordermanagement.service.impl;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import vn.fis.training.ordermanagement.exception.ProductNotFoundException;
import vn.fis.training.ordermanagement.model.Product;
import vn.fis.training.ordermanagement.repository.ProductRepository;
import vn.fis.training.ordermanagement.service.ProductService;
@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product findById(Long id) {
        Product product= productRepository.findById(id).get();
        if(product==null)
        try {
            throw new ProductNotFoundException("product not found with ");
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }
}
