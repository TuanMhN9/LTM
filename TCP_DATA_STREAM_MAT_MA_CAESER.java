/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): 16jAnMv8].  Mật mã caesar, còn gọi là mật mã dịch chuyển, để giải mã thì mỗi ký tự nhận được sẽ được thay thế bằng một ký tự cách nó một đoạn s. Ví dụ: với s = 3 thì ký tự “A” sẽ được thay thế bằng ký tự “D”.
//Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2207 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng chương trình client tương tác với server trên, sử dụng các luồng byte (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
//a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;D68C93F7"
//b.	Nhận lần lượt chuỗi đã bị mã hóa caesar và giá trị dịch chuyển s nguyên
//c.	Thực hiện giải mã ra thông điệp ban đầu và gửi lên Server
//d.	Đóng kết nối và kết thúc chương trình.

package LTM;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCP_DATA_STREAM_MAT_MA_CAESER {

    public static void main(String[] args) {
        String serverIp = "203.162.10.109"; // 👉 đổi thành Exam_IP khi thi
        int port = 2207;

        String studentCode = "B22DCCN760"; // 🔴 đổi mã SV
        String qCode = "16jAnMv8";

        try (Socket socket = new Socket(serverIp, port)) {

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            /* a. Gửi studentCode;qCode */
            dos.writeUTF(studentCode + ";" + qCode);
            dos.flush();

            /* b. Nhận chuỗi mã hóa và số dịch s */
            String encrypted = dis.readUTF();
            int s = dis.readInt();

            /* c. Giải mã */
            String decrypted = caesarDecode(encrypted, s);

            /* gửi kết quả */
            dos.writeUTF(decrypted);
            dos.flush();

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String caesarDecode(String text, int s) {
        StringBuilder sb = new StringBuilder();
        s = s % 26;

        for (char c : text.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                sb.append((char) ((c - 'A' - s + 26) % 26 + 'A'));
            } else if (c >= 'a' && c <= 'z') {
                sb.append((char) ((c - 'a' - s + 26) % 26 + 'a'));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
