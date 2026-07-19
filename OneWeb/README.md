# Hướng dẫn chạy:
- Cấu hình Node ENV: `npm install -g win-node-env`, chỉ chạy duy nhất một lần ban đầu
- Cài đặt thư viện: `npm install`, chỉ chạy một lần ban đầu hoặc khi có thay đổi trong `package.json`
- Lệnh chạy: `npm run dev`
# Lưu ý:
- Có thể tắt các module, chức năng không cần thiết, chỉ để lại module, chức năng cần thiết khi chạy cho đảm bảo tài nguyên **(không up các file router lên SCM)**.
- Khi chạy bằng lệnh `npm run dev` gặp lỗi phiên bản thư viện `vue-template-compiler` thì cập nhật về phiên bản `2.6.12` trong `package.json`. `"vue-template-compiler": "2.6.12"`

**Tắt module:**

- Mở router chính: `src/router/index.js`
- Comment các dòng import module sử dụng trong router để tắt module

**Tắt chức năng trong module:**
- Mở router chính: `src/router/index.js`
- Lấy đường dẫn router module cần tắt chức năng
- Mở router module theo đường dẫn trên
- Comment các dòng import chức năng sử dụng trong router module để tắt chức năng
