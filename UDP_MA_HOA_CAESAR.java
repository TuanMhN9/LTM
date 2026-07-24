// BÀI 4. MÃ HOÁ CAESAR [014]
// [Mã câu hỏi (qCode): J5SE2YXc]. Mật mã caesar, còn gọi là mật mã dịch chuyển, để giải mã thì
// mỗi ký tự nhận được sẽ được thay thế bằng một ký tự cách nó một đoạn s. Ví dụ: với s = 3 thì ký
// tự “A” sẽ được thay thế bằng ký tự “D”.
// Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu xây dựng
// chương trình client trao đổi thông tin với server theo kịch bản mô tả dưới đây:
// a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng
// ";studentCode;qCode". Ví dụ: ";B15DCCN001;825EE3A7"
// b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;strEncode;s".
// • requestId là chuỗi ngẫu nhiên duy nhất
// • strEncode là chuỗi thông điệp bị mã hóa
// • s là số nguyên chứa giá trị độ dịch của mã
// c. Giải mã tìm thông điệp ban đầu và gửi lên server theo định dạng “requestId;strDecode”
// d. Đóng socket và kết thúc chương trình.


package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

    public static void main(String[] args) throws Exception {

        String host = "172.188.19.218";
        int port = 2207;

        String studentCode = "B22DCCN760";
        String qCode = "J5SE2YXc";

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
        String strEncode = parts[1];
        int s = Integer.parseInt(parts[2]);

        String strDecode = decodeCaesar(strEncode, s);

        String result = requestId + ";" + strDecode;

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

    private static String decodeCaesar(String str, int s) {

        StringBuilder sb = new StringBuilder();

        s = s % 26;

        for (char c : str.toCharArray()) {

            if (c >= 'A' && c <= 'Z') {
                char x = (char) ((c - 'A' - s + 26) % 26 + 'A');
                sb.append(x);
            } else if (c >= 'a' && c <= 'z') {
                char x = (char) ((c - 'a' - s + 26) % 26 + 'a');
                sb.append(x);
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}