
/* Test Proc */
/*
** Inserts a row into a test table, reads it, then deletes it. 
** Used to verify that the application is functioning.
**
*/

DROP PROCEDURE IF EXISTS test;

DELIMITER $$
CREATE PROCEDURE test(query JSON)
BEGIN 
	DECLARE EntityID BIGINT DEFAULT -1;
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
		SET ProcStatus = 'ERROR';
		SET ProcMessage = CONCAT(lErrNumber, " (", lSqlState, "): ", lMessageText);
		SELECT JSON_OBJECT('MessageSource', 'DB0', 'ID', EntityID, 'ProcStatus', ProcStatus, 'MessageCode', lErrNumber, 'ProcMessage', ProcMessage);

	  ROLLBACK;
	END;

	DECLARE exit handler for sqlwarning
	 BEGIN
		-- WARNING
	 ROLLBACK;
	END;
    
	-- group_concat defaults to 1024 charaters; expand it for this query. 
	SET SESSION group_concat_max_len = 1000000;

  
    SET EntityID := 0;

	START TRANSACTION;
	-- Creating a new Member Entity
	-- Create the Entity record with type UserAccount 
	INSERT INTO StripeCampData (AuiD, IuID, LastAuID, LastIuID, stripeToken, stripeTokenType, stripeEmail, stripeBillingName, stripeBillingAddressCountry, stripeBillingAddressCountryCode, 
		stripeBillingAddressZip, stripeBillingAddressLine1, stripeBillingAddressCity
    ) 
    VALUES (AuID, IuID, AuID, IuID, StripeToken, StripeTokenType, StripeEmail, StripeBillingName, StripeBillingAddressCountry, StripeBillingAddressCountryCode, 
		StripeBillingAddressZip, StripeBillingAddressLine1, StripeBillingAddressCity
    );
    
	/* Get the EntityID and store it in @ID */
	SET EntityID := LAST_INSERT_ID();
    
  
    COMMIT;
	
    SELECT JSON_OBJECT('ID', EntityID, 'ProcStatus', ProcStatus, 'ProcMessage', ProcMessage) AS CommandResult;

END $$
DELIMITER ;
