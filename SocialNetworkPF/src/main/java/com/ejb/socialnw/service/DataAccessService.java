package com.ejb.socialnw.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.ejb.socialnw.entity.User;

/**
 * Implementation of the generic Data Access Service
 * All CRUD (create, read, update, delete) basic data access operations for any 
 * persistent object are performed in this class.
 * @author Andrei Bykov
 */
@Transactional
public abstract class DataAccessService<T> {

    @PersistenceContext
    private EntityManager em;

    public DataAccessService() {
    }
    
    private Class<T> type;

    /**
     * Default constructor
     * 
     * @param type entity class
     */
    public DataAccessService(Class<T> type) {
        this.type = type;
    }
    
    /**
     * Stores an instance of the entity class in the database
     * @param T Object
     * @return 
     */
    public T create(T t) {
        this.em.persist(t);
        this.em.flush();
        this.em.refresh(t);
        return t;
    }

    /**
     * Retrieves an entity instance that was previously persisted to the database 
     * @param T Object
     * @param id
     * @return 
     */
    public T find(Object id) {
        return this.em.find(this.type, id);
    }

    /**
     * Removes the record that is associated with the entity instance
     * @param type
     * @param id 
     */
    public void delete(Object id) {
        Object ref = this.em.getReference(this.type, id);
        this.em.remove(ref);
    }

    /**
     * Removes the number of entries from a table
     * @param <T>
     * @param items
     * @return 
     */
    public boolean deleteItems(T[] items) {
        for (T item : items) {
            if( item instanceof User){
                User user = (User)item;
                if(user.getId() == 1){
                    continue;
                }
            }
            em.remove(em.merge(item));
        }
        return true;
    }
    
    public boolean deleteItem(T item) {
    	
            em.remove(em.merge(item));
        return true;
    }

    /**
     * Updates the entity instance
     * @param <T>
     * @param t
     * @return the object that is updated
     */
    public T update(T item) {
    	
        return (T) this.em.merge(item);
        
    }

    /**
     * Returns the number of records that meet the criteria
     * @param namedQueryName
     * @return List
     */
    public List findWithNamedQuery(String namedQueryName) {
        return this.em.createNamedQuery(namedQueryName).getResultList();
    }

    /**
     * Returns the number of records that meet the criteria
     * @param namedQueryName
     * @param parameters
     * @return List
     */
    public List findWithNamedQuery(String namedQueryName, Map parameters) {
        return findWithNamedQuery(namedQueryName, parameters, 0);
    }

    /**
     * Returns the number of records with result limit
     * @param queryName
     * @param resultLimit
     * @return List
     */
    public List findWithNamedQuery(String queryName, int resultLimit) {
        return this.em.createNamedQuery(queryName).
                setMaxResults(resultLimit).
                getResultList();
    }

    /**
     * Returns the number of records that meet the criteria
     * @param <T>
     * @param sql
     * @param type
     * @return List
     */
    public List<T> findByNativeQuery(String sql) {
        return this.em.createNativeQuery(sql, type).getResultList();
    }

    /**
     * Returns the number of total records
     * @param namedQueryName
     * @return int
     */
    public int countTotalRecord(String namedQueryName) {
        Query query = em.createNamedQuery(namedQueryName);
        Number result = (Number) query.getSingleResult();
        return result.intValue();
    }

    /**
     * Returns the number of records that meet the criteria with parameter map and
     * result limit
     * @param namedQueryName
     * @param parameters
     * @param resultLimit
     * @return List
     */
    public List findWithNamedQuery(String namedQueryName, Map parameters, int resultLimit) {
        Set<Map.Entry<String, Object>> rawParameters = parameters.entrySet();
        Query query = this.em.createNamedQuery(namedQueryName);
        if (resultLimit > 0) {
            query.setMaxResults(resultLimit);
        }
        for (Map.Entry<String, Object> entry : rawParameters) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }
    
    /**
     * Returns the number of records that will be used with lazy loading / pagination 
     * @param namedQueryName
     * @param start
     * @param end
     * @return List
     */
    public List findWithNamedQuery(String namedQueryName, int start, int end) {
        Query query = this.em.createNamedQuery(namedQueryName);
       
        query.setMaxResults(end - start);
        query.setFirstResult(start);
        return query.getResultList();
    }
    
    
    /**
     * Returns the number of records that will be used with lazy loading / pagination 
     * with parameters map and result limit
     * @param namedQueryName
     * @param start
     * @param end
     * @return List
     */
    public List findWithNamedQuery(String namedQueryName,Map parameters, int start, int end) {
    	Set<Map.Entry<String, Object>> rawParameters = parameters.entrySet();
        Query query = this.em.createNamedQuery(namedQueryName);
        for (Map.Entry<String, Object> entry : rawParameters) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        query.setMaxResults(end - start);
        query.setFirstResult(start);
        return query.getResultList();
    }
}