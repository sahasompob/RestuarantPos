package com.refresh.pos.techicalservices.CategoryProduct;

import android.content.ContentValues;

import com.refresh.pos.domain.ProductCategory.CategoryProduct;
import com.refresh.pos.domain.inventory.ProductLot;
import com.refresh.pos.techicalservices.Database;
import com.refresh.pos.techicalservices.DatabaseContents;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TOUCH on 1/24/2019.
 */

public class CateProductDaoAndroid implements CateProductDao {
    public static final int  SYNC_STATUS_OK = 1;
    public static final int  SYNC_STATUS_FAILED = 0;
    public static final String SERVER_URL = "http://139.180.142.52:3000/api/categorys?token=";
    public static final String SERVER_URL_POST = "http://139.180.142.52:3000/api/category";
    public static final String SERVER_URL_PUT = "http://139.180.142.52:3000/api/category";

    private Database database;

    public CateProductDaoAndroid(Database database) {
        this.database = database;
    }


    @Override
    public int addCateProduct(CategoryProduct categoryProduct) {

        ContentValues content = new ContentValues();
        content.put("pk", categoryProduct.getGpk());
        content.put("sequence", categoryProduct.getSequence());
        content.put("parent_id", categoryProduct.getParent_id());
        content.put("name", categoryProduct.getName());
        content.put("sync", categoryProduct.getSyn());
        content.put("image", categoryProduct.getImage());
        content.put("status", categoryProduct.getStatus());
        content.put("type", categoryProduct.getType());

        int id = database.insert(DatabaseContents.TABLE_Category_Product.toString(), content);
        return id;
    }

    @Override
    public void editCategory(int id, String name, String image) {
        ContentValues content = new ContentValues();
        content.put("category_product_id", id);
        content.put("name", name);
        content.put("image", image);
        database.updateCategory(DatabaseContents.TABLE_Category_Product.toString(), content);
    }

    private List<CategoryProduct> toCategoryProductList(List<Object> objectList) {
        List<CategoryProduct> list = new ArrayList<CategoryProduct>();
        for (Object object: objectList) {
            ContentValues content = (ContentValues) object;
            list.add(new CategoryProduct(
                    content.getAsInteger("category_product_id"),
                    content.getAsString("pk"),
                    content.getAsInteger("sequence"),
                    content.getAsInteger("parent_id"),
                    content.getAsString("name"),
                    content.getAsInteger("sync"),
                    content.getAsString("image"),
                    content.getAsString("status"),
                    content.getAsString("type"))
            );
        }
        return list;
    }

    @Override
    public List<CategoryProduct> getAllCategoryProduct() {return getAllCategoryProduct(" WHERE status = 'use'");
    }

    private List<CategoryProduct> getAllCategoryProduct(String condition) {
        String queryString = "SELECT * FROM " + DatabaseContents.TABLE_Category_Product.toString() + condition + " ORDER BY category_product_id";
        List<CategoryProduct> list = toCategoryProductList(database.select(queryString));
        return list;
    }


    @Override
    public List<CategoryProduct>  getLastRow() {return getLastRow(" WHERE status = 'use'");

    }

    public List<CategoryProduct> getLastRow(String condition) {
        String queryString = "SELECT * FROM " + DatabaseContents.TABLE_Category_Product + " ORDER BY category_product_id DESC LIMIT 1 ";
        List<Object> objectList = database.select(queryString);
        List<CategoryProduct> list = new ArrayList<CategoryProduct>();
        for (Object object: objectList) {
            ContentValues content = (ContentValues) object;
            list.add(new CategoryProduct(
                    content.getAsInteger("category_product_id"),
                    content.getAsString("pk"),
                    content.getAsInteger("sequence"),
                    content.getAsInteger("parent_id"),
                    content.getAsString("name"),
                    content.getAsInteger("sync"),
                    content.getAsString("image"),
                    content.getAsString("status"),
                    content.getAsString("type"))
            );
        }
        return list;

    }


    @Override
    public int addProductLot(ProductLot productLot) {
        return 0;
    }

    @Override
    public boolean editCateProduct(CategoryProduct categoryProduct) {
        return false;
    }

    @Override
    public CategoryProduct getCateProductId(int id) {
        return null;
    }

    @Override
    public List<CategoryProduct> searchCateProduct(String search) {
        String condition = " WHERE name LIKE '%" + search + "%' OR type LIKE '%" + search + "%' ;";
        return getAllCategoryProduct(condition);
    }

    @Override
    public CategoryProduct getCateProductByName(String name) {
        return null;
    }


    @Override
    public List<CategoryProduct> getCategoryProductByName(String name) {
        return null;
    }
}
