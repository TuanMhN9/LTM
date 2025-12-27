/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 1. PHẦN TỬ LỚN NHẤT CỦA DÃY CON
//[Mã câu hỏi (qCode): iv00Hrq6].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B21DCCN795;ylrhZ6UM".
//b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;n;k;z1,z2,...,zn", trong đó:
//    requestId là chuỗi ngẫu nhiên duy nhất.
//    n là số phần tử của mảng.
//    k là kích thước cửa sổ trượt (k < n).
//    z1 đến zn là n phần tử là số nguyên của mảng.
//c. Thực hiện tìm giá trị lớn nhất trong mỗi cửa sổ trượt với kích thước k trên mảng số nguyên nhận được, và gửi thông điệp lên server theo định dạng "requestId;max1,max2,...,maxm", trong đó max1 đến maxm là các giá trị lớn nhất tương ứng trong mỗi cửa sổ.
//Ví dụ: "requestId;5;3;1,5,2,3,4"
//Kết quả: "requestId;5,5,4"
//d. Đóng socket và kết thúc chương trình.


package LTM;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_PHAN_TU_LON_NHAT_CUA_DAY_CON {

    public static void main(String[] args) throws Exception {

        // 1. Tạo socket UDP
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName("203.162.10.109");
        int serverPort = 2207;

        // 2. Gửi studentCode;qCode
        String studentCode = "B22DCCN760";
        String qCode = "j6TcNC1Q";
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

        // 4. Phân tích chuỗi nhận được
        // Format: requestId;n;k;z1,z2,...,zn
        String[] parts = received.split(";");
        String requestId = parts[0];
        int n = Integer.parseInt(parts[1]);
        int k = Integer.parseInt(parts[2]);

        String[] arrStr = parts[3].split(",");
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(arrStr[i]);
        }

        // 5. Tìm max trong mỗi cửa sổ trượt
        StringBuilder result = new StringBuilder();
        for (int i = 0; i <= n - k; i++) {
            int max = arr[i];
            for (int j = i; j < i + k; j++) {
                if (arr[j] > max) {
                    max = arr[j];
                }
            }
            result.append(max);
            if (i < n - k) {
                result.append(",");
            }
        }

        // 6. Gửi kết quả lên server
        String sendResult = requestId + ";" + result.toString();
        byte[] resultData = sendResult.getBytes();

        DatagramPacket resultPacket =
                new DatagramPacket(resultData, resultData.length, serverAddress, serverPort);
        socket.send(resultPacket);

        // 7. Đóng socket
        socket.close();
    }
}
