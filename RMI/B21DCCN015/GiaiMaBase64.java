// BÀI 3. [Byte] GIẢI MÃ
// [Mã câu hỏi (qCode): oeJljCIf]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho
// phép triệu gọi từ xa để xử lý dữ liệu nhị phân.
// Giao diện từ xa:
// public interface ByteService extends Remote {
// public byte[] requestData(String studentCode, String qCode) throws RemoteException;
// public void submitData(String studentCode, String qCode, byte[] data) throws RemoteException;
// }
// Trong đó:
// • Interface ByteService được viết trong package RMI.
// • Đối tượng cài đặt giao diện từ xa ByteService được đăng ký với RegistryServer với tên là:
// RMIByteService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu
// nhị phân đã mã hóa dưới dạng Base64 nhận được từ RMI Server:
// a. Triệu gọi phương thức requestData để nhận một mảng dữ liệu nhị phân (byte[]) từ server, trong
// đó mảng này chứa một chuỗi Base64 được mã hóa.
// b. Thực hiện giải mã dữ liệu nhận được từ định dạng Base64 về dữ liệu gốc. Sử dụng giải mã
// Base64 để chuyển mảng byte Base64 thành mảng byte nguyên bản ban đầu.
// Luyện tập RMI
// _____________________________________________________________________________________________
// Trang 16
// Ví dụ: Nếu dữ liệu Base64 nhận được là chuỗi mã hóa SGVsbG8gV29ybGQ= (tương ứng với chuỗi
// "Hello World" trong ASCII), chương trình sẽ thực hiện giải mã để nhận được mảng dữ liệu nhị
// phân [72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100].
// c. Triệu gọi phương thức submitData để gửi mảng dữ liệu gốc đã giải mã trở lại server.
// d. Kết thúc chương trình client.




package RMI.B21DCCN015;
import RMI.ByteService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Base64;
public class GiaiMaBase64 {
    public static void main(String[] args) throws Exception{
        // a. Nhận dữ liệu từ server
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        ByteService sv = (ByteService) rg.lookup("RMIByteService");
        byte[] a = sv.requestData("B21DCCN015", "oeJljCIf");
        // b. Giải mã 
        String s = new String(a);  //Byte -->String
        System.out.println(s);
        byte[] ans = Base64.getDecoder().decode(s);  // Giải mã Base64
        for (byte x: ans) System.out.print(x + " ");
        System.out.println();
        // c. 
        sv.submitData("B21DCCN015", "oeJljCIf", ans);
    }
}


