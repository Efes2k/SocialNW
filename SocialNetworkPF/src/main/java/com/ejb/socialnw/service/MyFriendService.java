package com.ejb.socialnw.service;

import javax.ejb.Stateless;

import com.ejb.socialnw.entity.MyFriends;

/**
 *  All CRUD (create, read, update, delete) basic data access operations for 
 *  MyFriend objects are performed in this class.
 *  
 * @author Andrei Bykov
 */

@Stateless
public class MyFriendService extends DataAccessService<MyFriends>{
    
    public MyFriendService(){
        super(MyFriends.class);
    }   
}
