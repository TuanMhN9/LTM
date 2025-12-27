/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): oNGj55wV].  Một chương trình server cho phép kết nối qua TCP tại cổng 807 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu xây dựng chương trình client thực hiện giao tiếp với server sử dụng luồng data (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
//a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
//Ví dụ: "B10DCCN002;B4C5D6E7"
//b. Nhận chuỗi chứa mảng số nguyên từ server, các phần tử được phân tách bởi dấu phẩy ",". Ví dụ: "1,3,2,5,4,7,6"
//c. Tính số lần đổi chiều và tổng độ biến thiên trong dãy số.
//- Đổi chiều: Khi dãy chuyển từ tăng sang giảm hoặc từ giảm sang tăng 
//- Độ biến thiên: Tổng giá trị tuyệt đối của các hiệu số liên tiếp
//Gửi lần lượt lên server: số nguyên đại diện cho số lần đổi chiều, sau đó là số nguyên đại diện cho tổng độ biến thiên. Ví dụ: Với mảng "1,3,2,5,4,7,6", số lần đổi chiều: 5 lần, Tổng độ biến thiên 11  Gửi lần lượt số nguyên 5 và 11 lên server.
//d. Đóng kết nối và kết thúc chương trình.


package LTM;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
/**
 *
 * @author Dell
 */
public class TCP_DATA_STREAM_DOI_CHIEU_VA_BIEN_THIEN {
    public static void main(String[] args) throws Exception {

        // 1. Kết nối tới server
        Socket socket = new Socket("localhost", 807);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        // 2. Gửi mã sinh viên và mã câu hỏi
        String studentCode = "B10DCCN002";
        String qCode = "oNGj55wV";
        String send = studentCode + ";" + qCode;

        dos.writeUTF(send);
        dos.flush();

        // 3. Nhận chuỗi mảng số nguyên
        String data = dis.readUTF(); // ví dụ: "1,3,2,5,4,7,6"
        String[] parts = data.split(",");

        int n = parts.length;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }

        // 4. Tính độ biến thiên
        int bienThien = 0;
        for (int i = 1; i < n; i++) {
            bienThien += Math.abs(arr[i] - arr[i - 1]);
        }

        // 5. Tính số lần đổi chiều
        int doiChieu = 0;
        int prevDiff = 0;

        for (int i = 1; i < n; i++) {
            int diff = arr[i] - arr[i - 1];

            if (diff == 0) continue;

            if (prevDiff != 0 && diff * prevDiff < 0) {
                doiChieu++;
            }

            prevDiff = diff;
        }

        // 6. Gửi kết quả lên server
        dos.writeInt(doiChieu);
        dos.writeInt(bienThien);
        dos.flush();

        // 7. Đóng kết nối
        dis.close();
        dos.close();
        socket.close();
    }
}
