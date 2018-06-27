USE WarpAdmin2017;

SHOW VARIABLES LIKE "%version%";

SELECT * FROM Version;

/*
INSERT INTO Version (ID, CreateDate, VersionNumber, Location) 
VALUES (2, '2018-04-16', '2018.04.16.1500', 'DEV-000');
*/

-- User Role creates connection between user and role. Connection is many-to-many.
 DROP TABLE IF EXISTS UserRole;
 DROP TABLE IF EXISTS EntityRole;
 CREATE TABLE EntityRole (
 	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
	EntityID							BIGINT, 
    RoleID								BIGINT,
    ExpirationDate						DATETIME DEFAULT '9999-12-31'
	);
    
    INSERT INTO EntityRole (EntityID, RoleID) VALUES(3, 1);
    
    SELECT * FROM EntityRole;
    
    SELECT ProductCode, ProductName
    FROM Product;
    
    SELECT CONCAT('product.name.', ProductCode, ' = ', ProductName)
    FROM Product;
    
	SELECT CONCAT('solution.name.', SolutionCode, ' = ', SolutionName)
    FROM Solution;
    
    SELECT * FROM Solution;
    
    
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
    ProductExternalKey					NVARCHAR(255),
    KeySet								INT,
    BillingEventID						BIGINT DEFAULT 0,
    StartDate							DATETIME
	);
    
    
DROP TABLE IF EXISTS EntityBirthDate;    
CREATE TABLE EntityBirthDate (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    EntityID							BIGINT, 
    EntityBirthDate						NVARCHAR(50),
    EntityBirthDateKey					NVARCHAR(100) /* Used to enforce unique constraint on specific entity types in the uniqueName constraint */
	);
    
DROP TABLE IF EXISTS EntityExternalProductKeys;
CREATE TABLE EntityExternalProductKeys (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    EntityID							BIGINT, 
    ExternalKeyGroup					NVARCHAR(20),
    WarpProductKey						NVARCHAR(255),
    ExternalLookupKey					NVARCHAR(255),
    ExternalKeyValue					NVARCHAR(255),
    ExternalKeyExpired					NVARCHAR(1)
);




    
    
    
    
