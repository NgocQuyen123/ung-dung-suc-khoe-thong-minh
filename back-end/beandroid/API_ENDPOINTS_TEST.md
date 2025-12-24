# 📋 API ENDPOINTS BƯỚC CHÂN - HƯỚNG DẪN TEST

## 🔧 CẤU HÌNH CƠ BẢN

**Base URL:** `http://localhost:8080`

**Port mặc định:** `8080` (kiểm tra trong `application.properties` nếu khác)

---

## 📡 DANH SÁCH ENDPOINTS

### 1️⃣ LẤY DỮ LIỆU 1 NGÀY
```
GET /api/buocchan/{idTaiKhoan}?loai=ngay&ngay=2024-12-24
```

**Ví dụ cụ thể:**
```
GET http://localhost:8080/api/buocchan/1?loai=ngay&ngay=2024-12-24
```

**Response mẫu:**
```json
[
  {
    "ngay": "2024-12-24",
    "soBuoc": 5000
  }
]
```

---

### 2️⃣ LẤY DỮ LIỆU 1 TUẦN (7 NGÀY)
```
GET /api/buocchan/{idTaiKhoan}?loai=tuan&ngay=2024-12-24
```

**Ví dụ cụ thể:**
```
GET http://localhost:8080/api/buocchan/1?loai=tuan&ngay=2024-12-24
```

**Giải thích:** Trả về 7 ngày trong tuần (Thứ 2 → Chủ nhật) chứa ngày được truyền vào.

**Response mẫu:**
```json
[
  {
    "ngay": "2024-12-23",
    "soBuoc": 4500
  },
  {
    "ngay": "2024-12-24",
    "soBuoc": 5000
  },
  {
    "ngay": "2024-12-25",
    "soBuoc": 6200
  },
  {
    "ngay": "2024-12-26",
    "soBuoc": 7100
  },
  {
    "ngay": "2024-12-27",
    "soBuoc": 0
  },
  {
    "ngay": "2024-12-28",
    "soBuoc": 0
  },
  {
    "ngay": "2024-12-29",
    "soBuoc": 0
  }
]
```

---

### 3️⃣ LẤY DỮ LIỆU CẢ THÁNG
```
GET /api/buocchan/{idTaiKhoan}?loai=thang&nam=2024&thang=12
```

**Ví dụ cụ thể:**
```
GET http://localhost:8080/api/buocchan/1?loai=thang&nam=2024&thang=12
```

**Giải thích:** Trả về tất cả các ngày trong tháng 12/2024 (ngày 1 → 31).

**Response mẫu:**
```json
[
  {
    "ngay": "2024-12-01",
    "soBuoc": 8000
  },
  {
    "ngay": "2024-12-02",
    "soBuoc": 7500
  },
  ...
  {
    "ngay": "2024-12-31",
    "soBuoc": 0
  }
]
```

---

### 4️⃣ LẤY DỮ LIỆU CẢ NĂM (TỔNG HỢP THEO THÁNG)
```
GET /api/buocchan/{idTaiKhoan}?loai=nam&nam=2024
```

**Ví dụ cụ thể:**
```
GET http://localhost:8080/api/buocchan/1?loai=nam&nam=2024
```

**Giải thích:** Trả về tổng số bước của 12 tháng trong năm 2024.

**Response mẫu:**
```json
[
  {
    "thang": 1,
    "soBuoc": 150000
  },
  {
    "thang": 2,
    "soBuoc": 180000
  },
  {
    "thang": 3,
    "soBuoc": 200000
  },
  ...
  {
    "thang": 12,
    "soBuoc": 0
  }
]
```

---

## 🧪 CÁCH TEST

### **Option 1: Dùng cURL (Terminal/PowerShell)**

```bash
# Test lấy dữ liệu 1 ngày
curl "http://localhost:8080/api/buocchan/1?loai=ngay&ngay=2024-12-24"

# Test lấy dữ liệu 1 tuần
curl "http://localhost:8080/api/buocchan/1?loai=tuan&ngay=2024-12-24"

# Test lấy dữ liệu cả tháng
curl "http://localhost:8080/api/buocchan/1?loai=thang&nam=2024&thang=12"

# Test lấy dữ liệu cả năm
curl "http://localhost:8080/api/buocchan/1?loai=nam&nam=2024"
```

---

### **Option 2: Dùng PowerShell (Windows)**

```powershell
# Test lấy dữ liệu 1 ngày
Invoke-WebRequest -Uri "http://localhost:8080/api/buocchan/1?loai=ngay&ngay=2024-12-24" | Select-Object -Expand Content

# Test lấy dữ liệu 1 tuần
Invoke-WebRequest -Uri "http://localhost:8080/api/buocchan/1?loai=tuan&ngay=2024-12-24" | Select-Object -Expand Content

# Test lấy dữ liệu cả tháng
Invoke-WebRequest -Uri "http://localhost:8080/api/buocchan/1?loai=thang&nam=2024&thang=12" | Select-Object -Expand Content

# Test lấy dữ liệu cả năm
Invoke-WebRequest -Uri "http://localhost:8080/api/buocchan/1?loai=nam&nam=2024" | Select-Object -Expand Content
```

---

### **Option 3: Dùng Postman/Insomnia**

1. Tạo request mới với method **GET**
2. Nhập URL từ các ví dụ trên
3. Click **Send**

---

### **Option 4: Dùng Browser**

Mở trình duyệt và paste URL vào thanh địa chỉ:
```
http://localhost:8080/api/buocchan/1?loai=ngay&ngay=2024-12-24
```

---

## ⚠️ LƯU Ý KHI TEST

1. **Khởi động server trước:**
   ```bash
   cd beandroid
   ./mvnw spring-boot:run
   ```

2. **Kiểm tra port:** Mặc định là `8080`, nếu khác thì xem file `application.properties`

3. **Thay đổi idTaiKhoan:** Thay `1` bằng ID tài khoản thực tế trong database của bạn

4. **Format ngày:** Luôn dùng `YYYY-MM-DD` (ví dụ: `2024-12-24`)

5. **Tháng:** Từ `1` đến `12` (không phải `01` đến `12`)

---

## 🐛 XỬ LÝ LỖI

### **Lỗi: Missing query param**
```json
{
  "timestamp": "2024-12-24T10:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Missing query param: ngay (YYYY-MM-DD) for loai=ngay",
  "path": "/api/buocchan/1"
}
```
**Nguyên nhân:** Thiếu tham số bắt buộc
**Giải pháp:** Kiểm tra lại URL có đầy đủ params chưa

---

### **Lỗi: Invalid loai**
```json
{
  "timestamp": "2024-12-24T10:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid loai. Allowed: ngay|tuan|thang|nam",
  "path": "/api/buocchan/1"
}
```
**Nguyên nhân:** Giá trị `loai` không hợp lệ
**Giải pháp:** Chỉ dùng: `ngay`, `tuan`, `thang`, hoặc `nam`

---

### **Lỗi: Connection refused**
```
Failed to connect to localhost port 8080: Connection refused
```
**Nguyên nhân:** Server chưa chạy
**Giải pháp:** Chạy lệnh `./mvnw spring-boot:run` trong thư mục `beandroid`

---

## 📊 KẾT QUẢ MONG ĐỢI

✅ **Nếu có dữ liệu:** Trả về mảng các object với `ngay` và `soBuoc`
✅ **Nếu không có dữ liệu:** Trả về mảng với `soBuoc = 0` (zero-fill)
✅ **HTTP Status:** `200 OK`
✅ **Content-Type:** `application/json`

---

## 🎯 TEST CHECKLIST

- [ ] Server đã khởi động thành công
- [ ] Test endpoint `loai=ngay` - hoạt động ✅
- [ ] Test endpoint `loai=tuan` - hoạt động ✅
- [ ] Test endpoint `loai=thang` - hoạt động ✅
- [ ] Test endpoint `loai=nam` - hoạt động ✅
- [ ] Test với idTaiKhoan khác nhau
- [ ] Test với ngày/tháng/năm khác nhau
- [ ] Test error handling (thiếu params, sai format)
- [ ] Test CORS từ frontend (nếu có)

---

## 📞 HỖ TRỢ

Nếu gặp vấn đề khi test, kiểm tra:
1. Log của server trong terminal
2. Database có dữ liệu test chưa
3. Port có bị chiếm bởi process khác không
4. Firewall có chặn port 8080 không

---

**Chúc bạn test thành công! 🚀**
