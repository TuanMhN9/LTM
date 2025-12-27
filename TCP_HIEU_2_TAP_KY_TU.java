/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 2. HIỆU CỦA HAI TẬP KÝ TỰ
//[Mã câu hỏi (qCode): wWTzyRuY]. [Loại bỏ các ký tự trong chuỗi thứ nhất mà xuất hiện trong chuỗi thứ hai] Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản sau:
//a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;DE0C2BF0"
//b.	Nhận lần lượt hai chuỗi ngẫu nhiên từ server
//c.	Loại bỏ các ký tự trong chuỗi thứ nhất mà xuất hiện trong chuỗi thứ hai, yêu cầu giữ nguyên thứ tự xuất hiện của ký tự. Gửi chuỗi thứ nhất đã được xử lý lên server.
//d.	Đóng kết nối và kết thúc chương trình


package LTM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCP_HIEU_2_TAP_KY_TU {

    public static void main(String[] args) throws Exception {

        String serverAddress = "172.188.19.218";
        int port = 2208;

        Socket socket = new Socket(serverAddress, port);

        BufferedReader in =
                new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out =
                new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String studentCode = "B15DCCN999";   // đổi mã SV của bạn
        String qCode = "wWTzyRuY";

        /* a. Gửi studentCode;qCode */
        out.write(studentCode + ";" + qCode);
        out.newLine();
        out.flush();

        /* b. Nhận 2 chuỗi từ server */
        String str1 = in.readLine();
        String str2 = in.readLine();

        /* c. Xử lý hiệu của hai tập ký tự */
        StringBuilder result = new StringBuilder();

        for (char c : str1.toCharArray()) {
            if (str2.indexOf(c) == -1) {
                result.append(c);
            }
        }

        /* d. Gửi chuỗi kết quả lên server */
        out.write(result.toString());
        out.newLine();
        out.flush();

        socket.close();
    }
}
