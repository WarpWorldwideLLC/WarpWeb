
DROP PROCEDURE IF EXISTS getMemberList;

DELIMITER $$
CREATE PROCEDURE getMemberList(query JSON)
BEGIN



	DECLARE AuID BIGINT DEFAULT NULL;
	DECLARE IuID BIGINT DEFAULT NULL;

	-- group_concat defaults to 1024 charaters; expand it for this query. 
	SET SESSION group_concat_max_len = 1000000;

	SET AuID = JSON_EXTRACT(query, '$.AuID');
    SET IuID = JSON_EXTRACT(query, '$.PuID');

		SELECT CONCAT('[',
			GROUP_CONCAT(
				JSON_OBJECT(
				'UserAccountID', e.ID, 
				'StatusID', e.RecordStatusID, 
				'CreateDate', e.CreateDate, 
				'AuID', e.AuID,
				'IuID', e.IuID,
				'LastModifyDate', e.LastModifyDate,
				'LastAuID', e.LastAuID,
				'LastIuID', e.LastIuID,
                'EntityTypeID', e.EntityTypeID, 
				'EntityTypeName', cet.EntityTypeName,
				'NameType', cent.EntityNameTypeName,
				'MemberName', en.EntityName, 
				'ContactType', cect.EContactTypeName,
				'ContactAddress', ec.eContactIdentifier
				)
			
			),
			']'
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
		;
    

END $$
DELIMITER ;