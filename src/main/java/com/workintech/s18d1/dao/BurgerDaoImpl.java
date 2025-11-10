package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.entity.BreadType;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BurgerDaoImpl implements BurgerDao {

    private final EntityManager entityManager;

    @Autowired
    public BurgerDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Burger save(Burger burger) {
        return entityManager.merge(burger);
    }

    @Override
    public Burger findById(int id) {
        return entityManager.find(Burger.class, id);
    }

    @Override
    public List<Burger> findAll() {
        return entityManager.createQuery("FROM Burger", Burger.class).getResultList();
    }

    @Override
    public List<Burger> findByPrice(double price) {
        return entityManager.createQuery(
                        "FROM Burger WHERE price > :price ORDER BY price DESC", Burger.class)
                .setParameter("price", price)
                .getResultList();
    }

    @Override
    public List<Burger> findByBreadType(BreadType breadType) {
        return entityManager.createQuery(
                        "FROM Burger WHERE breadType = :breadType ORDER BY name ASC", Burger.class)
                .setParameter("breadType", breadType)
                .getResultList();
    }

    @Override
    public List<Burger> findByContent(String content) {
        return entityManager.createQuery(
                        "FROM Burger WHERE contents LIKE CONCAT('%', :content, '%')", Burger.class)
                .setParameter("content", content)
                .getResultList();
    }

    @Override
    @Transactional
    public Burger update(Burger burger) {
        return entityManager.merge(burger);
    }

    @Override
    @Transactional
    public Burger remove(long id) {
        Burger burger = entityManager.find(Burger.class, id);
        if (burger != null) {
            entityManager.remove(burger);
        }
        return burger;
    }
}
