/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 2. LUỸ THỪA
//Mã bài tập nxMRj8z
//Một chương trình server tại địa chỉ 172.188.19.218 hỗ trợ kết nối qua giao thức TCP tại cổng 1604
//(hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực
//hiện kết nối tới server trên sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi
//thông tin theo thứ tự:
//a/ Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
//Ví dụ: "B20DCCN999;ABCDEF" với ABCDEF là mã bài tập đã đề cập ở trên.
//b. Nhận dữ liệu từ server là một chuỗi gồm hai giá trị nguyên a, b được phân tách với nhau bằng
//"|" Ex: 2|5
//c. Thực hiện tìm giá trị a
//b và gửi lên server. Ex: 32
//d. Đóng kết nối và kết thúc

package LTM;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCP_BYTE_STREAM_LUY_THUA {

    public static void main(String[] args) throws Exception {

        String serverAddress = "172.188.19.218";
        int serverPort = 1604;

        // ⚠️ Thay bằng mã sinh viên của bạn
        String studentCode = "B20DCCN999";
        String qCode = "nxMRj8z";

        // Kết nối tới server
        Socket socket = new Socket(serverAddress, serverPort);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        /* a. Gửi mã sinh viên và mã câu hỏi */
        String sendData = studentCode + ";" + qCode;
        out.write(sendData.getBytes());
        out.flush();

        /* b. Nhận chuỗi a|b từ server */
        byte[] buffer = new byte[1024];
        int bytesRead = in.read(buffer);
        String received = new String(buffer, 0, bytesRead).trim();

        String[] parts = received.split("\\|");
        int a = Integer.parseInt(parts[0]);
        int b = Integer.parseInt(parts[1]);

        /* c. Tính a^b */
        long result = 1;
        for (int i = 0; i < b; i++) {
            result *= a;
        }

        /* d. Gửi kết quả lên server */
        out.write(String.valueOf(result).getBytes());
        out.flush();

        /* e. Đóng kết nối */
        in.close();
        out.close();
        socket.close();
    }
}
