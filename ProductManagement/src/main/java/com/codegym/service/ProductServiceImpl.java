package com.codegym.service;
import com.codegym.model.Product;
import java.util.*;

public class ProductServiceImpl implements ProductService {
    private static final List<Product> products = new ArrayList<>();
    private static int nextId = 4;
    static {
        products.add(new Product(1,"iPhone 15",22000000,"Điện thoại Apple","Apple"));
        products.add(new Product(2,"Galaxy S24",18000000,"Điện thoại Samsung","Samsung"));
        products.add(new Product(3,"MacBook Air M2",27000000,"Laptop Apple","Apple"));
    }
    public List<Product> findAll(){return new ArrayList<>(products);}
    public Product findById(int id){for(Product p:products) if(p.getId()==id)return p; return null;}
    public void save(Product p){p.setId(nextId++); products.add(p);}
    public void update(int id, Product p){Product old=findById(id); if(old!=null){old.setName(p.getName());old.setPrice(p.getPrice());old.setDescription(p.getDescription());old.setManufacturer(p.getManufacturer());}}
    public void remove(int id){products.removeIf(p->p.getId()==id);}
    public List<Product> searchByName(String name){if(name==null||name.trim().isEmpty())return findAll(); List<Product> r=new ArrayList<>(); String q=name.toLowerCase(); for(Product p:products) if(p.getName().toLowerCase().contains(q))r.add(p); return r;}
}
