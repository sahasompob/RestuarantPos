package com.refresh.pos.domain.ProductCategory;

import com.refresh.pos.techicalservices.CategoryProduct.CateProductDao;

import java.util.List;

/**
 * Created by TOUCH on 1/24/2019.
 */

public class CateProductCatolog {

    private CateProductDao cateProductDao;

    public CateProductCatolog(CateProductDao cateProductDao) {
        this.cateProductDao = cateProductDao;
    }

    public List<CategoryProduct> getAllCategoryProduct() {
        return cateProductDao.getAllCategoryProduct();
    }

    public boolean addCateProduct(String gpk, int sequence,  int parent_id, String name, int sync, String image, String status, String type) {
        CategoryProduct cateproduct = new CategoryProduct(gpk,sequence,parent_id,name,sync,image,status,type);
        int id = cateProductDao.addCateProduct(cateproduct);
        return id != -1;
    }

    public void editCategory (int id, String name, String image){

        cateProductDao.editCategory(id,name,image);
    }

    public List<CategoryProduct> searchCateProduct(String search) {
        return cateProductDao.searchCateProduct(search);
    }

    public List<CategoryProduct> getLastRow() {
        return cateProductDao.getLastRow();
    }
}
