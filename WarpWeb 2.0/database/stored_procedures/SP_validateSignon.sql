
DROP PROCEDURE IF EXISTS validateSignon;

DELIMITER $$
CREATE PROCEDURE validateSignon(query JSON)
BEGIN

	DECLARE AuID BIGINT DEFAULT NULL;
	DECLARE IuID BIGINT DEFAULT NULL;
    DECLARE MemberName NVARCHAR(255);
    DECLARE PassphraseHash NVARCHAR(255);
    DECLARE AuthToken NVARCHAR(255);
    DECLARE AuthTokenTime DATETIME;

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
	SET MemberName := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.MemberName')));
    SET PassphraseHash := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.PassphraseHash')));

	SET AuthTokenTime := NOW();
    -- SET AuthToken := CONCAT((NOW() + 0), SHA1(DATE_FORMAT(NOW(), "%H%f%j%e%s")) ,SHA1(CONCAT(MemberName, PassphraseHash, NOW(0) +0)));
    SET AuthToken = 'foo';

	SELECT JSON_OBJECT(
                     'MemberNumber', e.ID,
                     'CommandResults', 'Success',
                     'MemberName', en0.EntityName ,
                     'FirstName', en1.EntityName,
                     'LastName', en2.EntityName,
                     'MemberSince', DATE_FORMAT(e.CreateDate, "%Y.%m.%d"), 
                     'AuthToken', AuthToken
				) AS CommandResult
		FROM Entity e 
			LEFT JOIN Passphrase p
			  ON e.ID = p.EntityID
			    AND p.RecordStatusID = 10
			LEFT JOIN EntityName en0
			  ON e.ID = en0.EntityID
				AND  en0.EntityNameTypeID = 2
				AND en0.RecordStatusID = 10
			LEFT JOIN EntityName en1
			  ON e.ID = en1.EntityID
				AND  en1.EntityNameTypeID = 3
				AND en1.RecordStatusID = 10
			LEFT JOIN EntityName en2
			  ON e.ID = en2.EntityID
				AND  en2.EntityNameTypeID = 5
				AND en2.RecordStatusID = 10
		WHERE e.EntityTypeID = 6
 		  AND en0.EntityName = MemberName
        
		;
        
END $$
DELIMITER ;