/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 4. TỔNG CÁC SỐ NGUYÊN TỐ
//[Mã câu hỏi (qCode): ZRFRsEcM].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
//a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
//Ví dụ: "B16DCCN999;C89DAB45"
//b. Nhận dữ liệu từ server là một chuỗi các số nguyên được phân tách bởi ký tự ",".
//Ví dụ: "8,4,2,10,5,6,1,3"
//c. Tính tổng của tất cả các số nguyên tố trong chuỗi và gửi kết quả lên server.
//Ví dụ: Với dãy "8,4,2,10,5,6,1,3", các số nguyên tố là 2, 5, 3, tổng là 10. Gửi lên server chuỗi "10".
//d. Đóng kết nối và kết thúc chương trình.


package LTM;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCP_BYTE_STREAM_TONG_CAC_SO_NGUYEN_TO {
    public static void main(String[] args) throws Exception {

        // 1. Kết nối server
        Socket socket = new Socket("localhost", 2206);

        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        // 2. Gửi mã sinh viên và mã câu hỏi
        String studentCode = "B16DCCN999";
        String qCode = "ZRFRsEcM";
        String send = studentCode + ";" + qCode;

        os.write(send.getBytes());
        os.flush();

        // 3. Nhận chuỗi số từ server
        byte[] buffer = new byte[1024];
        int len = is.read(buffer);
        String data = new String(buffer, 0, len); // ví dụ: "8,4,2,10,5,6,1,3"

        // 4. Tách chuỗi thành mảng số
        String[] parts = data.split(",");
        int sumPrime = 0;

        for (String p : parts) {
            int num = Integer.parseInt(p);
            if (isPrime(num)) {
                sumPrime += num;
            }
        }

        // 5. Gửi kết quả lên server
        os.write(String.valueOf(sumPrime).getBytes());
        os.flush();

        // 6. Đóng kết nối
        is.close();
        os.close();
        socket.close();
    }

    // Hàm kiểm tra số nguyên tố
    public static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }
}
