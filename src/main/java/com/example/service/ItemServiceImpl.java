package com.example.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.example.entity.ItemEntity;
import com.example.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository iRepository;

    @Autowired
    EntityManagerFactory emf;

    @Override
    public int insertItemBatch(List<ItemEntity> list) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            for (ItemEntity item : list) {
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

    @Override
    public List<ItemEntity> selectItemEntityIn(Long[] no) {
        return iRepository.findByIcodeIn(no);

    }

    @Override
    public int updateItemBatch(List<ItemEntity> list) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            for (ItemEntity item : list) {
                // 기본키를 이용해서 기존 데이터를 꺼냄
                // 그래야 바꾸지 않는 항목을 그대로 유지할 수 있음.
                ItemEntity oldItem = em.find(ItemEntity.class, item.getIcode());
                oldItem.setIname(item.getIname());
                oldItem.setIcontent(item.getIcontent());
                oldItem.setIprice(item.getIprice());
                oldItem.setIquantity(item.getIquantity());
                em.persist(oldItem);
            }
            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
            return 0;
        }
    }

    @Override
    public int deletItemBatch(Long[] no) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            for (Long tmp : no) {
                // 기본키를 이용해서 기존 데이터를 꺼냄
                ItemEntity oldItem = em.find(ItemEntity.class, tmp);
                em.remove(oldItem);
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
