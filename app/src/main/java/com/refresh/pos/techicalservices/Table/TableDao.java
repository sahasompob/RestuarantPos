package com.refresh.pos.techicalservices.Table;

import com.refresh.pos.domain.ProductCategory.CategoryProduct;
import com.refresh.pos.domain.Table.Table_Detail;
import com.refresh.pos.domain.inventory.ProductLot;

import java.util.List;

/**
 * Created by TOUCH on 1/24/2019.
 */

public interface TableDao {

    int addTable(Table_Detail table_detail);

    void editTable_Detail (int id, String name);

    /**
     * Adds ProductLot to inventory.
     * @param productLot the ProductLot to be added.
     * @return id of this ProductLot that assigned from database.
     */
    /**
     * Edits product.
     * @return true if product edits success ; otherwise false.
     */

    /**
     * Returns CategoryProduct from inventory finds by id.
     * @param id id of CategoryProduct.
     * @return CategoryProduct
     */
    Table_Detail getTable_DetailId(int id);

    List<Table_Detail> searchTable(String search);


    /**
     * Returns CategoryProduct
     * @param name name of CategoryProduct.
     * @return CategoryProduct
     */
    Table_Detail getTableByName(String name);

    /**
     * Returns list of all products in inventory.
     * @return list of all products in inventory.
     */

    List<Table_Detail> getAllTable();
    List<Table_Detail> getLastRow();

    /**
     * Returns list of product in inventory finds by name.
     * @param name name of product.
     * @return list of product in inventory finds by name.
     */
    List<Table_Detail> getTable_DetailByName(String name);

    /**
     * Search product from string in inventory.
     * @param search string for searching.
     * @return list of product.
     */


}

