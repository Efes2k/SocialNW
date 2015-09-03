package com.ejb.socialnw.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ejb.socialnw.entity.message.Comment;
import com.ejb.socialnw.entity.message.Message;

/**
 *  Simple business object representing a user.
 *  
 * @author Andrei Bykov
 */
/**
 * @author Efes
 *
 */
@Entity
@NamedQueries({
        @NamedQuery(name = User.ALL, query = "SELECT u FROM User u  "),
        @NamedQuery(name = User.FIND_BY_NAME, query = "SELECT u FROM User u WHERE u.username = :username"),
        @NamedQuery(name = User.TOTAL, query = "SELECT COUNT(u) FROM User u") })
@Table(name = "user")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1875388806547925724L;
    public final static String ALL = "User.populateUsers";
    public final static String TOTAL = "User.countUsersTotal";
    public final static String FIND_BY_NAME = "User.findByName";

    @Column(nullable = false, length = 50)
    private String username;

    @Column(length = 64)
    private String password;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(length = 50)
    private String email;

    @Column(name = "dateOfBirth", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column
    private Integer phoneNumber;

    @OneToOne(cascade = { CascadeType.ALL })
    private Address address;

    @OneToOne(cascade = { CascadeType.ALL })
    private Media avatar;

    @OneToMany(mappedBy = "owner", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private List<Message> messages;

    @OneToMany(mappedBy = "where", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    private List<Message> messagesWhere;

    @OneToMany(mappedBy = "author", cascade = { CascadeType.MERGE,
            CascadeType.REMOVE }, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(targetEntity = MyFriends.class, mappedBy = "me", orphanRemoval = true, cascade = {
            CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY)
    private List<MyFriends> myFriends;

    @OneToMany(targetEntity = MyFriends.class, mappedBy = "myFriend", orphanRemoval = true, cascade = {
            CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY)
    private List<MyFriends> iAmFriendOf;

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = { @JoinColumn(name = "User_userid") }, inverseJoinColumns = { @JoinColumn(name = "Role_roleid") })
    private List<Role> roles;

    /**
     * Default constructor
     */
    public User() {
        roles = new ArrayList<Role>();
        address = new Address();
        avatar = new Media();
    }

    /**
     * Getters, Setters
     */
    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return dateOfBirth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param Date
     *            dateOfBirth
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return detailed user's addres
     * @see Address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return list of user own messages
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Set list of user messages
     * 
     * @param messages
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * @return list of messages, which user has written
     */
    public List<Message> getMessagesWhere() {
        return messagesWhere;
    }

    /**
     * Set messages, which user has written
     * 
     * @param messagesWhere
     */
    public void setMessagesWhere(List<Message> messagesWhere) {
        this.messagesWhere = messagesWhere;
    }

    /**
     * @return user roles
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * @param roles
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * @return phoneNumber
     */
    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber
     */
    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return avatar
     * @see Media
     */
    public Media getAvatar() {
        return avatar;
    }

    /**
     * @param avatar
     */
    public void setAvatar(Media avatar) {
        this.avatar = avatar;
    }

    /**
     * @return list of my friends
     */
    public List<MyFriends> getMyFriends() {
        return myFriends;
    }

    /**
     * Set list of my friends
     * 
     * @param myFriends
     */
    public void setMyFriends(List<MyFriends> myFriends) {
        this.myFriends = myFriends;
    }

    /**
     * @return user's comments
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * User's comments
     * 
     * @param comments
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * @return list, representing where i'm friend of
     */
    public List<MyFriends> getiAmFriendOf() {
        return iAmFriendOf;
    }

    /**
     * Set list, representing where i'm friend of
     * 
     * @param iAmFriendOf
     */
    public void setiAmFriendOf(List<MyFriends> iAmFriendOf) {
        this.iAmFriendOf = iAmFriendOf;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + phoneNumber;
        result = prime * result + ((roles == null) ? 0 : roles.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (dateOfBirth == null) {
            if (other.dateOfBirth != null)
                return false;
        } else if (!dateOfBirth.equals(other.dateOfBirth))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (firstName == null) {
            if (other.firstName != null)
                return false;
        } else if (!firstName.equals(other.firstName))
            return false;
        if (lastName == null) {
            if (other.lastName != null)
                return false;
        } else if (!lastName.equals(other.lastName))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (phoneNumber != other.phoneNumber)
            return false;
        if (roles == null) {
            if (other.roles != null)
                return false;
        } else if (!roles.equals(other.roles))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return firstName + ", " + lastName;
    }

}