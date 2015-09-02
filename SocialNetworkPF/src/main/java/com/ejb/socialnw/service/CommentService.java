package com.ejb.socialnw.service;

import javax.ejb.Stateless;

import com.ejb.socialnw.entity.message.Comment;

/**
 *
 * All CRUD (create, read, update, delete) basic data access operations for
 * Comment objects are performed in this class.
 *
 * @author Andrei Bykov
 */

@Stateless
public class CommentService extends DataAccessService<Comment> {

    public CommentService() {
        super(Comment.class);
    }
}
