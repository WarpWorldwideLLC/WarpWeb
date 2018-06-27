/* addEntitySolution */
/*
** Adds a product (Solution) for an entity. 
** 
**
*/

DROP PROCEDURE IF EXISTS addEntitySolution;

DELIMITER $$
CREATE PROCEDURE addEntitySolution(query JSON)
BEGIN 
--  Basic Variables common to all stored procs.
    DECLARE AuID BIGINT DEFAULT NULL;
	DECLARE IuID BIGINT DEFAULT NULL;
	
--  Proc Specific Variables    
    DECLARE MemberID BIGINT DEFAULT -1;
    DECLARE SolutionID BIGINT DEFAULT -1;
    DECLARE EntitySolutionID BIGINT DEFAULT -1;
	DECLARE BillingEventID BIGINT DEFAULT -1;

--  Error and Warning Block Variables 
	DECLARE ProcStatus NVARCHAR(10) DEFAULT 'SUCCESS';
    DECLARE ProcMessage NVARCHAR(999) DEFAULT '';
	DECLARE lSqlState NVARCHAR(255) DEFAULT '';
    DECLARE lErrNumber NVARCHAR(255) DEFAULT '';
	DECLARE lMessageText NVARCHAR(255) DEFAULT '';

--  ERROR HANDLER
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	  BEGIN
        GET DIAGNOSTICS CONDITION 1
			lSqlState = RETURNED_SQLSTATE, 
			lErrNumber = MYSQL_ERRNO, 
            lMessageText = MESSAGE_TEXT;
		SET ProcStatus = 'ERROR';
		SET ProcMessage = CONCAT(lErrNumber, " (", lSqlState, "): ", lMessageText);
		SELECT JSON_OBJECT('MessageSource', 'DB0', 'ID', EntityID, 'ProcStatus', ProcStatus, 'MessageCode', lErrNumber, 'ProcMessage', ProcMessage);
	  ROLLBACK;
	END;

--  WARNING HANDLER
	DECLARE exit handler for sqlwarning
	  BEGIN
        GET DIAGNOSTICS CONDITION 1
			lSqlState = RETURNED_SQLSTATE, 
			lErrNumber = MYSQL_ERRNO, 
            lMessageText = MESSAGE_TEXT;
		SET ProcStatus = 'WARNING';
		SET ProcMessage = CONCAT(lErrNumber, " (", lSqlState, "): ", lMessageText);
		SELECT JSON_OBJECT('MessageSource', 'DB0', 'ID', EntityID, 'ProcStatus', ProcStatus, 'MessageCode', lErrNumber, 'ProcMessage', ProcMessage);
	  ROLLBACK;
	END;
    
	-- group_concat defaults to 1024 charaters; expand it for this query. 
	SET SESSION group_concat_max_len = 1000000;

	SET AuID := JSON_EXTRACT(query, '$.AuID');
    SET IuID := JSON_EXTRACT(query, '$.IuID');
 	SET MemberID := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.MemberID')));
	SET SolutionID := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.SolutionID')));
    SET BillingEventID := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.BillingEventID')));

  
	START TRANSACTION;
	-- Creating a new Member Entity
	-- Create the Entity record with type UserAccount 
	INSERT INTO EntitySolution (AuiD, IuID, LastAuID, LastIuID, SolutionID, EntityID, BillingEventID
    ) 
    VALUES (AuID, IuID, AuID, IuID, SolutionID, MemberID, BillingEventID
    );
    
	/* Get the EntityID and store it in @ID */
	SET EntitySolutionID := LAST_INSERT_ID();
    
  
    COMMIT;
	
    SELECT JSON_OBJECT('EntitySolutionID', EntitySolutionID, 'MemberID', MemberID, 'SolutionID', SolutionID, 'ProcStatus', ProcStatus, 'ProcMessage', ProcMessage) AS CommandResult;

END $$
DELIMITER ;