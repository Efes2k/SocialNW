package com.ejb.socialnw.entity.message;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ejb.socialnw.entity.BaseEntity;
import com.ejb.socialnw.entity.User;

/**
 * Simple business object representing a comments.
 * 
 * @author Andrei Bykov
 */

@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

    private static final long serialVersionUID = -4569666641567788369L;

    @Column(length = 200)
    private String text;

    @Column()
    private Date date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "whereMess_id")
    private Message messageWhere;

    /**
     * Default constructor
     */
    public Comment() {
        super();
        date = new Date();
    }

    /**
     * Getters, Setters
     */
    /**
     * @return text of comment
     */
    public String getText() {
        return text;
    }

    /**
     * @param comment
     *            's text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return date creating comment date
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
     * @return comment author
     * @see User
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Set comment author
     * 
     * @param author
     * @see User
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * @return message where comment was writing
     * @see Message
     */
    public Message getMessageWhere() {
        return messageWhere;
    }

    /**
     * Set message where comment was writing
     * 
     * @see Message
     * @param messageWhere
     */
    public void setMessageWhere(Message messageWhere) {
        this.messageWhere = messageWhere;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((author == null) ? 0 : author.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((text == null) ? 0 : text.hashCode());
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
        Comment other = (Comment) obj;
        if (author == null) {
            if (other.author != null)
                return false;
        } else if (!author.equals(other.author))
            return false;
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
        return true;
    }

}
