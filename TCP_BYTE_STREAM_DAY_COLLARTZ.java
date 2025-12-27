/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): BDF0CKv5].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2206 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s).
//Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng các luồng byte (InputStream/OutputStream) để trao đổi thông tin theo thứ tự: 
//a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;2B3A6510"
//b. Nhận dữ liệu từ server là một số nguyên n nhỏ hơn 400. Ví dụ: 7
//c. Thực hiện các bước sau đây để sinh ra chuỗi từ số nguyên n ban đầu và gửi lên server.
//Gọi n là số hiện tại, n1 là số tiếp theo. Ta có:
//•	Nếu n là số chẵn  n1 = n / 2;
//•	Nếu n là số lẻ  n1 = 3n + 1
//Lặp lại quá trình trên cho đến khi n = 1, tại đó dừng thuật toán.
//Kết quả là một dãy số liên tiếp, bắt đầu từ n ban đầu, kết thúc tại 1 và độ dài của chuỗi theo format "chuỗi kết quả; độ dài"  Ví dụ: kết quả với n = 7 thì dãy: 7 22 11 34 17 52 26 13 40 20 10 5 16 8 4 2 1; 17
//d.	Đóng kết nối và kết thúc chương trình.


package LTM;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCP_BYTE_STREAM_DAY_COLLARTZ {
    public static void main(String[] args) throws Exception {

        // 1. Kết nối tới server
        Socket socket = new Socket("localhost", 2206);

        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        // 2. Gửi mã sinh viên và mã câu hỏi
        String studentCode = "B16DCCN999";
        String qCode = "BDF0CKv5";
        String send = studentCode + ";" + qCode;

        os.write(send.getBytes());
        os.flush();

        // 3. Nhận số nguyên n từ server
        byte[] buffer = new byte[1024];
        int len = is.read(buffer);
        int n = Integer.parseInt(new String(buffer, 0, len).trim());

        // 4. Sinh chuỗi Collatz
        StringBuilder result = new StringBuilder();
        int count = 0;

        while (true) {
            result.append(n).append(" ");
            count++;

            if (n == 1) break;

            if (n % 2 == 0) {
                n = n / 2;
            } else {
                n = 3 * n + 1;
            }
        }

        // Bỏ khoảng trắng cuối
        String sequence = result.toString().trim();

        // 5. Gửi kết quả lên server
        String response = sequence + ";" + count;
        os.write(response.getBytes());
        os.flush();

        // 6. Đóng kết nối
        is.close();
        os.close();
        socket.close();
    }
}
