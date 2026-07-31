// BÀI 1. [Data] LIỆT KÊ SỐ ĐỐI XỨNG
// [Mã câu hỏi (qCode): t3cBY2uk]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện
// cho phép triệu gọi từ xa để xử lý dữ liệu.
// Giao diện từ xa:
// public interface DataService extends Remote {
// public Object requestData(String studentCode, String qCode) throws RemoteException;
// public void submitData(String studentCode, String qCode, Object data) throws RemoteException;
// Luyện tập RMI
// _____________________________________________________________________________________________
// Trang 17
// }
// Trong đó:
// • Interface DataService được viết trong package RMI.
// • Đối tượng cài đặt giao diện từ xa DataService được đăng ký với RegistryServer với tên là:
// RMIDataService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu
// nhận được từ RMI Server:
// a. Triệu gọi phương thức requestData để nhận hai số nguyên dương N và K từ server, đại diện cho
// khoảng cần kiểm tra (N ≤ số < K).
// b. Xác định tất cả các số nguyên đối xứng (Palindrome Number) trong khoảng từ N đến K. Kết quả
// trả về là danh sách các số đối xứng thỏa mãn yêu cầu.
// Ví dụ: Với N = 50 và K = 150, kết quả là [55, 66, 77, 88, 99, 101, 111, 121, 131, 141].
// c. Triệu gọi phương thức submitData để gửi đối tượng List<Integer> danh sách các số nguyên đối
// xứng đã tìm được trở lại server.
// d. Kết thúc chương trình client.




package RMI.B21DCCN021;
import RMI.DataService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
public class LietKeSoDoiXung {
    public static boolean check(int x){
        String s = x+"";
        for(int i = 0;i<=s.length()/2;i++){
            if(s.charAt(i)!=s.charAt(s.length() - i - 1)) return false;
        }
        return true;
    }
    public static void main(String[] args) throws Exception{
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        DataService sv = (DataService) rg.lookup("RMIDataService");
        String s = (String) sv.requestData("B21DCCN021", "t3cBY2uk");
        System.out.println(s);
        s = s.replace(";", "");
        String []tmp = s.trim().split("\\s+");
        int n = Integer.parseInt(tmp[0].trim()), k = Integer.parseInt(tmp[1].trim());
        List<Integer>a = new ArrayList<>();
        for(int i = n;i<k;i++){
            if(check(i)) a.add(i);
        }
        System.out.println(a);
        sv.submitData("B21DCCN021", "t3cBY2uk", a);
    }
}
