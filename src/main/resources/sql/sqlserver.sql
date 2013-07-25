if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ecv_driving_licence]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ecv_driving_licence]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ecv_education]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ecv_education]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ecv_language]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ecv_language]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ecv_nationality]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ecv_nationality]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ecv_work_experience]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ecv_work_experience]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ecv_xml]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ecv_xml]
GO

CREATE TABLE [dbo].[ecv_driving_licence] (
	[id] [numeric](18, 0) IDENTITY (1, 1) NOT NULL ,
	[xml_id] [numeric](18, 0) NULL ,
	[driving_skill] [varchar] (3) COLLATE Greek_CS_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[ecv_education] (
	[id] [numeric](18, 0) IDENTITY (1, 1) NOT NULL ,
	[xml_id] [numeric](18, 0) NULL ,
	[title] [varchar] (50) COLLATE Greek_CS_AS NULL ,
	[subject] [varchar] (200) COLLATE Greek_CS_AS NULL ,
	[org_name] [varchar] (100) COLLATE Greek_CS_AS NULL ,
	[org_type] [varchar] (50) COLLATE Greek_CS_AS NULL ,
	[org_address] [varchar] (100) COLLATE Greek_CS_AS NULL ,
	[org_munic] [varchar] (50) COLLATE Greek_CS_AS NULL ,
	[org_zcode] [varchar] (10) COLLATE Greek_CS_AS NULL ,
	[code_country] [varchar] (3) COLLATE Greek_CS_AS NULL ,
	[country] [varchar] (50) COLLATE Greek_CS_AS NULL ,
	[code_level] [varchar] (2) COLLATE Greek_CS_AS NULL ,
	[edulevel] [varchar] (30) COLLATE Greek_CS_AS NULL ,
	[code_edu_field] [varchar] (5) COLLATE Greek_CS_AS NULL ,
	[edu_field] [varchar] (50) COLLATE Greek_CS_AS NULL ,
	[day_from] [varchar] (2) COLLATE Greek_CS_AS NULL ,
	[month_from] [varchar] (2) COLLATE Greek_CS_AS NULL ,
	[year_from] [varchar] (4) COLLATE Greek_CS_AS NULL ,
	[day_to] [varchar] (2) COLLATE Greek_CS_AS NULL ,
	[month_to] [varchar] (2) COLLATE Greek_CS_AS NULL ,
	[year_to] [varchar] (4) COLLATE Greek_CS_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[ecv_language] (
	[id] [numeric](18, 0) IDENTITY (1, 1) NOT NULL ,
	[xml_id] [numeric](18, 0) NULL ,
	[code_language] [varchar] (3) COLLATE Greek_CS_AS NULL ,
	[olanguage] [varchar] (30) COLLATE Greek_CS_AS NULL ,
	[listening] [varchar] (2) COLLATE Greek_CS_AS NULL ,
	[reading] [varchar] (2) COLLATE Greek_CS_AS NULL ,
	[spoken_interaction] [varchar] (2) COLLATE Greek_CS_AS NULL ,
	[spoken_production] [varchar] (2) COLLATE Greek_CS_AS NULL ,
	[writing] [varchar] (2) COLLATE Greek_CS_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[ecv_nationality] (
	[id] [numeric](18, 0) IDENTITY (1, 1) NOT NULL ,
	[xml_id] [numeric](18, 0) NULL ,
	[code] [varchar] (10) COLLATE Greek_CS_AS NULL ,
	[nationality] [varchar] (32) COLLATE Greek_CS_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[ecv_work_experience] (
	[id] [numeric](18, 0) IDENTITY (1, 1) NOT NULL ,
	[xml_id] [numeric](18, 0) NULL ,
	[day_from] [varchar] (2) COLLATE Greek_CS_AS NULL ,
	[month_from] [varchar] (2) COLLATE Greek_CS_AS NULL ,
	[year_from] [varchar] (4) COLLATE Greek_CS_AS NULL ,
	[day_to] [varchar] (2) COLLATE Greek_CS_AS NULL ,
	[month_to] [varchar] (2) COLLATE Greek_CS_AS NULL ,
	[year_to] [varchar] (4) COLLATE Greek_CS_AS NULL ,
	[code_position] [varchar] (6) COLLATE Greek_CS_AS NULL ,
	[wposition] [varchar] (30) COLLATE Greek_CS_AS NULL ,
	[activities] [varchar] (100) COLLATE Greek_CS_AS NULL ,
	[employer_name] [varchar] (50) COLLATE Greek_CS_AS NULL ,
	[employer_address] [varchar] (50) COLLATE Greek_CS_AS NULL ,
	[employer_munic] [varchar] (50) COLLATE Greek_CS_AS NULL ,
	[employer_zcode] [varchar] (10) COLLATE Greek_CS_AS NULL ,
	[code_country] [varchar] (3) COLLATE Greek_CS_AS NULL ,
	[country] [varchar] (30) COLLATE Greek_CS_AS NULL ,
	[code_sector] [varchar] (3) COLLATE Greek_CS_AS NULL ,
	[sector] [varchar] (50) COLLATE Greek_CS_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[ecv_xml] (
	[id] [numeric](18, 0) IDENTITY (1, 1) NOT NULL ,
	[fname] [varchar] (30) COLLATE Greek_CS_AS NULL ,
	[lname] [varchar] (30) COLLATE Greek_CS_AS NULL ,
	[address] [varchar] (50) COLLATE Greek_CS_AS NULL ,
	[munic] [varchar] (50) COLLATE Greek_CS_AS NULL ,
	[postal_code] [varchar] (10) COLLATE Greek_CS_AS NULL ,
	[code_country] [varchar] (5) COLLATE Greek_CS_AS NULL ,
	[country] [varchar] (30) COLLATE Greek_CS_AS NULL ,
	[phone] [varchar] (30) COLLATE Greek_CS_AS NULL ,
	[fax] [varchar] (30) COLLATE Greek_CS_AS NULL ,
	[mobile] [varchar] (30) COLLATE Greek_CS_AS NULL ,
	[email] [varchar] (50) COLLATE Greek_CS_AS NULL ,
	[gender] [varchar] (2) COLLATE Greek_CS_AS NULL ,
	[birthdate] [varchar] (10) COLLATE Greek_CS_AS NULL ,
	[photo_type] [varchar] (10) COLLATE Greek_CS_AS NULL ,
	[code_application] [varchar] (10) COLLATE Greek_CS_AS NULL ,
	[application] [varchar] (50) COLLATE Greek_CS_AS NULL ,
	[code_mother_language] [varchar] (5) COLLATE Greek_CS_AS NULL ,
	[mother_language] [varchar] (100) COLLATE Greek_CS_AS NULL ,
	[social] [ntext] COLLATE Greek_CS_AS NULL ,
	[organisational] [ntext] COLLATE Greek_CS_AS NULL ,
	[technical] [ntext] COLLATE Greek_CS_AS NULL ,
	[computer] [ntext] COLLATE Greek_CS_AS NULL ,
	[artistic] [ntext] COLLATE Greek_CS_AS NULL ,
	[other] [ntext] COLLATE Greek_CS_AS NULL ,
	[additional] [ntext] COLLATE Greek_CS_AS NULL ,
	[annexes] [ntext] COLLATE Greek_CS_AS NULL ,
	[photo] [image] NULL 
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

