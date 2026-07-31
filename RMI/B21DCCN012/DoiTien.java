// BÀI 1. [Data] ĐỔI TIỀN
// [Mã câu hỏi (qCode): Iz06p8Zw]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện
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
// a. Triệu gọi phương thức requestData để nhận một số nguyên dương amount từ server, đại diện cho
// số tiền cần đạt được.
// b. Sử dụng thuật toán xếp đồng xu với các mệnh giá cố định [1, 2, 5, 10] để xác định số lượng đồng
// xu tối thiểu cần thiết để đạt được số tiền amount. Nếu không thể đạt được số tiền đó với các mệnh
// giá hiện có, trả về -1.
// Ví dụ: Với amount = 18 và mệnh giá đồng xu cố định [1, 2, 5, 10], kết quả là 4 (18 = 10 + 5 + 2 +
// 1). Chuỗi cần gửi lên server là: 4; 10,5,2,1
// c. Triệu gọi phương thức submitData để gửi chuỗi chứa kết quả số lượng đồng xu tối thiểu và giá
// trị các đồng xu tương ứng trở lại server.
// d. Kết thúc chương trình client.



package RMI.B21DCCN012;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import RMI.DataService;
public class DoiTien {
    public static void main(String[] args) throws Exception {
        // a. Nhận dữ liệu
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        DataService sv = (DataService) rg.lookup("RMIDataService");
        int n = (int) sv.requestData("B21DCCN012", "Iz06p8Zw"), res = 0;
        System.out.println(n);
        // b. Xử lý: Tìm số đồng tiền
        String ans = "";
        int[] a = {1, 2, 5, 10}; 
        for (int i = 3; i >= 0; i--) {
            int p = n / a[i]; 
            if (p > 0) {
                res += p; 
                n -= p * a[i]; 
                for (int j = 0; j < p; j++) ans+=a[i] + ",";
            }
        }
        if (n > 0) ans = "-1";
        else {
            ans = ans.substring(0, ans.length() - 1);
            ans= String.format("%d; ", res) + ans;
        }
        System.out.println(ans);
        // c. Gửi kết quả
        sv.submitData("B21DCCN012", "Iz06p8Zw", ans);
    }
}


