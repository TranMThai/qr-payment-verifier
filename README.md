# QR Payment Verifier 🔊

QR Payment Verifier là một dự án lấy cảm hứng từ các thiết bị **loa thông báo thanh toán** đang được sử dụng rộng rãi tại các cửa hàng và chợ ở Hà Nội. Khi có giao dịch thanh toán thành công, hệ thống sẽ phát giọng nói thông báo số tiền tương ứng.

## 🧰 Yêu cầu trước khi chạy dự án

### 1. Google Cloud Text-to-Speech credentials
- Truy cập: [Google Cloud Text-to-Speech](https://cloud.google.com/text-to-speech)
- Tạo service account và tải về file `credentials.json`

### 2. Sepay API Token
- Truy cập: [Sepay API](https://docs.sepay.vn/tao-api-token.html)
- Tạo API token để nhận thông báo giao dịch

## ⚙️ Cấu hình Backend

### 1. Khởi tạo cơ sở dữ liệu
- Sử dụng MySQL
- Chạy file SQL có sẵn: `qr-payment-verifier.sql`

### 2. Tạo file TTS credentials
- Đổi tên file `credentials.json` tải từ Google thành `tts-credentials.json`
- Đặt vào thư mục: `qr-payment-verifier/src/main/resources/credentials`

### 3. Cấu hình file `application.properties`
- Mở file `application.properties`
- Sửa thông tin kêt nối MySQL và Token API của SePay


## 📦 Công nghệ sử dụng

- **Backend:** Spring Boot, WebSocket, MySQL
- **Frontend:** React, TypeScript, STOMP over SockJS
- **TTS:** Google Cloud Text-to-Speech
- **API thanh toán:** Sepay.vn
