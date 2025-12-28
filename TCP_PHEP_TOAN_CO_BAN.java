/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 3. PHÉP TOÁN CƠ BẢN
//[Mã câu hỏi (qCode): nkBwM6AE]. Một chương trình máy chủ cho phép kết nối qua TCP tại cổng
//2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng chương trình
//client tương tác với server trên bằng các byte stream (DataInputStream/DataOutputStream) để trao
//đổi thông tin theo trình tự sau:
//a. Gửi một chuỗi chứa mã sinh viên và mã câu hỏi ở định dạng "studentCode;qCode". Ví dụ:
//"B15DCCN999;B1F1FDCD"
//b. Nhận hai số nguyên a và b tương ứng từ máy chủ
//c. Tính ước chung lớn nhất, bội chung nhỏ nhất, tổng, tích. Gửi từng giá trị số nguyên theo thứ tự
//trên đến máy chủ.
//d. Đóng kết nối và kết thúc chương trình.

package LTM;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCP_PHEP_TOAN_CO_BAN {

    public static void main(String[] args) throws Exception {

        String host = "localhost"; // đổi nếu server khác máy
        int port = 2207;

        String studentCode = "B15DCCN999";
        String qCode = "nkBwM6AE";

        Socket socket = new Socket(host, port);

        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        // a. Gửi studentCode;qCode
        String request = studentCode + ";" + qCode;
        out.writeUTF(request);
        out.flush();

        // b. Nhận hai số nguyên a và b
        int a = in.readInt();
        int b = in.readInt();

        System.out.println("Nhận được a = " + a + ", b = " + b);

        // c. Tính toán
        int gcd = gcd(a, b);
        int lcm = Math.abs(a * b) / gcd;
        int sum = a + b;
        int product = a * b;

        // Gửi kết quả theo đúng thứ tự
        out.writeInt(gcd);
        out.writeInt(lcm);
        out.writeInt(sum);
        out.writeInt(product);
        out.flush();

        // d. Đóng kết nối
        socket.close();
    }

    // Hàm tính ƯCLN (Euclid)
    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return Math.abs(a);
    }
}
