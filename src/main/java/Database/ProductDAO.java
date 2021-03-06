package Database;

import CustomExceptions.ProductIdNotSet;
import Entity.Description;
import Entity.Product;
import sun.security.krb5.internal.crypto.Des;

import javax.persistence.Query;
import java.util.List;

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

    public void updateProduct(Product product) {
        if (product.getId() != 0) {
            openSessionAndTransaction();
            session.update(product);
            closeSessionAndTransaction();
        } else {
            throw new ProductIdNotSet();
        }
    }

    public void deleteProductById(int id) {
        openSessionAndTransaction();
        product = session.find(Product.class, id);
        session.delete(product);
        closeSessionAndTransaction();
    }

    public List<Product> showAllProducts() {
        openSessionAndTransaction();
        Query query = session.createNamedQuery("show_all_products");
        List<Product> products = query.getResultList();
        closeSessionAndTransaction();
        return products;
    }
}
