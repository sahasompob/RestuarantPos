package com.refresh.pos.domain.register;

import com.refresh.pos.domain.ProductCategory.CategoryProduct;
import com.refresh.pos.techicalservices.CategoryProduct.CateProductDao;
import com.refresh.pos.techicalservices.register.RegisterDao;

import java.util.List;

/**
 * Created by TOUCH on 1/24/2019.
 */

public class UserCatolog {

    private RegisterDao registerDao;

    public UserCatolog(RegisterDao registerDao) {
        this.registerDao = registerDao;
    }

    public List<UserDetail> getAllUserDetail() {
        return registerDao.getAllUserDetail();
    }

    public boolean addUser(String pk, int sequence,  int parent_id, String name, int sync, String image, String status, String type) {
        UserDetail register_user = new UserDetail(pk,sequence,parent_id,name,sync,image,status,type);
        int id = registerDao.addUser(register_user);
        return id != -1;
    }
}
