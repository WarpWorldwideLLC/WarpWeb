

DROP PROCEDURE IF EXISTS setEllSolutionSso;

DELIMITER $$
CREATE PROCEDURE setEllSolutionSso(query JSON)
BEGIN

	DECLARE AuID BIGINT DEFAULT NULL;
	DECLARE IuID BIGINT DEFAULT NULL;
    DECLARE MemberID BIGINT DEFAULT NULL;
    DECLARE Command NVARCHAR(255) DEFAULT NULL;
    DECLARE SsoUri NVARCHAR(255) DEFAULT NULL;

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
    
    SET SsoUri := JSON_UNQUOTE(JSON_EXTRACT(query, '$.SsoUri'));
    
    UPDATE EntitySolution
    SET ProductExternalKey = CONCAT('LGO:',SsoUri)
	WHERE EntityID = MemberID
		AND ProductExternalKey LIKE 'LGO:%'
	;
    
    SELECT DISTINCT JSON_OBJECT(
        'MemberID', es.EntityID, 
        'ProductExternalKey', es.ProductExternalKey
	) AS CommandResult
	FROM EntitySolution es
	WHERE es.EntityID = MemberID
		AND es.ProductExternalKey LIKE 'LGO:%'

	;


END $$
DELIMITER ;