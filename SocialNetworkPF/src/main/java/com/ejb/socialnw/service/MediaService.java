package com.ejb.socialnw.service;

import javax.ejb.Stateless;

import com.ejb.socialnw.entity.Media;

/**
 * All CRUD (create, read, update, delete) basic data access operations for
 * Media objects are performed in this class.
 * 
 * @author Andrei Bykov
 */

@Stateless
public class MediaService extends DataAccessService<Media> {

    public MediaService() {
        super(Media.class);
    }
}
