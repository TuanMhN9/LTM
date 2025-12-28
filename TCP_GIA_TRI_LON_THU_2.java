/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): HyHAk4P5].
//Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2206 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client thực hiện kết nối tới server sử dụng các luồng byte (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
//a.	Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;10048F28".
//b.	Nhận chuỗi ký tự s từ server. Ví dụ: "abcabcbb"
//c.	Tìm và gửi lên server chuỗi con dài nhất từ chuỗi nhận được mà không có ký tự lặp lại theo format "longestsubstring;length". Ví dụ: "abc;3".
//d.	Đóng kết nối và kết thúc chương trình.
//Chú ý:
//1.	Ràng buộc: 10 ≤ len(s) ≤ 1000
//2.	Nếu có nhiều xâu con liên tiếp đều có chung độ dài lớn nhất và thoả mãn không lặp lại, chỉ ghi nhận xâu con liên tiếp có vị trí xuất hiện bé nhất trong xâu ban đầu.


package LTM;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCP_GIA_TRI_LON_THU_2 {

    public static void main(String[] args) throws Exception {

        String server = "172.188.19.218";
        int port = 2206;

        Socket socket = new Socket(server, port);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        String studentCode = "B16DCCN999"; // đổi mã SV của bạn
        String qCode = "4XNRzWzl";

        /* a. Gửi studentCode;qCode */
        String send = studentCode + ";" + qCode;
        out.write(send.getBytes());
        out.flush();

        /* b. Nhận chuỗi số */
        byte[] buffer = new byte[2048];
        int len = in.read(buffer);
        String data = new String(buffer, 0, len).trim();

        String[] parts = data.split(",");

        int max1 = Integer.MIN_VALUE;
        int max2 = Integer.MIN_VALUE;
        int idx2 = -1;

        for (int i = 0; i < parts.length; i++) {
            int val = Integer.parseInt(parts[i].trim());

            if (val > max1) {
                max2 = max1;
                max1 = val;
                if (i != 0) idx2 = i - 1;
            } else if (val < max1 && val > max2) {
                max2 = val;
                idx2 = i;
            }
        }

        /* c. Gửi kết quả */
        String result = max2 + "," + idx2;
        out.write(result.getBytes());
        out.flush();

        socket.close();
    }
}