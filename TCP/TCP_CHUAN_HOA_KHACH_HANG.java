/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): hwR2ZGGo].  Thông tin khách hàng cần thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:
//a.	Tên khách hàng cần được chuẩn hóa theo định dạng mới. Ví dụ: nguyen van hai duong -> DUONG, Nguyen Van Hai
//b.	Ngày sinh của khách hàng hiện đang ở dạng mm-dd-yyyy, cần được chuyển thành định dạng dd/mm/yyyy. Ví dụ: 10-11-2012 -> 11/10/2012
//c.	Tài khoản khách hàng là các chữ cái in thường được sinh tự động từ họ tên khách hàng. Ví dụ: nguyen van hai duong -> nvhduong
//
//Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) thực hiện gửi/nhận đối tượng khách hàng và chuẩn hóa. Cụ thể:
//a.	Đối tượng trao đổi là thể hiện của lớp Customer được mô tả như sau
//      •	Tên đầy đủ của lớp: TCP.Customer
//      •	Các thuộc tính: id int, code String, name String, dayOfBirth String, userName String
//      •	Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
//      •	Trường dữ liệu: private static final long serialVersionUID = 20170711L; 
//b.	Tương tác với server theo kịch bản dưới đây:
//	1) Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi ở định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;F2DA54F3"
//	2) Nhận một đối tượng là thể hiện của lớp Customer từ server với các thông tin đã được thiết lập
//	3) Thay đổi định dạng theo các yêu cầu ở trên và gán vào các thuộc tính tương ứng.  Gửi đối tượng đã được sửa đổi lên server
//	4) Đóng socket và kết thúc chương trình.

package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCP_CHUAN_HOA_KHACH_HANG {

    public static void main(String[] args) {
        String serverIp = "203.162.10.109"; // khi thi đổi thành Exam_IP
        int port = 2209;

        String studentCode = "B22DCCN760";   // 🔴 SỬA MÃ SV
        String qCode = "hwR2ZGGo";

        try (Socket socket = new Socket(serverIp, port)) {

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            /* Bước 1: gửi studentCode;qCode */
            oos.writeObject(studentCode + ";" + qCode);
            oos.flush();

            /* Bước 2: nhận đối tượng Customer */
            Customer customer = (Customer) ois.readObject();

            /* Bước 3: chuẩn hóa dữ liệu */

            // a. Chuẩn hóa tên
            String normalizedName = normalizeName(customer.getName());
            customer.setName(normalizedName);

            // b. Chuẩn hóa ngày sinh mm-dd-yyyy -> dd/mm/yyyy
            String normalizedDob = normalizeDate(customer.getDayOfBirth());
            customer.setDayOfBirth(normalizedDob);

            // c. Tạo username
            String username = generateUsername(customer.getName());
            customer.setUserName(username);

            /* Bước 4: gửi lại object */
            oos.writeObject(customer);
            oos.flush();

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ================= HÀM XỬ LÝ ================= */

    // DUONG, Nguyen Van Hai
    private static String normalizeName(String name) {
        String[] parts = name.trim().toLowerCase().split("\\s+");
        String ho = parts[parts.length - 1].toUpperCase();

        StringBuilder ten = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) {
            ten.append(Character.toUpperCase(parts[i].charAt(0)))
               .append(parts[i].substring(1))
               .append(" ");
        }

        return ho + ", " + ten.toString().trim();
    }

    // mm-dd-yyyy -> dd/mm/yyyy
    private static String normalizeDate(String dob) {
        String[] parts = dob.split("-");
        return parts[1] + "/" + parts[0] + "/" + parts[2];
    }

    // nvhduong
    private static String generateUsername(String normalizedName) {
        // normalizedName dạng: DUONG, Nguyen Van Hai
        String[] parts = normalizedName.split(", ");
        String ho = parts[0].toLowerCase();
        String[] tenParts = parts[1].toLowerCase().split("\\s+");

        StringBuilder sb = new StringBuilder();
        for (String s : tenParts) {
            sb.append(s.charAt(0));
        }
        sb.append(ho);
        return sb.toString();
    }
}
