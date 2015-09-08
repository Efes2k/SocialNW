package com.ejb.socialnw.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;

import com.ejb.socialnw.annotaions.PrincipalUser;
import com.ejb.socialnw.annotaions.VisitedUser;
import com.ejb.socialnw.entity.Media;
import com.ejb.socialnw.entity.MyFriends;
import com.ejb.socialnw.entity.User;
import com.ejb.socialnw.entity.message.Comment;
import com.ejb.socialnw.entity.message.Message;
import com.ejb.socialnw.service.CommentService;
import com.ejb.socialnw.service.MessageService;
import com.ejb.socialnw.service.MyFriendService;
import com.ejb.socialnw.service.UserService;
import com.ejb.socialnw.util.DateUtility;
import com.ejb.socialnw.util.LazyMessageDataModel;
 
/**
 * Action Controller class allows CRUD operations with messages,comments and it's media,
 * add/remove friends
 * 
 * @author Andrei Bykov
 */
@Named
@ViewScoped
public class UserActionMB implements Serializable {
	
    @Inject	private transient Logger logger;
    private static final long serialVersionUID = 1412683751304183808L;
	
    //creating new message
    private Message message;
	
    //creating new media content for message
    private Media media;
	
    //create new comment
    private Comment comment;
	
    //binding with jsf upload file to set in messages media
    private UploadedFile file;
	
    //used for PrettyFaces navigation
    private String userId = "";
	
    //Get current authorized user
    @Inject	@PrincipalUser private User user;
	
    //Get current visited user
    @Inject	 @VisitedUser private User visitedUser;
	
    // Lazy loading message list
    private LazyDataModel<Message> lazyModel; 
    
    //a flag to determine whether the user is your friend
    private boolean validFriend;
	
    private List<Message> datasource;
    
    //Services for CRUD operation
    @Inject private UserService userServ;
    @Inject private MessageService messageServ;
    @Inject private CommentService commentServ;
    @Inject private MyFriendService friendServ;
	
    //for dynamic relation with inputText
    private Map <Long, String> commentList;
	
	
	   /**
     * Initializing Data Access Service for LazyUserDataModel class,
     * initializing new message,media and comment.
     * 
     */
    @PostConstruct
    public void init(){
    	logger.log(Level.INFO, "Message controller initialized in # "+ (visitedUser == null? "NULL" : visitedUser.getUsername()) +" "  + DateUtility.getCurrentDateTime());
    	message = new Message();
    	media = new Media();
    	comment = new Comment();
    	commentList = new HashMap<Long, String>();
		if(visitedUser != null) {
			lazyModel = new LazyMessageDataModel(messageServ,visitedUser);
			validFriend = validateFriend();
		}
      
    }
    
    @PreDestroy
    public void destroy(){
    	logger.log(Level.INFO, "Message controller destroyed in # "+ (visitedUser == null? "NULL" : visitedUser.getUsername()) +" "  + DateUtility.getCurrentDateTime());
    }
	

	
	 /**
     * Create, Update and Delete operations
     */
	
	 /**
     * Create a new comment on message
     */
    public void doCreateComment(Message messageWhere){
	    comment.setAuthor(userServ.find(user.getId()));
	    comment.setMessageWhere(messageWhere);
	    comment.setText(commentList.get(messageWhere.getId()));
	    commentServ.create(comment);
	    comment = new Comment();
    }
	   
    /**
     * Delete current comment from message
     * @param actionEvent
     */
    public void doDeleteComment(Comment commentToDelete) {
    	commentServ.deleteItem(commentToDelete);
    }
	   
    /**
     * Delete message
     * @param message
     */
    public void doDeleteMessage(Message message) {
	    messageServ.deleteItem(message);
    }
	
	/**
    * Creating new message with text
    *  and (or without) media content
    */
    public void doCreateMessage() {
        User owner = userServ.find(user.getId());
        User where = userServ.find(visitedUser.getId());
        message.setOwner(owner);
        message.setWhere(where);
        if(file != null) {
        	storeImage();
        }
    	message.setDate(new Date());
        messageServ.create(message);
        message = new Message();
        file = null;
    }
    
    /**
     * Listen for getting file from uploadFile form,
     * and save it to variable.
     *
     * @param event
     */
    public void fileUploadListener(FileUploadEvent event)  {
        file = event.getFile();
        FacesMessage msg = new FacesMessage("File uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    /**
     * For saving image in current message
     *
     */
    public void storeImage() {
        try {
            media.setContentType(file.getContentType());
            media.setFileName("filename");
            media.setMedia(IOUtils.toByteArray(file.getInputstream()));
            message.setMedia(media);
	        media = new Media();
        } catch (IOException e) {
            e.printStackTrace();
            FacesMessage errorMsg = new FacesMessage(
            FacesMessage.SEVERITY_ERROR, "Upload error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, errorMsg);
        }
    }
    
    
    /**
     * Get all user's not null user media for 
     * representing in gallery.
     * TODO: change 
     * @return result
     */
    @SuppressWarnings("unchecked")
    public List<Message> userGallery(){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("id", user.getId());
    	List<Message> result = messageServ.findWithNamedQuery(Message.FIND_BY_OWNER,map);
    	return result;
    }
    
    /**
     * For add and remove friends
     *
     */
    public boolean validateFriend(){
	    User friend = userServ.find(visitedUser.getId());
	    User principal = userServ.find(user.getId());
	    boolean status = false;
	    for (MyFriends fr : principal.getMyFriends()) {
		    if (fr.getMyFriend().getUsername().equals(friend.getUsername())){
			    status = true;
			    break;
		    }
		    else status = false;
	    }
        return status;
    }
    
    /**
     * Decides what to do, based on whether the user your friend: 
     * add or remove.
     */
    public void addOrRemove(){
    	validFriend = validateFriend();
    	if(!validFriend) addFriend();
    	else removeFriend();
    }
    
    /**
     *Add new friend
     */
    public void addFriend(){
    	MyFriends mf = new MyFriends();
    	mf.setMe(userServ.find(user.getId()));
    	mf.setMyFriend(userServ.find(visitedUser.getId()));
    	friendServ.create(mf);
    	validFriend = true;
    }
    
    /**
     *Remove friend
     */
    public void removeFriend(){
        User me = userServ.find(user.getId());
	    MyFriends temp = null;
	    for (int i = 0; i < me.getMyFriends().size(); i++) {
		     temp = me.getMyFriends().get(i);
		    if (temp.getMyFriend().getUsername().equals(visitedUser.getUsername())) {
			    me.getMyFriends().remove(i);
			    friendServ.delete(temp.getId());
		    }
	    }
	    validFriend = false;
    }
    
    /**
     * Getters, Setters
     */
    
    /**
     * 
     * @return message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * @param message
    */
    public void setMessage(Message message) {
     this.message = message;
    }

    /**
     * @return comment
	 */
    public Comment getComment() {
        return comment;
    }

    /**
      * @param comment
      */
    public void setComment(Comment comment) {
        this.comment = comment;
    }

     /**
	 * @return file
	 * {@link UploadedFile}
	 */
    public UploadedFile getFile() {
        return file;
    }

     /**
	 * @param file
	 * {@link UploadedFile}
	 */
    public void setFile(UploadedFile file) {
        this.file = file;
    }

    /**
	 * @return media
	 */
    public Media getMedia() {
	    return media;
    }

    /**
	 * @param media
	 */
    public void setMedia(Media media) {
        this.media = media;
    }

    /**
	 * @return lazyModel
	 * {@link LazyDataModel}
	 */
    public LazyDataModel<Message> getLazyModel() {
        return lazyModel;
    }

    /**
	 * @return datasource
	 */
    public List<Message> getDatasource() {
       return datasource;
    }

    /**
	 * @param datasource
	 */
    public void setDatasource(List<Message> datasource) {
        this.datasource = datasource;
    }

    /**
	 * @return visitedUser
	 */
    public User getVisitedUser() {
         return visitedUser;
    }

    /**
	 * @param visitedUser
	 */
    public void setVisitedUser(User visitedUser) {
        this.visitedUser = visitedUser;
    }

    /**
	 * @return user
	 */
    public User getUser() {
        return user;
    }

    /**
	 * @param user
	 */
    public void setUser(User user) {
        this.user = user;
    }

    /**
	 * @param lazyModel
	 */
    public void setLazyModel(LazyDataModel<Message> lazyModel) {
        this.lazyModel = lazyModel;
    }
 
    /**
	 * @return
	 */
    public String getUserId() {
        return userId;
    }

    /**
	 * @param userId
	 */
    public void setUserId(String userId) {
         this.userId = userId;
    }

    /**
	 * @return
	 */
    public boolean isValidFriend() {
         return validFriend;
    }

    /**
	 * @param validFriend
	 */
    public void setValidFriend(boolean validFriend) {
        this.validFriend = validFriend;
    }

    /**
	 * @return Map comments
	 */
    public Map<Long, String> getCommentList() {
        return commentList;
    }

    /**
	 * @param commentList
	 */
    public void setCommentList(Map<Long, String> commentList) {
        this.commentList = commentList;
    }
    
    
}