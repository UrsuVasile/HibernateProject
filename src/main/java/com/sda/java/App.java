package com.sda.java;

import Database.ProductDAO;
import Entity.Product;
import Service.Service;

public class App {
    public static void main(String[] args) {
        Product product = new Product();
        product.setName("Ariel");

        ProductDAO productDAO = new ProductDAO();
        productDAO.insertProduct(product);

        Service service = new Service();
        service.action();
    }
}
