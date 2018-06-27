/* EntityMiscellany */
/* Generic Key-Value pairs for miscellaneous entity assocaitions */

DROP TABLE IF EXISTS EntityMiscellany;  
CREATE TABLE EntityMiscellany (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    EntityID							BIGINT,
    MiscKey								NVARCHAR(255),
    MiscValue							NVARCHAR(255)
);

