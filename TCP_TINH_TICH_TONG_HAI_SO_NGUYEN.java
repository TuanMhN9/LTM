// Một chương trình máy chủ cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5s), yêu cầu xây dựng chương trình (tạm gọi là client) thực hiện kết nối tới server tại cổng 2207, sử dụng luồng byte dữ liệu (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:   a. Gửi chuỗi là mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;1D25ED92"  b. Nhận lần lượt hai số nguyên a và b từ server  c. Thực hiện tính toán tổng, tích và gửi lần lượt từng giá trị theo đúng thứ tự trên lên server  d. Đóng kết nối và kết thúc



import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TCP_TINH_TICH_TONG_HAI_SO_NGUYEN {
    public static void main(String[] args) throws Exception {

        String host = "203.162.xxx.xxx";
        int port = 2207;

        Socket socket = new Socket(host, port);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        String studentCode = "B15DCCN999";
        String qCode = "1D25ED92";

        dos.writeUTF(studentCode + ";" + qCode);
        dos.flush();

        int a = dis.readInt();
        int b = dis.readInt();

        int sum = a + b;
        int product = a * b;

        dos.writeInt(sum);
        dos.writeInt(product);
        dos.flush();

        dis.close();
        dos.close();
        socket.close();
    }
}