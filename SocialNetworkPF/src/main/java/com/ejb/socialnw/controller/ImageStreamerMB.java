package com.ejb.socialnw.controller;

import java.io.ByteArrayInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.ejb.socialnw.entity.Media;
import com.ejb.socialnw.service.MediaService;
import com.ejb.socialnw.util.DateUtility;

/**
 * Controller for dynamically streaming image content from database
 *
 * @author Andrei Bykov
 */

@ManagedBean
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
            logger.log(Level.INFO, "Finded media ({0}) in #" + DateUtility.getCurrentDateTime(), media.getId());
            return new DefaultStreamedContent(new ByteArrayInputStream(media.getMedia()));
        }
    }

}