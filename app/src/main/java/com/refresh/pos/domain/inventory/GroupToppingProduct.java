package com.refresh.pos.domain.inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by TOUCH on 1/24/2019.
 */

public class GroupToppingProduct {
    private int id;
    private String gpk;
    private String name;
    private String image;
    private String status;
    private boolean isSelected = false;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGpk() {
        return gpk;
    }

    public void setGpk(String gpk) {
        this.gpk = gpk;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public boolean isSelected() {
        return isSelected;
    }


    public GroupToppingProduct(int id, String gpk, String name, String image, String status) {
        this.id = id;
        this.gpk = gpk;
        this.name = name;
        this.image = image;
        this.status = status;

    }



    /**
     * Static value for UNDEFINED ID.
     */
    public static final int UNDEFINED_ID = -1;

    public GroupToppingProduct(String gpk, String name, String image, String status) {
        this(UNDEFINED_ID, gpk,name,image,status);
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
        map.put("id", id + "");
        map.put("gpk", gpk);
        map.put("name", name);
        map.put("image", image);
        map.put("status", status);
        map.put("select", isSelected+"");

        return map;

    }
}
