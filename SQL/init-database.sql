CREATE DATABASE NasaImage;
GO

USE NasaImage;
GO

CREATE TABLE Roles
(
    IDRole INT CONSTRAINT PK_Roles PRIMARY KEY IDENTITY,
    [Name] NVARCHAR(50) NOT NULL CONSTRAINT UQ_Roles_Name UNIQUE
);
GO

CREATE TABLE AppUsers
(
    IDAppUser INT CONSTRAINT PK_AppUsers PRIMARY KEY IDENTITY,
    Username NVARCHAR(100) NOT NULL CONSTRAINT UQ_AppUsers_Username UNIQUE,
    PasswordHash NVARCHAR(64) NOT NULL,
    IDRole INT NOT NULL CONSTRAINT FK_AppUsers_Roles FOREIGN KEY REFERENCES Roles(IDRole)
);
GO

CREATE TABLE Categories
(
    IDCategory INT CONSTRAINT PK_Categories PRIMARY KEY IDENTITY,
    [Name] NVARCHAR(100) NOT NULL CONSTRAINT UQ_Categories_Name UNIQUE
);
GO

CREATE TABLE Tags
(
    IDTag INT CONSTRAINT PK_Tags PRIMARY KEY IDENTITY,
    [Name] NVARCHAR(100) NOT NULL CONSTRAINT UQ_Tags_Name UNIQUE
);
GO

CREATE TABLE NasaImages
(
    IDNasaImage INT CONSTRAINT PK_NasaImages PRIMARY KEY IDENTITY,
    Title NVARCHAR(300) NOT NULL,
    Link NVARCHAR(500) NOT NULL CONSTRAINT UQ_NasaImages_Link UNIQUE,
    [Description] NVARCHAR(MAX) NOT NULL,
    PicturePath NVARCHAR(500) NULL,
    PublishedDate NVARCHAR(50) NOT NULL,
    IDCategory INT NOT NULL CONSTRAINT FK_NasaImages_Categories FOREIGN KEY REFERENCES Categories(IDCategory)
);
GO

CREATE TABLE NasaImageTags
(
    IDNasaImage INT NOT NULL CONSTRAINT FK_NasaImageTags_NasaImages FOREIGN KEY REFERENCES NasaImages(IDNasaImage),
    IDTag INT NOT NULL CONSTRAINT FK_NasaImageTags_Tags FOREIGN KEY REFERENCES Tags(IDTag),

    CONSTRAINT PK_NasaImageTags PRIMARY KEY (IDNasaImage, IDTag)
);
GO


-- DEFAULT DATA

INSERT INTO Roles (Name) VALUES ('ADMINISTRATOR');
INSERT INTO Roles (Name) VALUES ('USER');
GO

INSERT INTO Categories (Name) VALUES ('NASA Image of the Day');
GO

-- DEFAULT ADMIN PROCEDURE
-- Username: admin
-- Password: admin

CREATE PROCEDURE createDefaultAdmin
AS
BEGIN
    IF NOT EXISTS
    (
        SELECT 1
        FROM AppUsers
        WHERE Username = 'admin'
    )
    BEGIN
        INSERT INTO AppUsers (Username, PasswordHash, IDRole)
        VALUES
        (
            'admin',
            '8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918',
            (SELECT IDRole FROM Roles WHERE Name = 'ADMINISTRATOR')
        );
    END
END
GO

EXEC createDefaultAdmin;
GO


-- USER PROCEDURES

CREATE PROCEDURE loginUser
    @Username NVARCHAR(100),
    @PasswordHash NVARCHAR(64)
AS
BEGIN
    SELECT 
        au.IDAppUser,
        au.Username,
        au.PasswordHash,
        r.Name AS RoleName
    FROM AppUsers au
    INNER JOIN Roles r ON au.IDRole = r.IDRole
    WHERE au.Username = @Username
      AND au.PasswordHash = @PasswordHash;
END
GO

CREATE PROCEDURE registerUser
    @Username NVARCHAR(100),
    @PasswordHash NVARCHAR(64),
    @IDAppUser INT OUTPUT
AS
BEGIN
    INSERT INTO AppUsers (Username, PasswordHash, IDRole)
    VALUES
    (
        @Username,
        @PasswordHash,
        (SELECT IDRole FROM Roles WHERE Name = 'USER')
    );

    SET @IDAppUser = CAST(SCOPE_IDENTITY() AS INT);
END
GO

CREATE PROCEDURE selectUsers
AS
BEGIN
    SELECT
        au.IDAppUser,
        au.Username,
        au.PasswordHash,
        r.Name AS RoleName
    FROM AppUsers au
    INNER JOIN Roles r ON au.IDRole = r.IDRole
    ORDER BY au.Username;
END
GO


-- CATEGORY PROCEDURES

CREATE PROCEDURE selectCategories
AS
BEGIN
    SELECT 
        IDCategory,
        Name
    FROM Categories
    ORDER BY Name;
END
GO

CREATE PROCEDURE selectCategory
    @IDCategory INT
AS
BEGIN
    SELECT 
        IDCategory,
        Name
    FROM Categories
    WHERE IDCategory = @IDCategory;
END
GO

CREATE PROCEDURE createCategory
    @Name NVARCHAR(100),
    @IDCategory INT OUTPUT
AS
BEGIN
    INSERT INTO Categories (Name)
    VALUES (@Name);

    SET @IDCategory = CAST(SCOPE_IDENTITY() AS INT);
END
GO

CREATE PROCEDURE updateCategory
    @IDCategory INT,
    @Name NVARCHAR(100)
AS
BEGIN
    UPDATE Categories
    SET Name = @Name
    WHERE IDCategory = @IDCategory;
END
GO

CREATE PROCEDURE deleteCategory
    @IDCategory INT
AS
BEGIN
    DELETE FROM Categories
    WHERE IDCategory = @IDCategory;
END
GO


-- TAG PROCEDURES

CREATE PROCEDURE selectTags
AS
BEGIN
    SELECT 
        IDTag,
        Name
    FROM Tags
    ORDER BY Name;
END
GO

CREATE PROCEDURE selectTag
    @IDTag INT
AS
BEGIN
    SELECT 
        IDTag,
        Name
    FROM Tags
    WHERE IDTag = @IDTag;
END
GO

CREATE PROCEDURE createTag
    @Name NVARCHAR(100),
    @IDTag INT OUTPUT
AS
BEGIN
    INSERT INTO Tags (Name)
    VALUES (@Name);

    SET @IDTag = CAST(SCOPE_IDENTITY() AS INT);
END
GO

CREATE PROCEDURE updateTag
    @IDTag INT,
    @Name NVARCHAR(100)
AS
BEGIN
    UPDATE Tags
    SET Name = @Name
    WHERE IDTag = @IDTag;
END
GO

CREATE PROCEDURE deleteTag
    @IDTag INT
AS
BEGIN
    DELETE FROM NasaImageTags
    WHERE IDTag = @IDTag;

    DELETE FROM Tags
    WHERE IDTag = @IDTag;
END
GO


-- NASA IMAGE PROCEDURES

CREATE PROCEDURE createNasaImage
    @Title NVARCHAR(300),
    @Link NVARCHAR(500),
    @Description NVARCHAR(MAX),
    @PicturePath NVARCHAR(500),
    @PublishedDate NVARCHAR(50),
    @IDCategory INT,
    @IDNasaImage INT OUTPUT
AS
BEGIN
    INSERT INTO NasaImages
    (
        Title,
        Link,
        Description,
        PicturePath,
        PublishedDate,
        IDCategory
    )
    VALUES
    (
        @Title,
        @Link,
        @Description,
        @PicturePath,
        @PublishedDate,
        @IDCategory
    );

    SET @IDNasaImage = CAST(SCOPE_IDENTITY() AS INT);
END
GO

CREATE PROCEDURE updateNasaImage
    @IDNasaImage INT,
    @Title NVARCHAR(300),
    @Link NVARCHAR(500),
    @Description NVARCHAR(MAX),
    @PicturePath NVARCHAR(500),
    @PublishedDate NVARCHAR(50),
    @IDCategory INT
AS
BEGIN
    UPDATE NasaImages
    SET
        Title = @Title,
        Link = @Link,
        Description = @Description,
        PicturePath = @PicturePath,
        PublishedDate = @PublishedDate,
        IDCategory = @IDCategory
    WHERE IDNasaImage = @IDNasaImage;
END
GO

CREATE PROCEDURE deleteNasaImage
    @IDNasaImage INT
AS
BEGIN
    DELETE FROM NasaImageTags
    WHERE IDNasaImage = @IDNasaImage;

    DELETE FROM NasaImages
    WHERE IDNasaImage = @IDNasaImage;
END
GO

CREATE PROCEDURE selectNasaImage
    @IDNasaImage INT
AS
BEGIN
    SELECT
        ni.IDNasaImage,
        ni.Title,
        ni.Link,
        ni.[Description],
        ni.PicturePath,
        ni.PublishedDate,
        c.IDCategory,
        c.[Name] AS CategoryName
    FROM NasaImages ni
    INNER JOIN Categories c ON ni.IDCategory = c.IDCategory
    WHERE ni.IDNasaImage = @IDNasaImage;
END
GO

CREATE PROCEDURE selectNasaImages
AS
BEGIN
    SELECT
        ni.IDNasaImage,
        ni.Title,
        ni.Link,
        ni.[Description],
        ni.PicturePath,
        ni.PublishedDate,
        c.IDCategory,
        c.[Name] AS CategoryName
    FROM NasaImages ni
    INNER JOIN Categories c ON ni.IDCategory = c.IDCategory
    ORDER BY ni.IDNasaImage;
END
GO


-- NASA IMAGE TAG PROCEDURES

CREATE PROCEDURE assignTagToImage
    @IDNasaImage INT,
    @IDTag INT
AS
BEGIN
    IF NOT EXISTS
    (
        SELECT 1
        FROM NasaImageTags
        WHERE IDNasaImage = @IDNasaImage
          AND IDTag = @IDTag
    )
    BEGIN
        INSERT INTO NasaImageTags (IDNasaImage, IDTag)
        VALUES (@IDNasaImage, @IDTag);
    END
END
GO

CREATE PROCEDURE removeTagFromImage
    @IDNasaImage INT,
    @IDTag INT
AS
BEGIN
    DELETE FROM NasaImageTags
    WHERE IDNasaImage = @IDNasaImage
      AND IDTag = @IDTag;
END
GO

CREATE PROCEDURE selectTagsForImage
    @IDNasaImage INT
AS
BEGIN
    SELECT
        t.IDTag,
        t.[Name]
    FROM Tags t
    INNER JOIN NasaImageTags nit ON t.IDTag = nit.IDTag
    WHERE nit.IDNasaImage = @IDNasaImage
    ORDER BY t.[Name];
END
GO


-- ADMIN PROCEDURE

CREATE PROCEDURE clearApplicationData
AS
BEGIN
    DELETE FROM NasaImageTags;
    DELETE FROM NasaImages;
    DELETE FROM Tags;

    DELETE FROM Categories
    WHERE [Name] != 'NASA Image of the Day';

    DELETE FROM AppUsers
    WHERE Username != 'admin';
	
	DBCC CHECKIDENT ('NasaImages', RESEED, 0);
	DBCC CHECKIDENT ('Tags', RESEED, 0);
	DBCC CHECKIDENT ('Categories', RESEED, 1);
	DBCC CHECKIDENT ('AppUsers', RESEED, 1);
END
GO