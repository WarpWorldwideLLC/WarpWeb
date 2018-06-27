

DROP PROCEDURE IF EXISTS getEllNewUserData;

DELIMITER $$
CREATE PROCEDURE getEllNewUserData(query JSON)
BEGIN

	DECLARE AuID BIGINT DEFAULT NULL;
	DECLARE IuID BIGINT DEFAULT NULL;
    DECLARE EntityID BIGINT DEFAULT NULL;


	SET AuID = JSON_EXTRACT(query, '$.AuID');
    SET IuID = JSON_EXTRACT(query, '$.PuID');
    SET EntityID = JSON_EXTRACT(query, '$.MemberID');

 		SELECT JSON_OBJECT(
			'UserAccountID', e.ID,
			'StatusID', e.RecordStatusID, 
			'CreateDate', e.CreateDate, 
			'AuID', e.AuID,
            'IuID', e.IuID,
			'LastModifyDate', e.LastModifyDate,
			'LastAuID', e.LastAuID,
			'LastIuID', e.LastIuID,
            'MemberName', en.EntityName,
            'Password', "abcdef",
            'Email', ec.eContactIdentifier,
            'FirstName', en1.EntityName,
            'LastName', en2.EntityName,
            'BirthDate', IFNULL(ebd.EntityBirthDate, '1900-01-01'),
            'Language', 'english',
            'Country', 'China'
			) 
		FROM Entity e
			-- Account Name Information
		  LEFT JOIN EntityName en 
			ON e.ID = en.EntityID
				AND en.EntityNameTypeID = 2
			-- Account Name Information
		  LEFT JOIN EntityName en1
			ON e.ID = en1.EntityID
				AND en1.EntityNameTypeID = 3
			-- Account Name Information
		  LEFT JOIN EntityName en2 
			ON e.ID = en2.EntityID
				AND en2.EntityNameTypeID = 5
		-- Email Information
		  LEFT JOIN eContact ec
			ON e.ID = ec.EntityID
				AND ec.ContactTypeID = 1
		  LEFT JOIN EntityBirthDate ebd
            ON e.ID = ebd.EntityID
		-- Selection Criteria
		WHERE e.ID = EntityID
              AND e.EntityTypeID = 6    -- Retrieve only UserAccount Entity Types
		;
    

END $$
DELIMITER ;