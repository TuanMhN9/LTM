/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 6. SẮP XẾP THEO ĐỘ DÀI 
//[Mã câu hỏi (qCode): we3kcWxZ].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản sau:
//a. Gửi một chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;C1234567"
//b. Nhận từ server một chuỗi chứa nhiều từ, các từ được phân tách bởi khoảng trắng. Ví dụ: "hello world this is a test example"
//c. Sắp xếp các từ trong chuỗi theo độ dài, thứ tự xuất hiện. Gửi danh sách các từ theo từng nhóm về server theo định dạng: "a, is, this, test, hello, world, example".
//d. Đóng kết nối và kết thúc chương trình.

package LTM;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Comparator;

public class TCP_CHARACTER_STREAM_SAP_XEP_THEO_DO_DAI {
    public static void main(String[] args) throws Exception {

        // 1. Kết nối server
        Socket socket = new Socket("localhost", 2208);

        BufferedReader br = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));

        // 2. Gửi mã sinh viên và mã câu hỏi
        String studentCode = "B15DCCN999";
        String qCode = "we3kcWxZ";
        bw.write(studentCode + ";" + qCode);
        bw.newLine();
        bw.flush();

        // 3. Nhận chuỗi từ server
        String data = br.readLine(); 
        // ví dụ: "hello world this is a test example"

        // 4. Tách các từ
        String[] words = data.split("\\s+");

        // 5. Sắp xếp theo độ dài (giữ thứ tự xuất hiện)
        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });

        // 6. Ghép kết quả theo định dạng yêu cầu
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            result.append(words[i]);
            if (i < words.length - 1) {
                result.append(", ");
            }
        }

        // 7. Gửi kết quả lên server
        bw.write(result.toString());
        bw.newLine();
        bw.flush();

        // 8. Đóng kết nối
        br.close();
        bw.close();
        socket.close();
    }
}
