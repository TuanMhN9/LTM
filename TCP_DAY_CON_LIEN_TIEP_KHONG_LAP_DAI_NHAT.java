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
import java.util.HashSet;
import java.util.Set;

public class TCP_DAY_CON_LIEN_TIEP_KHONG_LAP_DAI_NHAT {

    public static void main(String[] args) throws Exception {

        String server = "172.188.19.218";
        int port = 2206;

        Socket socket = new Socket(server, port);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        String studentCode = "B16DCCN999"; // đổi mã SV của bạn
        String qCode = "HyHAk4P5";

        /* a. Gửi studentCode;qCode */
        String send = studentCode + ";" + qCode;
        out.write(send.getBytes());
        out.flush();

        /* b. Nhận chuỗi từ server */
        byte[] buffer = new byte[2048];
        int len = in.read(buffer);
        String s = new String(buffer, 0, len).trim();

        /* c. Tìm longest substring không lặp */
        Set<Character> set = new HashSet<>();
        int left = 0;
        int maxLen = 0;
        int startIndex = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            while (set.contains(c)) {
                set.remove(s.charAt(left));
                left++;
            }

            set.add(c);

            if (right - left + 1 > maxLen) {
                maxLen = right - left + 1;
                startIndex = left;
            }
        }

        String longestSubstring = s.substring(startIndex, startIndex + maxLen);

        /* d. Gửi kết quả */
        String result = longestSubstring + ";" + maxLen;
        out.write(result.getBytes());
        out.flush();

        socket.close();
    }
}
