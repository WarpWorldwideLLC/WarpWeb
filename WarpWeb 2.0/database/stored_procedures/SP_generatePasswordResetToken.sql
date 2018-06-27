
-- Error Code: 1064. You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near ');       SET ID := LAST_INSERT_ID();       COMMIT;        SELECT JSON_OBJECT('Us' at line 45


DROP PROCEDURE IF EXISTS generatePasswordResetToken;

DELIMITER $$
CREATE PROCEDURE generatePasswordResetToken(query JSON)
BEGIN 

	DECLARE ProcStatus NVARCHAR(10) DEFAULT 'SUCCESS';
    DECLARE ProcMessage NVARCHAR(999) DEFAULT 'SUCCESS';
    
    DECLARE ID BIGINT DEFAULT 0;
	DECLARE AuID BIGINT DEFAULT NULL;
	DECLARE IuID BIGINT DEFAULT NULL;
	DECLARE ResetEntityID BIGINT DEFAULT NULL;
    DECLARE TokenValue NVARCHAR(255) DEFAULT NULL;
    DECLARE ExpirationDate DATETIME;
    
	-- ERROR
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	  BEGIN

        GET DIAGNOSTICS CONDITION 1
			@sqlstate = RETURNED_SQLSTATE,
			@errno = MYSQL_ERRNO, 
            @text = MESSAGE_TEXT;
		SET ProcStatus := 'ERROR';
		SET ProcMessage := CONCAT(@errno, " (", @sqlstate, "): ", @text);
		SELECT JSON_OBJECT('UserAccountID', @ID, 'ProcStatus', ProcStatus, 'ProcMessage', ProcMessage);

	  ROLLBACK;
	END;

	-- WARNING
	DECLARE EXIT HANDLER FOR SQLWARNING
	 BEGIN
		
	 ROLLBACK;
	END;

	SET AuID := JSON_EXTRACT(query, '$.AuID');
    SET IuID := JSON_EXTRACT(query, '$.IuID');
    SET ResetEntityID:= JSON_EXTRACT(query, '$.EntityID');
    SET TokenValue := JSON_EXTRACT(query, '$.PassphraseResetToken');
    SET ExpirationDate := DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 4 HOUR);

	START TRANSACTION;

	INSERT INTO PassphraseResetToken (AuiD, IuID, LastAuID, LastIuID, EntityID, PassphraseResetToken, ExpirationDate) VALUES (AuID, IuID, AuID, IuID, EntityID, TokenValue, ExpirationDate);
    
	SET ID := LAST_INSERT_ID();
	
    COMMIT;
	

    SELECT JSON_OBJECT('UserAccountID', @ID, 'ProcStatus', ProcStatus, 'ProcMessage', ProcMessage);

END $$
DELIMITER ;