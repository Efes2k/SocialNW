CREATE TABLE `Country` (  
      `id` int(6) NOT NULL AUTO_INCREMENT,  
	    `country_name` varchar(200) NOT NULL,  
      PRIMARY KEY (`id`)  
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;  
	
CREATE TABLE `city` (  
      `id` int(6) NOT NULL AUTO_INCREMENT,  
	    `city_name` varchar(200) NOT NULL,  
  	  `country_id` int(6) NOT NULL,	  
	  FOREIGN KEY (`country_id`) REFERENCES Country(id), 
      PRIMARY KEY (`id`)  
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;  
	
INSERT INTO `country` (`country_name`) VALUES ('Belarus');
INSERT INTO `country` (`country_name`) VALUES ('Russia');
INSERT INTO `country` (`country_name`) VALUES ('Ukraine');

INSERT INTO `city` (`city_name`, `country_id`) VALUES ('Minsk', 
(SELECT `id` FROM `country` WHERE country_name = "Belarus")
);
INSERT INTO `city` (`city_name`, `country_id`) VALUES ('Brest', 
(SELECT `id` FROM `country` WHERE country_name = "Belarus")
);
INSERT INTO `city` (`city_name`, `country_id`) VALUES ('Moskow', 
(SELECT `id` FROM `country` WHERE country_name = "Russia")
);
INSERT INTO `city` (`city_name`, `country_id`) VALUES ('S-Peterburg', 
(SELECT `id` FROM `country` WHERE country_name = "Russia")
);
INSERT INTO `city` (`city_name`, `country_id`) VALUES ('Kiev', 
(SELECT `id` FROM `country` WHERE country_name = "Ukraine")
);
INSERT INTO `city` (`city_name`, `country_id`) VALUES ('Harkov', 
(SELECT `id` FROM `country` WHERE country_name = "Ukraine")
);

