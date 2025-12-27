/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//BÀI 1. TỔNG HAI SỐ NHỊ PHÂN
//[Mã câu hỏi (qCode): lIQVug9S].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng “;studentCode;qCode”. Ví dụ: “;B15DCCN000;XbYdNZ3”.
//b. Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;b1,b2”, trong đó:
//    requestId là chuỗi ngẫu nhiên duy nhất.
//    b1 là số nhị phân thứ nhất
//    b2 là số nhị phân thứ hai.
//Ví dụ: requestId;0100011111001101,1101000111110101
//c. Thực hiện tính tổng hai số nhị phân nhận được, chuyển về dạng thập phân và gửi lên server theo định dạng “requestId;sum”
//Kết quả: requestId;72130 
//d. Đóng socket và kết thúc chương trình.


package LTM;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_TONG_HAI_SO_NHI_PHAN {

    public static void main(String[] args) throws Exception {

        // 1. Tạo socket UDP
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("203.162.10.109");
        int serverPort = 2208;

        // 2. Gửi studentCode;qCode
        String studentCode = "B22DCCN760";
        String qCode = "amGzMw6d";
        String sendMsg = ";" + studentCode + ";" + qCode;

        byte[] sendData = sendMsg.getBytes();
        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        socket.send(sendPacket);

        // 3. Nhận dữ liệu từ server
        byte[] receiveData = new byte[4096];
        DatagramPacket receivePacket =
                new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        String received =
                new String(receivePacket.getData(), 0, receivePacket.getLength());

        // 4. Phân tích dữ liệu
        // Format: requestId;b1,b2
        String[] parts = received.split(";");
        String requestId = parts[0];

        String[] binaries = parts[1].split(",");
        String b1 = binaries[0];
        String b2 = binaries[1];

        // 5. Chuyển nhị phân -> thập phân
        long num1 = Long.parseLong(b1, 2);
        long num2 = Long.parseLong(b2, 2);

        long sum = num1 + num2;

        // 6. Gửi kết quả về server
        String response = requestId + ";" + sum;
        byte[] responseData = response.getBytes();

        DatagramPacket responsePacket =
                new DatagramPacket(responseData, responseData.length, serverAddress, serverPort);
        socket.send(responsePacket);

        // 7. Đóng socket
        socket.close();
    }
}
