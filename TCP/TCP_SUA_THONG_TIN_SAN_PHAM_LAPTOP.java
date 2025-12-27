/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): 3PIY6Asu].  Thông tin sản phẩm vì một lý do nào đó đã bị sửa đổi thành không đúng, cụ thể:
//a) Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng, ví dụ: “lenovo thinkpad T520” bị chuyển thành “T520 thinkpad lenovo”
//b) Số lượng sản phẩm cũng bị đảo ngược giá trị, ví dụ từ 9981 thành 1899
//
//Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) để gửi/nhận và sửa các thông tin bị sai của sản phẩm. Chi tiết dưới đây:
//a) Đối tượng trao đổi là thể hiện của lớp Laptop được mô tả như sau
//      •	Tên đầy đủ của lớp: TCP.Laptop
//      •	Các thuộc tính: id int, code String, name String, quantity int
//      •	Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
//      •	Trường dữ liệu: private static final long serialVersionUID = 20150711L; 
//b)	Tương tác với server theo kịch bản
//1)	Gửi đối tượng là chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;5AD2B818"
//2)	Nhận một đối tượng là thể hiện của lớp Laptop từ server
//3)	Sửa các thông tin sai của sản phẩm về tên và số lượng.  Gửi đối tượng vừa được sửa sai lên server
//4)	Đóng socket và kết thúc chương trình.
package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCP_SUA_THONG_TIN_SAN_PHAM_LAPTOP {

    public static void main(String[] args) {
        String serverIp = "203.162.10.109"; // 🔴 đổi thành Exam_IP khi thi
        int port = 2209;

        String studentCode = "B22DCCN088"; // 🔴 đổi mã SV
        String qCode = "3PIY6Asu";

        try (Socket socket = new Socket(serverIp, port)) {

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            /* 1. Gửi studentCode;qCode */
            oos.writeObject(studentCode + ";" + qCode);
            oos.flush();

            /* 2. Nhận đối tượng Laptop */
            TCP.Laptop laptop = (TCP.Laptop) ois.readObject();

            /* 3. Sửa dữ liệu */
            laptop.setName(fixName(laptop.getName()));
            laptop.setQuantity(reverseNumber(laptop.getQuantity()));

            /* 4. Gửi lại đối tượng */
            oos.writeObject(laptop);
            oos.flush();

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* đảo thứ tự từ trong tên */
    private static String fixName(String name) {
        String[] words = name.trim().split("\\s+");

        if (words.length < 2) {
            return name;
        }

        String first = words[0];
        String last = words[words.length - 1];

        StringBuilder sb = new StringBuilder();
        sb.append(last).append(" ");

        for (int i = 1; i < words.length - 1; i++) {
            sb.append(words[i]).append(" ");
        }

        sb.append(first);

        return sb.toString().trim();
    }


    /* đảo chữ số của số lượng */
    private static int reverseNumber(int n) {
        String reversed = new StringBuilder(String.valueOf(n)).reverse().toString();
        return Integer.parseInt(reversed);
    }
}
