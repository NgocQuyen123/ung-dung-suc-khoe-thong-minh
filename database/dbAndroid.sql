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
GO

CREATE TABLE MucDoHoatDong (
    id INT IDENTITY(1,1) PRIMARY KEY,
    TenMDHD NVARCHAR(100) NOT NULL,
    MoTa NVARCHAR(255),
    CaloHD INT
);
GO

CREATE TABLE NhipDoCanNang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    TenNDCD NVARCHAR(50) NOT NULL,
    TocDoKgTuan FLOAT NOT NULL,
    CaloThayDoiMoiNgay INT NOT NULL
);

CREATE TABLE ThongTinCanNang (
    id INT IDENTITY(1,1) PRIMARY KEY,
    idTK INT NOT NULL,
    CanNangMucTieu FLOAT NOT NULL,
    idMDHD INT NOT NULL,
    idNDHD INT NOT NULL,
    CanNangTheoNgay FLOAT NOT NULL,
    NgayThietLap DATE NOT NULL,
    CONSTRAINT FK_ThongTinCanNang_TaiKhoan FOREIGN KEY (idTK)
        REFERENCES TaiKhoan(id),
    CONSTRAINT FK_ThongTinCanNang_MucDoHoatDong FOREIGN KEY (idMDHD)
        REFERENCES MucDoHoatDong(id),
    CONSTRAINT FK_ThongTinCanNang_NhipDoCanNang FOREIGN KEY (idNDHD)
        REFERENCES NhipDoCanNang(id) 
);
GO

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
GO

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
('0912345678', N'Nguyễn Văn An', N'Nam', 170, 2000, 65.5),
('0987654321', N'Trần Thị Bình', N'Nữ', 160, 1999, 50.2),
('0901122334', N'Lê Văn Cường', N'Nam', 175, 1998, 70.0),
('0977888999', N'Phạm Thị Duyên', N'Nữ', 158, 2001, 48.5),
('0933445566', N'Hoàng Văn Em', N'Nam', 180, 1997, 78.3);
GO

INSERT INTO MucDoHoatDong (TenMDHD, MoTa, CaloHD)
VALUES
(N'Thụ động', N'Ít hoặc không tập thể dục', 118),
(N'Ít hoạt động', N'Tập luyện 2 - 3 giờ mỗi tuần', 235),
(N'Hoạt động trung bình', N'Tập luyện 4 - 6 giờ mỗi tuần', 471),
(N'Rất năng động', N'Tập luyện trên 7 giờ mỗi tuần', 785);
GO

INSERT INTO NhipDoCanNang (TenNDCD, TocDoKgTuan, CaloThayDoiMoiNgay)
VALUES
(N'Dễ', 0.25, 275),
(N'Vừa', 0.50, 550),
(N'Khó', 0.75, 825),
(N'Tối đa', 1.00, 1100);
GO

INSERT INTO ThongTinCanNang
(idTK, CanNangMucTieu, idMDHD, idNDHD, CanNangTheoNgay, NgayThietLap)
VALUES
(1, 65.0, 2, 1, 65.0, '2025-01-01'),
(2, 50.0, 3, 2, 50.5, '2025-01-02'),
(3, 70.0, 4, 3, 70.2, '2025-01-03'),
(4, 48.0, 1, 4, 48.0, '2025-01-04'),
(5, 78.0, 3, 3, 78.3, '2025-01-05');
GO

INSERT INTO BuocChanNgay
(idTaiKhoan, Ngay, SoBuoc, Kcal, QuangDuong, ThoiGianGiay)
VALUES
(1, '2025-01-01', 6500, 260.5, 4.8, 3600),
(2, '2025-01-01', 8200, 330.0, 6.1, 4200),
(3, '2025-01-02', 10000, 400.2, 7.5, 5400),
(4, '2025-01-02', 4500, 180.0, 3.2, 2700),
(5, '2025-01-03', 12000, 480.7, 9.0, 6300);
GO

INSERT INTO SleepNgay
(idTaiKhoan, ngay, tongThoiGianNgu, thoiGianNguSau, thoiGianNguNhe, thoiGianNguREM, thoiGianThuc, gioBatDau, gioKetThuc)
VALUES
(1, '2025-01-01', 25200, 10800, 9000, 5400, 1800, '2025-01-01 23:00:00', '2025-01-02 06:00:00'),
(2, '2025-01-01', 23400, 9000, 9600, 4800, 2100, '2025-01-01 22:30:00', '2025-01-02 05:30:00'),
(3, '2025-01-02', 28800, 12600, 10200, 6000, 1200, '2025-01-02 22:00:00', '2025-01-03 06:00:00'),
(4, '2025-01-02', 21600, 7200, 9600, 4800, 2400, '2025-01-02 23:30:00', '2025-01-03 05:30:00'),
(5, '2025-01-03', 27000, 10800, 10200, 6000, 1500, '2025-01-03 22:15:00', '2025-01-04 06:15:00');
GO



--------CÂN NẶNG----------
INSERT INTO ThongTinCanNang
(idTK, CanNangMucTieu, idMDHD, idNDHD, CanNangTheoNgay, NgayThietLap)
VALUES
(1, 64.0, 2, 4, 65.2, '2025-10-27'),
(1, 64.0, 2, 4, 65.1, '2025-10-29'),
(1, 64.0, 2, 4, 65.0, '2025-11-01'),
(1, 64.0, 2, 4, 64.9, '2025-11-02');
GO

INSERT INTO ThongTinCanNang
(idTK, CanNangMucTieu, idMDHD, idNDHD, CanNangTheoNgay, NgayThietLap)
VALUES
(1, 64.0, 2, 4, 64.8, '2025-11-03'),
(1, 64.0, 2, 4, 64.7, '2025-11-05'),
(1, 64.0, 2, 4, 64.6, '2025-11-07'),
(1, 64.0, 2, 4, 64.5, '2025-11-09');
GO

INSERT INTO ThongTinCanNang
(idTK, CanNangMucTieu, idMDHD, idNDHD, CanNangTheoNgay, NgayThietLap)
VALUES
(1, 64.0, 2, 4, 64.8, '2025-11-04'),
(1, 64.0, 2, 4, 64.7, '2025-11-05'),
(1, 64.0, 2, 4, 64.6, '2025-11-07'),
(1, 64.0, 2, 4, 64.5, '2025-11-09');
GO

INSERT INTO ThongTinCanNang
(idTK, CanNangMucTieu, idMDHD, idNDHD, CanNangTheoNgay, NgayThietLap)
VALUES
(1, 63.5, 3, 4, 64.0, '2025-12-15'),
(1, 63.5, 3, 4, 63.9, '2025-12-17'),
(1, 63.5, 3, 4, 63.8, '2025-12-19'),
(1, 63.5, 3, 4, 63.7, '2025-12-21');
GO

INSERT INTO ThongTinCanNang
(idTK, CanNangMucTieu, idMDHD, idNDHD, CanNangTheoNgay, NgayThietLap)
VALUES
(1, 63.0, 2, 4, 63.5, '2026-01-05'),
(1, 63.0, 2, 4, 63.3, '2026-02-10'),
(1, 63.0, 2, 4, 63.0, '2026-03-15');
GO



