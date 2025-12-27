/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//Mã bài tập x8c45mq
//Một chương trình server tại địa chỉ 172.188.19.218 cho phép kết nối qua giao thức TCP tại cổng
//1606 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương
//trình client tương tác với server sử dụng các luồng byte (BufferedWriter/BufferedReader) theo kịch
//bản sau:
//a/ Gửi chuỗi là mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
//Ví dụ: "B20DCCN999;ABCDEF" với ABCDEF là mã bài tập đã đề cập ở trên.
//b. Nhận một chuỗi từ server (Chỉ chứa kí tự thường)
//c. Thực hiện loại bỏ các nguyên âm trong chuỗi và gửi kết quả lên server
//d. Đóng kết nối và kết thúc.

package LTM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCP_CHARACTER_LOAI_BO_KI_TU_NGUYEN_AM {

    public static void main(String[] args) throws Exception {

        String serverAddress = "172.188.19.218";
        int serverPort = 1606;

        // ⚠️ Thay bằng mã sinh viên của bạn
        String studentCode = "B20DCCN999";
        String qCode = "x8c45mq";

        // Kết nối tới server
        Socket socket = new Socket(serverAddress, serverPort);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())
        );

        /* a. Gửi mã sinh viên và mã câu hỏi */
        writer.write(studentCode + ";" + qCode);
        writer.newLine();
        writer.flush();

        /* b. Nhận chuỗi từ server */
        String input = reader.readLine();

        /* c. Loại bỏ nguyên âm */
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c != 'a' && c != 'e' && c != 'i' && c != 'o' && c != 'u') {
                result.append(c);
            }
        }

        /* d. Gửi kết quả lên server */
        writer.write(result.toString());
        writer.newLine();
        writer.flush();

        /* e. Đóng kết nối */
        reader.close();
        writer.close();
        socket.close();
    }
}
