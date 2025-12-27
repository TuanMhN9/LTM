/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): 0LTGyX4p].  Một chương trình server cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu xây dựng chương trình client thực hiện giao tiếp với server sử dụng luồng data (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
//a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
//Ví dụ: "B10DCCN001;A1B2C3D4"
//b. Nhận một số nguyên hệ thập phân từ server. Ví dụ: 255
//c. Chuyển đổi số nguyên nhận được sang hai hệ cơ số 8 và 16. Gửi lần lượt chuỗi kết quả lên server. Ví dụ: Với số 255 hệ thập phân, kết quả gửi lên sẽ là một chuỗi dạng “377;FF”
//d. Đóng kết nối và kết thúc chương trình.


package LTM;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCP_DATA_STREAM_CHUYEN_SANG_CO_SO_8_VA_16 {
    public static void main(String[] args) throws Exception {

        // 1. Kết nối tới server
        Socket socket = new Socket("localhost", 2207);

        DataOutputStream dos =
                new DataOutputStream(socket.getOutputStream());
        DataInputStream dis =
                new DataInputStream(socket.getInputStream());

        // 2. Gửi mã sinh viên và mã câu hỏi
        String studentCode = "B10DCCN001";
        String qCode = "0LTGyX4p";
        dos.writeUTF(studentCode + ";" + qCode);
        dos.flush();

        // 3. Nhận số nguyên hệ 10 từ server
        int number = dis.readInt();

        // 4. Chuyển sang hệ 8 và hệ 16
        String octal = Integer.toOctalString(number);
        String hex = Integer.toHexString(number).toUpperCase();

        // 5. Gửi kết quả lên server
        dos.writeUTF(octal + ";" + hex);
        dos.flush();

        // 6. Đóng kết nối
        dis.close();
        dos.close();
        socket.close();
    }
}
