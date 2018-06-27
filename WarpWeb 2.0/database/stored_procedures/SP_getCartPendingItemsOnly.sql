
DROP PROCEDURE IF EXISTS getCartPendingItemsOnly;

DELIMITER $$
CREATE PROCEDURE getCartPendingItemsOnly(query JSON)
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
                     'Command', Command, 
                     'CommandResults', ProcStatus, 
                     'CartID', sc.ID,
                     'MemberNumber', sc.EntityID,
                     'MemberName', mn.EntityName, 
                     'SolutionID', sc.SolutionID,
                     'SolutionName', s.SolutionName, 
                     'SolutionCode', s.SolutionCode, 
                     'SolutionCost', s.SolutionCost,
                     'ProductID', p.ID, 
                     'ProdctName', p.ProductName, 
                     'ProductCode', p.ProductCode,
                     'ReceiptNumber', sc.ReceiptNumber
				) ) AS CommandResult
	FROM ShoppingCart sc
	  LEFT JOIN EntityName mn
		ON mn.ID = sc.EntityID
		  AND mn.EntityNameTypeID = 2
	  LEFT JOIN Solution s
		ON s.ID = sc.SolutionID
	  LEFT JOIN SolutionProduct sp
		ON s.SolutionCode = sp.SolutionCode
	  LEFT JOIN Product p
		ON sp.ProductCode = p.ProductCode 
	WHERE sc.RecordStatusID IN (50)
      AND sc.EntityID = MemberID
	ORDER BY sc.ID
        
		;
        
END $$
DELIMITER ;