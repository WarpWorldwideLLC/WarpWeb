
DROP PROCEDURE IF EXISTS getGreeting;

DELIMITER $$
CREATE PROCEDURE getGreeting(query JSON)
BEGIN

	DECLARE AuID BIGINT DEFAULT NULL;
	DECLARE IuID BIGINT DEFAULT NULL;
    DECLARE MemberID BIGINT DEFAULT -1;
	DECLARE MemberGreeting NVARCHAR(255);

	-- Error and Warning Block Variables 
	DECLARE ProcStatus NVARCHAR(10) DEFAULT 'Success';
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

	SELECT JSON_OBJECT(
					 'CommandResults', ProcStatus,
                     'MemberNumber', e.ID,
                     'FirstName', en1.EntityName, 
                     'LastName', en2.EntityName,
                     'MemberSince', DATE_FORMAT(e.CreateDate, "%M %Y")
				) AS CommandResult
	FROM Entity e
	  INNER JOIN EntityName en1
		ON e.ID = en1.EntityID
		AND en1.EntityNameTypeID = 3
	  INNER JOIN EntityName en2
		ON e.ID = en2.EntityID
		AND en2.EntityNameTypeID = 5
    WHERE e.ID = MemberID    
		;
        
END $$
DELIMITER ;