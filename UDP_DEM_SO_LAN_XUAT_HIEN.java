/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 2. ĐẾM SỐ LẦN XUẤT HIỆN[002]
//[Mã câu hỏi (qCode): vSgxl3HQ]. Một chương trình server cho phép giao tiếp qua giao thức UDP
//tại cổng 2208. Yêu cầu xây dựng chương trình client trao đổi thông tin với server theo kịch bản:
//a. Gửi một thông điệp chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví
//dụ: ";B15DCCN001;9F8C2D3A".
//b. Nhận một thông điệp từ server theo định dạng "requestId;data", với:
// requestId là chuỗi ngẫu nhiên duy nhất.
// data là một chuỗi ký tự liên tiếp cần xử lý. Ví dụ: "requestId;aaabbbccdaa"
//Luyện thi UDP
//_____________________________________________________________________________________________
//Trang 2
//c. Xử lý chuỗi bằng cách đếm số lượng ký tự và gom chúng lại theo định dạng "số_lần_ký_tự".
//Gửi kết quả về server theo định dạng:
// "requestId;processedData"
//Ví dụ: Với chuỗi "aaabbbccdaa", kết quả sẽ là: "requestId;5a3b2c1d"
//d. Đóng socket và kết thúc chương trình.

package LTM;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedHashMap;
import java.util.Map;

public class UDP_DEM_SO_LAN_XUAT_HIEN {

    public static void main(String[] args) throws Exception {

        String serverHost = "172.188.19.218";
        int serverPort = 2208;

        String studentCode = "B15DCCN001";
        String qCode = "vSgxl3HQ";

        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName(serverHost);

        /* a. Gửi mã sinh viên và mã câu hỏi */
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

        // requestId;data
        String[] parts = received.split(";", 2);
        String requestId = parts[0];
        String data = parts[1];

        /* c. Đếm tần suất ký tự (giữ thứ tự xuất hiện) */
        Map<Character, Integer> countMap = new LinkedHashMap<>();

        for (char c : data.toCharArray()) {
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }

        StringBuilder processed = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            processed.append(entry.getValue())
                     .append(entry.getKey());
        }

        String response = requestId + ";" + processed.toString();
        byte[] responseData = response.getBytes();

        DatagramPacket responsePacket =
                new DatagramPacket(responseData, responseData.length,
                        serverAddress, serverPort);
        socket.send(responsePacket);

        /* d. Đóng socket */
        socket.close();
    }
}
