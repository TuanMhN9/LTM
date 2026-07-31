// BÀI 2. [Character] XỬ LÝ XÂU KÝ TỰ
// [Mã câu hỏi (qCode): NFldNPp6]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện
// cho phép triệu gọi từ xa để xử lý chuỗi.
// Giao diện từ xa:
// public interface CharacterService extends Remote {
// public String requestCharacter(String studentCode, String qCode) throws
// RemoteException;
// public void submitCharacter(String studentCode, String qCode, String strSubmit) throws
// RemoteException;
// }
// Trong đó:
// • Interface CharacterService được viết trong package RMI.
// • Đối tượng cài đặt giao diện từ xa CharacterService được đăng ký với RegistryServer với tên
// là: RMICharacterService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với chuỗi
// được nhận từ RMI Server:
// Luyện tập RMI
// _____________________________________________________________________________________________
// Trang 3
// a. Triệu gọi phương thức requestCharacter để nhận chuỗi JSON ngẫu nhiên từ server với định dạng:
// "Chuỗi JSON đầu vào".
// b. Phân tích cú pháp chuỗi JSON nhận được và trích xuất các cặp key: value dựa trên vị trí của
// chúng:
// • Các cặp key: value ở vị trí chẵn sẽ được đưa vào chuỗi đầu tiên.
// • Các cặp key: value ở vị trí lẻ sẽ được đưa vào chuỗi thứ hai.
// • Hai chuỗi kết quả sẽ được nối với nhau và phân tách bởi dấu ;
// Ví dụ: Chuỗi JSON ban đầu {"name": "Alice", "age": 25, "city": "Wonderland", "country":
// "Fictionland"} → Kết quả trích xuất: "name: Alice, city: Wonderland; age: 25, country:
// Fictionland".
// c. Triệu gọi phương thức submitCharacter để gửi chuỗi kết quả trích xuất đã được định dạng trở lại
// server.
// d. Kết thúc chương trình client.


package RMI.B21DCCN319;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.*;
import RMI.CharacterService;
public class XuLyVanBan {
    public static void main(String[] args) throws Exception {
        // a. Nhận chuỗi từ server
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        CharacterService sv = (CharacterService) rg.lookup("RMICharacterService");
        String s = sv.requestCharacter("B21DCCN319", "NFldNPp6");
        System.out.println(s);
        // b. Xử lý xâu
        s = s.replace("\"", "");  s = s.replace("{", "");  s = s.replace("}", "");   
        String[] tmp = s.trim().split(",");// Tách chuỗi thành các cặp key-value
        String chan = "", le = "";
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = tmp[i].trim(); 
            if (i % 2 == 0) {
                if (chan.length() > 0) chan+=", ";
                chan+=tmp[i];
            } else {
                if (le.length() > 0) le+=", ";
                le+=tmp[i];
            }
        }
        String res = chan + "; " + le;
        System.out.println(res);
        // c. Gửi kết quả lại server
        sv.submitCharacter("B21DCCN319", "NFldNPp6", res);
    }
}
