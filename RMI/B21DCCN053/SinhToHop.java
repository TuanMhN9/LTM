// BÀI 1. [Data] SINH TỔ HỢP
// [Mã câu hỏi (qCode): juC3u7C6]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện
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
// a. Triệu gọi phương thức requestData để nhận một chuỗi gồm tập hợp số nguyên và một số nguyên
// K từ server với định dạng: “mảng; số nguyên K”.
// b. Sử dụng thuật toán sinh tổ hợp để tạo ra tất cả các tổ hợp kích thước K của tập hợp đã cho. Kết
// quả trả về là danh sách các tổ hợp con có K phần tử.
// Ví dụ: Với tập hợp [1, 2, 3, 4] và K = 2, kết quả là danh sách các tổ hợp [[1, 2], [1, 3], [1, 4], [2, 3],
// [2, 4], [3, 4]].
// c. Triệu gọi phương thức submitData để gửi chuỗi chứa danh sách các tổ hợp đã sinh được trở lại
// server.
// d. Kết thúc chương trình client.



package RMI.B21DCCN053;
import RMI.DataService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
public class SinhToHop {
    static int ok; 
    public static void kt(int a[], int k) {
        for (int i = 1; i <= k; i++) a[i] = i;
    }
    public static void sinh(int a[], int n, int k) {
        int i = k;
        while (i >= 1 && a[i] == n - k + i) i--;
        if (i == 0) {
            ok = 0;
        } else {
            a[i]++;
            for (int j = i + 1; j <= k; j++) a[j] = a[j - 1] + 1;
        }
    }
    public static void main(String[] args) throws Exception{
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        DataService sv = (DataService) rg.lookup("RMIDataService");
        String s = (String)sv.requestData("B21DCCN053", "juC3u7C6");
        //String s = "2, 0, 5, 8, 1 ;3"; 
        System.out.println(s);
        int idx = s.indexOf(";");
        String s1 = s.substring(0, idx), s2 = s.substring(idx + 1);
        int k = Integer.parseInt(s2.trim());// Lấy số k
        // Lấy ra mảng và sắp xếp
        ArrayList<Integer> a = new ArrayList<>();
        s1 = s1.replace(",", " ");
        String[] tmp = s1.trim().split("\\s+");
        for (String x : tmp) a.add(Integer.parseInt(x));
        // Bắt đầu sinh tổ hợp. Khởi tạo
        int n = a.size();
        int[] genIdx = new int[k + 1];
        kt(genIdx, k);
        ok = 1;
        // Duyệt qua từng tổ hợp chỉ số
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        while (ok == 1) {
            ArrayList<Integer> tmp1 = new ArrayList<>();
            for (int i = 1; i <= k; i++) tmp1.add(a.get(genIdx[i] - 1));
            ans.add(tmp1);
            sinh(genIdx, n, k);
        }
        //c.
        System.out.println(ans);
        sv.submitData("B21DCCN053", "juC3u7C6", ans);
    }
}
