/* TEST SCRIPT 2 */

USE WarpAdmin2017;

CALL registerMember('{"Command":"RegisterMember","AuID":1,"IuID":1,"MemberName":"PeteyWarp","EmailAddress":"pete@warpww.com","PassphraseHash":"1000:61cf72830e6b86a712bd238b7450454de00dbbd66f8fd451:f87249e17fcca50365dfa708ff9f8dee00da74811225696d","PhoneNumber":"(402) 890-0168","FirsName":"Pete","LastName":"Warp","CountryID":"0"}');


SELECT * FROM Entity;
SELECT * FROM EntityName;
SELECT * FROM EntityCountry;
SELECT * FROM Phone;
SELECT * FROM EntityPhone;


SELECT CAST(CURRENT_TIMESTAMP AS SIGNED);
SELECT CONCAT('FirstName',CAST(CURRENT_TIMESTAMP AS SIGNED));

