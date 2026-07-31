// BÀI 2. [Character] MÃ HOÁ VIGENERE
// [Mã câu hỏi (qCode): Y7YMXHs4]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện
// cho phép triệu gọi từ xa để xử lý chuỗi.
// Giao diện từ xa:
//  public interface CharacterService extends Remote {
//  public String requestCharacter(String studentCode, String qCode) throws RemoteException;
//  public void submitCharacter(String studentCode, String qCode, String strSubmit) throws
// RemoteException;
//  }
// Trong đó:
// • Interface CharacterService được viết trong package RMI.
// • Đối tượng cài đặt giao diện từ xa CharacterService được đăng ký với RegistryServer với tên
// là: RMICharacterService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với chuỗi
// được nhận từ RMI Server:
// a. Triệu gọi phương thức requestCharacter để nhận chuỗi ngẫu nhiên từ server với định dạng: "Từ
// khóa;Chuỗi đầu vào"
// Luyện tập RMI
// _____________________________________________________________________________________________
// Trang 18
// b. Thực hiện thao tác mã hóa Vigenère cho chuỗi nhận được. Biết rằng, mã hóa Vigenère thực hiện
// mã hóa mỗi ký tự trong chuỗi đầu vào được dịch đi một khoảng bằng với vị trí tương ứng của ký
// tự trong từ khóa. (Từ khóa được lặp lại để khớp với độ dài của chuỗi)
// Ví dụ: chuỗi ban đầu "PTIT;HELLO" → từ khóa "PTIT" và chuỗi mã hóa là: "WXTED"
// c. Triệu gọi phương thức submitCharacter để gửi chuỗi đã được mã hóa trở lại server.
// d. Kết thúc chương trình client.




package RMI.B21DCCN021;
import java.util.*;
import java.rmi.*;
import java.rmi.registry.*;
import RMI.CharacterService;
public class MaHoaVigen {
    public static void main(String[] args) throws Exception{
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        CharacterService sv = (CharacterService) rg.lookup("RMICharacterService");
        String s = sv.requestCharacter("B21DCCN021", "Y7YMXHs4");
        System.out.println(s);
        int idx = s.indexOf(";");
        String keyW = s.substring(0, idx), text = s.substring(idx + 1), ans = "";
        for(int i = 0;i<text.length();i++){
            char x = text.charAt(i), y = keyW.charAt(i % keyW.length());
            char z;
            if (Character.isUpperCase(x)) z = (char) ((x - 'A' + y - 'A') % 26 + 'A');
            else z = (char) ((x - 'a' + y - 'a') % 26 + 'a');
            ans+=z;
        }
        System.out.println(ans);
        sv.submitCharacter("B21DCCN021", "Y7YMXHs4", ans);
    } 
}
