
DROP PROCEDURE IF EXISTS getSolutionList;

DELIMITER $$
CREATE PROCEDURE getSolutionList(query JSON)
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

	SET AuID := JSON_EXTRACT(query, '$.AuID');
    SET IuID := JSON_EXTRACT(query, '$.PuID');

	SELECT JSON_OBJECT(
                     'SolutionCode', s.SolutionCode, 
                     'ProductCode', p.ProductCode, 
                     'SolutionName', s.SolutionName, 
                     'ProductName', p.ProductName, 
                     'SolutionCost', s.SolutionCost
				) AS CommandResult
	FROM Solution s
		INNER JOIN SolutionProduct sp
		  ON s.SolutionCode = sp.SolutionCode
		LEFT JOIN Product p
		  ON sp.ProductCode = p.ProductCode
	;
        
		
        
END $$
DELIMITER ;