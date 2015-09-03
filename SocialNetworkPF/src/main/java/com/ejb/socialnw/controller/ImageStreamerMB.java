package com.ejb.socialnw.controller;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ejb.socialnw.entity.Media;
import com.ejb.socialnw.service.MediaService;

/**
 * Controller for dynamically streaming image content from database
 *
 * @author Andrei Bykov
 */

@Named
@ApplicationScoped
public class ImageStreamerMB {

    @Inject	private transient Logger logger;
    @Inject private MediaService mediaServ;

	/**
	  * 
	  * @return {@link DefaultStreamedContent}
	 */
    public StreamedContent getImage()  {  
    	
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE ) {
            //Return a stub StreamedContent, it will generate right URL.
            return new DefaultStreamedContent();
        } else {
            // Browser is requesting the image. Return a real StreamedContent with the image bytes.
            String imageId = context.getExternalContext().getRequestParameterMap().get("id");
            Media media = mediaServ.find(Integer.valueOf(imageId));
            return new DefaultStreamedContent(new ByteArrayInputStream(media.getMedia()));
        }
    }

}