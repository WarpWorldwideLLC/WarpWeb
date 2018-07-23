DROP DATABASE IF EXISTS WarpSec_CN;
CREATE DATABASE WarpSec_CN CHARACTER SET utf8 COLLATE utf8_general_ci;

USE WarpSec_CN;

CREATE TABLE Parameters (
	ID 									BIGINT PRIMARY KEY AUTO_INCREMENT,
    RecordStatusID						BIGINT DEFAULT 10, 
    CreateDate 							DATETIME DEFAULT CURRENT_TIMESTAMP,
    AuID 								BIGINT DEFAULT -1,
	IuID 								BIGINT DEFAULT -1,
	LastModifyDate 						DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	LastAuID 							BIGINT DEFAULT -1,
	LastIuID 							BIGINT DEFAULT -1,
    WarpEnvironment						NVARCHAR(100) NOT NULL,
    WarpSalt							NVARCHAR(100) NOT NULL,
    WarpKey								NVARCHAR(255) NOT NULL, 
    WarpValue							NVARCHAR(2000) NOT NULL
);

INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'ell_sk', 'fkelJ4bD');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'ell_clientid', '2544');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'ell_ssouri', 'https://sdb.elldevelopment.com');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'ell_apiuri', 'https://api.elldevelopment.com');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'pk1', 'pk_test_SVBFNF3t7mU3ycUFW8nEzENp');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'sk1', 'sk_test_STal0dCNUL1dUZ09CswwVe7E');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'resourceFile', '/com/warpww/web/i18n/warp.properties');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'jdbcURI', 'jdbc:mysql://localhost:3306/WarpAdmin_CN?useUnicode=yes&useSSL=false&characterEncoding=UTF-8');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'jdbcUser', 'root');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'jdbcPassword', '62XYhC;erw;zZaCmZVzrFEwW');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'tokenSalt', 'Action Comics #1, June 1939');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'tokenMemberPadding', '1100000');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'tokenExpiratonDuration', '18000');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'cookieName', 'com.warpww');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'cookieDomain', 'NONE');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'cookieSSL', 'false');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'currencySymbol', '$');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_CN', 'NO_SALT', 'systemMode', 'TEST');

/*
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'ell_sk', 'fkelJ4bD');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'ell_clientid', '2544');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'ell_ssouri', 'https://sdb.elldevelopment.com');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'ell_apiuri', 'https://api.elldevelopment.com');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'pk1', 'pk_test_SVBFNF3t7mU3ycUFW8nEzENp');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'sk1', 'sk_test_STal0dCNUL1dUZ09CswwVe7E');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'resourceFile', '/com/warpww/web/i18n/warp-en-us.properties');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'jdbcURI', 'jdbc:mysql://localhost:3306/WarpAdmin2017?useUnicode=yes&useSSL=false&characterEncoding=UTF-8');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'jdbcUser', 'root');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'jdbcPassword', '62XYhC;erw;zZaCmZVzrFEwW');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'tokenSalt', 'Action Comics #1, June 1939');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'tokenMemberPadding', '1100000');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'tokenExpiratonDuration', '18000');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'cookieName', 'com.warpww');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'cookieDomain', 'NONE');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'cookieSSL', 'false');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'currencySymbol', '$');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('TEST_US', 'NO_SALT', 'systemMode', 'TEST');
*/

/*
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'ell_sk', 'fJuWlfiB');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'ell_clientid', '3945');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'ell_ssouri', 'http://sdb.ellcampus.com');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'ell_apiuri', 'https://api.ellcampus.com');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'pk1', 'pk_live_9IU3u0bPEmnKRLb103pW4ILu');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'sk1', 'sk_live_R3mT2ILaX6p0OdO6Dk9cotz6');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'resourceFile', '/com/warpww/web/i18n/warp-en-us.properties');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'jdbcURI', 'jdbc:mysql://singapore-db-1.cuwqizgjegfw.ap-southeast-1.rds.amazonaws.com:3306/WarpAdmin2017?useUnicode=yes&useSSL=false&characterEncoding=UTF-8');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'jdbcUser', 'warpdbm');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'jdbcPassword', 'warp-is-live-2018');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'tokenSalt', 'Action Comics #1, June 1939');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'tokenMemberPadding', '1100000');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'tokenExpiratonDuration', '18000');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'cookieName', 'com.warpww');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'cookieDomain', 'NONE');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'cookieSSL', 'false');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'currencySymbol', '$');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_US', 'NO_SALT', 'systemMode', 'PROD');
*/

/*
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'ell_sk', 'fJuWlfiB');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'ell_clientid', '3945');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'ell_ssouri', 'http://sdb.ellcampus.com');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'ell_apiuri', 'https://api.ellcampus.com');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'pk1', 'pk_live_9IU3u0bPEmnKRLb103pW4ILu');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'sk1', 'sk_live_R3mT2ILaX6p0OdO6Dk9cotz6');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'resourceFile', '/com/warpww/web/i18n/warp-en-us.properties');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'jdbcURI', 'jdbc:mysql://singapore-db-1.cuwqizgjegfw.ap-southeast-1.rds.amazonaws.com:3306/WarpAdmin2017?useUnicode=yes&useSSL=false&characterEncoding=UTF-8');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'jdbcUser', 'warpdbm');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'jdbcPassword', 'warp-is-live-2018');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'tokenSalt', 'Action Comics #1, June 1939');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'tokenMemberPadding', '1100000');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'tokenExpiratonDuration', '18000');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'cookieName', 'com.warpww');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'cookieDomain', 'NONE');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'cookieSSL', 'false');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'currencySymbol', '$');
INSERT INTO Parameters (WarpEnvironment, WarpSalt, WarpKey, WarpValue) VALUES ('PROD_CN', 'NO_SALT', 'systemMode', 'PROD');
*/

SELECT * FROM Parameters;

/* 

Environments

	TEST_CN
    TEST_US
    UAT_CN
    UAT_US
    PROD_CN
    PROD_US


*/