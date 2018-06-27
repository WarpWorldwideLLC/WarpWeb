

DROP PROCEDURE IF EXISTS addEntityEllUserId;

DELIMITER $$
CREATE PROCEDURE addEntityEllUserId(query JSON)
BEGIN

	DECLARE AuID BIGINT DEFAULT NULL;
	DECLARE IuID BIGINT DEFAULT NULL;
    DECLARE MemberID BIGINT DEFAULT NULL;
    DECLARE Command NVARCHAR(255) DEFAULT NULL;
    DECLARE MiscKey NVARCHAR(255) DEFAULT NULL;
    DECLARE MiscValue NVARCHAR(255) DEFAULT NULL;
    DECLARE Identity BIGINT DEFAULT NULL;


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

	SET AuID := JSON_EXTRACT(query, '$.AuID');
    SET IuID := JSON_EXTRACT(query, '$.PuID');
    SET MemberID := JSON_EXTRACT(query, '$.MemberID');
    SET Command := JSON_EXTRACT(query, '$.Command');
    
    SET MiscKey := JSON_UNQUOTE(JSON_EXTRACT(query, '$.MiscKey'));
    SET MiscValue := JSON_UNQUOTE(JSON_EXTRACT(query, '$.MiscValue'));
    
	INSERT INTO EntityMiscellany (AuID, IuID, LastAuID, LastIuID, EntityID, MiscKey, MiscValue)
    VALUES (AuID, IuID, AuID, IuID, MemberID, MiscKey, MiscValue);
    
    SET Identity := LAST_INSERT_ID();
    
	SELECT GROUP_CONCAT(JSON_OBJECT(
                     'Command', Command, 
                     'CommandResults', ProcStatus, 
                     'EntiyMiscellanyID', ID,
                     'MemberNumber', EntityID,
                     'MiscKey', MiscKey, 
                     'MiscValue', MiscValue
				) ) AS CommandResult
                FROM EntityMiscellany 
                WHERE ID = Identity
    
    ;
    



END $$
DELIMITER ;