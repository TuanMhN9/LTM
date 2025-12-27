/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): IjxDQK2c].  [Loại bỏ ký tự đặc biệt và ký tự trùng giữ nguyên thứ tự xuất hiện]
//Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208 . Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản dưới đây:
//a.	Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode”. Ví dụ: ";B15DCCN001;B34D51E0"
//b.	Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;str1;str2".
//•	requestId là chuỗi ngẫu nhiên duy nhất
//•	str1,str2 lần lượt là chuỗi thứ nhất và chuỗi thứ hai
//c.	Loại bỏ các ký tự trong chuỗi thứ nhất mà xuất hiện trong chuỗi thứ hai, giữ nguyên thứ tự xuất hiện. Gửi thông điệp là một chuỗi lên server theo định dạng "requestId;strOutput", trong đó chuỗi strOutput là chuỗi đã được xử lý ở trên.
//d.	Đóng socket và kết thúc chương trình.

package LTM;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class UDP_LOAI_BO_KY_TU_TRONG_CHUOI_1_MA_XUAT_HIEN_TRONG_CHUOI_THU_2 {

    public static void main(String[] args) {
        String studentCode = "B22DCCN088";   // đổi nếu cần
        String qCode = "IjxDQK2c";

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("203.162.10.109");
            int serverPort = 2208;

            /* ===== a. Gửi mã sinh viên + mã câu hỏi ===== */
            String sendMsg = ";" + studentCode + ";" + qCode;
            byte[] sendData = sendMsg.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);

            /* ===== b. Nhận dữ liệu từ server ===== */
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String received =
                    new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);

            // requestId;str1;str2
            String[] parts = received.split(";", 3);
            String requestId = parts[0];
            String str1 = parts[1];
            String str2 = parts[2];

            /* ===== c. Xử lý loại bỏ ký tự ===== */
            String result = removeChars(str1, str2);

            /* ===== d. Gửi kết quả lại server ===== */
            String response = requestId + ";" + result;
            byte[] responseData = response.getBytes(StandardCharsets.UTF_8);
            DatagramPacket responsePacket =
                    new DatagramPacket(responseData, responseData.length, serverAddress, serverPort);
            socket.send(responsePacket);

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ===== Hàm loại bỏ ký tự xuất hiện trong str2 ===== */
    private static String removeChars(String str1, String str2) {
        Set<Character> removeSet = new HashSet<>();
        for (char c : str2.toCharArray()) {
            removeSet.add(c);
        }

        StringBuilder sb = new StringBuilder();
        for (char c : str1.toCharArray()) {
            if (!removeSet.contains(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
