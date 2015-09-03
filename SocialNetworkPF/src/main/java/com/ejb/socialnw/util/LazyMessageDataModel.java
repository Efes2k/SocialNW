package com.ejb.socialnw.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.ejb.socialnw.annotaions.PrincipalUser;
import com.ejb.socialnw.entity.User;
import com.ejb.socialnw.entity.message.Message;
import com.ejb.socialnw.service.DataAccessService;

/**
 * 
 * Custom Lazy Message DataModel which extends PrimeFaces LazyDataModel.
 * 
 */

public class LazyMessageDataModel extends LazyDataModel<Message> {

    private static final long serialVersionUID = 2147702833769997700L;
    // Data Source for binding data to the DataTable
    private List<Message> datasource;
    // Selected Page size in the DataTable
    private int pageSize;
    // Current row index number
    private int rowIndex;
    // Total row number
    private int rowCount;
    // Data Access Service for CRUD operations
    private DataAccessService crudService;

    @Inject
    @PrincipalUser
    private User userPrincipal;

    private User visitedUser;

    /**
     *
     * @param crudService
     * @param visitedUser
     */
    public LazyMessageDataModel(DataAccessService crudService, User visitedUser) {
        this.crudService = crudService;
        this.visitedUser = visitedUser;
    }

    /**
     * Checks if the row is available
     * 
     * @return boolean
     */
    @Override
    public boolean isRowAvailable() {
        if (datasource == null)
            return false;
        int index = rowIndex % pageSize;
        return index >= 0 && index < datasource.size();
    }

    @Override
    public List<Message> load(int first, int pageSize, String sortField,
            SortOrder sortOrder, Map<String, Object> filters) {
        List<Message> data = new ArrayList<Message>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", visitedUser.getId());
        datasource = crudService.findWithNamedQuery(Message.FIND_BY_ID, map);
        for (Message message : datasource) {
            data.add(message);
        }
        Collections.sort(data, new LazySorter("date", SortOrder.DESCENDING));
        int dataSize = data.size();
        this.setRowCount(dataSize);

        // paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }

    }

    /**
     * Gets the user object's primary key
     * 
     * @param user
     * @return Object
     */
    @Override
    public Object getRowKey(Message message) {
        return message.getId().toString();
    }

    /**
     * Returns the user object at the specified position in datasource.
     * 
     * @return
     */
    @Override
    public Message getRowData() {
        if (datasource == null)
            return null;
        int index = rowIndex % pageSize;
        if (index > datasource.size()) {
            return null;
        }
        return datasource.get(index);
    }

    /**
     * Returns the user object that has the row key.
     * 
     * @param rowKey
     * @return
     */
    @Override
    public Message getRowData(String rowKey) {
        if (datasource == null)
            return null;
        for (Message message : datasource) {
            if (message.getId().toString().equals(rowKey))
                return message;
        }
        return null;
    }

    /*
     * ===== Getters and Setters of LazyUserDataModel fields
     */

    /**
     *
     * @param pageSize
     */
    @Override
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Returns page size
     * 
     * @return int
     */
    @Override
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Returns current row index
     * 
     * @return int
     */
    @Override
    public int getRowIndex() {
        return this.rowIndex;
    }

    /**
     * Sets row index
     * 
     * @param rowIndex
     */
    @Override
    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    /**
     * Sets row count
     * 
     * @param rowCount
     */
    @Override
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    /**
     * Returns row count
     * 
     * @return int
     */
    @Override
    public int getRowCount() {
        return this.rowCount;
    }

    /**
     * Sets wrapped data
     * 
     * @param list
     */
    @Override
    public void setWrappedData(Object list) {
        this.datasource = (List<Message>) list;
    }

    /**
     * Returns wrapped data
     * 
     * @return
     */
    @Override
    public Object getWrappedData() {
        return datasource;
    }

}
