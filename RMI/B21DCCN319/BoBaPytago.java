// BÀI 1. [Data] BỘ BA SỐ PYTAGO
// [Mã câu hỏi (qCode): NMATI6Zw]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện
// cho phép triệu gọi từ xa để xử lý dữ liệu.
// Giao diện từ xa:
// public interface DataService extends Remote {
// public Object requestData(String studentCode, String qCode) throws RemoteException;
// public void submitData(String studentCode, String qCode, Object data) throws
// RemoteException;
// }
// Trong đó:
// • Interface DataService được viết trong package RMI.
// • Đối tượng cài đặt giao diện từ xa DataService được đăng ký với RegistryServer với tên là:
// RMIDataService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu
// nhận được từ RMI Server:
// a. Triệu gọi phương thức requestData để nhận một số nguyên dương N từ server, đại diện cho giới
// hạn trên của khoảng cần kiểm tra.
// b. Xác định tất cả các bộ ba số nguyên (a, b, c) sao cho a² + b² = c² và a < b < c ≤ N. Kết quả trả về
// là danh sách các bộ ba số Pythagore thỏa mãn yêu cầu.
// Ví dụ: Với N = 20, kết quả là [(3, 4, 5), (5, 12, 13), (8, 15, 17)].
// c. Triệu gọi phương thức submitData để gửi đối tượng List<List<Integer>> danh sách các bộ ba số
// Pytago đã tìm được trở lại server.
// d. Kết thúc chương trình client.

package RMI.B21DCCN319;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import RMI.DataService;
public class BoBaPytago {
    public static void main(String[] args) throws Exception {
        // a. Nhận dữ liệu
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        DataService sv = (DataService) rg.lookup("RMIDataService");
        int N = (int) sv.requestData("B21DCCN319", "NMATI6Zw");
        System.out.println(N);
        // b. Xử lý: Tìm danh sách các bộ ba số Pythagore
        List<List<Integer>> res = new ArrayList<>();
        for (int a = 1; a <= N; a++) {
            for (int b = a + 1; b <= N; b++) {
                for (int c = b + 1; c <= N; c++) {
                    if (a * a + b * b == c * c) {
                        List<Integer> triple = new ArrayList<>();
                        triple.add(a); triple.add(b); triple.add(c);
                        res.add(triple);
                    }
                }
            }
        }
        System.out.println(res);
        // c. Gửi kết quả
        sv.submitData("B21DCCN319", "NMATI6Zw", res);
    }
}
