package com.ejb.socialnw.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.persistence.ManyToMany;

/**
* Simple business object representing a user's roles.
 * @author Andrei Bykov
 */
@Entity
@NamedQueries({@NamedQuery(name = Role.ALL, query = "SELECT r FROM Role r"),
			   @NamedQuery(name = Role.LIST_NAMED, query = "SELECT r FROM Role r WHERE r.roledesc = 'User'")})
@Table(name = "role")
public class Role extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 518165116003220814L;

	public final static String ALL = "Role.populateRoles";
	public final static String LIST_NAMED = "Role.nameRoleList";
   
    private String roledesc;
    private String rolename;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
    
    /**
     * Default constructor
     */
    public Role() {
    }

    /**
     * Constructor
     * @param roleid
     * @param rolename
     */
    public Role(Integer roleid, String rolename) {
        this.rolename = rolename;
    }
    
    /**
     * Getters, Setters
     */
    /**
     * @return role description
     */
    public String getRoledesc() {
        return this.roledesc;
    }

    /**
     * @param roledesc
     */
    public void setRoledesc(String roledesc) {
        this.roledesc = roledesc;
    }

    /**
     * @return rolename
     */
    public String getRolename() {
        return this.rolename;
    }

    /**
     * @param rolename
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    /**
     * @return users list
     * @see User
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * @param users
     * @see User
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }    
}