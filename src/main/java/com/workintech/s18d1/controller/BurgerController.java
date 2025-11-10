package com.workintech.s18d1.controller;

import com.workintech.s18d1.dao.BurgerDao;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workintech/burgers")
public class BurgerController {

    private final BurgerDao burgerDao;

    @Autowired
    public BurgerController(BurgerDao burgerDao) {
        this.burgerDao = burgerDao;
    }

    // Tüm burgerleri listele
    @GetMapping
    public List<Burger> findAll() {
        return burgerDao.findAll();
    }

    // Id'ye göre burger getir
    @GetMapping("/{id}")
    public ResponseEntity<Burger> findById(@PathVariable int id) {
        Burger burger = burgerDao.findById(id);
        if (burger == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(burger, HttpStatus.OK);
    }

    // Yeni burger ekle
    @PostMapping
    public ResponseEntity<Burger> save(@RequestBody Burger burger) {
        Burger saved = burgerDao.save(burger);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // Mevcut burger güncelle
    @PutMapping("/{id}")
    public ResponseEntity<Burger> update(@PathVariable int id, @RequestBody Burger burger) {
        Burger existing = burgerDao.findById(id);
        if (existing == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        burger.setId(id);
        Burger updated = burgerDao.update(burger);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // Burger sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Burger burger = burgerDao.findById(id);
        if (burger == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        burgerDao.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Fiyatına göre listele
    @GetMapping("/findByPrice")
    public List<Burger> findByPrice(@RequestParam double price) {
        return burgerDao.findByPrice(price);
    }

    // BreadType’a göre filtrele
    @GetMapping("/findByBreadType")
    public List<Burger> findByBreadType(@RequestParam BreadType breadType) {
        return burgerDao.findByBreadType(breadType);
    }

    // İçeriğe göre filtrele
    @GetMapping("/findByContent")
    public List<Burger> findByContent(@RequestParam String content) {
        return burgerDao.findByContent(content);
    }
}
