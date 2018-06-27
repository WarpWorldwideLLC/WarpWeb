

DROP PROCEDURE IF EXISTS getUserAccountByID;

DELIMITER $$
CREATE PROCEDURE getMemberByID(query JSON)
BEGIN

	DECLARE AuID BIGINT DEFAULT NULL;
	DECLARE IuID BIGINT DEFAULT NULL;
    DECLARE EntityID BIGINT DEFAULT NULL;


	SET AuID = JSON_EXTRACT(query, '$.AuID');
    SET IuID = JSON_EXTRACT(query, '$.PuID');
    SET EntityID = JSON_EXTRACT(query, '$.UserAccountID');

		SELECT JSON_OBJECT(
			'UserAccountID', e.ID, 
			'StatusID', e.RecordStatusID, 
			'CreateDate', e.CreateDate, 
			'AuID', e.AuID,
            'IuID', e.IuID,
			'LastModifyDate', e.LastModifyDate,
			'LastAuID', e.LastAuID,
			'LastIuID', e.LastIuID,
			'EntityTypeID', e.EntityTypeID,
			'EntityTypeName', cet.EntityTypeName
			) 
		FROM Entity e
		-- Account Type Information
		  LEFT JOIN ctlEntityType cet
			ON e.EntityTypeID = cet.ID
			-- Account Name Information
		  LEFT JOIN EntityName en 
			  LEFT JOIN ctlEntityNameType cent
				ON en.EntityNameTypeID = cent.ID
			ON e.ID = en.EntityID
		-- Account Contact Information
		  LEFT JOIN eContact ec
			  LEFT JOIN ctlEContactType cect
				ON ec.ContactTypeID = cect.ID 
			ON e.ID = ec.EntityID
			-- Selection Criteria
		WHERE e.EntityTypeID = 6    -- Retrieve only UserAccount Entity Types
          AND e.ID = EntityID
		;
    

END $$
DELIMITER ;