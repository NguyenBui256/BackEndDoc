# Buổi 5:
🎯 **Nội dung cần chuẩn bị:**
- Spring security là gì ?
- JWT là gì ?
- Authentication, Authorization là gì ?

📑 **Bài tập:** 
- Triển khai 1 hệ thống quản lý User đơn giản
- Có đăng nhập, đăng ký
- Các API trừ (đăng ký đăng nhập) muốn sử dụng thì cần phải đăng nhập mới sử dụng được. Không qua đăng nhập, chưa xác thức thì báo lỗi chưa xác thực
Phân quyền User : USER và ADMIN
- Có API lấy ra thông tin của chính User đang đăng nhập (không phải là truyền id vào xong lấy ra mà không được truyền id hay key gì hết phải lấy ra thông tin của chính User đang đăng nhập)
- Có API lấy ra danh sách tất cả User. Chỉ User có quyền ADMIN mới dùng được API này