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
---
## 1. Spring security
### 1.1. Khái niệm
- Spring Security được phát triển bởi SpringSource (hiện thuộc Pivotal) và được xem là một trong những framework bảo mật phổ biến nhất cho ứng dụng Java
- Spring Security cung cấp các tính năng xác thực (authentication) và phân quyền (authorization) cho các ứng dụng, cũng như hỗ trợ các tiêu chuẩn và giao thức bảo mật như HTTPS, OAuth2, JWT, LDAP, SAML, OpenID Connect.

### 1.2. Cơ chế hoạt động
- Spring Security hoạt động theo mô hình client-server. Khi một client gửi một request đến server, server sẽ xác thực người dùng và phân quyền để đảm bảo rằng người dùng chỉ có thể truy cập vào những tài nguyên mà họ được phép truy cập.
- Cơ chế hoạt động của Spring Security dựa trên cơ chế lọc (filter) và sự kiện (event) để can thiệp vào quá trình xử lý yêu cầu (request) và phản hồi (response) của ứng dụng web, tức là khi một yêu cầu được gửi đến ứng dụng web, nó sẽ được chuyển qua một chuỗi các bộ lọc (filter chain) do Spring Security quản lý. Mỗi bộ lọc có một nhiệm vụ cụ thể, như kiểm tra xác thực, kiểm tra phân quyền, điều hướng đến trang đăng nhập hoặc đăng xuất, xử lý các lỗi bảo mật.
- Nếu một yêu cầu không thỏa mãn các điều kiện bảo mật của ứng dụng, Spring Security sẽ sinh ra một sự kiện (event) để thông báo cho ứng dụng biết. Ứng dụng có thể lắng nghe và xử lý các sự kiện này theo ý muốn, ví dụ như ghi log, gửi email hoặc hiển thị thông báo lỗi.
- Ngược lại, nếu một yêu cầu được chấp nhận bởi Spring Security, nó sẽ được tiếp tục xử lý bởi ứng dụng web như bình thường. Khi ứng dụng web trả về một phản hồi cho yêu cầu, nó cũng sẽ được chuyển qua lại chuỗi các bộ lọc của Spring Security để áp dụng các thiết lập bảo mật cho phản hồi.
- Và cơ chế ấy bao gồm ba thành phần chính:
    - Authentication
    - Authorization
    - Authentication Provider
### 1.3. Các thành phần cơ bản
#### Authentication
- Authentication là quá trình xác thực xem người dùng có quyền truy cập vào ứng dụng hay không. Khi người dùng đăng nhập vào hệ thống, thông tin đăng nhập của họ sẽ được xác thực để đảm bảo rằng họ là người dùng hợp lệ và có quyền truy cập vào các tài nguyên yêu cầu.
- **Stateful và Stateless:**
  - **Trạng thái (Stateful)** là một cách tiếp cận xác thực trong đó hệ thống sẽ lưu trữ thông tin xác thực của người dùng hoặc ứng dụng trong một phiên (session) trên máy chủ. Khi người dùng hoặc ứng dụng gửi một yêu cầu mới, hệ thống sẽ kiểm tra phiên hiện tại để xác định danh tính và quyền hạn của người dùng hoặc ứng dụng.
  - **Phi trạng thái (Stateless)** là một cách tiếp cận khác với Stateful, khi đó hệ thống không lưu trữ thông tin xác thực của người dùng hoặc ứng dụng trên máy chủ, mà chỉ sử dụng các mã token đã được ký số để xác thực thông tin. Khi đó người dùng hoặc ứng dụng gửi một yêu cầu mới, hệ thống sẽ kiểm tra mã token để xác định danh tính và quyền hạn của người dùng hoặc ứng dụng.

#### Authorization
- Authorization là quá trình xác định quyền truy cập của người dùng đối với các tài nguyên trong ứng dụng. Khi người dùng truy cập vào một tài nguyên, Spring Security sẽ kiểm tra xem người dùng có được phép truy cập vào tài nguyên đó hay không hoặc thực hiện một hành động nào đó trong hệ thống.
- Authorization thường dựa trên các thông tin về vai trò (role), nhóm (group), quyền hạn (permission), chính sách (policy). Ngoài ra, còn giúp đảm bảo rằng chỉ những người dùng hoặc ứng dụng có quyền thích hợp mới có thể truy cập vào tài nguyên hoặc thực hiện hành động được bảo vệ.