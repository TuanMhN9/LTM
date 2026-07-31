// BÀI 3. [Byte] PHẦN TỬ XUẤT HIỆN NHIỀU LẦN NHẤT
// [Mã câu hỏi (qCode): 1mPMIkGJ]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện
// cho phép triệu gọi từ xa để xử lý dữ liệu nhị phân.
// Giao diện từ xa:
// public interface ByteService extends Remote {
// public byte[] requestData(String studentCode, String qCode) throws RemoteException;
// public void submitData(String studentCode, String qCode, byte[] data) throws
// RemoteException;
// }
// Trong đó:
// • Interface ByteService được viết trong package RMI.
// Đối tượng cài đặt giao diện từ xa ByteService được đăng ký với RegistryServer với tên là:
// RMIByteService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu
// nhị phân nhận được từ RMI Server:
// a. Triệu gọi phương thức requestData để nhận một mảng dữ liệu nhị phân (byte[]) từ server.
// b. Tìm phần tử xuất hiện nhiều nhất trong mảng byte[]. Nếu có nhiều phần tử có cùng số lần xuất
// hiện cao nhất, chỉ cần trả về phần tử đầu tiên xuất hiện trong các phần tử đó.
// Ví dụ: Nếu mảng dữ liệu nhận được là [1, 2, 3, 2, 1, 2], phần tử xuất hiện nhiều nhất là 2, với tần
// suất xuất hiện 3 lần.
// c. Triệu gọi phương thức submitData để gửi mảng byte chứa phần tử phổ biến nhất, cùng với tần
// suất xuất hiện của nó trở lại server.
// d. Kết thúc chương trình client

package RMI.B21DCCN319;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import RMI.ByteService;
public class XHMax {
    public static void main(String[] args) throws Exception {
        // a. Nhận dữ liệu từ server
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        ByteService sv = (ByteService) rg.lookup("RMIByteService");
        byte[] a = sv.requestData("B21DCCN319", "1mPMIkGJ");
        for(byte x: a) System.out.print(x + " ");
        System.out.println("");
        //b. Đếm
        int[] cnt = new int[128]; 
        for (byte x : a) cnt[x]++;
        byte ptuMax = a[0];
        int slMax = cnt[ptuMax];
        for (byte x : a) {
            if (cnt[x] > slMax) {
                ptuMax = x;
                slMax = cnt[x];
            }
        }
        System.out.println(ptuMax + " " + slMax);
        // c. Gửi
        byte[] res = {ptuMax, (byte) slMax};
        sv.submitData("B21DCCN319", "1mPMIkGJ", res);
    }
}
