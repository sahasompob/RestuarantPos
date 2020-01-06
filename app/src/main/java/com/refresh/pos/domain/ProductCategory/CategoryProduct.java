package com.refresh.pos.domain.ProductCategory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TOUCH on 1/24/2019.
 */

public class CategoryProduct {
    private int cate_product_id;
    private String gpk;
    private int sequence;
    private int parent_id;
    private String name;
    private int sync;
    private String image;
    private String status;
    private String type;

    public void setCate_product_id(int cate_product_id) {
        this.cate_product_id = cate_product_id;
    }

    public void setPk(String gpk) {
        this.gpk = gpk;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public void setSyn(int syn) {
        this.sync = syn;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCate_product_id() {
        return cate_product_id;
    }

    public String getGpk() {
        return gpk;
    }

    public int getSequence() {
        return sequence;
    }

    public int getParent_id() {
        return parent_id;
    }

    public String getName() {
        return name;
    }

    public int getSyn() {
        return sync;
    }

    public String getImage() {
        return image;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public CategoryProduct(int cate_product_id, String gpk, int sequence, int parent_id, String name, int syn, String image, String status, String type) {
        this.cate_product_id = cate_product_id;
        this.gpk = gpk;
        this.sequence = sequence;
        this.parent_id = parent_id;
        this.name = name;
        this.sync = syn;
        this.image = image;
        this.status = status;
        this.type = type;
    }

    /**
     * Static value for UNDEFINED ID.
     */
    public static final int UNDEFINED_ID = -1;

    public CategoryProduct(String gpk,int sequence,  int parent_id,String name, int syn, String image, String status, String type) {
        this(UNDEFINED_ID, gpk, sequence, parent_id, name, syn, image, status, type);
    }

    /**
     * Constructs a new Product.
     * @param id ID of the product, This value should be assigned from database.
     * @param name name of this product.
     * @param barcode barcode (any standard format) of this product.
     * @param salePrice price for using when doing sale.
     */

    /**
     * Constructs a new Product.
     * @param name name of this product.
     * @param barcode barcode (any standard format) of this product.
     * @param salePrice price for using when doing sale.
     */

    /**
     * Returns name of this product.
     * @return name of this product.
     */

    /**
     * Returns the description of this Product in Map format.
     * @return the description of this Product in Map format.
     */


    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("cate_product_id", cate_product_id + "");
        map.put("gpk", gpk);
        map.put("sequence", sequence + "");
        map.put("paren_id", parent_id+"");
        map.put("name", name);
        map.put("sync", sync+"");
        map.put("image", image);
        map.put("status", status);
        map.put("type", type);
        return map;

    }
}
