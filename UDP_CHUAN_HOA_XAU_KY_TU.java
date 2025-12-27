/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 8. CHUẨN HOÁ XÂU KÝ TỰ [001]
//[Mã câu hỏi (qCode): NRVwBVvx]. Một chương trình server cho phép kết nối qua giao thức UDP
//tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch
//bản dưới đây:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng
//“;studentCode;qCode”. Ví dụ: “;B15DCCN001;5B35BCC1”
//b. Nhận thông điệp từ server theo định dạng “requestId;data”
//- requestId là một chuỗi ngẫu nhiên duy nhất
//- data là chuỗi dữ liệu cần xử lý
//c. Xử lý chuẩn hóa chuỗi đã nhận thành theo nguyên tắc
//i. Ký tự đầu tiên của từng từ trong chuỗi là in hoa
//ii. Các ký tự còn lại của chuỗi là in thường
//Luyện thi UDP
//_____________________________________________________________________________________________
//Trang 5
//Gửi thông điệp chứa chuỗi đã được chuẩn hóa lên server theo định dạng “requestId;data”
//d. Đóng socket và kết thúc chương trình

package LTM;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_CHUAN_HOA_XAU_KY_TU {

    public static void main(String[] args) throws Exception {

        InetAddress serverAddress = InetAddress.getByName("172.188.19.218");
        int port = 2208;

        DatagramSocket socket = new DatagramSocket();

        String studentCode = "B15DCCN001";
        String qCode = "NRVwBVvx";

        /* a. Gửi studentCode;qCode */
        String sendMsg = ";" + studentCode + ";" + qCode;
        byte[] sendData = sendMsg.getBytes();
        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, serverAddress, port);
        socket.send(sendPacket);

        /* b. Nhận requestId;data */
        byte[] buffer = new byte[4096];
        DatagramPacket receivePacket =
                new DatagramPacket(buffer, buffer.length);
        socket.receive(receivePacket);

        String received =
                new String(receivePacket.getData(), 0, receivePacket.getLength());

        String[] parts = received.split(";", 2);
        String requestId = parts[0];
        String data = parts[1];

        /* c. Chuẩn hóa chuỗi */
        String[] words = data.trim().split("\\s+");
        StringBuilder normalized = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                normalized.append(
                        word.substring(0, 1).toUpperCase()
                                + word.substring(1).toLowerCase()
                ).append(" ");
            }
        }

        String result = normalized.toString().trim();

        /* d. Gửi lại kết quả */
        String response = requestId + ";" + result;
        byte[] responseData = response.getBytes();
        DatagramPacket responsePacket =
                new DatagramPacket(responseData, responseData.length, serverAddress, port);
        socket.send(responsePacket);

        socket.close();
    }
}
