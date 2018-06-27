
DROP PROCEDURE IF EXISTS setMemberSolution;

DELIMITER $$
CREATE PROCEDURE setMemberSolution(query JSON)
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
    
    -- DELETE FROM EntitySolution WHERE EntityID = MemberID AND ID > 0;

	-- Create EntitySolution records from ShoppingCart Records.
	INSERT INTO EntitySolution (SolutionID, EntityID, BillingEventID, KeySet, StartDate)
	SELECT SolutionID, EntityID, 0, 0, MAX(CreateDate) AS StartDate
	FROM ShoppingCart
	WHERE RecordStatusID = 51
	  AND EntityID = MemberID
      AND ID > 0
	GROUP BY SolutionID, EntityID
	;
    

    -- Update the hyperlink for each product the user has in their solutions list,
    -- but only if the key has not already been set. 
    -- This record will be customized in code later. 
	SET SQL_SAFE_UPDATES = 0;
	UPDATE EntitySolution es
		INNER JOIN Solution s
          ON es.SolutionID = s.ID
		INNER JOIN SolutionProduct sp
          ON s.SolutionCode = sp.SolutionCode
		INNER JOIN Product p
          ON sp.ProductCode = p.ProductCode
	SET es.KeySet = 1,
		es.ProductExternalKey =p.ProductExternalKey
	WHERE es.ID > 0
      AND es.EntityID = MemberID
      AND es.KeySet = 0
	;
    SET SQL_SAFE_UPDATES = 1;
    
    
    -- Set ShoppingCart Status to resolved. 
    UPDATE ShoppingCart
    SET RecordStatusID = 999
	WHERE RecordStatusID = 51
	  AND EntityID = MemberID
      AND ID > 0
	;
    
    -- Add the link template to EntitySolution 
    
	SELECT JSON_OBJECT(
                     'MemberID', MemberID,
                     'Command', Command,
                     'CommandResults', ProcStatus,
                     'Count', COUNT(*)
				) AS CommandResult
	FROM EntitySolution es
	WHERE es.EntityID = MemberID

	;
        
END $$
DELIMITER ;