package Database;

import CustomExceptions.ProductIdNotSet;
import Entity.Description;
import Entity.Product;

public class DescriptionDao extends DbInitializer {

    public void updateDescription(Description description) {
        openSessionAndTransaction();
        session.update(description);
        closeSessionAndTransaction();

    }
}
