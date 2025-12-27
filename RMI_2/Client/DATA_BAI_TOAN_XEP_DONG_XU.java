/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): 4ypoJvIn].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu.
//Giao diện từ xa:
//public interface DataService extends Remote {
//public Object requestData(String studentCode, String qCode) throws RemoteException;
//public void submitData(String studentCode, String qCode, Object data) throws RemoteException;
//}
//Trong đó:
//•	Interface DataService được viết trong package RMI.
//•	Đối tượng cài đặt giao diện từ xa DataService được đăng ký với RegistryServer với tên là: RMIDataService.
//Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhận được từ RMI Server:
//a. Triệu gọi phương thức requestData để nhận một số nguyên dương amount từ server, đại diện cho số tiền cần đạt được.
//b. Sử dụng thuật toán xếp đồng xu với các mệnh giá cố định [1, 2, 5, 10] để xác định số lượng đồng xu tối thiểu cần thiết để đạt được số tiền amount. Nếu không thể đạt được số tiền đó với các mệnh giá hiện có, trả về -1.
//Ví dụ: Với amount = 18 và mệnh giá đồng xu cố định [1, 2, 5, 10], kết quả là 4 (18 = 10 + 5 + 2 + 1). Chuỗi cần gửi lên server là: 4; 10,5,2,1
//c. Triệu gọi phương thức submitData để gửi chuỗi (kiểu String) chứa kết quả số lượng đồng xu tối thiểu và giá trị các đồng xu tương ứng  trở lại server.
//d. Kết thúc chương trình client.

package Client;

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class DATA_BAI_TOAN_XEP_DONG_XU {

    public static void main(String[] args) {

        String studentCode = "B22DCCN088"; // đổi MSSV
        String qCode = "4ypoJvIn";

        int[] coins = {10, 5, 2, 1};

        try {
            /* ===== 1. Kết nối RMI ===== */
            Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);
            DataService service =
                    (DataService) registry.lookup("RMIDataService");

            /* ===== 2. Nhận amount ===== */
            int amount = (int) service.requestData(studentCode, qCode);

            List<Integer> usedCoins = new ArrayList<>();
            int remaining = amount;

            /* ===== 3. Thuật toán tham lam ===== */
            for (int coin : coins) {
                while (remaining >= coin) {
                    remaining -= coin;
                    usedCoins.add(coin);
                }
            }

            String result;

            /* ===== 4. Kiểm tra có đạt được không ===== */
            if (remaining != 0) {
                result = "-1";
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(usedCoins.size()).append("; ");

                for (int i = 0; i < usedCoins.size(); i++) {
                    sb.append(usedCoins.get(i));
                    if (i < usedCoins.size() - 1) {
                        sb.append(",");
                    }
                }
                result = sb.toString();
            }

            /* ===== 5. Gửi kết quả ===== */
            service.submitData(studentCode, qCode, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
