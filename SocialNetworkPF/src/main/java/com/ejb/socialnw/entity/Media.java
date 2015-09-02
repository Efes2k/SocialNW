package com.ejb.socialnw.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Simple business object representing a media, like photo, audio, video.
 * 
 * @author Andrei Bykov
 */

@Entity
@Table(name = "media")
public class Media extends BaseEntity {

    private static final long serialVersionUID = 4484473975550581074L;

    @Column(length = 50)
    private String contentType;

    @Column(length = 50)
    private String fileName;

    @Lob
    @Column(length = 500000)
    private byte[] media;

    /**
     * Default constructor
     */
    public Media() {
    }

    /**
     * Getters, Setters
     */

    /**
     * @return media content type
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Set media content type
     * 
     * @param contentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return name of using file
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return media as a byte array
     */
    public byte[] getMedia() {
        return media;
    }

    /**
     * @param media
     */
    public void setMedia(byte[] media) {
        this.media = media;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((contentType == null) ? 0 : contentType.hashCode());
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
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
        Media other = (Media) obj;
        if (contentType == null) {
            if (other.contentType != null)
                return false;
        } else if (!contentType.equals(other.contentType))
            return false;
        if (fileName == null) {
            if (other.fileName != null)
                return false;
        } else if (!fileName.equals(other.fileName))
            return false;
        return true;
    }

}
