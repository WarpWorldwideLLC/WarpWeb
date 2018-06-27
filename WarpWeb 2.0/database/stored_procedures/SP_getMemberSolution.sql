
DROP PROCEDURE IF EXISTS getMemberSolution;

DELIMITER $$
CREATE PROCEDURE getMemberSolution(query JSON)
BEGIN

	DECLARE AuID BIGINT DEFAULT NULL;
	DECLARE IuID BIGINT DEFAULT NULL;
    DECLARE MemberID BIGINT DEFAULT NULL;
    DECLARE Command NVARCHAR(255) DEFAULT NULL;

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
    
	SELECT GROUP_CONCAT(JSON_OBJECT(
                     'MemberID', MemberID,
                     'Command', Command,
                     'CommandResults', ProcStatus,
                     'SolutionCode', s.SolutionCode,
                     'SolutionName', s.SolutionName,
                     'ProductCode', p.ProductCode, 
                     'ProductName', p.ProductName,
                     'ProductExternalKey', p.ProductExternalKey, 
                     'MemberSolutionExternalKey', es.ProductExternalKey,
                     'StartDate', es.StartDate
                     
				)) AS CommandResult
	FROM EntitySolution es
	  LEFT JOIN Solution s
		ON es.SolutionID = s.ID
	  LEFT JOIN SolutionProduct sp
		ON s.SolutionCode = sp.SolutionCode
	  LEFT JOIN Product p
		ON sp.ProductCode = p.ProductCode
	WHERE es.EntityID = memberID

	;
        
END $$
DELIMITER ;