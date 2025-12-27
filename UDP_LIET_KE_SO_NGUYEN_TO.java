/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 11. LIỆT KÊ SỐ NGUYÊN TỐ [B17DCAT176]
//[Mã câu hỏi (qCode): 78CCQ6xD]. Một chương trình server cho phép giao tiếp qua giao thức UDP
//tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch
//bản:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng
//";studentCode;qCode". Ví dụ: ";B15DCCN009;F3E8B2D4".
//b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;n, n", với:
//--- requestId là chuỗi ngẫu nhiên duy nhất.
//--- n là một số nguyên ngẫu nhiên ≤ 100.
//c. Tính và gửi về server danh sách n số nguyên tố đầu tiên theo định dạng "requestId;p1,p2,...,pk",
//trong đó p1,p2,...,pk là các số nguyên tố.
//d. Đóng socket và kết thúc chương trình.

package LTM;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_LIET_KE_SO_NGUYEN_TO {

    // Hàm kiểm tra số nguyên tố
    private static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {

        InetAddress serverAddress = InetAddress.getByName("172.188.19.218");
        int port = 2207;

        DatagramSocket socket = new DatagramSocket();

        String studentCode = "B17DCAT176";
        String qCode = "78CCQ6xD";

        /* a. Gửi studentCode;qCode */
        String sendMsg = ";" + studentCode + ";" + qCode;
        byte[] sendData = sendMsg.getBytes();
        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, serverAddress, port);
        socket.send(sendPacket);

        /* b. Nhận requestId;n */
        byte[] buffer = new byte[4096];
        DatagramPacket receivePacket =
                new DatagramPacket(buffer, buffer.length);
        socket.receive(receivePacket);

        String received =
                new String(receivePacket.getData(), 0, receivePacket.getLength());

        String[] parts = received.split(";");
        String requestId = parts[0];
        int n = Integer.parseInt(parts[1]);

        /* c. Tính n số nguyên tố đầu tiên */
        StringBuilder primes = new StringBuilder();
        int count = 0;
        int num = 2;

        while (count < n) {
            if (isPrime(num)) {
                primes.append(num).append(",");
                count++;
            }
            num++;
        }

        // Xóa dấu , cuối
        primes.deleteCharAt(primes.length() - 1);

        /* d. Gửi kết quả về server */
        String response = requestId + ";" + primes;
        byte[] responseData = response.getBytes();
        DatagramPacket responsePacket =
                new DatagramPacket(responseData, responseData.length, serverAddress, port);
        socket.send(responsePacket);

        socket.close();
    }
}
