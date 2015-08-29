package com.ejb.socialnw.service;

import javax.ejb.Stateless;

import com.ejb.socialnw.entity.User;

/**
 *  All CRUD (create, read, update, delete) basic data access operations for 
 *  User objects are performed in this class.
 *  
 * @author Andrei Bykov
 */

@Stateless
public class UserService extends DataAccessService<User>{
    
    public UserService(){
        super(User.class);
    }   
}
