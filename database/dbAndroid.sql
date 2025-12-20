USE master;
GO

IF EXISTS (SELECT * FROM sys.databases WHERE name = 'QLSUCKHOE')
BEGIN
    ALTER DATABASE QLSUCKHOE SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE QLSUCKHOE;
END
GO

CREATE DATABASE QLSUCKHOE;
GO

USE QLSUCKHOE;
GO
CREATE TABLE TaiKhoan (
    id INT IDENTITY(1,1) PRIMARY KEY,
    Sdt VARCHAR(15) NOT NULL,
    TenTK NVARCHAR(100) NOT NULL,
    GioiTinh NVARCHAR(10),
    ChieuCao INT,
    NamSinh INT,
    CanNang FLOAT
);
CREATE TABLE MucDoHoatDong (
    id INT IDENTITY(1,1) PRIMARY KEY,
    TenMDHD NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(255),
    CaloHD INT
);
CREATE TABLE NhipDoCanNang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    TenNDCD NVARCHAR(100) NOT NULL,
    NhipDoCanNang INT
);
CREATE TABLE ThongTinCanNang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    idTKCanNangMT INT NOT NULL,
    idMDHD INT NOT NULL,
    idNDHD INT NOT NULL,
    CanNangTheoNgay FLOAT NOT NULL,
    NgayThietLap DATE NOT NULL,
    CONSTRAINT FK_ThongTinCanNang_TaiKhoan FOREIGN KEY (idTKCanNangMT)
        REFERENCES TaiKhoan(id),
    CONSTRAINT FK_ThongTinCanNang_MucDoHoatDong FOREIGN KEY (idMDHD)
        REFERENCES MucDoHoatDong(id),
    CONSTRAINT FK_ThongTinCanNang_NhipDoCanNang FOREIGN KEY (idNDHD)
        REFERENCES NhipDoCanNang(id) 
);
CREATE TABLE BuocChanNgay (
    id INT IDENTITY(1,1)  PRIMARY KEY,
    idTaiKhoan INT NOT NULL,
    Ngay DATE NOT NULL,
    SoBuoc INT DEFAULT 0,
    Kcal FLOAT DEFAULT 0,          
    QuangDuong FLOAT DEFAULT 0,   
    ThoiGianGiay INT DEFAULT 0,   

    Nam AS YEAR(Ngay),
    Thang AS MONTH(Ngay),
    Tuan AS DATEPART(WEEK, Ngay),

    UNIQUE (idTaiKhoan, Ngay), 

    FOREIGN KEY (idTaiKhoan) REFERENCES TaiKhoan(id)
);
CREATE TABLE SleepNgay (
    id INT IDENTITY(1,1) PRIMARY KEY,
    idTaiKhoan INT NOT NULL,
    ngay DATE NOT NULL,

    tongThoiGianNgu INT DEFAULT 0,         
    thoiGianNguSau INT DEFAULT 0,         
    thoiGianNguNhe INT DEFAULT 0,          
    thoiGianNguREM INT DEFAULT 0,          
    thoiGianThuc INT DEFAULT 0,           

    gioBatDau DATETIME NULL,
    gioKetThuc DATETIME NULL,

    nam AS YEAR(ngay),
	thang AS MONTH(ngay),
	tuan AS DATEPART(WEEK, ngay)

    UNIQUE (idTaiKhoan, Ngay), 

    FOREIGN KEY (idTaiKhoan) REFERENCES TaiKhoan(id)
);


INSERT INTO TaiKhoan (Sdt, TenTK, GioiTinh, ChieuCao, NamSinh, CanNang)
VALUES 
    ('0901234567', N'Trần Thị B', N'Nữ', 160, 1992, 55.0),
    ('0912345678', N'Le Van C', N'Nam', NULL, 1985, 70.2);