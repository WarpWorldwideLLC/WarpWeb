

DROP PROCEDURE IF EXISTS saveStripeCampData;

DELIMITER $$
CREATE PROCEDURE saveStripeCampData(query JSON)
BEGIN 
	DECLARE EntityID BIGINT DEFAULT -1;
    DECLARE AuID BIGINT DEFAULT NULL;
	DECLARE IuID BIGINT DEFAULT NULL;
	DECLARE StripeToken NVARCHAR(512) DEFAULT '';
    DECLARE StripeTokenType NVARCHAR(512) DEFAULT '';
	DECLARE StripeEmail NVARCHAR(512) DEFAULT '';
    DECLARE StripeBillingName NVARCHAR(512) DEFAULT '';
    DECLARE StripeBillingAddressCountry NVARCHAR(512) DEFAULT '';
    DECLARE StripeBillingAddressCountryCode NVARCHAR(512) DEFAULT '';
    DECLARE StripeBillingAddressZip NVARCHAR(512) DEFAULT '';
    DECLARE StripeBillingAddressLine1 NVARCHAR(512) DEFAULT '';
    DECLARE StripeBillingAddressCity NVARCHAR(512) DEFAULT '';
  


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

	SET AuID := JSON_EXTRACT(query, '$.AuID');
    SET IuID := JSON_EXTRACT(query, '$.IuID');
 	SET StripeToken := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.stripeToken')));
    SET StripeTokenType := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.stripeTokenType')));
	SET StripeEmail := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.stripeEmail')));
    SET StripeBillingName := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.stripeBillingName')));
    SET StripeBillingAddressCountry := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.stripeBillingAddressCountry')));
    SET StripeBillingAddressCountryCode := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.stripeBillingAddressCountryCode')));
    SET StripeBillingAddressZip := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.stripeBillingAddressZip')));
    SET StripeBillingAddressLine1 := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.stripeBillingAddressLine1')));
    SET StripeBillingAddressCity := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.stripeBillingAddressCity')));
    
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
