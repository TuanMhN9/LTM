/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 3. TỔNG GẤP ĐÔI TRUNG BÌNH
//[Mã câu hỏi (qCode): TL9Pol9D].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
//a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
//Ví dụ: "B16DCCN999;D45EFA12"
//b. Nhận dữ liệu từ server là một chuỗi các số nguyên được phân tách bởi ký tự ",".
//Ví dụ: "10,5,15,20,25,30,35"
//c. Xác định hai số trong dãy có tổng gần nhất với gấp đôi giá trị trung bình của toàn bộ dãy. Gửi thông điệp lên server theo định dạng "num1,num2".
//Ví dụ: Với dãy "10,5,15,20,25,30,35", gấp đôi giá trị trung bình là 40, hai số có tổng gần nhất là 15 và 25. Gửi lên server chuỗi "15,25".
//d. Đóng kết nối và kết thúc chương trình.


package LTM;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCP_BYTE_STREAM_TONG_GAP_DOI_TRUNG_BINH {
    public static void main(String[] args) throws Exception {

        // 1. Kết nối server
        Socket socket = new Socket("localhost", 2206);

        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        // 2. Gửi mã sinh viên và mã câu hỏi
        String studentCode = "B16DCCN999";
        String qCode = "TL9Pol9D";
        String send = studentCode + ";" + qCode;

        os.write(send.getBytes());
        os.flush();

        // 3. Nhận chuỗi dữ liệu từ server
        byte[] buffer = new byte[1024];
        int len = is.read(buffer);
        String data = new String(buffer, 0, len); // ví dụ: "10,5,15,20,25,30,35"

        // 4. Chuyển chuỗi thành mảng số
        String[] parts = data.split(",");
        int n = parts.length;
        int[] arr = new int[n];

        int sum = 0;
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(parts[i]);
            sum += arr[i];
        }

        // 5. Tính gấp đôi trung bình
        double avg = (double) sum / n;
        double target = 2 * avg;

        // 6. Tìm cặp số có tổng gần nhất
        int num1 = arr[0];
        int num2 = arr[1];
        double minDiff = Math.abs((num1 + num2) - target);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double diff = Math.abs((arr[i] + arr[j]) - target);
                if (diff < minDiff) {
                    minDiff = diff;
                    num1 = arr[i];
                    num2 = arr[j];
                }
            }
        }

        // 7. Gửi kết quả lên server
        String result = num1 + "," + num2;
        os.write(result.getBytes());
        os.flush();

        // 8. Đóng kết nối
        is.close();
        os.close();
        socket.close();
    }
}
