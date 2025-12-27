/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 1. SẮP XẾP THEO VỊ TRÍ BAN ĐẦU
//[Mã câu hỏi (qCode): aKZwZxWk].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN009;F3E8B2D4".
//b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;string", với:
//--- requestId là chuỗi ngẫu nhiên duy nhất.
//---string là một chuỗi chứa các chuỗi con bị thay đổi vị trí. Ví dụ: "veM3xgA1g:4,IPFfgEanY:5,aWXlSzDwe:2,PHupvPc:3,PR3gH8ahN:6,UEEKHLIt:7,M6dpWTE:1"
//c. Xử lý chuỗi xáo trộn và gửi về chuỗi sau khi sắp xếp: "requestId;string". Ví dụ chuỗi đã được xử lý: "M6dpWTE,aWXlSzDwe,PHupvPc,veM3xgA1g,IPFfgEanY,PR3gH8ahN,UEEKHLIt"
//d. Đóng socket và kết thúc chương trình.


package LTM;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import java.util.TreeMap;

public class UDP_SAP_XEP_THEO_VI_TRI_BAN_DAU {

    public static void main(String[] args) throws Exception {

        // 1. Tạo UDP socket
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("localhost");
        int serverPort = 2207;

        // 2. Gửi studentCode;qCode
        String studentCode = "B15DCCN009";
        String qCode = "aKZwZxWk";
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

        // 4. Phân tích dữ liệu nhận
        // Format: requestId;string
        String[] parts = received.split(";");
        String requestId = parts[0];
        String data = parts[1];

        // 5. Xử lý sắp xếp theo vị trí ban đầu
        String[] items = data.split(",");
        TreeMap<Integer, String> map = new TreeMap<>();

        for (String item : items) {
            String[] pair = item.split(":");
            String value = pair[0];
            int position = Integer.parseInt(pair[1]);
            map.put(position, value);
        }

        // 6. Ghép chuỗi kết quả
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            result.append(entry.getValue()).append(",");
        }
        result.deleteCharAt(result.length() - 1); // xóa dấu phẩy cuối

        // 7. Gửi kết quả về server
        String response = requestId + ";" + result.toString();
        byte[] responseData = response.getBytes();

        DatagramPacket responsePacket =
                new DatagramPacket(responseData, responseData.length, serverAddress, serverPort);
        socket.send(responsePacket);

        // 8. Đóng socket
        socket.close();
    }
}
