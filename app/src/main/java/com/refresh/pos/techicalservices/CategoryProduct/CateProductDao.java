package com.refresh.pos.techicalservices.CategoryProduct;

import com.refresh.pos.domain.ProductCategory.CategoryProduct;
import com.refresh.pos.domain.inventory.ProductLot;

import java.util.List;

/**
 * Created by TOUCH on 1/24/2019.
 */

public interface CateProductDao {
    /**
     * Adds product to inventory.
     * @param categoryProduct the product to be added.
     * @return id of this product that assigned from database.
     */
    int addCateProduct(CategoryProduct categoryProduct);

    /**
     * Adds ProductLot to inventory.
     * @param productLot the ProductLot to be added.
     * @return id of this ProductLot that assigned from database.
     */
    int addProductLot(ProductLot productLot);
    void editCategory(int id, String name, String image);

    /**
     * Edits product.
     * @param categoryProduct the product to be edited.
     * @return true if product edits success ; otherwise false.
     */
    boolean editCateProduct(CategoryProduct categoryProduct);

    /**
     * Returns CategoryProduct from inventory finds by id.
     * @param id id of CategoryProduct.
     * @return CategoryProduct
     */
    CategoryProduct getCateProductId(int id);

    List<CategoryProduct> searchCateProduct(String search);


    /**
     * Returns CategoryProduct
     * @param name name of CategoryProduct.
     * @return CategoryProduct
     */
    CategoryProduct getCateProductByName(String name);

    /**
     * Returns list of all products in inventory.
     * @return list of all products in inventory.
     */
    List<CategoryProduct> getAllCategoryProduct();
    List<CategoryProduct> getLastRow();

    /**
     * Returns list of product in inventory finds by name.
     * @param name name of product.
     * @return list of product in inventory finds by name.
     */
    List<CategoryProduct> getCategoryProductByName(String name);

    /**
     * Search product from string in inventory.
     * @param search string for searching.
     * @return list of product.
     */


}

