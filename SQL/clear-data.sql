USE NasaImage;
GO

DELETE FROM NasaImageTags;
DELETE FROM NasaImages;
DELETE FROM Tags;

DELETE FROM Categories
WHERE [Name] != 'NASA Image of the Day';

DELETE FROM AppUsers
WHERE Username != 'admin';
GO
DBCC CHECKIDENT ('NasaImages', RESEED, 0);
DBCC CHECKIDENT ('Tags', RESEED, 0);
DBCC CHECKIDENT ('Categories', RESEED, 1);
DBCC CHECKIDENT ('AppUsers', RESEED, 1);
GO