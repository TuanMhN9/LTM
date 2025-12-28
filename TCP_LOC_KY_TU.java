/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 2. LỌC KÝ TỰ[718]
//[Mã câu hỏi (qCode): uQWRjN4f]. Một chương trình server cho phép kết nối qua giao thức TCP
//tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một
//chương trình client thực hiện kết nối tới server và sử dụng luồng ký tự
//(BufferedWriter/BufferedReader) để trao đổi thông tin theo kịch bản
//a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ:
//"B15DCCN999;5E263AE1"
//b. Nhận một chuỗi ngẫu nhiên từ server
//c. Tách chuỗi đã nhận thành 2 chuỗi và gửi lần lượt theo thứ tự lên server
// i. Chuỗi thứ nhất gồm các ký tự và số (loại bỏ các ký tự đặc biệt)
// ii. Chuỗi thứ hai gồm các ký tự đặc biệt
//d. Đóng kết nối và kết thúc chương trình

package LTM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCP_LOC_KY_TU {

    public static void main(String[] args) throws Exception {

        String host = "localhost"; // đổi nếu server khác máy
        int port = 2208;

        String studentCode = "B15DCCN999";
        String qCode = "uQWRjN4f";

        Socket socket = new Socket(host, port);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())
        );

        // a. Gửi studentCode;qCode
        String request = studentCode + ";" + qCode;
        out.write(request);
        out.newLine();
        out.flush();

        // b. Nhận chuỗi từ server
        String s = in.readLine();

        StringBuilder normal = new StringBuilder();
        StringBuilder special = new StringBuilder();

        // c. Lọc ký tự
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                normal.append(c);
            } else {
                special.append(c);
            }
        }

        // d. Gửi 2 chuỗi lên server (đúng thứ tự)
        out.write(normal.toString());
        out.newLine();
        out.write(special.toString());
        out.newLine();
        out.flush();

        // e. Đóng kết nối
        socket.close();
    }
}
