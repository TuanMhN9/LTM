// BÀI 3. [Byte] CHUYỂN ĐỔI HỆ BÁT PHÂN
// [Mã câu hỏi (qCode): HhPAxeDw]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện
// cho phép triệu gọi từ xa để xử lý dữ liệu nhị phân.
// Giao diện từ xa:
// public interface ByteService extends Remote {
// public byte[] requestData(String studentCode, String qCode) throws RemoteException;
// public void submitData(String studentCode, String qCode, byte[] data) throws
// RemoteException;
// }
// Trong đó:
// • Interface ByteService được viết trong package RMI.
// • Đối tượng cài đặt giao diện từ xa ByteService được đăng ký với RegistryServer với tên là:
// RMIByteService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu
// nhị phân nhận được từ RMI Server:
// a. Triệu gọi phương thức requestData để nhận một mảng dữ liệu nhị phân (byte[]) từ server.
// b. Chuyển đổi mỗi byte trong mảng dữ liệu nhận được thành chuỗi biểu diễn số bát phân (octal).
// Mỗi byte sẽ được chuyển thành một chuỗi số bát phân gồm ba chữ số (nếu cần, thêm các chữ số 0
// ở đầu để đảm bảo đúng định dạng).
// Ví dụ: Nếu mảng dữ liệu nhận được là [72, 101, 108], chương trình sẽ thực hiện chuyển đổi từng
// byte thành dạng bát phân:
// • 72 → "110"
// • 101 → "145"
// • 108 → "154"
// Kết quả là chuỗi octal "110145154". Chuyển chuỗi bát phân này trở lại thành mảng byte[] (mỗi ký
// tự trong chuỗi octal được mã hóa dưới dạng ASCII byte) để gửi về server.
// c. Triệu gọi phương thức submitData để gửi mảng byte[] chứa chuỗi bát phân trở lại server.
// d. Kết thúc chương trình client.



package RMI.B21DCCN008;
import java.rmi.*;
import java.rmi.registry.*;
import RMI.ByteService;
public class ChuyenDoiBatPhan {
    public static void main(String[] args) throws Exception {
        // a. Nhận dữ liệu từ server
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        ByteService sv = (ByteService) rg.lookup("RMIByteService");
        byte[] a = sv.requestData("B21DCCN008", "HhPAxeDw");
        for (byte x : a) System.out.print(x + " ");
        System.out.println();
        // b. Chuyển đổi mảng byte thành chuỗi bát phân
        String res = "";
        for (byte x : a) res+=String.format("%03o", x & 0xFF);
        System.out.println(res);
        byte[] ans = res.toString().getBytes();
        sv.submitData("B21DCCN008", "HhPAxeDw", ans);
    }
}

