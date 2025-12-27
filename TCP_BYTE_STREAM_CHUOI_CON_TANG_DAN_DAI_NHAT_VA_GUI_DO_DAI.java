/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): XFbnctlx].  Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
//    a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;76B68B3B".
//    b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách bởi ký tự ",". Ví dụ: 5,10,20,25,50,40,30,35.
//    c. Tìm chuỗi con tăng dần dài nhất và gửi độ dài của chuỗi đó lên server theo format "chuỗi tăng dài nhất; độ dài". Ví dụ: 5,10,20,25,50;5
//    d. Đóng kết nối và kết thúc chương trình.

package LTM;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;

public class TCP_BYTE_STREAM_CHUOI_CON_TANG_DAN_DAI_NHAT_VA_GUI_DO_DAI {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("203.162.10.109", 2206);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        // a. Gửi studentCode;qCode
        String studentCode = "B22DCCN760";
        String qCode = "XFbnctlx";
        out.write((studentCode + ";" + qCode).getBytes());
        out.flush();

        // b. Nhận dữ liệu
        byte[] buffer = new byte[10000];
        int len = in.read(buffer);
        String data = new String(buffer, 0, len).trim();

        String[] parts = data.split(",");
        int n = parts.length;
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(parts[i].trim());
        }

        // ================= LIS =================
        int[] dp = new int[n];
        int[] prev = new int[n];

        Arrays.fill(dp, 1);
        Arrays.fill(prev, -1);

        int maxLen = 1;
        int lastIndex = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i] && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }
            if (dp[i] > maxLen) {
                maxLen = dp[i];
                lastIndex = i;
            }
        }

        // ================= Truy vết =================
        List<Integer> lis = new ArrayList<>();
        while (lastIndex != -1) {
            lis.add(arr[lastIndex]);
            lastIndex = prev[lastIndex];
        }
        Collections.reverse(lis);

        // ================= Gửi kết quả =================
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lis.size(); i++) {
            sb.append(lis.get(i));
            if (i < lis.size() - 1) sb.append(",");
        }
        sb.append(";").append(maxLen);

        out.write(sb.toString().getBytes());
        out.flush();

        socket.close();
    }
}
