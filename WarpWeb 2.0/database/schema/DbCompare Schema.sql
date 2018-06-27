/* Compare Database */

DROP DATABASE IF EXISTS DbCompare;
CREATE DATABASE DbCompare CHARACTER SET utf8 COLLATE utf8_general_ci;
USE  DbCompare;


CREATE TABLE ColumnCompare (
			TABLE_NAME				NVARCHAR(255), 
            COLUMN_NAME				NVARCHAR(255), 
            ORDINAL_POSITION 		BIGINT, 
            COLUMN_DEFAULT    		NVARCHAR(255), 
            IS_NULLABLE 			NVARCHAR(100), 
            DATA_TYPE 				NVARCHAR(100),  
            CHARACTER_MAXIMUM_LENGTH BIGINT, 
            CHARACTER_SET_NAME 		NVARCHAR(100), 
            COLLATION_NAME 			NVARCHAR(100), 
            COLUMN_TYPE 			NVARCHAR(100), 
            COLUMN_KEY 				NVARCHAR(100) 
);
