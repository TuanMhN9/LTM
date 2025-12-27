/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): UsLuWWET].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2207 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu sinh viên xây dựng chương trình client để tương tác với server, sử dụng các luồng data (DataInputStream và DataOutputStream) để trao đổi thông tin theo thứ tự sau:
//a. Gửi mã sinh viên và mã câu hỏi: Chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;D68C93F7".
//b. Nhận một số nguyên hệ thập phân từ server. Ví dụ:: 15226.
//c. Chuyển đổi số nguyên nhận được sang hệ nhị phân và thập lục phân, ghép thành chuỗi và gửi lên server. Ví dụ: 15226 sẽ thành "11101101111010;3B7A"
//d. Đóng kết nối: Kết thúc chương trình sau khi gửi kết quả chuyển đổi.


package LTM;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCP_BYTE_STREAM_CHUYEN_SANG_2_VA_16 {

    public static void main(String[] args) throws Exception {

        // 1. Kết nối server
        Socket socket = new Socket("localhost", 2207);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        // 2. Gửi mã sinh viên và mã câu hỏi
        String studentCode = "B15DCCN999";
        String qCode = "UsLuWWET";
        dos.writeUTF(studentCode + ";" + qCode);
        dos.flush();

        // 3. Nhận số nguyên từ server
        int number = dis.readInt();

        // 4. Chuyển đổi sang nhị phân và hex
        String binary = Integer.toBinaryString(number);
        String hex = Integer.toHexString(number).toUpperCase();

        // 5. Gửi kết quả lên server
        String result = binary + ";" + hex;
        dos.writeUTF(result);
        dos.flush();

        // 6. Đóng kết nối
        dis.close();
        dos.close();
        socket.close();
    }
}
