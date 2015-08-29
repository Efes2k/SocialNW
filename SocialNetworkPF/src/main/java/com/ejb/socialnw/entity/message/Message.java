package com.ejb.socialnw.entity.message;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ejb.socialnw.entity.BaseEntity;
import com.ejb.socialnw.entity.Media;
import com.ejb.socialnw.entity.User;

/**
 *  Simple business object representing a message.
 *  
 * @author Andrei Bykov
 */

@Entity
@NamedQueries({
    @NamedQuery(name = Message.ALL, query = "SELECT m FROM Message m "),
    @NamedQuery(name= Message.FIND_BY_ID,  query="SELECT m FROM Message m INNER JOIN m.where u where u.id = :id ORDER BY m.date DESC"),
    @NamedQuery(name= Message.FIND_BY_OWNER,  query="SELECT m FROM Message m INNER JOIN m.owner u INNER JOIN m.media med  WHERE u.id = :id AND med.media IS NOT NULL ORDER BY m.date DESC"),
    @NamedQuery(name = Message.TOTAL, query = "SELECT COUNT(m) FROM Message m")})
@Table(name = "message")
public class Message extends BaseEntity {

	private static final long serialVersionUID = -3104933723895657000L;
	public final static String ALL = "Message.populateMessages";
    public final static String TOTAL = "Messager.countMessagesTotal";
    public final static String FIND_BY_ID = "Message.findById";
    public final static String FIND_BY_OWNER = "Message.findOwner";
	
	@Column(length = 200)
	private String text;
	
	@Column(length = 20)
	private String type;
	
	@Column
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User owner;
	 
    @OneToOne(cascade = {CascadeType.ALL})
    private Media media;
	
	@ManyToOne
	@JoinColumn(name = "where_id")
	private User where;
	
	@OneToMany(mappedBy = "messageWhere",cascade = {CascadeType.REMOVE,CascadeType.MERGE} , fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Comment> comments;

	/**
	 * Default constructor
	 */
	public Message(){
		
	}
	
	/**
     * Getters, Setters
     */
	/**
	 * @return message text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set message text
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param message type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return message creating date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return owner of message
	 * @see User
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * Set owner of message
	 * @param owner
	 * @see User
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return where location of message
	 * @see User
	 */
	public User getWhere() {
		return where;
	}

	/**
	 * Set location of message
	 * @see User
	 * @param where
	 */
	public void setWhere(User where) {
		this.where = where;
	}

	/**
	 * @return comments
	 * @see Comment
	 */
	public List<Comment> getComments() {
		return comments;
	}

	/**
	 * @param comments
	 * @see Comment
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	/**
	 * @return media
	 * @see Media
	 */
	public Media getMedia() {
		return media;
	}

	/**
	 * @param media
	 * @see Media
	 */
	public void setMedia(Media media) {
		this.media = media;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Message other = (Message) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
	
}
