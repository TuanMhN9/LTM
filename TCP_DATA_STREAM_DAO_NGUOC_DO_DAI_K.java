/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 2. ĐẢO NGƯỢC ĐOẠN DÀI K
//[Mã câu hỏi (qCode): dCNDHojG].  Một chương trình server cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu xây dựng chương trình client thực hiện giao tiếp với server sử dụng luồng data (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
//a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".
//Ví dụ: "B10DCCN003;C6D7E8F9"
//b. Nhận lần lượt:
//•	Một số nguyên k là độ dài đoạn.
//•	Chuỗi chứa mảng số nguyên, các phần tử được phân tách bởi dấu phẩy ",".
//Ví dụ: Nhận k = 3 và "1,2,3,4,5,6,7,8".
//c. Thực hiện chia mảng thành các đoạn có độ dài k và đảo ngược mỗi đoạn, sau đó gửi mảng đã xử lý lên server. Ví dụ: Với k = 3 và mảng "1,2,3,4,5,6,7,8", kết quả là "3,2,1,6,5,4,8,7". Gửi chuỗi kết quả "3,2,1,6,5,4,8,7" lên server.
//d. Đóng kết nối và kết thúc chương trình


package LTM;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
/**
 *
 * @author Dell
 */
public class TCP_DATA_STREAM_DAO_NGUOC_DO_DAI_K {
    public static void main(String[] args) throws Exception {

        // 1. Kết nối server
        Socket socket = new Socket("localhost", 2207);

        DataInputStream dis = new DataInputStream(socket.getInputStream());
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

        // 2. Gửi mã sinh viên và mã câu hỏi
        String studentCode = "B10DCCN003";
        String qCode = "dCNDHojG";
        dos.writeUTF(studentCode + ";" + qCode);
        dos.flush();

        // 3. Nhận k
        int k = dis.readInt();

        // 4. Nhận chuỗi mảng
        String data = dis.readUTF(); // ví dụ: "1,2,3,4,5,6,7,8"
        String[] parts = data.split(",");

        int n = parts.length;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(parts[i]);
        }

        // 5. Đảo ngược từng đoạn độ dài k
        for (int i = 0; i < n; i += k) {
            int left = i;
            int right = Math.min(i + k - 1, n - 1);

            while (left < right) {
                int temp = arr[left];
                arr[left] = arr[right];
                arr[right] = temp;
                left++;
                right--;
            }
        }

        // 6. Chuyển mảng kết quả thành chuỗi
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            result.append(arr[i]);
            if (i < n - 1) {
                result.append(",");
            }
        }

        // 7. Gửi kết quả lên server
        dos.writeUTF(result.toString());
        dos.flush();

        // 8. Đóng kết nối
        dis.close();
        dos.close();
        socket.close();
    }
}
