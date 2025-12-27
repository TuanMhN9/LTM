/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 1. DÃY CON LIÊN TIẾP TĂNG DÀI NHẤT
//[Mã câu hỏi (qCode): RnPqP3f7].
//Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
//a.	Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;76B68B3B".
//b.	Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách bởi ký tự ",". Ví dụ: 5,10,20,25,50,40,30,35.
//c.	Tìm chuỗi con tăng dần dài nhất và gửi độ dài của chuỗi đó lên server. Ví dụ: 5,10,20,25 có độ dài 4.
//d.	Đóng kết nối và kết thúc chương trình.


package LTM;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCP_DAY_CON_LIEN_TIEP_TANG_DAN_DAI_NHAT {

    public static void main(String[] args) throws Exception {

        String server = "172.188.19.218";
        int port = 2206;

        Socket socket = new Socket(server, port);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        String studentCode = "B16DCCN999"; // đổi mã SV của bạn
        String qCode = "RnPqP3f7";

        /* a. Gửi studentCode;qCode */
        String send = studentCode + ";" + qCode;
        out.write(send.getBytes());
        out.flush();

        /* b. Nhận chuỗi số nguyên */
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        String data = new String(buffer, 0, len).trim();

        /* c. Xử lý tìm dãy con tăng dài nhất */
        String[] parts = data.split(",");
        int n = parts.length;

        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(parts[i].trim());
        }

        int maxLen = 1;
        int curLen = 1;

        for (int i = 1; i < n; i++) {
            if (a[i] > a[i - 1]) {
                curLen++;
            } else {
                maxLen = Math.max(maxLen, curLen);
                curLen = 1;
            }
        }
        maxLen = Math.max(maxLen, curLen);

        /* d. Gửi kết quả lên server */
        out.write(String.valueOf(maxLen).getBytes());
        out.flush();

        socket.close();
    }
}
