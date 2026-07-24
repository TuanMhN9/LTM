// BÀI 4. NHỎ THỨ HAI VÀ LỚN THỨ 2 [DataType]
// [Mã câu hỏi (qCode): oQifsr90].
// Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng
// một chương trình client trao đổi thông tin với server theo kịch bản:
// a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng
// “;studentCode;qCode”. Ví dụ: “;B15DCCN004;99D9F604”
// b. Nhận thông điệp là một chuỗi từ server theo định dạng “requestId;z1,z2,...,z50” requestId là
// chuỗi ngẫu nhiên duy nhất
// Trang 3
//  z1 -> z50 là 50 số nguyên ngẫu nhiên
//  c. Thực hiện tính số lớn thứ hai và số nhỏ thứ hai của thông điệp trong z1 -> z50 và gửi thông
// điệp lên server theo định dạng “requestId;secondMax,secondMin”
//  d. Đóng socket và kết thúc chương trình

package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_LON_THU_HAI_VA_NHO_THU_HAI {

    public static void main(String[] args) throws Exception {

        String host = "172.188.19.218";
        int port = 2207;

        String studentCode = "B22DCCN760";
        String qCode = "oQifsr90";

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

        String[] arr = parts[1].split(",");

        int max = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;

        int min = Integer.MAX_VALUE;
        int secondMin = Integer.MAX_VALUE;

        for (String s : arr) {
            int x = Integer.parseInt(s.trim());

            if (x > max) {
                secondMax = max;
                max = x;
            } else if (x > secondMax && x != max) {
                secondMax = x;
            }

            if (x < min) {
                secondMin = min;
                min = x;
            } else if (x < secondMin && x != min) {
                secondMin = x;
            }
        }

        String result = requestId + ";" + secondMax + "," + secondMin;

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