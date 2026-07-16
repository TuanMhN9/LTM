// Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2206 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng các luồng byte (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:   a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;2B3A6510"  b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách nhau bởi ký tự ",". Ví dụ: 1,3,9,19,33,20  c. Tìm và gửi lên server giá trị lớn thứ hai cùng vị trí xuất hiện của nó trong chuỗi.Ví dụ: 20,5  d. Đóng kết nối và kết thúc chương trình.





import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {

    public static void main(String[] args) throws Exception {

        String host = "203.162.xxx.xxx"; // Thay bằng IP server
        int port = 2206;

        Socket socket = new Socket(host, port);

        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        // a. Gửi mã sinh viên và mã câu hỏi
        String studentCode = "B16DCCN999";
        String qCode = "2B3A6510";

        String request = studentCode + ";" + qCode;
        os.write(request.getBytes());
        os.flush();

        // b. Nhận dữ liệu từ server
        byte[] buffer = new byte[1024];
        int len = is.read(buffer);

        String data = new String(buffer, 0, len).trim();

        String[] arr = data.split(",");

        int[] numbers = new int[arr.length];

        for (int i = 0; i < arr.length; i++) {
            numbers[i] = Integer.parseInt(arr[i].trim());
        }

        // c. Tìm số lớn thứ hai và vị trí của nó
        int max = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;

        // Tìm max
        for (int x : numbers) {
            if (x > max) {
                max = x;
            }
        }

        // Tìm second max
        for (int x : numbers) {
            if (x > secondMax && x < max) {
                secondMax = x;
            }
        }

        // Tìm vị trí xuất hiện đầu tiên của secondMax
        int index = -1;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == secondMax) {
                index = i;
                break;
            }
        }

        String result = secondMax + "," + index;

        os.write(result.getBytes());
        os.flush();

        // d. Đóng kết nối
        is.close();
        os.close();
        socket.close();
    }
}