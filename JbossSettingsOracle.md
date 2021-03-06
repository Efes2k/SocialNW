# Jboss configuration
 
 We need to tell jBoss application server that we are going to use this database for JDBCRealm purpose. 
 You would place oracle connector jar into library of web application on path
 `<jboss_home>`\modules\system\layers\base\com\oracle\main and put file module.xml with:
 
`<module xmlns="urn:jboss:module:1.1" name="com.oracle">`

`<resources>`
 
`<resource-root path="ojdbc7.jar"/>`
 
`</resources>`
  
`<dependencies>`
  
`<module name="javax.api"/>`

`<module name="javax.transaction.api"/>`
    
`<module name="javax.servlet.api" optional="true"/>`
	 
`</dependencies>`
  
`</module>`
 
 
**Configure module in standalone.xml**

1) **Navigate to `<jboss_home>`/standalone/configuration and open standalone.xml .**

 You will find a datasources tag under which you need to put this:
 
`<datasource jta="false" jndi-name="java:/socialnwOracle" pool-name="socialnwOracle" enabled="true" use-ccm="false">`

`<connection-url>`jdbc:oracle:thin:@localhost:1521:xe`</connection-url>`

`<driver>`OracleJDBCDriver`/driver>`

`<security>`

`<user-name>`root`</user-name>`

`<password>`pass`</password>`

`</security>`

`<validation>`

`<validate-on-match>`false`</validate-on-match>`

`<background-validation>`false`</background-validation>`

`</validation>`

`<statement>`

`<share-prepared-statements>`false`</share-prepared-statements>`

`</statement>`

`</datasource>`


  - jndi name is the identifier we are going to use in our security configuration.
  - jdbc:oracle:thin:@localhost:1521:xe is our database to which jndi name points.

2) **Configure jdbc driver using previously created module. Add following into drivers tag in standalone.xml**

`<driver name="OracleJDBCDriver" module="com.oracle"/>`
 

3) **Now jBoss know that this database will be used as datasource. Now we need to configure this JAAS for jboss.So we will define security subsystem for authentication and authorization.**
**Add following code to  standalone.xml  under security-domains :**

`<security-domain name="socialnwOracleRealm">`

`<authentication>`
                   
`<login-module code="org.jboss.security.auth.spi.DatabaseServerLoginModule" flag="required">`
                        
`<module-option name="dsJndiName" value="java:/socialnwOracle"/>`
                           
`<module-option name="principalsQuery" value="select password from app_user where username = ?"/>`
                            
`<module-option name="rolesQuery" value="SELECT r.ROLEdesc, 'Roles' FROM ROLE r, USER_ROLES ur, USER u WHERE u.USERNAME =? AND u.ID=ur.USER_USERID AND ur.ROLE_ROLEID=r.ID"/>`
                            
`<module-option name="hashAlgorithm" value="SHA-256"/>`

`<module-option name="hashEncoding" value="hex"/>`
                            
`<module-option name="hashCharset" value="UTF-8"/>`
                            
`</login-module>`
                        
`authentication>`

`</security-domain>`
				
				
  - dsJndiName defines the name of the datasource used for jdbc realm.
  - principalsQuery defines the query which retrieves all usernames from the database which is configured for jdbc realm. In our case tutorialsdb will be used.
  - rolesQuery defines the roles defined for user which is authenticated.

Configuration is done for jBoss application server.
