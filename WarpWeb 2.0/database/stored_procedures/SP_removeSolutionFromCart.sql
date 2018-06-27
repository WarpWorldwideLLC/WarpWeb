/* addEntitySolution */
/*
** Adds a product (Solution) for an entity. 
** 
**
*/

DROP PROCEDURE IF EXISTS removeSolutionFromCart;

DELIMITER $$
CREATE PROCEDURE removeSolutionFromCart(query JSON)
BEGIN 
--  Basic Variables common to all stored procs.
    DECLARE AuID BIGINT DEFAULT NULL;
	DECLARE IuID BIGINT DEFAULT NULL;
    DECLARE Command NVARCHAR(255) DEFAULT NULL;
   
	
--  Proc Specific Variables    
    DECLARE MemberID BIGINT DEFAULT -1;
    DECLARE ShoppingCartID BIGINT DEFAULT -1;

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
		SELECT JSON_OBJECT('MessageSource', 'DB0', 'ID', EntityID, 'ProcStatus', ProcStatus, 'MessageCode', lErrNumber, 'ProcMessage', ProcMessage, 'CommandName', CommandName);
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
		SELECT JSON_OBJECT('MessageSource', 'DB0', 'ID', EntityID, 'ProcStatus', ProcStatus, 'MessageCode', lErrNumber, 'ProcMessage', ProcMessage, 'CommandName', CommandName);
	  ROLLBACK;
	END;
    
	-- group_concat defaults to 1024 charaters; expand it for this query. 
	SET SESSION group_concat_max_len = 1000000;

	SET AuID := JSON_EXTRACT(query, '$.AuID');
    SET IuID := JSON_EXTRACT(query, '$.IuID');
    SET Command := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.Command')));
 	SET MemberID := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.MemberID')));
	SET ShoppingCartID := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.CartID')));

	START TRANSACTION;
	-- Creating a new Member Entity
	-- Create the Entity record with type UserAccount 
	DELETE FROM ShoppingCart WHERE ID = ShoppingCartID AND EntityID = MemberID AND RecordStatusID IN (10, 50);
    
	  
    COMMIT;
    
    SELECT JSON_OBJECT('ShoppingCartID', ShoppingCartID, 'MemberID', MemberID, 'ProcStatus', ProcStatus, 'ProcMessage', ProcMessage, 'Command', Command) AS CommandResult;

END $$
DELIMITER ;