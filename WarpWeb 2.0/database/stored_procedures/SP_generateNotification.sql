-- SP_generateNotification

/*
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    EntityID
	eContactTypeID 						BIGINT,
    eContactAddress						NVARCHAR(255), 
    MessageCode							NVARCHAR(10),
    MessageText							NVARCHAR(255)
*/

DROP PROCEDURE IF EXISTS generateNotification;

DELIMITER $$
CREATE PROCEDURE generateNotification(query JSON)
BEGIN 

	DECLARE ProcStatus NVARCHAR(10) DEFAULT 'SUCCESS';
    DECLARE ProcMessage NVARCHAR(999) DEFAULT 'SUCCESS';
    
    DECLARE ID BIGINT DEFAULT 0;
	DECLARE AuID BIGINT DEFAULT NULL;
	DECLARE IuID BIGINT DEFAULT NULL;
    
    DECLARE EntityID BIGINT DEFAULT NULL;
	DECLARE EContactTypeID BIGINT DEFAULT NULL;
    DECLARE EContactAddress NVARCHAR(255) DEFAULT NULL;
    DECLARE MessageCode NVARCHAR(10) DEFAULT NULL;
	DECLARE MessageText NVARCHAR(255) DEFAULT NULL;
    
    
	-- ERROR HANDLER
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

	-- WARNING HANDLER
	DECLARE EXIT HANDLER FOR SQLWARNING
	 BEGIN
		
	 ROLLBACK;
	END;

	SET AuID := JSON_EXTRACT(query, '$.AuID');
    SET IuID := JSON_EXTRACT(query, '$.IuID');
    SET EContactTypeID:= JSON_EXTRACT(query, '$.EntityID');
    SET EContactAddress := JSON_UNQUOTE(JSON_EXTRACT(query, '$.PassphraseResetToken'));
    SET MessageCode := JSON_UNQUOTE(JSON_EXTRACT(query, '$.PassphraseResetToken'));
	SET MessageText := JSON_UNQUOTE(JSON_EXTRACT(query, '$.PassphraseResetToken'));

	START TRANSACTION;

	INSERT INTO Notification (AuiD, IuID, LastAuID, LastIuID, EntityID, eContactTypeID, eContactAddress, MessageCode, MessageText) VALUES (AuID, IuID, AuID, IuID, EntityID, EContactTypeID, EContactAddress, MessageCode, MessageText);
    
	SET ID := LAST_INSERT_ID();
	
    COMMIT;
	

    SELECT JSON_OBJECT('UserAccountID', @ID, 'ProcStatus', ProcStatus, 'ProcMessage', ProcMessage);

END $$
DELIMITER ;