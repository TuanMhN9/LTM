// BÀI 1. [Data] LIỆT KÊ SỐ NGUYÊN TỐ
// [Mã câu hỏi (qCode): nD8MdtME]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện
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
// b. Thực hiện tìm tất cả các số nguyên tố trong khoảng từ 1 đến N. Ví dụ: Với N = 10, kết quả là
// danh sách các số nguyên tố “2, 3, 5, 7”.
// c. Triệu gọi phương thức submitData để gửi List< Integer> danh sách các số nguyên tố đã tìm được
// trở lại server.
// d. Kết thúc chương trình client.




package RMI.B21DCCN015;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import RMI.DataService;
public class LietKeSoNguyenTo {
    public static void main(String[] args) throws Exception {
        // a. Nhận dữ liệu
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        DataService sv = (DataService) rg.lookup("RMIDataService");
        int N = (int) sv.requestData("B21DCCN015", "nD8MdtME");
        System.out.println(N);
        // b. Xử lý: Tìm list các SNT
        List<Integer> res = new ArrayList<>();
        for(int i = 2;i<=N;i++){
            if(check(i)==1) res.add(i);
        }
        System.out.println(res);
        // c. Gửi kết quả
        sv.submitData("B21DCCN015", "NMATI6Zw", res);
    }
    public static int check(int n){//ktra snt
        for(int i = 2;i*i<=n;i++){
            if(n%i==0) return 0;
        }
        return 1;
    }
}
