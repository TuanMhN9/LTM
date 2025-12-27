/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): t1C4x4Eu].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN009;F3E8B2D4".
//
//b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;string", với:
//--- requestId là chuỗi ngẫu nhiên duy nhất.
//--- string là một chuỗi chứa các chuỗi con bị thay đổi vị trí. Ví dụ: "veM3xgA1g:4,IPFfgEanY:5,aWXlSzDwe:2,PHupvPc:3,PR3gH8ahN:6,UEEKHLIt:7,M6dpWTE:1"
//
//c. Xử lý chuỗi xáo trộn và gửi về chuỗi sau khi sắp xếp: "requestId;string". Ví dụ chuỗi đã được xử lý: "M6dpWTE,aWXlSzDwe,PHupvPc,veM3xgA1g,IPFfgEanY,PR3gH8ahN,UEEKHLIt"
//
//d. Đóng socket và kết thúc chương trình.

package LTM;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

public class UDP_XU_LY_CHUOI_XAO_TRON {

    public static void main(String[] args) {
        String studentCode = "B22DCCN088";   // đổi nếu cần
        String qCode = "t1C4x4Eu";

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("203.162.10.109");
            int serverPort = 2207;

            /* ===== a. Gửi studentCode;qCode ===== */
            String sendMsg = ";" + studentCode + ";" + qCode;
            byte[] sendData = sendMsg.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
            socket.send(sendPacket);

            /* ===== b. Nhận dữ liệu từ server ===== */
            byte[] receiveData = new byte[2048];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String received = new String(
                    receivePacket.getData(),
                    0,
                    receivePacket.getLength(),
                    StandardCharsets.UTF_8
            );

            // requestId;string
            String[] parts = received.split(";", 2);
            String requestId = parts[0];
            String data = parts[1];

            /* ===== c. Xử lý sắp xếp ===== */
            String sortedResult = sortString(data);

            /* ===== d. Gửi kết quả về server ===== */
            String response = requestId + ";" + sortedResult;
            byte[] responseData = response.getBytes(StandardCharsets.UTF_8);
            DatagramPacket responsePacket =
                    new DatagramPacket(responseData, responseData.length, serverAddress, serverPort);
            socket.send(responsePacket);

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ===== Hàm xử lý sắp xếp chuỗi ===== */
    private static String sortString(String input) {
        // TreeMap tự động sắp xếp theo key (chỉ số)
        Map<Integer, String> map = new TreeMap<>();

        String[] items = input.split(",");
        for (String item : items) {
            String[] pair = item.split(":");
            String text = pair[0];
            int index = Integer.parseInt(pair[1]);
            map.put(index, text);
        }

        StringBuilder sb = new StringBuilder();
        for (String value : map.values()) {
            sb.append(value).append(",");
        }

        // Xóa dấu , cuối
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
