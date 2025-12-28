/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 2. PHƯƠNG SAI
//[Mã câu hỏi (qCode): 1pKJUCce]. Một chương trình server hỗ trợ kết nối qua giao thức TCP tại
//cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình
//client thực hiện kết nối tới server trên bằng các byte stream (DataInputStream/DataOutputStream)
//để trao đổi thông tin theo thứ tự:
//a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ:
//"B16DCCN999;FF49DC02"
//b. Nhận dữ liệu từ server là một mảng gồm n số nguyên
//c. Thực hiện gửi tổng, trung bình cộng và phương sai của dãy số (TBC và phương sai dạng
//float không làm tròn)
//d. Đóng kết nối và kết thúc

package LTM;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCP_PHUONG_SAI {

    public static void main(String[] args) throws Exception {

        String host = "localhost"; // đổi nếu server khác máy
        int port = 2206;

        String studentCode = "B15DCCN999";
        String qCode = "1pKJUCce";

        Socket socket = new Socket(host, port);

        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        // a. Gửi studentCode;qCode
        String request = studentCode + ";" + qCode;
        out.writeUTF(request);
        out.flush();

        // b. Nhận mảng số nguyên
        int n = in.readInt();
        int[] arr = new int[n];

        int sum = 0;
        for (int i = 0; i < n; i++) {
            arr[i] = in.readInt();
            sum += arr[i];
        }

        // c. Tính trung bình và phương sai
        float mean = (float) sum / n;

        float variance = 0;
        for (int i = 0; i < n; i++) {
            float diff = arr[i] - mean;
            variance += diff * diff;
        }
        variance = variance / n;

        // Gửi kết quả lên server
        out.writeInt(sum);
        out.writeFloat(mean);
        out.writeFloat(variance);
        out.flush();

        // d. Đóng kết nối
        socket.close();
    }
}
