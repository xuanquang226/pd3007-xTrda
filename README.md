# Project name: xTrda
# Author: Bùi Xuân Quang
# Created Date: 2024

## 📌 Mô tả dự án
+ Website show tranh đăng ký đăng nhập đặt hàng,....   Sử dụng account này để trải nghiệm giỏ hàng, đặt hàng> propro456321 Quang.0312
+ Dự án này là một ứng dụng Mono bao gồm hai phần chính:

- **Backend**: Được xây dựng với **Java Spring Boot**, kết nối **PostgreSQL** để lưu trữ dữ liệu và sử dụng **Redis** cho việc quản lý vòng đời jwt.
- **Frontend**: Xây dựng với **Next.js** client side tạo trải nghiệm mượt mà, ngôn ngữ sử dụng **TypeScript**.

+ Project đã được deploy bằng Docker trên server VPS OS AlmaLinux tại **https://xiaotrada.com** ||||| VPS 500k/3tháng giá chắc hơi cao được cái uptime 99%, domain mua 1 năm lúc có discount 600k
- **Nginx** làm reverse proxy.
- Tất cả các config ở **docker-compose** chứa thông tin name service, container name, volume, network, các args và enviroment,... được lưu tại server nên ở git này chỉ có docker file build thôi.
- Toàn bộ service trong docker compose cùng network nên chỉ có service **nginx** được expose cho phép client truy cập và postgresql được expose để dùng dbeaver. 



