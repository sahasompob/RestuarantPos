package com.refresh.pos.domain.Table;

import com.refresh.pos.domain.ProductCategory.CateProductCatolog;
import com.refresh.pos.techicalservices.CategoryProduct.CateProductDao;
import com.refresh.pos.techicalservices.NoDaoSetException;
import com.refresh.pos.techicalservices.Table.TableDao;

/**
 * Created by TOUCH on 1/24/2019.
 */

public class TableLedger {
    private TableCatolog tableCatolog;
    private static TableLedger instance = null;
    private static TableDao tableDao = null;

    private TableLedger() throws NoDaoSetException {
        if (!isDaoSet()) {
            throw new NoDaoSetException();
        }

        tableCatolog = new TableCatolog(tableDao);
    }

    /**
     * Determines whether the DAO already set or not.
     * @return true if the DAO already set; otherwise false.
     */
    public static boolean isDaoSet() {
        return tableDao != null;
    }

    /**
     * Sets the database connector.
     * @param dao Data Access Object of inventory.
     */
    public static void setTableDao(TableDao dao) {
        tableDao = dao;
    }

    /**
     * Returns product catalog using in this inventory.
     * @return product catalog using in this inventory.
     */
    public TableCatolog getTableCatalog() {
        return tableCatolog;
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
    public static TableLedger getInstance() throws NoDaoSetException {
        if (instance == null)
            instance = new TableLedger();
        return instance;
    }
}
