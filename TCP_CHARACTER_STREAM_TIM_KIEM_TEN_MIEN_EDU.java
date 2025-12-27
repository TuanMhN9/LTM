/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): 7QRXiewF].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng byte (BufferedWriter/BufferedReader) theo kịch bản sau: 
//a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;EC4F899B"
//b.	Nhận một chuỗi ngẫu nhiên là danh sách các một số tên miền từ server
//Ví dụ: giHgWHwkLf0Rd0.io, I7jpjuRw13D.io, wXf6GP3KP.vn, MdpIzhxDVtTFTF.edu, TUHuMfn25chmw.vn, HHjE9.com, 4hJld2m2yiweto.vn, y2L4SQwH.vn, s2aUrZGdzS.com, 4hXfJe9giAA.edu
//c.	Tìm kiếm các tên miền .edu và gửi lên server
//Ví dụ: MdpIzhxDVtTFTF.edu, 4hXfJe9giAA.edu
//d.	Đóng kết nối và kết thúc chương trình.

package LTM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCP_CHARACTER_STREAM_TIM_KIEM_TEN_MIEN_EDU {

    public static void main(String[] args) {
        String studentCode = "B22DCCN136"; // đổi nếu cần
        String qCode = "7QRXiewF";

        try {
            Socket socket = new Socket("203.162.10.109", 2208);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            /* ===== a. Gửi studentCode;qCode ===== */
            out.write(studentCode + ";" + qCode);
            out.newLine();
            out.flush();

            /* ===== b. Nhận danh sách domain ===== */
            String received = in.readLine();

            String[] domains = received.split(",");
            StringBuilder result = new StringBuilder();

            /* ===== c. Lọc domain .edu ===== */
            for (String domain : domains) {
                domain = domain.trim();
                if (domain.endsWith(".edu")) {
                    result.append(domain).append(", ");
                }
            }

            // Xóa dấu ", " cuối
            if (result.length() > 0) {
                result.setLength(result.length() - 2);
            }

            /* ===== d. Gửi kết quả ===== */
            out.write(result.toString());
            out.newLine();
            out.flush();

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
