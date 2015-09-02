package com.ejb.socialnw.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Business object representing a friends.
 *
 * @author Andrei Bykov
 */
@Entity
@Table(name = "my_friends")
public class MyFriends extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1249180944994329007L;

    @ManyToOne
    @JoinColumn(name = "me_id")
    private User me;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User myFriend;

    /**
     * Default constructor
     */
    public MyFriends() {
        super();
    }

    /**
     * Constructor
     * 
     * @param User
     *            me
     * @param User
     *            myFriend
     */
    public MyFriends(User me, User myFriend) {
        super();
        this.me = me;
        this.myFriend = myFriend;
    }

    /**
     * Getters, Setters
     */

    /**
     * @return User me, appropriated with same user
     * @see User
     */
    public User getMe() {
        return me;
    }

    /**
     * Set user, appropriated with same user
     * 
     * @param me
     * @see User
     */
    public void setMe(User me) {
        this.me = me;
    }

    /**
     * @return User myFriend
     * @see User
     */
    public User getMyFriend() {
        return myFriend;
    }

    /**
     * @param myFriend
     */
    public void setMyFriend(User myFriend) {
        this.myFriend = myFriend;
    }

}