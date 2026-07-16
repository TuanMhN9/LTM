// BÀI 5. TỔNG CÁC SỐ
// Mã bài tập PUh9Ki1
// Một chương trình server tại địa chỉ 172.188.19.218 hỗ trợ kết nối qua giao thức TCP tại cổng 1604
// (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực
// hiện kết nối tới server trên sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi
// thông tin theo thứ tự:
// a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
// Ví dụ: "B20DCCN999;ABCDEF" với ABCDEF là mã bài tập đã đề cập ở trên.
// b. Nhận dữ liệu từ server là một chuỗi gồm hai giá trị nguyên được phân tách với nhau bằng
// "|"
// Ex: 2|5|9|11
//  c. Thực hiện tìm giá trị tổng của các số nguyên trong chuỗi và gửi lên server Ex: 27
//  d. Đóng kết nối và kết thúc chương trình


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCP_TONG_CAC_SO_NGUYEN_TRON_CHUOI {

    public static void main(String[] args) throws Exception {

        String host = "172.188.19.218";
        int port = 1604;

        String studentCode = "B22DCCN760";
        String qCode = "PUh9Ki1";

        Socket socket = new Socket(host, port);

        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();

        os.write((studentCode + ";" + qCode).getBytes("UTF-8"));
        os.flush();

        byte[] buffer = new byte[1024];
        int len = is.read(buffer);

        String data = new String(buffer, 0, len, "UTF-8").trim();

        String[] arr = data.split("\\|");

        long sum = 0;

        for (String s : arr) {
            sum += Integer.parseInt(s.trim());
        }

        os.write(String.valueOf(sum).getBytes("UTF-8"));
        os.flush();

        is.close();
        os.close();
        socket.close();
    }
}