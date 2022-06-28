package vn.fis.training.ordermanagement.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import vn.fis.training.ordermanagement.model.Order;
import vn.fis.training.ordermanagement.repository.CustomOrderRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.List;

public class CustomOrderRepositoryImpl implements CustomOrderRepository {
//    public CustomOrderRepositoryImpl(EntityManager entityManager, DataSource dataSource) {
//        this.entityManager =entityManager;
//        this.dataSource=dataSource;
//    }
//    @PersistenceContext
//    private EntityManager entityManager;
//    @Autowired
//    private DataSource dataSource;
//
//    @Override
//    public List<Order> findAllOrderUsingCustomRepository() {
//
//
//        return entityManager.createQuery("select o from Oder o").getResultList();
//    }
}
