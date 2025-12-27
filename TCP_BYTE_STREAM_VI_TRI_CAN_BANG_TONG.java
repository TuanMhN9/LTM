/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 5. VỊ TRÍ CÂN BẰNG TỔNG
//[Mã câu hỏi (qCode): zmNHK0Y7].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
//a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
//Ví dụ: "B16DCCN999;E56FAB67"
//b. Nhận dữ liệu từ server là một chuỗi các số nguyên được phân tách bởi ký tự ",".
//Ví dụ: " 3,7,2,5,8,1"
//c. Tìm vị trí mà độ lệch của tổng bên trái và tổng bên phải là nhỏ nhất -> Gửi lên server vị trí đó, tổng trái, tổng phải và độ lệch. Ví dụ: với dãy " 3,7,2,5,8,1", vị trí 3 có độ lệch nhỏ nhất = 3 → Kết quả gửi server: "3,12,9,3"
//d. Đóng kết nối và kết thúc chương trình.


package LTM;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCP_BYTE_STREAM_VI_TRI_CAN_BANG_TONG {
    public static void main(String[] args) throws Exception {

        // 1. Kết nối server
        Socket socket = new Socket("localhost", 2206);

        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        // 2. Gửi mã sinh viên và mã câu hỏi
        String studentCode = "B16DCCN999";
        String qCode = "zmNHK0Y7";
        String send = studentCode + ";" + qCode;

        os.write(send.getBytes());
        os.flush();

        // 3. Nhận chuỗi số từ server
        byte[] buffer = new byte[1024];
        int len = is.read(buffer);
        String data = new String(buffer, 0, len).trim(); 
        // ví dụ: "3,7,2,5,8,1"

        // 4. Chuyển chuỗi thành mảng số
        String[] parts = data.split(",");
        int n = parts.length;
        int[] arr = new int[n];

        int totalSum = 0;
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(parts[i].trim());
            totalSum += arr[i];
        }

        // 5. Tìm vị trí có độ lệch nhỏ nhất
        int leftSum = 0;
        int bestIndex = 0;
        int bestLeft = 0;
        int bestRight = 0;
        int minDiff = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            leftSum += arr[i];
            int rightSum = totalSum - leftSum;
            int diff = Math.abs(leftSum - rightSum);

            if (diff < minDiff) {
                minDiff = diff;
                bestIndex = i + 1; // vị trí tính từ 1
                bestLeft = leftSum;
                bestRight = rightSum;
            }
        }

        // 6. Gửi kết quả lên server
        String result = bestIndex + "," + bestLeft + "," + bestRight + "," + minDiff;
        os.write(result.getBytes());
        os.flush();

        // 7. Đóng kết nối
        is.close();
        os.close();
        socket.close();
    }
}
