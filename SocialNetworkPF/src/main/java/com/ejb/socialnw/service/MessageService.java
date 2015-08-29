package com.ejb.socialnw.service;

import javax.ejb.Stateless;

import com.ejb.socialnw.entity.message.Message;

/**
 *  All CRUD (create, read, update, delete) basic data access operations for 
 *  Message objects are performed in this class.
 *  
 * @author Andrei Bykov
 */

@Stateless
public class MessageService extends DataAccessService<Message>{
    
    public MessageService(){
        super(Message.class);
    }   
}
