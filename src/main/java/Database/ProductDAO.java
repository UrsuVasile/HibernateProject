package Database;

import Entity.Product;

import java.util.Scanner;

public class ProductDAO extends DbInitializer {

    private Product product;

    public Product findProduct(int id) {
        openSessionAndTransaction();
        product = session.find(Product.class, id);
        closeSessionAndTransaction();
        return product;
    }

    public void insertProduct(Product product) {
        openSessionAndTransaction();
        session.persist(product);
        closeSessionAndTransaction();
    }

}
