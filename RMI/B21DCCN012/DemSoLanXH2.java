// BÀI 2. [Character] ĐẾM SỐ LẦN XUẤT HIỆN - 2
// [Mã câu hỏi (qCode): ctRfIejL]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho
// phép triệu gọi từ xa để xử lý chuỗi.
// Giao diện từ xa:
// public interface CharacterService extends Remote {
// public String requestCharacter(String studentCode, String qCode) throws
// RemoteException;
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
// "Chuỗi văn bản đầu vào".
// b. Thực hiện thao tác đếm tần số xuất hiện của từng ký tự trong chuỗi đầu vào. Kết quả trả về là
// danh sách các ký tự kèm theo số lần xuất hiện của mỗi ký tự.
// Ví dụ: Chuỗi ban đầu "Hello world" -> Kết quả đếm tần số ký tự: {"H": 1, "e": 1, "l": 3, "o": 2, "
// ": 1, "w": 1, "r": 1, "d": 1}.
// c. Triệu gọi phương thức submitCharacter để gửi kết quả đếm tần số ký tự trở lại server dưới dạng
// chuỗi kết quả đã được định dạng.
// d. Kết thúc chương trình client.



package RMI.B21DCCN012;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import RMI.CharacterService;
public class DemSoLanXH2 {
    public static void main(String[] args) throws Exception {
        // a. Nhận chuỗi từ server
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        CharacterService sv = (CharacterService) rg.lookup("RMICharacterService");
        String s = sv.requestCharacter("B21DCCN012", "ctRfIejL");
        System.out.println(s);
        int[] cnt = new int[256]; 
        for (char x : s.toCharArray()) cnt[x]++;
        String res = "";
        boolean first = true;
        for (char x : s.toCharArray()) {
            if (cnt[x] > 0) {
                if (!first) res+=", ";
                res+=String.format("\"%c\": %d", x, cnt[x]); 
                cnt[x] = 0; 
                first = false;
            }
        }
        res = "{" + res + "}";
        System.out.println(res);
        // c. Gửi kết quả lại server
        sv.submitCharacter("B21DCCN012", "ctRfIejL", res);
    }
}
