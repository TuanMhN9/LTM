/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 1. TÌM SỐ CÒN THIẾU[002]
//[Mã câu hỏi (qCode): wAKCZwjj]. Một chương trình server cho phép giao tiếp qua giao thức UDP
//tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch
//bản:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng
//“;studentCode;qCode”. Ví dụ: “;B15DCCN001;73457A17”
//b. Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;n;A1,A2,...An” , với
//- requestId là chuỗi ngẫu nhiên duy nhất
//- n là một số ngẫu nhiên nhỏ hơn 100.
//- A1, A2 ... Am (m <= n) là các giá trị ngẫu nhiên nhỏ hơn hoặc bằng n và có thể trùng nhau.
//Ex: requestId;10;2,3,5,6,5
//c. Tìm kiếm các giá trị còn thiếu và gửi lên server theo định dạng “requestId;B1,B2,...,Bm”
//Ex: requestId;1,4,7,8,9,10
//d. Đóng socket và kết thúc chương trình.

package LTM;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

public class UDP_TIM_SO_CON_THIEU {

    public static void main(String[] args) throws Exception {

        String serverHost = "172.188.19.218";
        int serverPort = 2207;

        String studentCode = "B15DCCN001";
        String qCode = "wAKCZwjj";

        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName(serverHost);

        /* a. Gửi studentCode;qCode */
        String sendMsg = ";" + studentCode + ";" + qCode;
        byte[] sendData = sendMsg.getBytes();

        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        socket.send(sendPacket);

        /* b. Nhận dữ liệu từ server */
        byte[] receiveData = new byte[4096];
        DatagramPacket receivePacket =
                new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        String received =
                new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();

        // requestId;n;A1,A2,...
        String[] parts = received.split(";");
        String requestId = parts[0];
        int n = Integer.parseInt(parts[1]);

        Set<Integer> existed = new HashSet<>();
        if (parts.length > 2 && !parts[2].isEmpty()) {
            String[] nums = parts[2].split(",");
            for (String num : nums) {
                existed.add(Integer.parseInt(num));
            }
        }

        /* c. Tìm các số còn thiếu */
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (!existed.contains(i)) {
                result.append(i).append(",");
            }
        }

        // Xóa dấu phẩy cuối
        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }

        String response = requestId + ";" + result.toString();
        byte[] responseData = response.getBytes();

        DatagramPacket responsePacket =
                new DatagramPacket(responseData, responseData.length,
                        serverAddress, serverPort);
        socket.send(responsePacket);

        /* d. Đóng socket */
        socket.close();
    }
}
