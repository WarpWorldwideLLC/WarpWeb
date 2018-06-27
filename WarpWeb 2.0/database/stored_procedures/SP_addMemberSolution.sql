
DROP PROCEDURE IF EXISTS addMemberSolution;

DELIMITER $$
CREATE PROCEDURE addMemberSolution(query JSON)
BEGIN

	DECLARE AuID BIGINT DEFAULT NULL;
	DECLARE IuID BIGINT DEFAULT NULL;
    DECLARE MemberID BIGINT DEFAULT NULL;
    DECLARE SolutionID BIGINT DEFAULT NULL;


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
    SET SolutionID := JSON_EXTRACT(query, '$.SolutionID');
    
	INSERT INTO EntitySolution (SolutionID, EntityID, BillingEventID) VALUES (1, 2, 0);

	SELECT JSON_OBJECT(
                     'MemberID', MemberID,
                     'SolutionID', SolutionID,
                     'CommandResults', ProcStatus
				) AS CommandResult
        
		;
        
END $$
DELIMITER ;