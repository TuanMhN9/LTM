/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 2. ĐẾM SỐ LẦN XUẤT HIỆN
//[Mã câu hỏi (qCode): CVkVQheX]. Một chương trình server cho phép kết nối qua giao thức TCP
//tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một
//chương trình client tương tác với server sử dụng các luồng byte (BufferedWriter/BufferedReader)
//theo kịch bản sau:
//a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ:
//"B15DCCN999;BAA62945"
//b. Nhận một chuỗi ngẫu nhiên từ server
//Ví dụ: dgUOo ch2k22ldsOo
//c. Liệt kê các ký tự (là chữ hoặc số) xuất hiện nhiều hơn một lần trong chuỗi và số lần xuất
//hiện của chúng và gửi lên server
//Ví dụ: d:2,O:2,o:2,2:3,
//d. Đóng kết nối và kết thúc chương trình.

package LTM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class TCP_DEM_SO_LAN_XUAT_HIEN {

    public static void main(String[] args) throws Exception {

        String host = "localhost"; // đổi nếu server khác máy
        int port = 2208;

        String studentCode = "B15DCCN999";
        String qCode = "CVkVQheX";

        Socket socket = new Socket(host, port);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())
        );

        // a. Gửi studentCode;qCode
        String request = studentCode + ";" + qCode;
        out.write(request);
        out.newLine();
        out.flush();

        // b. Nhận chuỗi từ server
        String data = in.readLine();
        System.out.println("Chuỗi nhận được: " + data);

        // c. Đếm ký tự xuất hiện nhiều hơn 1 lần
        Map<Character, Integer> countMap = new LinkedHashMap<>();

        for (char c : data.toCharArray()) {
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

        // Gửi kết quả lên server
        out.write(result.toString());
        out.newLine();
        out.flush();

        // d. Đóng kết nối
        socket.close();
    }
}
