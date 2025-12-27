/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 7. NÉN RLE
//[Mã câu hỏi (qCode): ji3fQD3Q].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản sau:
//a. Gửi một chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode".
//Ví dụ: "B15DCCN999;1D08FX21"
//b. Nhận từ server một chuỗi chứa nhiều từ, các từ được phân tách bởi khoảng trắng.
//Ví dụ: "hello world programming is fun"
//c. Thực hiện đảo ngược từ và mã hóa RLE để nén chuỗi ("aabb" nén thành "a2b2"). Gửi chuỗi đã được xử lý lên server. Ví dụ: "ol2eh dlrow gnim2argorp si nuf".
//d. Đóng kết nối và kết thúc chương trình


package LTM;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCP_CHARACTER_STREAM_NEN_RLE {
    public static void main(String[] args) throws Exception {

        // 1. Kết nối server
        Socket socket = new Socket("localhost", 2208);

        BufferedReader br = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));

        // 2. Gửi mã sinh viên và mã câu hỏi
        String studentCode = "B15DCCN999";
        String qCode = "ji3fQD3Q";
        bw.write(studentCode + ";" + qCode);
        bw.newLine();
        bw.flush();

        // 3. Nhận chuỗi từ server
        String data = br.readLine();
        // ví dụ: "hello world programming is fun"

        String[] words = data.split("\\s+");
        StringBuilder result = new StringBuilder();

        // 4. Xử lý từng từ
        for (int w = 0; w < words.length; w++) {
            // Đảo ngược từ
            String reversed = new StringBuilder(words[w]).reverse().toString();

            // Nén RLE
            StringBuilder rle = new StringBuilder();
            int count = 1;

            for (int i = 1; i <= reversed.length(); i++) {
                if (i < reversed.length() &&
                        reversed.charAt(i) == reversed.charAt(i - 1)) {
                    count++;
                } else {
                    rle.append(reversed.charAt(i - 1));
                    if (count > 1) {
                        rle.append(count);
                    }
                    count = 1;
                }
            }

            result.append(rle);
            if (w < words.length - 1) {
                result.append(" ");
            }
        }

        // 5. Gửi kết quả lên server
        bw.write(result.toString());
        bw.newLine();
        bw.flush();

        // 6. Đóng kết nối
        br.close();
        bw.close();
        socket.close();
    }
}
