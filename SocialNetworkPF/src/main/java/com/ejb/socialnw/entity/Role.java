package com.ejb.socialnw.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result	+ ((roledesc == null) ? 0 : roledesc.hashCode());
		result = prime * result	+ ((rolename == null) ? 0 : rolename.hashCode());
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
		Role other = (Role) obj;
		if (roledesc == null) {
			if (other.roledesc != null)
				return false;
		} else if (!roledesc.equals(other.roledesc))
			return false;
		if (rolename == null) {
			if (other.rolename != null)
				return false;
		} else if (!rolename.equals(other.rolename))
			return false;
		return true;
	}   
    
    
}