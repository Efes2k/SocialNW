package com.ejb.socialnw.util;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.bean.ManagedBean;

/**
 * Logging producer for injectable log4j logger
 *
 * @author Andrei Bykov
 */
@ManagedBean
public class LoggerProducer {
   
    /**
    * @param injectionPoint
    * @return logger
    */
    @Produces
    public Logger produceLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }
}