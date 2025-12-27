/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 3. LỌC KÝ TỰ [010]
//[Mã câu hỏi (qCode): GfeNSBMT]. [Loại bỏ ký tự đặc biệt, số, trùng và giữ nguyên thứ tự xuất
//hiện]
//Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208 . Yêu cầu là xây dựng
//một chương trình client trao đổi thông tin với server theo kịch bản dưới đây:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng
//";studentCode;qCode". Ví dụ: ";B15DCCN001;06D6800D"
//b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;strInput"
//• requestId là chuỗi ngẫu nhiên duy nhất
//• strInput là chuỗi thông điệp cần xử lý
//c. Thực hiện loại bỏ ký tự đặc biệt, số, ký tự trùng và giữ nguyên thứ tự xuất hiện của chúng.
//Gửi thông điệp lên server theo định dạng "requestId;strOutput", trong đó strOutput là chuỗi đã được
//xử lý ở trên
//d. Đóng socket và kết thúc chương trình.

package LTM;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedHashSet;
import java.util.Set;

public class UDP_LOC_KY_TU {

    public static void main(String[] args) throws Exception {

        String serverHost = "172.188.19.218";
        int serverPort = 2208;

        String studentCode = "B15DCCN001";
        String qCode = "GfeNSBMT";

        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName(serverHost);

        /* a. Gửi studentCode và qCode */
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

        // requestId;strInput
        String[] parts = received.split(";", 2);
        String requestId = parts[0];
        String strInput = parts[1];

        /* c. Lọc ký tự */
        Set<Character> resultSet = new LinkedHashSet<>();

        for (char c : strInput.toCharArray()) {
            if (Character.isLetter(c)) {   // chỉ giữ chữ cái
                resultSet.add(c);         // tự loại trùng, giữ thứ tự
            }
        }

        StringBuilder strOutput = new StringBuilder();
        for (char c : resultSet) {
            strOutput.append(c);
        }

        String response = requestId + ";" + strOutput.toString();
        byte[] responseData = response.getBytes();

        DatagramPacket responsePacket =
                new DatagramPacket(responseData, responseData.length,
                        serverAddress, serverPort);
        socket.send(responsePacket);

        /* d. Đóng socket */
        socket.close();
    }
}
