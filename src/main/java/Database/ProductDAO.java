package Database;

import CustomExceptions.ProductIdNotSet;
import Entity.Product;

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
        if(product.getId() != 0){
            openSessionAndTransaction();
            session.update(product);
            closeSessionAndTransaction();
        } else {
            throw new ProductIdNotSet();
        }

    }

    public void deleteProductById(int id){
        openSessionAndTransaction();
        product = session.find(Product.class,id);
        session.delete(product);
        closeSessionAndTransaction();
    }

}
