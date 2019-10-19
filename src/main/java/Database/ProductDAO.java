package Database;

import Entity.Product;

import java.util.Scanner;

public class ProductDAO extends DbInitializer {

    private Product product;

    public Product findProductById(int id) {
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

    public void updateProduct(Product product){
        openSessionAndTransaction();
        session.update(product);
        closeSessionAndTransaction();
    }

    public void deleteProductById(int id){
        openSessionAndTransaction();
        product = session.find(Product.class,id);
        session.delete(product);
        closeSessionAndTransaction();
    }

}
