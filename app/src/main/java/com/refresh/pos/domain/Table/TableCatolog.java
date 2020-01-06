package com.refresh.pos.domain.Table;

import com.refresh.pos.domain.ProductCategory.CategoryProduct;
import com.refresh.pos.techicalservices.CategoryProduct.CateProductDao;
import com.refresh.pos.techicalservices.Table.TableDao;

import java.util.List;

/**
 * Created by TOUCH on 1/24/2019.
 */

public class TableCatolog {

    private TableDao tableDao;

    public TableCatolog(TableDao tableDao) {
        this.tableDao = tableDao;
    }

    public List<Table_Detail> getAllTable() {
        return tableDao.getAllTable();
    }

    public boolean addTable(int sequence, String name, String gpk,String status,int sync, String staus_service) {
        Table_Detail table_detail = new Table_Detail(sequence,name,gpk,status,sync,staus_service);
        int id = tableDao.addTable(table_detail);
        return id != -1;
    }

    public void editTable_Detail (int id, String name){

        tableDao.editTable_Detail(id,name);
    }

    public List<Table_Detail> searchTable(String search) {
        return tableDao.searchTable(search);
    }

    public List<Table_Detail> getLastRow() {
        return tableDao.getLastRow();
    }

}
