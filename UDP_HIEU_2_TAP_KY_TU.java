/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// BÀI 9. HIỆU CỦA HAI TẬP KÝ TỰ[020]
// [Mã câu hỏi (qCode): JQCO3izC]. [Loại bỏ ký tự đặc biệt và ký tự trùng giữ nguyên thứ tự xuất
// hiện]
// Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208 . Yêu cầu là xây dựng
// một chương trình client trao đổi thông tin với server theo kịch bản dưới đây:
// a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng
// ";studentCode;qCode”. Ví dụ: ";B15DCCN001;B34D51E0"
// b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;str1;str2".
// • requestId là chuỗi ngẫu nhiên duy nhất
// • str1,str2 lần lượt là chuỗi thứ nhất và chuỗi thứ hai
// c. Loại bỏ các ký tự trong chuỗi thứ nhất mà xuất hiện trong chuỗi thứ hai, giữ nguyên thứ tự
// xuất hiện. Gửi thông điệp là một chuỗi lên server theo định dạng "requestId;strOutput", trong đó
// chuỗi strOutput là chuỗi đã được xử lý ở trên.
// d. Đóng socket và kết thúc chương trình.


package LTM;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class UDP_HIEU_2_TAP_KY_TU {

    public static void main(String[] args) throws Exception {

        InetAddress serverAddress = InetAddress.getByName("172.188.19.218");
        int port = 2208;

        DatagramSocket socket = new DatagramSocket();

        String studentCode = "B15DCCN001";
        String qCode = "JQCO3izC";

        /* a. Gửi studentCode;qCode */
        String sendMsg = ";" + studentCode + ";" + qCode;
        byte[] sendData = sendMsg.getBytes();
        socket.send(new DatagramPacket(sendData, sendData.length, serverAddress, port));

        /* b. Nhận requestId;str1;str2 */
        byte[] buffer = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(receivePacket);

        String received = new String(receivePacket.getData(), 0, receivePacket.getLength());
        String[] parts = received.split(";", 3);

        String requestId = parts[0];
        String str1 = parts[1];
        String str2 = parts[2];

        /* c. Xử lý */

        // tập ký tự của str2
        Set<Character> set2 = new HashSet<>();
        for (char c : str2.toCharArray()) {
            if (Character.isLetter(c)) {
                set2.add(c);
            }
        }

        // lọc str1: bỏ đặc biệt + trùng + trừ tập str2
        Set<Character> resultSet = new LinkedHashSet<>();
        for (char c : str1.toCharArray()) {
            if (Character.isLetter(c) && !set2.contains(c)) {
                resultSet.add(c);
            }
        }

        StringBuilder result = new StringBuilder();
        for (char c : resultSet) {
            result.append(c);
        }

        /* d. Gửi lại kết quả */
        String response = requestId + ";" + result.toString();
        byte[] responseData = response.getBytes();

        socket.send(new DatagramPacket(responseData, responseData.length, serverAddress, port));

        socket.close();
    }
}
