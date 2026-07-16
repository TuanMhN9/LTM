// BÀI 7. ĐẢO NGƯỢC CHUỖI
// Một chương trình server tại địa chỉ 172.188.19.218 cho phép kết nối qua giao thức TCP tại cổng
// 1606 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương
// trình client tương tác với server sử dụng các luồng byte (BufferedWriter/BufferedReader) theo kịch
// bản sau:
// a/ Gửi chuỗi là mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
// Ví dụ: "B20DCCN999;ABCDEF" với ABCDEF là mã bài tập đã đề cập ở trên.
// b. Nhận một chuỗi từ server
// c. Thực hiện đảo ngược lại chuỗi và gửi lên server
// d. Đóng kết nối và kết thúc


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCP_DAO_NGUOC_CHUOI {

    public static void main(String[] args) throws Exception {

        String host = "172.188.19.218";
        int port = 1606;

        String studentCode = "B20DCCN999";
        String qCode = "ABCDEF";

        Socket socket = new Socket(host, port);

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));

        writer.write(studentCode + ";" + qCode);
        writer.newLine();
        writer.flush();

        String str = reader.readLine();

        String reversed = new StringBuilder(str).reverse().toString();

        writer.write(reversed);
        writer.newLine();
        writer.flush();

        reader.close();
        writer.close();
        socket.close();
    }
}