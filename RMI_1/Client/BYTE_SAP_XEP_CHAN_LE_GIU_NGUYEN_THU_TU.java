/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): cHHusZN7].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu nhị phân.
//Giao diện từ xa:
//public interface ByteService extends Remote {
//public byte[] requestData(String studentCode, String qCode) throws RemoteException;
//public void submitData(String studentCode, String qCode, byte[] data) throws RemoteException;
//}
//Trong đó:
//•	Interface ByteService được viết trong package RMI.
//Đối tượng cài đặt giao diện từ xa ByteService được đăng ký với RegistryServer với tên là: RMIByteService.
//Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhị phân nhận được từ RMI Server:
//a. Triệu gọi phương thức requestData để nhận một mảng dữ liệu nhị phân (byte[]) từ server.
//b. Thực hiện phân chia mảng byte[] nhận được thành hai phần: phần đầu chứa các byte có giá trị chẵn và phần sau chứa các byte có giá trị lẻ, duy trì thứ tự xuất hiện của các phần tử trong từng nhóm.
//Ví dụ: Nếu mảng dữ liệu nhận được là [1, 2, 3, 4, 5], sau khi phân chia chẵn-lẻ, mảng kết quả sẽ là [2, 4, 1, 3, 5] (tất cả phần tử chẵn được đặt trước, theo sau là tất cả phần tử lẻ).
//c. Triệu gọi phương thức submitData để gửi mảng byte[] đã được phân chia chẵn-lẻ trở lại server.
//d. Kết thúc chương trình client.

package Client;

import RMI.ByteService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class BYTE_SAP_XEP_CHAN_LE_GIU_NGUYEN_THU_TU {

    public static void main(String[] args) {
        String studentCode = "B22DCCN760"; // đổi MSSV của bạn
        String qCode = "cHHusZN7";

        try {
            /* ===== 1. Kết nối RMI Registry ===== */
            Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);
            ByteService service = (ByteService) registry.lookup("RMIByteService");

            /* ===== 2. Nhận mảng byte ===== */
            byte[] input = service.requestData(studentCode, qCode);

            /* ===== 3. Tách chẵn – lẻ, giữ nguyên thứ tự ===== */
            List<Byte> even = new ArrayList<>();
            List<Byte> odd = new ArrayList<>();

            for (byte b : input) {
                if (b % 2 == 0) {
                    even.add(b);
                } else {
                    odd.add(b);
                }
            }

            /* ===== 4. Ghép mảng kết quả ===== */
            byte[] result = new byte[input.length];
            int index = 0;

            for (byte b : even) {
                result[index++] = b;
            }
            for (byte b : odd) {
                result[index++] = b;
            }

            /* ===== 5. Gửi kết quả về server ===== */
            service.submitData(studentCode, qCode, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
