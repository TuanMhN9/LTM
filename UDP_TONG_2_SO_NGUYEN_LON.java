/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 1. TỔNG HAI SỐ NGUYÊN LỚN
//[Mã câu hỏi (qCode): 2sIjAYaU].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode".
//Ví dụ: ";B15DCCN010;D3F9A7B8"
//b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;a;b", với:
//•	requestId là chuỗi ngẫu nhiên duy nhất.
//•	a và b là chuỗi thể hiện hai số nguyên lớn (hơn hoặc bằng 100 chữ số).
//Ví dụ: "X1Y2Z3;9876543210;123456789"
//c. Tính tổng và hiệu của hai số a và b, gửi thông điệp lên server theo định dạng "requestId;sum;difference".Ví dụ: 
//Nếu nhận được "X1Y2Z3;9876543210,123456789", tổng là 9999999999 và hiệu là 9753086421. Kết quả gửi lại sẽ là "X1Y2Z3;9999999999,9753086421".
//d. Đóng socket và kết thúc chương trình
//Chú ý: Yêu cầu sử dụng BigInter, tính a – b chứ không tính |a – b|


package LTM;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_TONG_2_SO_NGUYEN_LON {

    public static void main(String[] args) throws Exception {

        // 1. Tạo socket UDP
        DatagramSocket socket = new DatagramSocket();

        InetAddress serverAddress = InetAddress.getByName("localhost");
        int serverPort = 2207;

        // 2. Gửi studentCode và qCode
        String studentCode = "B15DCCN010";
        String qCode = "2sIjAYaU";
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

        String received = new String(receivePacket.getData(), 0, receivePacket.getLength());

        // 4. Phân tích dữ liệu nhận được
        // Format: requestId;a;b
        String[] parts = received.split(";");
        String requestId = parts[0];
        BigInteger a = new BigInteger(parts[1]);
        BigInteger b = new BigInteger(parts[2]);

        // 5. Tính tổng và hiệu
        BigInteger sum = a.add(b);
        BigInteger diff = a.subtract(b);

        // 6. Gửi kết quả lại server
        String result = requestId + ";" + sum.toString() + ";" + diff.toString();
        byte[] resultData = result.getBytes();

        DatagramPacket resultPacket =
                new DatagramPacket(resultData, resultData.length, serverAddress, serverPort);
        socket.send(resultPacket);

        // 7. Đóng socket
        socket.close();
    }
}
