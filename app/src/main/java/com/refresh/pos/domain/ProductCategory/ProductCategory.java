package com.refresh.pos.domain.ProductCategory;

import com.refresh.pos.techicalservices.CategoryProduct.CateProductDao;
import com.refresh.pos.techicalservices.NoDaoSetException;

/**
 * Created by TOUCH on 1/24/2019.
 */

public class ProductCategory {
    private CateProductCatolog cateProductCatolog;
    private static ProductCategory instance = null;
    private static CateProductDao cateProductDao = null;

    private ProductCategory() throws NoDaoSetException {
        if (!isDaoSet()) {
            throw new NoDaoSetException();
        }

        cateProductCatolog = new CateProductCatolog(cateProductDao);
    }

    /**
     * Determines whether the DAO already set or not.
     * @return true if the DAO already set; otherwise false.
     */
    public static boolean isDaoSet() {
        return cateProductDao != null;
    }

    /**
     * Sets the database connector.
     * @param dao Data Access Object of inventory.
     */
    public static void setCateProductDao(CateProductDao dao) {
        cateProductDao = dao;
    }

    /**
     * Returns product catalog using in this inventory.
     * @return product catalog using in this inventory.
     */
    public CateProductCatolog getCateProductCatalog() {
        return cateProductCatolog;
    }

    /**
     * Returns stock using in this inventory.
     * @return stock using in this inventory.
     */

    /**
     * Returns the instance of this singleton class.
     * @return instance of this class.
     * @throws NoDaoSetException if DAO was not set.
     */
    public static ProductCategory getInstance() throws NoDaoSetException {
        if (instance == null)
            instance = new ProductCategory();
        return instance;
    }
}
