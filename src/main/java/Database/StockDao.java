package Database;

import Entity.Result;
import Entity.Stock;

import javax.persistence.Query;
import java.util.*;

public class StockDao extends DbInitializer {

    public long totalStock() {
        openSessionAndTransaction();
        Query query = session.createNamedQuery("total_stock");
        long total = (long) query.getSingleResult();
        closeSessionAndTransaction();
        return total;
    }

    public List<Result> stockForEachProduct() {
        openSessionAndTransaction();
        Query query = session.createNamedQuery("stock_products");
        List<Result> list = new LinkedList<>();
        list = query.getResultList();
        closeSessionAndTransaction();
        return list;
    }

    public List<Result> showProductsWithStockZero() {
        openSessionAndTransaction();
        Query query = session.createNamedQuery("stock_zero");
        List<Result> results = query.getResultList();
        closeSessionAndTransaction();
        return results;
    }

    public List<Result> displayProductsWithSufficientStock(){
        openSessionAndTransaction();
        Query query = session.createNamedQuery("sufficient_stock");
        List<Result> results = query.getResultList();
        closeSessionAndTransaction();
        return results;
    }

}
