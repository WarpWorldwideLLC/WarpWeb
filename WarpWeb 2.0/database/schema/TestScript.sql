

USE WarpAdmin2017;

SELECT * FROM EntityName;

/* 40 Bytes, 54 with time + SHA1 */
SELECT LENGTH(CONCAT((NOW() + 0) ,SHA1(NOW())));

SELECT NOW(), DATE_FORMAT(NOW(), "%H%f%j%e%s");

SELECT CONCAT((NOW() + 0), SHA1(DATE_FORMAT(NOW(), "%H%f%j%e%s")) ,SHA1(CONCAT('JohnnyWarp', '1000:67391a09d9dbf3b267f1b983796104928119dd873b7eecb7', NOW(0) +0))) ;

SELECT CONCAT(REVERSE((NOW() + 0)) ,SHA1(CONCAT('JohnnyWarp', '1000:67391a09d9dbf3b267f1b983796104928119dd873b7eecb7', NOW(0) +0))) ;

SELECT NOW(), NOW() + 0;


SELECT * FROM Passphrase;

SELECT @@version;

/* 
'4', '10', '2017-12-24 13:04:23', '1', '1', '2017-12-24 13:04:23', '1', '1', '10', '2', 'JonLi', '1.7976931348623157e308', 'N'
# JSON_OBJECT('MemberID', EntityID, 'ProcStatus', ProcStatus, 'ProcMessage', ProcMessage)
'{\"MemberID\": 19, \"ProcStatus\": \"ERROR\", \"ProcMessage\": \"1062 (23000): Duplicate entry \'2-JonLi\' for key \'uniqueName\'\"}'
Error Code: 1054. Unknown column 'MessageSource' in 'field list'



*/

SELECT * FROM EntityName;

SELECT UUID();

SELECT CONCAT(
    '[', 
    GROUP_CONCAT(json_object('ID', ID, 'CreateDate', CreateDate)),
    ']')
FROM Entity;

SELECT COALESCE(NULL, 'W3Schools.com', 1, 2);

 CALL getMemberList('{"AuID": 1, "IuID": 1}');

 CALL registerMember ('{"Command":"RegisterMember","AuID":1,"IuID":1,"MemberName":"PeterWarp","EmailAddress":"peter.parker@warpww.com","PassphraseHash":"1000:dd47287a430d50a93a5e39e5184090661a1c13297395d6fb:67391a09d9dbf3b267f1b983796104928119dd873b7eecb7"}');
 
 
 CALL registerUserAccount ('{"Command":"RegisterMember","AuID":1,"IuID":1,"MemberName":"JonLi","EmailAddress":"john.arp@warpww.com","PassphraseHash":"1000:74068e30d8adc7a357d374643e9397d10ff3856b93734075:ed639b2ed05ad00aebe40d2dd81bfd593fdf491fcb3da5cb"}');
 CALL registerUserAccount ('{"Command":"RegisterMember","AuID":1,"IuID":1,"MemberName":"","EmailAddress":"john.arp@warpww.com","PassphraseHash":"1000:fa8fe8aba06bb324368c20271924cef3eefc005b917f424c:f16aa4e478293a00563af6218c2cf17d1496a806047773fb"}');

SELECT * FROM Entity WHERE EntityTypeID = 6;  /* UserAccount/MemberName records only." */
SELECT * FROM ctlEntityType;
SELECT * FROM EntityName;
SELECT * FROM ctlEntityNameType;
SELECT * FROM eContact;
SELECT * FROM ctlEContactType;
SELECT * FROM Passphrase;

SELECT * FROM ctlCountry;

UPDATE Passphrase SeT PassphraseHash = '1000:8b3777ad427cfcf006d41cfef4134b2d072e85ee12ffcc9b:197717b70ba500e1494674496b84d32cf6ce694d745789af' WHERE ID = 1;

 CALL getUserAccountByID('{"AuID": 1, "IuID": 1, "UserAccountID": 2}');
 CALL getMemberList('{"AuID": 1, "IuID": 1}');

CALL registerMember('{"Command":"RegisterMember","AuID":1,"IuID":1,"MemberName":"PeteyWarp","EmailAddress":"pete@warpww.com","PassphraseHash":"1000:61cf72830e6b86a712bd238b7450454de00dbbd66f8fd451:f87249e17fcca50365dfa708ff9f8dee00da74811225696d","PhoneNumber":"9999999999","FirsName":"Pete","LastName":"Warp","CountryID":"0"}');

 CALL validateSignon('{"Command":"ValidateSignon","AuID":1,"IuID":1,"MemberName":"JohnnyWarp","PassphraseHash":"1000:8b3777ad427cfcf006d41cfef4134b2d072e85ee12ffcc9b:197717b70ba500e1494674496b84d32cf6ce694d745789af"}');

/*
'3','First Name'
'4','Middle Name'
'5','Last Name'
 
 
 {"Command":"RegisterMember","AuID":1,"IuID":1,"MemberName":"PeteyWarp","EmailAddress":"pete@warpww.com","PassphraseHash":"1000:61cf72830e6b86a712bd238b7450454de00dbbd66f8fd451:f87249e17fcca50365dfa708ff9f8dee00da74811225696d","PhoneNumber":"9999999999","FirsName":"Pete","LastName":"Warp","CountryID":"0"}
*/


-- SHOW VARIABLES LIKE 'datadir';

/* 

Error Code: 1305. PROCEDURE warpadmin2017.validateSignon does not exist

Error Code: 1305. PROCEDURE warpadmin2017.SP_registerUserAccount does not exist
Error Code: 1305. PROCEDURE warpadmin2017.validateSignon does not exist


*/


