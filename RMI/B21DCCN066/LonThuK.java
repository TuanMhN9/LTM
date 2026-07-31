// BÀI 3. [Byte] PHẦN TỬ LỚN THỨ K
// [Mã câu hỏi (qCode): uIKHCTWG]. Một chương trình (tạm gọi là RMI Server) cung cấp giao
// diện cho phép triệu gọi từ xa để xử lý dữ liệu nhị phân.
// Giao diện từ xa:
// public interface ByteService extends Remote {
// public byte[] requestData(String studentCode, String qCode) throws RemoteException;
// public void submitData(String studentCode, String qCode, byte[] data) throws RemoteException;
// }
// Trong đó:
// • Interface ByteService được viết trong package RMI.
// Đối tượng cài đặt giao diện từ xa ByteService được đăng ký với RegistryServer với tên là:
// RMIByteService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu
// nhị phân nhận được từ RMI Server:
// a. Triệu gọi phương thức requestData để nhận một mảng dữ liệu nhị phân (byte[]) từ server, cùng
// với một số nguyên K, đại diện cho thứ tự phần tử cần tìm.
// Luyện tập RMI
// _____________________________________________________________________________________________
// Trang 30
// b. Tìm phần tử lớn thứ K trong mảng byte[] với K là phần tử cuối cùng của mảng.
// Ví dụ: Nếu mảng dữ liệu nhận được là [3, 1, 5, 12, 2, 11, 3], giá trị K = 3, chương trình sẽ tìm phần
// tử lớn thứ ba → Kết quả là 5.
// c. Triệu gọi phương thức submitData để gửi mảng byte gồm phần tử lớn thứ K và vị trí của K trở
// lại server.
// d. Kết thúc chương trình client.




package RMI.B21DCCN066;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import RMI.ByteService;
public class LonThuK {
    public static void main(String[] args) throws Exception{
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        ByteService sv = (ByteService) rg.lookup("RMIByteService");
        byte[] a = sv.requestData("B21DCCN066", "uIKHCTWG");
        for(byte x: a) System.out.print(x + " ");  
        System.out.println();
        //b.
        byte []b = Arrays.copyOf(a, a.length);
        int k = a[a.length - 1], pos = 0;
        Arrays.sort(a);
        for(int i = 0;i<b.length;i++){
            if(b[i]==a[a.length - k]){
                pos = i;
                break;
            }
        }
        byte []ans = {(byte)a[a.length - k], (byte)(pos + 1)};
        for(byte x: ans) System.out.print(x + " ");
        //c.
        sv.submitData("B21DCCN066", "uIKHCTWG", ans);
    }
}
