USE  WarpAdmin2017;

/* The first 10,000 ID Values are reserved for SystemValues. User entered values start at 10,000 and increment from there. */

/* Default Values for the EnityType table. */
INSERT INTO EntityType (ID, CreateUserID, LastModifyUserID, EntityTypeName) VALUES (0, 0, 0, 'WARP'); 			/* WARP is a unique Entity */
INSERT INTO EntityType (ID, CreateUserID, LastModifyUserID, EntityTypeName) VALUES (1, 0, 0, 'Client'); 		/* Paying Customer */
INSERT INTO EntityType (ID, CreateUserID, LastModifyUserID, EntityTypeName) VALUES (2, 0, 0, 'User');			/* individual User of one or more products */
INSERT INTO EntityType (ID, CreateUserID, LastModifyUserID, EntityTypeName) VALUES (3, 0, 0, 'Service');		/* Service or product being accessed */
INSERT INTO EntityType (ID, CreateUserID, LastModifyUserID, EntityTypeName) VALUES (4, 0, 0, 'Prospect');		/* Engaged Sales Target */
INSERT INTO EntityType (ID, CreateUserID, LastModifyUserID, EntityTypeName) VALUES (5, 0, 0, 'Lead');			/* Sales Target not yet engaged */

/* Default Values for the EnityNameType table. */
INSERT INTO EntityNameType(ID, CreateUserID, LastModifyUserID, EntityNameTypeName) VALUES (1, 0, 0, 'Company Legal Name');
INSERT INTO EntityNameType(ID, CreateUserID, LastModifyUserID, EntityNameTypeName) VALUES (2, 0, 0, 'Given Name');
INSERT INTO EntityNameType(ID, CreateUserID, LastModifyUserID, EntityNameTypeName) VALUES (3, 0, 0, 'Middle Name');
INSERT INTO EntityNameType(ID, CreateUserID, LastModifyUserID, EntityNameTypeName) VALUES (4, 0, 0, 'Family Name');
INSERT INTO EntityNameType(ID, CreateUserID, LastModifyUserID, EntityNameTypeName) VALUES (5, 0, 0, 'DBA Name');

/* Default Value(s) for the Entity table - Entity 0 is WARP. */
INSERT INTO Entity (ID, EntityTypeID, CreateDate, CreateUserID, LastModifiyDate, LastModifyUserID)
  VALUES (0, 0, NOW(), 0, NOW(), 0);

/* Default Value(s) for the EntityName table */
INSERT INTO EntityName (ID, EntityID, EntityNameTypeID, EntityName)
  VALUES (0, 0, 1, 'WARP Worldwide, LLC');

/* Now do the address for WARP */

/* http://www.mysqltutorial.org/calling-mysql-stored-procedures-from-jdbc/ */