/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//BÀI 1. TÌM TỪ DÀI NHẤT [546]
//(Ghi chú: Sử dụng hàm s.indexOf, lấy nguyên chỉ số, không cộng thêm 1)
//[Mã câu hỏi (qCode): TdOpiEAP]. Một chương trình server cho phép kết nối qua giao thức TCP
//tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng
//một chương trình client thực hiện kết nối tới server và sử dụng luồng ký tự
//(BufferedWriter/BufferedReader) để trao đổi thông tin theo kịch bản sau:
//a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ:
//"B15DCCN999;A1B2C3D4".
//b. Nhận từ server một chuỗi ngẫu nhiên.
//c. Xử lý chuỗi đã nhận theo các bước sau:
// Bước 1: Tìm từ dài nhất trong trong chuỗi ngẫu nhiên (từ là chuỗi con phân tách bởi khoảng
//trắng).
// Bước 2: Xác định vị trí bắt đầu của từ dài nhất đó trong chuỗi ban đầu.
//d. Gửi lần lượt hai giá trị lên server:
// - Từ dài nhất xuất hiện trong chuỗi.
// - Vị trí bắt đầu của từ trong chuỗi ban đầu.
//e. Đóng kết nối và kết thúc chương trình.

package LTM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCP_TIM_TU_DAI_NHAT {

    public static void main(String[] args) throws Exception {

        String host = "localhost"; // đổi nếu server khác máy
        int port = 2208;

        String studentCode = "B15DCCN999";
        String qCode = "TdOpiEAP";

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

        // b. Nhận chuỗi ngẫu nhiên từ server
        String s = in.readLine();

        // c. Xử lý tìm từ dài nhất
        String[] words = s.split("\\s+");

        String longestWord = words[0];
        for (String word : words) {
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
        }

        // Vị trí bắt đầu (theo yêu cầu KHÔNG +1)
        int index = s.indexOf(longestWord);

        // d. Gửi kết quả lên server
        out.write(longestWord);
        out.newLine();
        out.write(String.valueOf(index));
        out.newLine();
        out.flush();

        // e. Đóng kết nối
        socket.close();
    }
}
