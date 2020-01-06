package com.refresh.pos.techicalservices.Table;

import android.content.ContentValues;

import com.refresh.pos.domain.DateTimeStrategy;
import com.refresh.pos.domain.ProductCategory.CategoryProduct;
import com.refresh.pos.domain.Table.Table_Detail;
import com.refresh.pos.domain.inventory.ProductLot;
import com.refresh.pos.techicalservices.CategoryProduct.CateProductDao;
import com.refresh.pos.techicalservices.Database;
import com.refresh.pos.techicalservices.DatabaseContents;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TOUCH on 1/24/2019.
 */

public class TableDaoAndroid implements TableDao {

    public static final int  SYNC_STATUS_OK = 1;
    public static final int  SYNC_STATUS_FAILED = 0;
    public static final String SERVER_URL = "http://139.180.142.52:3000/api/table";
    public static final String SERVER_POST = "http://139.180.142.52:3000/api/table";
    public static final String SERVER_PUT = "http://139.180.142.52:3000/api/table";


    private Database database;

    public TableDaoAndroid(Database database) {
        this.database = database;
    }


    @Override
    public int addTable(Table_Detail table_detail) {
        ContentValues content = new ContentValues();
        content.put("sequence", table_detail.getSequence());
        content.put("table_name", table_detail.getName());
        content.put("gpk", table_detail.getGpk());
        content.put("status", table_detail.getStatus());
        content.put("sync", table_detail.getSync());
        content.put("status_service", table_detail.getStatusService());


        int id = database.insert(DatabaseContents.TABLE_TABLE_DETAIL.toString(), content);
        return id;
    }

    @Override
    public void editTable_Detail(int id,String name) {
        ContentValues content = new ContentValues();
        content.put("table_id", id);
        content.put("table_name", name);
        database.updateTable(DatabaseContents.TABLE_TABLE_DETAIL.toString(), content);
    }

    @Override
    public Table_Detail getTable_DetailId(int id) {
        return null;
    }

    private List<Table_Detail> toTableList(List<Object> objectList) {
        List<Table_Detail> list = new ArrayList<Table_Detail>();
        for (Object object: objectList) {
            ContentValues content = (ContentValues) object;
            list.add(new Table_Detail(
                    content.getAsInteger("table_id"),
                    content.getAsInteger("sequence"),
                    content.getAsString("table_name"),
                    content.getAsString("gpk"),
                    content.getAsString("status"),
                    content.getAsInteger("sync"),
                    content.getAsString("status_service")
                    ));
        }
        return list;
    }

    @Override
    public List<Table_Detail> getAllTable() {return getAllTable(" WHERE status = 'use'");
    }

    private List<Table_Detail> getAllTable(String condition) {
        String queryString = "SELECT * FROM " + DatabaseContents.TABLE_TABLE_DETAIL.toString() + condition + " ORDER BY table_id";
        List<Table_Detail> list = toTableList(database.select(queryString));
        return list;
    }


    @Override
    public List<Table_Detail> getTable_DetailByName(String name) {
        return null;
    }


    @Override
    public List<Table_Detail> searchTable(String search) {
        String condition = " WHERE name LIKE '%" + search + "%' OR type LIKE '%" + search + "%' ;";
        return getAllTable(condition);
    }

    @Override
    public Table_Detail getTableByName(String name) {
        return null;
    }

    @Override
    public List<Table_Detail>  getLastRow() {return getLastRow(" WHERE status = 'use'");

    }

    public List<Table_Detail> getLastRow(String condition) {
        String queryString = "SELECT * FROM " + DatabaseContents.TABLE_TABLE_DETAIL + " ORDER BY table_id DESC LIMIT 1 ";
        List<Object> objectList = database.select(queryString);
        List<Table_Detail> list = new ArrayList<Table_Detail>();
        for (Object object: objectList) {
            ContentValues content = (ContentValues) object;
            list.add(new Table_Detail(
                    content.getAsInteger("table_id"),
                    content.getAsInteger("sequence"),
                    content.getAsString("table_name"),
                    content.getAsString("gpk"),
                    content.getAsString("status"),
                    content.getAsInteger("sync"),
                    content.getAsString("status_service")
            ));
        }
        return list;

    }


}
