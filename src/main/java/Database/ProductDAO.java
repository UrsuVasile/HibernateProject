package Database;

import CustomExceptions.ProductIdNotSet;
import Entity.Description;
import Entity.Product;
import sun.security.krb5.internal.crypto.Des;

public class ProductDAO extends DbInitializer {

    private Product product;
    private Description description;

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

    public void updateProduct(Product product, Description description) {
        if (product.getId() != 0) {
            openSessionAndTransaction();
            session.update(product);
            session.update(description);
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
}
