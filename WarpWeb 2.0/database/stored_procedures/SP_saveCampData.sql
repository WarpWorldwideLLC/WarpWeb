
DROP PROCEDURE IF EXISTS saveCampData;

DELIMITER $$
CREATE PROCEDURE saveCampData(query JSON)
BEGIN 
	DECLARE EntityID BIGINT DEFAULT -1;
    DECLARE AuID BIGINT DEFAULT NULL;
	DECLARE IuID BIGINT DEFAULT NULL;
	DECLARE TravelerName NVARCHAR(512) DEFAULT '';
	DECLARE ParentName NVARCHAR(512) DEFAULT '';
    DECLARE TravelerAddress NVARCHAR(512) DEFAULT '';
    DECLARE TravelerPrimaryPhoneNumber NVARCHAR(512) DEFAULT '';
    DECLARE TravelerAlternatePhoneNumber NVARCHAR(512) DEFAULT '';
    DECLARE TravelerEmailAddress NVARCHAR(512) DEFAULT '';
    DECLARE TravelerGender NVARCHAR(512) DEFAULT '';
    DECLARE TravelerDateOfBirth NVARCHAR(512) DEFAULT '';
    DECLARE TravelerComments NVARCHAR(512) DEFAULT '';
    DECLARE PaymentType NVARCHAR(512) DEFAULT '';
    DECLARE PaymentAmountText NVARCHAR(512) DEFAULT '';
    DECLARE PaymentAmount NVARCHAR(512) DEFAULT '';
    DECLARE PaymentDescription NVARCHAR(512) DEFAULT '';
    DECLARE PaymentNotes NVARCHAR(512) DEFAULT '';

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
 	SET TravelerName := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.travelerName')));
	SET ParentName := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.parentName')));
    SET TravelerAddress := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.travelerAddress')));
    SET TravelerPrimaryPhoneNumber := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.travelerPrimaryPhoneNumber')));
    SET TravelerAlternatePhoneNumber := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.travelerAlternatePhoneNumber')));
    SET TravelerEmailAddress := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.travelerEMailAddress')));
    SET TravelerGender := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.travelerGender')));
    SET TravelerDateOfBirth := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.travelerDateOfBirth')));
    SET TravelerComments := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.travelerComments')));
    SET PaymentType := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.paymentType')));
    SET PaymentAmountText := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.paymentAmountText')));
    SET PaymentAmount := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.paymentAmount')));
    SET PaymentDescription := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.paymentDescription')));
    SET PaymentNotes := TRIM(JSON_UNQUOTE(JSON_EXTRACT(query, '$.paymentNotes')));
    
    SET EntityID := 0;

	START TRANSACTION;
	-- Creating a new Member Entity
	-- Create the Entity record with type UserAccount 
	INSERT INTO CampData (AuiD, IuID, LastAuID, LastIuID, travelerName, parentName, travelerAddress, travelerPrimaryPhoneNumber, travelerAlternatePhoneNumber, 
		travelerEMailAddress, travelerGender, travelerDateOfBirth, travelerComments, paymentType, paymentAmountText, paymentAmount,
        paymentDescription, paymentNotes
    ) 
    VALUES (AuID, IuID, AuID, IuID, TravelerName, ParentName, TravelerAddress, TravelerPrimaryPhoneNumber, TravelerAlternatePhoneNumber, 
		TravelerEmailAddress, TravelerGender, TravelerDateOfBirth, TravelerComments, PaymentType, PaymentAmountText, PaymentAmount, 
        PaymentDescription, PaymentNotes
    );
    
	/* Get the EntityID and store it in @ID */
	SET EntityID := LAST_INSERT_ID();
    
  
    COMMIT;
	
    SELECT JSON_OBJECT('ID', EntityID, 'ProcStatus', ProcStatus, 'ProcMessage', ProcMessage) AS CommandResult;

END $$
DELIMITER ;
