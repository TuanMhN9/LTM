

// BÀI 1. [Data] PHƯƠNG SAI – ĐỘ LỆCH CHUẨN
// [Mã câu hỏi (qCode): uZMEY3Zg]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện
// cho phép triệu gọi từ xa để xử lý dữ liệu.
// Giao diện từ xa:
// public interface DataService extends Remote {
// public Object requestData(String studentCode, String qCode) throws RemoteException;
// public void submitData(String studentCode, String qCode, Object data) throws RemoteException;
// }
// Trong đó:
// • Interface DataService được viết trong package RMI.
// • Đối tượng cài đặt giao diện từ xa DataService được đăng ký với RegistryServer với tên là:
// RMIDataService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu
// nhận được từ RMI Server:
// a. Triệu gọi phương thức requestData để nhận một chuỗi các tập hợp các số thực từ server.
// b. Tính toán phương sai (variance) và độ lệch chuẩn (standard deviation) của tập hợp dữ liệu này
// (làm tròn tới 02 chữ số thập phân)
// Ví dụ: Với tập dữ liệu nhận được “2.0, 4.0, 4.0, 4.0, 5.0, 5.0, 7.0, 9.0”, phương sai là 4.00 và độ
// lệch chuẩn là 2.00
// c. Triệu gọi phương thức submitData để gửi chuỗi chứa kết quả phương sai và độ lệch chuẩn trở lại
// server dưới dạng một cặp giá trị “variance : standard deviation”
// d. Kết thúc chương trình client.


package RMI.B21DCCN016;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import RMI.DataService;
public class PhuongSai {
    public static void main(String[] args) throws Exception {
        // a. Nhận dữ liệu
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        DataService sv = (DataService) rg.lookup("RMIDataService");
        String s = (String) sv.requestData("B21DCCN016", "uZMEY3Zg");
        System.out.println(s);
        // b. Xử lý: Tìm danh sách các bộ ba số Pythagore
        ArrayList<Double> a = new ArrayList<>();
        s = s.replace(",", " ");
        String[] tmp = s.trim().split("\\s+");
        for (String x : tmp) a.add(Double.parseDouble(x));
        int n = a.size(); 
        double tong = 0, tongTmp = 0;
        for (double x : a) tong += x;
        double tbc = tong / (double) n;
        for (double x : a) tongTmp += (x - tbc) * (x - tbc);
        double pSai = tongTmp / n;  
        double doLechChuan = Math.sqrt(pSai);
        System.out.println(pSai);
        System.out.println(doLechChuan);
        // c. Gửi kết quả
        String res = String.format("%.2f : %.2f", pSai, doLechChuan);
        System.out.println(res);
        sv.submitData("B21DCCN016", "uZMEY3Zg", res);
    }
}