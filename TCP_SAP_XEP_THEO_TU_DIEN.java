/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 4. SẮP XẾP CHUỖI
//[Mã câu hỏi (qCode): lXo9m21K]. Một chương trình server cho phép kết nối qua giao thức TCP
//tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một
//chương trình client thực hiện kết nối tới server và sử dụng luồng ký tự
//(BufferedWriter/BufferedReader) để trao đổi thông tin theo kịch bản sau:
//a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ:
//"B15DCCN999;X1107ABC".
//b. Nhận từ server một chuỗi ngẫu nhiên chứa nhiều từ, các từ phân tách bởi khoảng trắng.
//c. Thực hiện các bước xử lý:
// Bước 1: Tách chuỗi thành các từ dựa trên khoảng trắng.
// Bước 2: Sắp xếp các từ theo thứ tự từ điển (có phân biệt chữ cái hoa thường).
//d. Gửi lại chuỗi đã sắp xếp theo thứ tự từ điển lên server.


package LTM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;

public class TCP_SAP_XEP_THEO_TU_DIEN {

    public static void main(String[] args) throws Exception {

        String server = "172.188.19.218";
        int port = 2208;

        Socket socket = new Socket(server, port);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())
        );

        String studentCode = "B15DCCN999";   // đổi mã SV
        String qCode = "lXo9m21K";

        /* a. Gửi studentCode;qCode */
        out.write(studentCode + ";" + qCode);
        out.newLine();
        out.flush();

        /* b. Nhận chuỗi */
        String data = in.readLine();

        /* c. Xử lý */
        String[] words = data.split(" ");
        Arrays.sort(words); // phân biệt hoa thường

        String result = String.join(" ", words);

        /* d. Gửi lại chuỗi đã sắp xếp */
        out.write(result);
        out.newLine();
        out.flush();

        socket.close();
    }
}
