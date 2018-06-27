DROP PROCEDURE IF EXISTS adminGetColumns;

DELIMITER $$
CREATE PROCEDURE adminGetColumns()
BEGIN

	DECLARE AuID BIGINT DEFAULT NULL;
	DECLARE IuID BIGINT DEFAULT NULL;


	-- Error and Warning Block Variables 
	DECLARE ProcStatus NVARCHAR(10) DEFAULT 'SUCCESS';
    DECLARE ProcMessage NVARCHAR(999) DEFAULT '';
	DECLARE lSqlState NVARCHAR(255) DEFAULT '';
    DECLARE lErrNumber NVARCHAR(255) DEFAULT '';
	DECLARE lMessageText NVARCHAR(255) DEFAULT '';

	DECLARE exit handler for sqlexception
	  BEGIN
		-- ERROR
        GET DIAGNOSTICS CONDITION 1 
			lSqlState = RETURNED_SQLSTATE,
			lErrNumber = MYSQL_ERRNO,
			lMessageText = MESSAGE_TEXT;
		SET ProcStatus := 'ERROR';
		SET ProcMessage := CONCAT(lErrNumber, " (", lSqlState, "): ", lMessageText);
		SELECT JSON_OBJECT('MessageSource', 'DB0', 'CommandResults', ProcStatus, 'MessageCode', lErrNumber, 'ProcMessage', ProcMessage);

	  ROLLBACK;
	END;

	DECLARE exit handler for sqlwarning
	 BEGIN
		-- WARNING
	 ROLLBACK;
	END;

	-- group_concat defaults to 1024 charaters; expand it for this query. 
	SET SESSION group_concat_max_len := 1000000;

	/*
	SET AuID := JSON_EXTRACT(query, '$.AuID');
    SET IuID := JSON_EXTRACT(query, '$.PuID');
	*/

		SELECT
			TABLE_NAME, COLUMN_NAME, ORDINAL_POSITION, COLUMN_DEFAULT, IS_NULLABLE, 
            DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, CHARACTER_SET_NAME, COLLATION_NAME, 
            COLUMN_TYPE, COLUMN_KEY 
		FROM information_schema.columns 
		WHERE TABLE_SCHEMA = 'WarpAdmin2017'
        
		;
        
END $$
DELIMITER ;