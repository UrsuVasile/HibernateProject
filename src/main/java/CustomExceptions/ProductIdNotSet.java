package CustomExceptions;

public class ProductIdNotSet extends RuntimeException {

    public ProductIdNotSet(){
        super("ID is not set fot this product");
    }
}
