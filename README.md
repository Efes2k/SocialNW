#EJB/JPA/JSF/Primefaces project

Social Network Application

This sample aims to demonstrate the simple possible EJB-based Java webapp.
Here we walk through the entire content of the application.

**It includes:**

- Maven for dependency management.
- EJB, JSF, Primefaces.
- MySQL database.
- JPA for working with DB.
- Logger log4j.
- JBoss Wildfly 8.2.0

**Functionality:**

- New user registration.
- Authorization and authentication.
- The roles(Admin/User) privileges (including user blocking in admin's page).
- Searching users(including autocomplite search).
- Ability to change user settings.
- Ability to add/remove friends.
- Ability to add/remove messages on your own and other users pages(images,audio).
- Ability to add/remove comments on messages.
- Gallery with your pictures.
- Password encryption SHA-256.


**Working with application in Eclipse/STS.**

The following items should be installed in your system:
- Maven 3 (https://www.sonatype.com/books/mvnref-book/reference/installation.html)
- Git command line tool (https://help.github.com/articles/set-up-git)
- Eclipse with the m2e plugin (m2e is installed by default when using the STS 
       (http://www.springsource.org/sts) distribution of Eclipse)


1) In the command line

    git clone https://github.com/Efes2k/SocialNW.git

2) Inside Eclipse

    File -> Import -> Maven -> Existing Maven project
    
3) For JBoss configuration see JBossSetting.txt    
    
4) Execute script 

    location.sql
    roles.sql
