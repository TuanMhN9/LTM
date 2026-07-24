/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 5. KÝ TỰ XUẤT HIỆN NHIỀU LẦN NHẤT[011]
//[Mã câu hỏi (qCode): CvlqJmaa]. Một chương trình server cho phép kết nối qua giao thức UDP
//tại cổng 2208. Yêu cầu là xây dựng một chương trình client tương tác với server kịch bản dưới đây:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng
//“;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”
//b. Nhận thông điệp từ server theo định dạng “requestId; data”
//- requestId là một chuỗi ngẫu nhiên duy nhất
//- data là chuỗi dữ liệu đầu vào cần xử lý
//Ex: “requestId;Qnc8d5x78aldSGWWmaAAjyg3”
//c. Tìm kiếm ký tự xuất hiện nhiều nhất trong chuỗi và gửi lên server theo định dạng
//“requestId;ký tự xuất hiện nhiều nhất: các vị trí xuất hiện ký tự đó”
//ví dụ: “requestId;8:4,9,”
//d. Đóng socket và kết thúc chương trình

ppackage javaapplication2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UDP_KY_TU_XUAT_HIEN_NHIEU_NHAT {

    public static void main(String[] args) throws Exception {

        String serverHost = "36.50.135.242";
        int serverPort = 2208;

        String studentCode = "B22DCCN760";
        String qCode = "SDfTtrEI";

        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName(serverHost);

        // a. Gửi studentCode;qCode
        String sendMsg = ";" + studentCode + ";" + qCode;
        byte[] sendData = sendMsg.getBytes();

        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        socket.send(sendPacket);

        // b. Nhận dữ liệu
        byte[] receiveData = new byte[4096];
        DatagramPacket receivePacket =
                new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        String received = new String(
                receivePacket.getData(),
                0,
                receivePacket.getLength()
        ).trim();

        String[] parts = received.split(";", 2);
        String requestId = parts[0];
        String data = parts[1];

        // c. Đếm số lần xuất hiện và lưu vị trí (bắt đầu từ 1)
        Map<Character, List<Integer>> map = new LinkedHashMap<>();

        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            map.putIfAbsent(c, new ArrayList<>());
            map.get(c).add(i + 1);
        }

        char maxChar = 0;
        int maxCount = 0;

        for (Map.Entry<Character, List<Integer>> entry : map.entrySet()) {
            if (entry.getValue().size() > maxCount) {
                maxCount = entry.getValue().size();
                maxChar = entry.getKey();
            }
        }

        StringBuilder result = new StringBuilder();
        result.append(requestId).append(";").append(maxChar).append(":");

        for (int pos : map.get(maxChar)) {
            result.append(pos).append(",");
        }

        // d. Gửi kết quả
        byte[] responseData = result.toString().getBytes();

        DatagramPacket responsePacket =
                new DatagramPacket(
                        responseData,
                        responseData.length,
                        serverAddress,
                        serverPort
                );

        socket.send(responsePacket);

        socket.close();
    }
}