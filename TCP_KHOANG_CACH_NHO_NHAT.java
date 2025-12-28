/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 1. KHOẢNG CÁCH NHỎ NHẤT
//[Mã câu hỏi (qCode): TbzkNfL3]. Một chương trình server hỗ trợ kết nối qua giao thức TCP tại
//cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình
//client thực hiện kết nối tới server trên sử dụng luồng byte dữ liệu (InputStream/OutputStream) để
//trao đổi thông tin theo thứ tự:
//a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ:
//"B16DCCN999;FF49DC02"
//b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách nhau bởi ký tự ","
//Ex: 1,3,9,19,33,20
//c. Thực hiện tìm giá trị khoảng cách nhỏ nhất của các phần tử nằm trong chuỗi và hai giá trị
//lớn nhất tạo nên khoảng cách đó. Gửi lên server chuỗi gồm "khoảng cách nhỏ nhất, số thứ nhất, số
//thứ hai". Ex: 1,19,20
//d. Đóng kết nối và kết thúc


package LTM;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class TCP_KHOANG_CACH_NHO_NHAT {

    public static void main(String[] args) throws Exception {

        String host = "localhost"; // đổi nếu server khác máy
        int port = 2206;

        String studentCode = "B15DCCN999";
        String qCode = "TbzkNfL3";

        Socket socket = new Socket(host, port);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        // a. Gửi studentCode;qCode
        String request = studentCode + ";" + qCode;
        out.write(request.getBytes());
        out.flush();

        // b. Nhận chuỗi số nguyên từ server
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        String data = new String(buffer, 0, len).trim();

        // Parse chuỗi thành mảng số
        String[] parts = data.split(",");
        int[] arr = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i].trim());
        }

        // c. Tìm khoảng cách nhỏ nhất
        Arrays.sort(arr);

        int minDist = Integer.MAX_VALUE;
        int x = arr[0], y = arr[1];

        for (int i = 1; i < arr.length; i++) {
            int dist = arr[i] - arr[i - 1];
            if (dist < minDist) {
                minDist = dist;
                x = arr[i - 1];
                y = arr[i];
            }
        }

        // Gửi kết quả lên server
        String result = minDist + "," + x + "," + y;
        out.write(result.getBytes());
        out.flush();

        // d. Đóng kết nối
        socket.close();
    }
}
