/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): qosGpnIW].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng byte (BufferedWriter/BufferedReader) theo kịch bản sau: 
//a.	Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;BAA62945"
//b.	Nhận một chuỗi ngẫu nhiên từ server
//Ví dụ: dgUOo ch2k22ldsOo
//c.	Liệt kê các ký tự (là chữ hoặc số) xuất hiện nhiều hơn một lần trong chuỗi và số lần xuất hiện của chúng và gửi lên server
//Ví dụ: d:2,O:2,o:2,2:3,
//d.	Đóng kết nối và kết thúc chương trình.

package LTM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class TCP_CHARACTER_LIET_KE_KY_TU_XUAT_HIEN_NHIEU_HON_1_LAN_VA_SO_LAN_XUAT_HIEN {

    public static void main(String[] args) {
        String serverIp = "36.50.135.242";
        int port = 2208;

        String studentCode = "B22DCCN760";
        String qCode = "9z6g3v5H";

        try (Socket socket = new Socket(serverIp, port)) {

            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            bw.write(studentCode + ";" + qCode);
            bw.newLine();
            bw.flush();

            String input = br.readLine();

            Map<Character, Integer> countMap = new LinkedHashMap<>();

            for (char c : input.toCharArray()) {
                if (Character.isLetterOrDigit(c)) {
                    countMap.put(c, countMap.getOrDefault(c, 0) + 1);
                }
            }

            StringBuilder result = new StringBuilder();
            for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
                if (entry.getValue() > 1) {
                    result.append(entry.getKey())
                          .append(":")
                          .append(entry.getValue())
                          .append(",");
                }
            }

            bw.write(result.toString());
            bw.newLine();
            bw.flush();

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
