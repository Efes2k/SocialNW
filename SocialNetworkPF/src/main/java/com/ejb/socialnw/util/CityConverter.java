package com.ejb.socialnw.util;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.ejb.socialnw.entity.location.City;
import com.ejb.socialnw.service.UserService;

/**
 * Object converting utility is to convert between String and the desired Object
 * type. JSF cannot convert the given String value to the Object type, to be
 * able to bind the selected Object to the list we will have to override
 * getAsObject and getAsString functions. Usage: <f:converter
 * converterId="com.ejb.socialnw.util.ObjectConverter" /> TODO: Make it generic
 * 
 * @author Andrei Bykov
 */
@FacesConverter(value = "com.ejb.socialnw.util.CityConverter")
public class CityConverter implements Converter {

    private @Inject UserService userServ;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cityName", value);
        return userServ.findWithNamedQuery(City.FIND_BY_NAME, map).get(0);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        if (value != null) {
            City city = (City) value;
            return city.getCityName();
        }
        return "";

    }
}
