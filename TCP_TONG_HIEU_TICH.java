// Mã bài tập 1T6hNnZ
// Một chương trình server được triển khai tại địa chỉ 172.188.19.218 (hỗ trợ thời gian giao tiếp tối
// đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình Client thực hiện kết nối tới server tại
// cổng 1605, sử dụng luồng byte dữ liệu (DataInputStream/DataOutputStream) để trao đổi thông tin
// theo thứ tự:
// a/ Gửi chuỗi là mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
// Ví dụ: "B20DCCN999;ABCDEF" với ABCDEF là mã bài tập đã đề cập ở trên.
// b. Nhận lần lượt hai số nguyên a và b từ server
// c. Thực hiện tính toán tổng, hiệu, tích và gửi lần lượt từng giá trị theo đúng thứ tự trên lên server
// d. Đóng kết nối và kết thúc



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCPClient {

    public static void main(String[] args) throws Exception {

        String host = "172.188.19.218";
        int port = 1605;

        String studentCode = "B20DCCN999";
        String qCode = "1T6hNnZ";

        Socket socket = new Socket(host, port);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        dos.writeUTF(studentCode + ";" + qCode);
        dos.flush();

        int a = dis.readInt();
        int b = dis.readInt();

        dos.writeInt(a + b);
        dos.writeInt(a - b);
        dos.writeInt(a * b);
        dos.flush();

        dis.close();
        dos.close();
        socket.close();
    }
}