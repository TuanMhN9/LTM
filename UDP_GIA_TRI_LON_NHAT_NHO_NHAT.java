// BÀI 1. GIÁ TRỊ NHỎ NHẤT – GIÁ TRỊ LỚN NHẤT [DataType]
// [Mã câu hỏi (qCode): uWKK8u3W]. Một chương trình server cho phép giao tiếp qua giao thức
// UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo
// kịch bản:
// a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng
// “;studentCode;qCode”. Ví dụ: “;B15DCCN001;DC73CA2E”
// b. Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;a1,a2,...,a50”
// - requestId là chuỗi ngẫu nhiên duy nhất
// - a1 -> a50 là 50 số nguyên ngẫu nhiên
// c. Thực hiện tìm giá trị lớn nhất và giá trị nhỏ nhất thông điệp trong a1 -> a50 và gửi thông
// điệp lên lên server theo định dạng “requestId;max,min”
// d. Đóng socket và kết thúc chương trình

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_GIA_TRI_LON_NHAT_NHO_NHAT {

    public static void main(String[] args) throws Exception {

        String host = "[IP_ADDRESS]";
        int port = 2207;

        String studentCode = "B22DCCN760";
        String qCode = "uWKK8u3W";

        DatagramSocket socket = new DatagramSocket();

        InetAddress server = InetAddress.getByName(host);

        String request = ";" + studentCode + ";" + qCode;

        byte[] sendData = request.getBytes("UTF-8");

        DatagramPacket sendPacket = new DatagramPacket(
                sendData,
                sendData.length,
                server,
                port
        );

        socket.send(sendPacket);

        byte[] receiveData = new byte[4096];

        DatagramPacket receivePacket = new DatagramPacket(
                receiveData,
                receiveData.length
        );

        socket.receive(receivePacket);

        String response = new String(
                receivePacket.getData(),
                0,
                receivePacket.getLength(),
                "UTF-8"
        ).trim();

        String[] parts = response.split(";");

        String requestId = parts[0];

        String[] numbers = parts[1].split(",");

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (String s : numbers) {
            int x = Integer.parseInt(s.trim());

            if (x > max) {
                max = x;
            }

            if (x < min) {
                min = x;
            }
        }

        String result = requestId + ";" + max + "," + min;

        sendData = result.getBytes("UTF-8");

        sendPacket = new DatagramPacket(
                sendData,
                sendData.length,
                server,
                port
        );

        socket.send(sendPacket);

        socket.close();
    }
}