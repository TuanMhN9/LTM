/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 2. SẮP XẾP THEO TỪ ĐIỂN NGƯỢC
//[Mã câu hỏi (qCode): 9UfU4Vky].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: ";B15DCCN009;EF56GH78"
//b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;data", với:
//•	requestId là chuỗi ngẫu nhiên duy nhất.
//•	data là một chuỗi ký tự chứa nhiều từ, được phân cách bởi dấu cách.
//Ví dụ: "EF56GH78;The quick brown fox"
//c. Sắp xếp các từ trong chuỗi theo thứ tự từ điển ngược (z đến a) và gửi thông điệp lên server theo định dạng "requestId;word1,word2,...,wordN".
//Ví dụ: Với data = "The quick brown fox", kết quả là: "EF56GH78;quick,fox,brown,The"
//d. Đóng socket và kết thúc chương trình


package LTM;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UDP_SAP_XEP_THEO_TU_DIEN_NGUOC {

    public static void main(String[] args) throws Exception {

        // 1. Tạo socket UDP
        DatagramSocket socket = new DatagramSocket();

        InetAddress serverAddress = InetAddress.getByName("localhost");
        int serverPort = 2208;

        // 2. Gửi studentCode và qCode
        String studentCode = "B15DCCN009";
        String qCode = "9UfU4Vky";
        String sendMsg = studentCode + ";" + qCode;

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
        // Format: requestId;data
        String[] parts = received.split(";", 2);
        String requestId = parts[0];
        String data = parts[1];

        // 5. Tách và sắp xếp từ điển ngược
        String[] words = data.split("\\s+");
        List<String> wordList = Arrays.asList(words);
        Collections.sort(wordList, Collections.reverseOrder());

        // 6. Ghép kết quả
        String resultWords = String.join(",", wordList);
        String result = requestId + ";" + resultWords;

        // 7. Gửi kết quả lên server
        byte[] resultData = result.getBytes();
        DatagramPacket resultPacket =
                new DatagramPacket(resultData, resultData.length, serverAddress, serverPort);
        socket.send(resultPacket);

        // 8. Đóng socket
        socket.close();
    }
}
