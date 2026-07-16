// BÀI 1. SẮP XẾP CHẴN LẺ
// [Mã câu hỏi (qCode): rMdCliDV]. Một chương trình server cho phép kết nối qua giao thức TCP
// tại cổng 2206 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương
// trình client tương tác tới server ở trên sử dụng các luồng byte (InputStream/OutputStream) để trao
// đổi thông tin theo thứ tự:
// a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ:
// "B16DCCN999;A63D9404".
// b. Nhận dữ liệu từ server là một chuỗi các số nguyên được sắp xếp ngẫu nhiên, các số được phân
// tách nhau bởi ký tự ",". Ví dụ: "2,15,4,3,6,8,10,7,1".
// c. Sắp xếp tăng dần các giá trị chẵn và sau đó tăng dần các giá trị lẻ trong dãy số. Ví dụ: "[2, 4, 6,
// 8, 10];[1, 3, 7, 15]". Gửi chuỗi được sắp xếp này lên server.
// d. Đóng kết nối và kết thúc chương trình.



import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class TCPClient {

    public static void main(String[] args) throws Exception {

        String host = "172.188.19.218";
        int port = 2206;

        String studentCode = "B22DCCN760";
        String qCode = "rMdCliDV";

        Socket socket = new Socket(host, port);

        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        os.write((studentCode + ";" + qCode).getBytes("UTF-8"));
        os.flush();

        byte[] buffer = new byte[1024];
        int len = is.read(buffer);

        String data = new String(buffer, 0, len, "UTF-8").trim();

        String[] arr = data.split(",");

        ArrayList<Integer> even = new ArrayList<>();
        ArrayList<Integer> odd = new ArrayList<>();

        for (String s : arr) {
            int x = Integer.parseInt(s.trim());
            if (x % 2 == 0) {
                even.add(x);
            } else {
                odd.add(x);
            }
        }

        Collections.sort(even);
        Collections.sort(odd);

        String result = even.toString() + ";" + odd.toString();

        os.write(result.getBytes("UTF-8"));
        os.flush();

        is.close();
        os.close();
        socket.close();
    }
}