// BÀI 2. [Character] MÃ HOÁ Base64
// [Mã câu hỏi (qCode): psd4Jmnt]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện
// cho phép triệu gọi từ xa để xử lý chuỗi.
// Giao diện từ xa:
// public interface CharacterService extends Remote {
// public String requestCharacter(String studentCode, String qCode) throws RemoteException;
// public void submitCharacter(String studentCode, String qCode, String strSubmit) throws
// RemoteException;
// }
// Trong đó:
// • Interface CharacterService được viết trong package RMI.
// • Đối tượng cài đặt giao diện từ xa CharacterService được đăng ký với RegistryServer với tên là:
// RMICharacterService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với chuỗi
// được nhận từ RMI Server:
// a. Triệu gọi phương thức requestCharacter để nhận chuỗi ngẫu nhiên từ server với định dạng:
// "Chuỗi đầu vào".
// b. Thực hiện thao tác mã hóa Base64 cho chuỗi đầu vào nhận được từ server. Mã hóa Base64
// chuyển đổi chuỗi nhị phân thành định dạng văn bản ASCII bằng cách mã hóa mỗi nhóm 6 bit của
// chuỗi thành một ký tự.
// Ví dụ: Chuỗi ban đầu "HELLO" → Chuỗi mã hóa Base64 là: "SEVMTE8="
// c. Triệu gọi phương thức submitCharacter để gửi chuỗi đã được mã hóa trở lại server.
// d. Kết thúc chương trình client.




package RMI.B21DCCN029;
import RMI.CharacterService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Base64;
public class MaHoaBase64 {
    public static void main(String[] args) throws Exception{
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        CharacterService sv = (CharacterService) rg.lookup("RMICharacterService");
        String s = sv.requestCharacter("B21DCCN029", "psd4Jmnt");
        System.out.println(s);
        //
        byte []tmp = Base64.getEncoder().encode(s.getBytes());
        String ans = new String(tmp);
        System.out.println(ans);
        //
        sv.submitCharacter("B21DCCN029", "psd4Jmnt", ans);
    }
}
