// BÀI 3. [Byte] NÉN RLE
// [Mã câu hỏi (qCode): 2uG0lQGi]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện
// cho phép triệu gọi từ xa để xử lý dữ liệu nhị phân.
// Giao diện từ xa:
// public interface ByteService extends Remote {
// public byte[] requestData(String studentCode, String qCode) throws RemoteException;
// public void submitData(String studentCode, String qCode, byte[] data) throws RemoteException;
// }
// Trong đó:
// • Interface ByteService được viết trong package RMI.
// Luyện tập RMI
// _____________________________________________________________________________________________
// Trang 26
// • Đối tượng cài đặt giao diện từ xa ByteService được đăng ký với RegistryServer với tên là:
// RMIByteService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu
// byte nhận được từ RMI Server:
// a. Triệu gọi phương thức requestData để nhận một mảng dữ liệu byte từ server, đại diện cho một
// chuỗi dữ liệu byte tổng quát.
// b. Thực hiện nén dữ liệu sử dụng thuật toán Run-Length Encoding (RLE) bằng cách ghi nhận mỗi
// byte và số lần lặp liên tiếp của nó. Kết quả nén là một mảng mới biểu diễn các cặp (byte, số lần
// lặp).
// Ví dụ: Nếu dữ liệu byte nhận được là [10, 10, 10, 20, 20, 30, 30, 30, 30], chương trình sẽ thực
// hiện nén RLE như sau:
//  Kết quả nén RLE là mảng [10, 3, 20, 2, 30, 4].
// c. Triệu gọi phương thức submitData để gửi mảng dữ liệu đã được nén RLE trở lại server.
// d. Kết thúc chương trình client.




package RMI.B21DCCN036;
import java.util.*;
import java.rmi.*;
import java.rmi.registry.*;
import RMI.ByteService;
public class NenRLE {
    public static void main(String[] args) throws Exception{
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        ByteService sv = (ByteService) rg.lookup("RMIByteService");
        byte[] a = sv.requestData("B21DCCN036", "2uG0lQGi");
        for (byte x : a) System.out.print(x + " ");
        System.out.println();
        // b. 
        int cnt = 1;
        ArrayList<Byte>res = new ArrayList<>();
        for(int i = 1;i<a.length;i++){
            if(a[i] == a[i - 1]) cnt++;
            else{
                res.add(a[i - 1]); res.add((byte)cnt);
                cnt = 1;
            }
        }
        res.add(a[a.length - 1]); res.add((byte)cnt);
        //Cóp sang mảng
        byte []ans = new byte[res.size()];
        int idx = 0;
        for(byte x: res) ans[idx++] = x;
        for(byte x: ans) System.out.print(x + " ");
        sv.submitData("B21DCCN036", "2uG0lQGi", ans);        
    }
}
