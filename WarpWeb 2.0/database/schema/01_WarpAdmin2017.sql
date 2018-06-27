/* 
CREATE Schema WarpAdmin2017;
*/
-- DROP DATABASE IF EXISTS WarpAdmin2017;
-- CREATE DATABASE WarpAdmin2017 CHARACTER SET utf8 COLLATE utf8_general_ci;
USE  WarpAdmin2017;

CREATE TABLE Version (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
	VersionNumber						NVARCHAR(20),
    Location							NVARCHAR(20)
	)
;

/*                                                                                                     */
/* Just an empty table used as a template for building new tables */
/*                                                                                                 
DROP TABLE IF EXISTS TableTemplate;
CREATE TABLE Entity (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1
    )
    
    ALTER TABLE tbl_name 
    ADD CONSTRAINT FOREIGN KEY (RecordStatusID)
    REFERENCES 
    
    Ctl = Control Table - full of values that will be referenced elsewhere.
    
    
    );
*/


/*******************************************************************************************************/
/* Begin Control Tables                                                                                */
/*******************************************************************************************************/
DROP TABLE IF EXISTS ctlPhoneType;    
CREATE TABLE ctlPhoneType (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
	PhoneTypeName						NVARCHAR(20)
	)
    AUTO_INCREMENT = 10000,
    ENGINE = InnoDB;

INSERT INTO ctlPhoneType (ID, PhoneTypeName) VALUES (1, 'Unknown');
INSERT INTO ctlPhoneType (ID, PhoneTypeName) VALUES (2, 'Cell');
INSERT INTO ctlPhoneType (ID, PhoneTypeName) VALUES (3, 'Not Cell');

DROP TABLE IF EXISTS ctlRecordStatus;    
CREATE TABLE ctlRecordStatus (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
	RecordStatusName					NVARCHAR(20),
    RecordStatusCode					NVARCHAR(4)
	)
    AUTO_INCREMENT = 10000,
    ENGINE = InnoDB;

INSERT INTO ctlRecordStatus (ID, RecordStatusName, RecordStatusCode) VALUES (10, 'ACTIVE', 'ACT');
INSERT INTO ctlRecordStatus (ID, RecordStatusName, RecordStatusCode) VALUES (20, 'INACTIVE', 'INA');
INSERT INTO ctlRecordStatus (ID, RecordStatusName, RecordStatusCode) VALUES (30, 'PENDING', 'PEN');			-- Record Pending approval or confirmation
INSERT INTO ctlRecordStatus (ID, RecordStatusName, RecordStatusCode) VALUES (80, 'PURGE', 'PURG');				-- Records to be Physically Deleted.
INSERT INTO ctlRecordStatus (ID, RecordStatusName, RecordStatusCode) VALUES (90, 'DELETED', 'DEL');			-- Records to be treated as Logically Deleted.
INSERT INTO ctlRecordStatus (ID, RecordStatusName, RecordStatusCode) VALUES (99, 'RESTRICTED', 'RES');
INSERT INTO ctlRecordStatus (ID, RecordStatusName, RecordStatusCode) VALUES (50, 'Receipt Identified', 'RCI');
INSERT INTO ctlRecordStatus (ID, RecordStatusName, RecordStatusCode) VALUES (51, 'Purchase Completed', 'PUR');

UPDATE ctlRecordStatus SET RecordStatusName = 'PURCHASE PENDING', RecordStatusCode = 'PPN' WHERE ID = 50;
UPDATE ctlRecordStatus SET RecordStatusName = 'PURCHASE COMPLETE', RecordStatusCode = 'PUR' WHERE ID = 51;

SELECT * FROM ctlRecordStatus;

DROP TABLE IF EXISTS ctlCommand;    
CREATE TABLE ctlCommand (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    CommandName							NVARCHAR(100)
	);

INSERT INTO ctlCommand (ID, CommandName) VALUES (1, 'RegisterUserAccount');
INSERT INTO ctlCommand (ID, CommandName) VALUES (2, 'GeneratePasswordResetToken');
INSERT INTO ctlCommand (ID, CommandName) VALUES (3, 'GenerateNotification');

SELECT * FROM ctlCommand;

DROP TABLE IF EXISTS ctlEntityType;    
CREATE TABLE ctlEntityType (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    EntityTypeName						NVARCHAR(100)
	);

ALTER TABLE ctlEntityType 
    ADD CONSTRAINT FOREIGN KEY (RecordStatusID)
    REFERENCES ctlRecordStatus(ID);  

INSERT INTO ctlEntityType (ID, EntityTypeName) VALUES (1, 'Master'); 
INSERT INTO ctlEntityType (ID, EntityTypeName) VALUES (2, 'Client'); 
INSERT INTO ctlEntityType (ID, EntityTypeName) VALUES (3, 'Customer'); 
INSERT INTO ctlEntityType (ID, EntityTypeName) VALUES (4, 'Group'); 
INSERT INTO ctlEntityType (ID, EntityTypeName) VALUES (5, 'Contact');   
INSERT INTO ctlEntityType (ID, EntityTypeName) VALUES (6, 'UserAccount');  

SELECT * FROM ctlEntityType;

DROP TABLE IF EXISTS ctlEntityNameType;    
CREATE TABLE ctlEntityNameType (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    EntityNameTypeName					NVARCHAR(100)
	);

INSERT INTO ctlEntityNameType (ID, EntityNameTypeName) VALUES (1, 'Legal Name');
INSERT INTO ctlEntityNameType (ID, EntityNameTypeName) VALUES (2, 'Member Name'); /* Member name is Signon Account Name */
INSERT INTO ctlEntityNameType (ID, EntityNameTypeName) VALUES (3, 'First Name');
INSERT INTO ctlEntityNameType (ID, EntityNameTypeName) VALUES (4, 'Middle Name');
INSERT INTO ctlEntityNameType (ID, EntityNameTypeName) VALUES (5, 'Last Name');

SELECT * FROM ctlEntityNameType;

DROP TABLE IF EXISTS ctlEContactType;    
CREATE TABLE ctlEContactType (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    EContactTypeName					NVARCHAR(100)
);

INSERT INTO ctlEContactType (ID, EContactTypeName) VALUES (1, "eMail");
INSERT INTO ctlEContactType (ID, EContactTypeName) VALUES (2, "SMS");
INSERT INTO ctlEContactType (ID, EContactTypeName) VALUES (3, "Facebook");
INSERT INTO ctlEContactType (ID, EContactTypeName) VALUES (4, "Twitter");
INSERT INTO ctlEContactType (ID, EContactTypeName) VALUES (5, "YouTube");
INSERT INTO ctlEContactType (ID, EContactTypeName) VALUES (6, "Instagram");
INSERT INTO ctlEContactType (ID, EContactTypeName) VALUES (7, "Google+");
INSERT INTO ctlEContactType (ID, EContactTypeName) VALUES (8, "LinkedIn");
INSERT INTO ctlEContactType (ID, EContactTypeName) VALUES (9, "WeChat");
INSERT INTO ctlEContactType (ID, EContactTypeName) VALUES (10, "Weibo");
INSERT INTO ctlEContactType (ID, EContactTypeName) VALUES (11, "QQ");

DROP TABLE IF EXISTS ctlBillingEventType;    
CREATE TABLE ctlBillingEventType (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    BillingEventName					NVARCHAR(100),
    BillingEventDescr					NVARCHAR(100)
);


/* ISO-3166-1 Country Code Information */
DROP TABLE IF EXISTS ctlCountry;    
CREATE TABLE ctlCountry (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 					DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    CustomEntry							BOOLEAN,
    CountryNameEng						NVARCHAR(255),
    Alpha2Code							NVARCHAR(255),
    Alpha3Code							NVARCHAR(255),
    NumericCode							BIGINT
    );


-- INSERT INTO ctlCountry (ID, CountryNameEng, Alpha2Code, Alpha3Code, NumericCode)
LOAD DATA LOCAL INFILE '/Users/jarp/Dropbox/Personal/WARP Worldwide/WARP Source/JWeb/SQL/DbBuild/CountryCodes.txt' 
INTO TABLE ctlCountry  
    FIELDS TERMINATED BY ',' 
           OPTIONALLY ENCLOSED BY '"'
    LINES  TERMINATED BY '\n'
(ID, CountryNameEng, Alpha2Code, Alpha3Code, NumericCode);


/* ISO 3166-2 Country Sub-Divison Information */
DROP TABLE IF EXISTS CountrySubdivision;    
CREATE TABLE CountrySubdivision (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    SuvDivisionCode						NVARCHAR(255)
    );

DROP TABLE IF EXISTS cltPostalCode;    
CREATE TABLE ctlPostalCode (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
	PostalCode1							NVARCHAR(255),
    PostalCode2							NVARCHAR(255)
);



/* ISO 639-1 Language Codes */
-- The Accept-Language Header is in the format ISO 639-1 Language Code, optionally followed by "-" and the ISO 3166-2 Country Code.


/*******************************************************************************************************/
/* End Control Tables                                                                                  */
/*******************************************************************************************************/


/*******************************************************************************************************/
/* Begin Billing Tables                                                                                */
/*******************************************************************************************************/


DROP TABLE IF EXISTS BillingEvent;    
CREATE TABLE BillingEvent (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    BillingEventTypeID 					BIGINT,
    EventEntityID						BIGINT
);
    
/*******************************************************************************************************/
/* End Billing Tables                                                                                  */
/*******************************************************************************************************/

    

/*******************************************************************************************************/
/* Begin HIerarchy Tables                                                                              */
/*******************************************************************************************************/
/* Any Entity type can participate in the Hierarchy table, but only Entity Types can do so.            */
/* http://mikehillyer.com/articles/managing-hierarchical-data-in-mysql/                                */
/*-----------------------------------------------------------------------------------------------------*/

DROP TABLE IF EXISTS Hierarchy;    
CREATE TABLE Hierarchy (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
	HierarchyName						NVARCHAR(100),
    HieararchyDescr 					NVARCHAR(1000)
	);
    
/* 
	Note that A ChildEntity can have only one ParentEntityID in any given hierarchy, 
	but a ParentEntityID may have unlimited ChildEnityIDs
*/

DROP TABLE IF EXISTS HierarchyXref;
CREATE TABLE HierarchyXref (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    HierarchyID							BIGINT UNIQUE,
	ParentEntityID						BIGINT,
    ChildEntityID						BIGINT
); 
/*  HierarchyID; ParentEntityID;     ChildEntityID	are compound unique index. They are FK back to their individual tables */
    

/*******************************************************************************************************/
/* End Hierarchy Tables                                                                                  */
/*******************************************************************************************************/


DROP TABLE IF EXISTS Entity;
CREATE TABLE Entity (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
	EntityTypeID 						BIGINT
    );
    
INSERT INTO Entity (ID, RecordStatusID, EntityTypeID) VALUES (1, 10, 1);
    
SELECT * FROM Entity;    
    
    
DROP TABLE IF EXISTS EntityCountry;
CREATE TABLE EntityCountry (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    EntityID							BIGINT,
    CountryID							BIGINT
);    
    
DROP TABLE IF EXISTS Passphrase;    
CREATE TABLE Passphrase (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    EntityID							BIGINT, 
    PassphraseHash						NVARCHAR(255)
	);
	
DROP TABLE IF EXISTS PassphraseResetToken;    
CREATE TABLE PassphraseResetToken (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    EntityID							BIGINT, 
    PassphraseResetToken				NVARCHAR(255),
    ExpirationDate						DATETIME DEFAULT CURRENT_TIMESTAMP
	);
    
ALTER TABLE PassphraseResetToken
	ADD CONSTRAINT uniqueToken UNIQUE (PassphraseResetToken);

DROP TABLE IF EXISTS AuthToken; 
CREATE TABLE AuthToken (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    EntityID							BIGINT, 
    AuthToken							NVARCHAR(255),
    ExpirationDate						DATETIME DEFAULT CURRENT_TIMESTAMP
	);
    
ALTER TABLE AuthToken 
	ADD CONSTRAINT uniqueToken UNIQUE (AuthToken);



DROP TABLE IF EXISTS EntityName;    
CREATE TABLE EntityName (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    EntityID							BIGINT, 
    EntityNameTypeID					BIGINT, 
    EntityName							NVARCHAR(50),
    EntityNameKey						NVARCHAR(100), /* Used to enforce unique constraint on specific entity types in the uniqueName constraint */
    DefaultName							NVARCHAR(1) DEFAULT 'N'
	);
   
/* This constraint is used to enforce uniqueness on specific EntityName Types. For those that don't require uniquenss,  
   a UUIDis appended to the name and inserted in this column. */
ALTER TABLE EntityName
	ADD CONSTRAINT uniqueName UNIQUE (EntityNameTypeID, EntityNameKey);
   
INSERT INTO EntityName (ID, EntityID, EntityNameTypeID, EntityName, DefaultName) VALUES (1, 1, 1, 'WARP Worldwide, LLC', 'Y');

SELECT * FROM EntityName;   
SELECT * FROM ctlEntityNameType;
SELECT * FROM Entity;
SELECT * FROM ctlEntityType;

/* 
Use no more than 5 lines, including:
Addressee's name.
Street address or P.O. box number.
City or town, principal subdivision such as province, state, or county, and postal code. In some countries, the postal code may precede the city or town.
Country name.
https://blink.ucsd.edu/facilities/services/mail/international/addressing/index.html

~ http://www.columbia.edu/~fdc/postal/ ~
Frank's Compulsive Guide to Postal Addresses


As a basis for discussion, let's begin by looking at a typical international address:

JOE BLOGGS							Person's name
COMPUTER CENTER						Department (if any)
CURTIN UNIVERSITY OF TECHNOLOGY    	Institution or Company (if any)
309 KENT STREET						Street Address (or Post Office Box)
BENTLEY WA  6102					City Line (WA = Western Australia)
AUSTRALIA							Country Name
It illustrates several points, all of which are discussed later in greater detail:

*/


DROP TABLE IF EXISTS PostalAddress;    
CREATE TABLE PostalAddress (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
	ContactTypeID						BIGINT,
	CityID								BIGINT, 
    PostalCodeID						BIGINT
);

DROP TABLE IF EXISTS PostalAddressLine;    
CREATE TABLE PostalAddressLine (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
	PostalAddressID						BIGINT,
    PostalAddressLineSeq				BIGINT,
	PostalAddressLine					NVARCHAR(255)
);

DROP TABLE IF EXISTS City;    
CREATE TABLE City (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    CityName							NVARCHAR(100),
	StateProvID							BIGINT,
	CountryID							BIGINT
);

DROP TABLE IF EXISTS Phone;        
CREATE TABLE Phone (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    ContactTypeID						BIGINT,
    PhoneTypeID							BIGINT,
	PhoneNumber							NVARCHAR(100),
	CountryID							BIGINT
    );

DROP TABLE IF EXISTS EntityPhone;
CREATE TABLE EntityPhone (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    EntityID							BIGINT, 
    PhoneID								BIGINT
);
        
DROP TABLE IF EXISTS eContact;        
CREATE TABLE eContact (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    EntityID							BIGINT,
    ContactTypeID						BIGINT,
	eContactIdentifier					NVARCHAR(255),
	CountryID							BIGINT
    );

 DROP TABLE IF EXISTS Role;
 CREATE TABLE Role (
 	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
	RoleName							NVARCHAR(100),
    AllowInheritance					BOOL
	);

-- Which Roles does this role inherit from
 DROP TABLE IF EXISTS RoleInheritance;
 CREATE TABLE RoleInheritance (
 	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
	RoleIDParent						BIGINT,
    RoleIDChild							BIGINT
	);

-- User Role creates connection between user and role. Connection is many-to-many.
 DROP TABLE IF EXISTS UserRole;
 CREATE TABLE UserRole (
 	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
	UserID								BIGINT, 
    RoleID								BIGINT
	);

-- No Group table for roles,just user hierarchies?


-- Products are the compontents of a solution. Only Solutions are sold, not individual products.    
 DROP TABLE IF EXISTS Product;
 CREATE TABLE Product (
 	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    ProductCode							NVARCHAR(25) NOT NULL,
    ProductExternalKey					NVARCHAR(255) NULL, 
	ProductName							NVARCHAR(100) NOT NULL,
    ProductCost							DECIMAL(19,4) NOT NULL
	);
    
    
-- Solutions are what we sell. A solution is made up of 1 or more products.    
 DROP TABLE IF EXISTS Solution;
 CREATE TABLE Solution (
 	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    SolutionCode						NVARCHAR(25) NOT NULL,
	SolutionName						NVARCHAR(100) NOT NULL,
    SolutionCost						DECIMAL(19,4) NOT NULL
	);
        

 -- Describes which products are part of a solution. It is possible that a solution has only one product.       
 DROP TABLE IF EXISTS SolutionProduct;
 CREATE TABLE SolutionProduct (
 	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    SolutionCode						NVARCHAR(25) NOT NULL,
    ProductCode							NVARCHAR(25) NOT NULL
	);       

-- EntitySolution shows which Entities have access to which Solutions, and which billing method is assigned.     
 DROP TABLE IF EXISTS EntitySolution;
 CREATE TABLE EntitySolution (
 	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
	SolutionID							BIGINT, 
    EntityID							BIGINT,
    BillingEventID						BIGINT,
    StartDate							DATETIME
	);
    
    
DROP TABLE IF EXISTS Notification;
CREATE TABLE Notification (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    EntityID							BIGINT,
	eContactTypeID 						BIGINT,
    eContactAddress						NVARCHAR(255), 
    MessageCode							NVARCHAR(10),
    MessageText							NVARCHAR(255)
    );

DROP TABLE IF EXISTS CampData;
CREATE TABLE CampData (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    travelerName						NVARCHAR(512), 
    parentName							NVARCHAR(512),
    travelerAddress						NVARCHAR(512),
    travelerPrimaryPhoneNumber			NVARCHAR(512),
    travelerAlternatePhoneNumber		NVARCHAR(512),
    travelerEMailAddress				NVARCHAR(512),
    travelerGender						NVARCHAR(512),
    travelerDateOfBirth					NVARCHAR(512),
    travelerComments					NVARCHAR(1024),
    paymentType							NVARCHAR(512),
    paymentAmountText					NVARCHAR(512),
    paymentAmount						NVARCHAR(512),
    paymentDescription					NVARCHAR(1024),
    paymentNotes						NVARCHAR(1024)
);

DROP TABLE IF EXISTS StripeCampData;
CREATE TABLE StripeCampData (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    stripeToken							NVARCHAR(512), 
    stripeTokenType						NVARCHAR(512),
    stripeEmail							NVARCHAR(512),
    stripeBillingName					NVARCHAR(512),
    stripeBillingAddressCountry			NVARCHAR(512),
    stripeBillingAddressCountryCode		NVARCHAR(512),
    stripeBillingAddressZip				NVARCHAR(512),
    stripeBillingAddressLine1			NVARCHAR(512),
    stripeBillingAddressCity			NVARCHAR(512)
);

DROP TABLE IF EXISTS ShoppingCart;
CREATE TABLE ShoppingCart (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    EntityID							BIGINT NOT NULL, 
    SolutionID							BIGINT NOT NULL,
    BillingEventID						BIGINT NOT NULL,
    Quantity							BIGINT, 
    PaymentDate							DATETIME, 
    ReceiptNumber						NVARCHAR(100)
);


-- EntitySolution shows which Entities have access to which Solutions, and which billing method is assigned.     
 DROP TABLE IF EXISTS XXit;
 CREATE TABLE XXit (
 	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1
	);
    
    
  SELECT 'Build Complete. Add Stored Procedures.'  