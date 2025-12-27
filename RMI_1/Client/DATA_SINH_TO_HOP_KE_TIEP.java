/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): RfjjC3iz].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu.
//Giao diện từ xa:
//public interface DataService extends Remote {
//public Object requestData(String studentCode, String qCode) throws RemoteException;
//public void submitData(String studentCode, String qCode, Object data) throws RemoteException;
//}
//Trong đó:
//•	Interface DataService được viết trong package RMI.
//•	Đối tượng cài đặt giao diện từ xa DataService được đăng ký với RegistryServer với tên là: RMIDataService.
//Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhận được từ RMI Server:
//a. Triệu gọi phương thức requestData để nhận một chuỗi các số nguyên.
//b. Sử dụng thuật toán sinh tổ hợp kế tiếp để tìm tổ hợp kế tiếp của chuỗi số này theo thứ tự từ điển. Nếu chuỗi đã là tổ hợp lớn nhất, trả về tổ hợp nhỏ nhất (sắp xếp lại từ đầu theo thứ tự từ điển).
//Ví dụ: Với chuỗi 1, 2, 3 tổ hợp kế tiếp là 1, 3, 2. Nếu chuỗi là 3, 2, 1 (tổ hợp lớn nhất), kết quả sẽ là 1, 2, 3 (tổ hợp nhỏ nhất).
//c. Triệu gọi phương thức submitData để gửi chuỗi (String) chứa tổ hợp kế tiếp đã tìm được trở lại server.
//d. Kết thúc chương trình client.

package Client;

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class DATA_SINH_TO_HOP_KE_TIEP {

    public static void main(String[] args) {
        String studentCode = "B22DCCN760"; // đổi MSSV
        String qCode = "RfjjC3iz";

        try {
            /* ===== 1. Kết nối RMI ===== */
            Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);
            DataService service = (DataService) registry.lookup("RMIDataService");

            /* ===== 2. Nhận dữ liệu ===== */
            String data = (String) service.requestData(studentCode, qCode);

            /* ===== 3. Parse chuỗi số ===== */
            String[] parts = data.split("[,\\s]+");
            int[] a = new int[parts.length];

            for (int i = 0; i < parts.length; i++) {
                a[i] = Integer.parseInt(parts[i]);
            }

            /* ===== 4. Sinh hoán vị kế tiếp ===== */
            if (!nextPermutation(a)) {
                Arrays.sort(a); // nếu là hoán vị lớn nhất
            }

            /* ===== 5. Chuyển về String ===== */
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < a.length; i++) {
                result.append(a[i]);
                if (i < a.length - 1) result.append(",");
            }

            /* ===== 6. Gửi kết quả ===== */
            service.submitData(studentCode, qCode, result.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ===== Thuật toán sinh hoán vị kế tiếp ===== */
    private static boolean nextPermutation(int[] a) {
        int i = a.length - 2;
        while (i >= 0 && a[i] >= a[i + 1]) i--;
        if (i < 0) return false;

        int j = a.length - 1;
        while (a[j] <= a[i]) j--;

        swap(a, i, j);
        reverse(a, i + 1, a.length - 1);
        return true;
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private static void reverse(int[] a, int l, int r) {
        while (l < r) swap(a, l++, r--);
    }
}
