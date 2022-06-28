package vn.fis.training.ordermanagement.service;

import vn.fis.training.ordermanagement.model.Product;

public interface ProductService {
    Product findById(Long id);
    Product update(Product product);
}

