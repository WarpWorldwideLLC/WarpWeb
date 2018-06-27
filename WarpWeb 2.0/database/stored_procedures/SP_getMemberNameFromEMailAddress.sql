-- SP_GetMemberNameFromEmailAddress


-- Error Code: 1064. You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near ');       SET ID := LAST_INSERT_ID();       COMMIT;        SELECT JSON_OBJECT('Us' at line 45


DROP PROCEDURE IF EXISTS getMemberNamesFromEmailAddress;

DELIMITER $$
CREATE PROCEDURE getMemberNamesFromEmailAddress(query JSON)
BEGIN 

	DECLARE varProcStatus NVARCHAR(10) DEFAULT 'SUCCESS';
    DECLARE varProcMessage NVARCHAR(999) DEFAULT 'SUCCESS';
    
    DECLARE varEMailAddress NVARCHAR(255) DEFAULT NULL;

    
	-- ERROR
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	  BEGIN

        GET DIAGNOSTICS CONDITION 1getMemberNamesFromEmailAddress
			@sqlstate = RETURNED_SQLSTATE,
			@errno = MYSQL_ERRNO, 
            @text = MESSAGE_TEXT;
		SET varProcStatus := 'ERROR';
		SET varProcMessage := CONCAT(@errno, " (", @sqlstate, "): ", @text);
		SELECT JSON_OBJECT('UserAccountID', @ID, 'ProcStatus', ProcStatus, 'ProcMessage', ProcMessage);
	END;

	-- WARNING
	DECLARE EXIT HANDLER FOR SQLWARNING
	 BEGIN

	END;

	SET varEMailAddress := JSON_UNQUOTE(JSON_EXTRACT(query, '$.EMailAddress'));

	SELECT JSON_OBJECT(
				'AccountID', e.ID, 
                'MemberName', en.EntityName, 
                'EMailAddress', ec.eContactIdentifier
                )
	FROM Entity e
	  INNER JOIN EntityName en
		ON e.ID = en.EntityID
		  AND e.EntityTypeID = 6
	  INNER JOIN ctlEntityType cet
		ON e.EntityTypeID = cet.ID
	  INNER JOIN eContact ec
		ON e.ID = ec.EntityID
	  INNER JOIN ctlEContactType cect
		ON ec.ContactTypeID = cect.ID
	-- WHERE ec.eContactIdentifier = 'john.arp@warpww.com';
	WHERE ec.eContactIdentifier = varEMailAddress;

END $$
DELIMITER ;


