package com.example.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.example.entity.ProductEntity;
import com.example.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository pRepository;

    @Autowired
    EntityManagerFactory emf;

    @Override
    public int insertBatch(List<ProductEntity> list) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            for (ProductEntity item : list) {
                em.persist(item);
            }
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return 0;
        }

    }

}
