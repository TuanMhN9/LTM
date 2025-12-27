/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): XysITuzy].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN011;A1F3D5B7".
//
//b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;num", với:
//   - requestId là chuỗi ngẫu nhiên duy nhất.
//   - num là một số nguyên lớn.
//
//c. Tính tổng các chữ số trong num và gửi lại tổng này về server theo định dạng "requestId;sumDigits".
//
//d. Đóng socket và kết thúc chương trình.

package LTM;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_TONG_CAC_CHU_SO {

    public static void main(String[] args) {
        String studentCode = "B22DCCN016"; // đổi nếu cần
        String qCode = "XysITuzy";

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("203.162.10.109");
            int serverPort = 2207;

            /* ===== a. Gửi studentCode;qCode ===== */
            String sendMsg = ";" + studentCode + ";" + qCode;
            byte[] sendData = sendMsg.getBytes();
            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);

            /* ===== b. Nhận requestId;num ===== */
            byte[] buffer = new byte[2048];
            DatagramPacket receivePacket =
                    new DatagramPacket(buffer, buffer.length);
            socket.receive(receivePacket);

            String received = new String(
                    receivePacket.getData(), 0, receivePacket.getLength()
            );

            String[] parts = received.split(";");
            String requestId = parts[0];
            String num = parts[1];

            /* ===== c. Tính tổng chữ số ===== */
            int sumDigits = 0;
            for (char c : num.toCharArray()) {
                if (Character.isDigit(c)) {
                    sumDigits += c - '0';
                }
            }

            /* ===== d. Gửi lại kết quả ===== */
            String result = requestId + ";" + sumDigits;
            byte[] resultData = result.getBytes();
            DatagramPacket resultPacket =
                    new DatagramPacket(resultData, resultData.length, serverAddress, serverPort);
            socket.send(resultPacket);

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
