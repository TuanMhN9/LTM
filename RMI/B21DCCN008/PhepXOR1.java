// BÀI 2 [Character] PHÉP XOR - 1
// [Mã câu hỏi (qCode): Xidb0Thk]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện
// cho phép triệu gọi từ xa để xử lý chuỗi.
// Giao diện từ xa:
// public interface CharacterService extends Remote {
// public String requestCharacter(String studentCode, tring qCode) throws RemoteException;
// public void submitCharacter(String studentCode, String qCode, String strSubmit) throws
// RemoteException;
// }
// Trong đó:
// • Interface CharacterService được viết trong package RMI.
// • Đối tượng cài đặt giao diện từ xa CharacterService được đăng ký với RegistryServer với tên là:
// RMICharacterService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với chuỗi
// được nhận từ RMI Server:
// a. Triệu gọi phương thức requestCharacter để nhận chuỗi ngẫu nhiên từ server với định dạng: "Khóa
// XOR;Chuỗi đầu vào". 
// Luyện tập RMI
// _____________________________________________________________________________________________
// Trang 9
// b. Thực hiện thao tác mã hóa XOR cho chuỗi đầu vào với khóa XOR nhận được từ server. Mã hóa
// XOR thực hiện bằng cách áp dụng phép XOR trên từng ký tự trong chuỗi đầu vào và ký tự tương
// ứng trong khóa (khóa được lặp lại để khớp độ dài chuỗi đầu vào).
// Ví dụ: Chuỗi ban đầu "A;HELLO" -> Khóa "A" → chuỗi mã hóa là: "IFMMN"
// c. Triệu gọi phương thức submitCharacter để gửi chuỗi đã được mã hóa trở lại server.
// d. Kết thúc chương trình client.



package RMI.B21DCCN008;
import RMI.CharacterService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class PhepXOR1 {
    public static void main(String[] args) throws Exception{
        // a. 
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        CharacterService sv = (CharacterService) rg.lookup("RMICharacterService");
        String s = sv.requestCharacter("B21DCCN008", "Xidb0Thk");
        System.out.println(s);
        // b. Xử lý xâu
        String []tmp = s.split(";");
        String xorKey =tmp[0], inp = tmp[1], res = "";
        for(int i = 0;i<inp.length();i++) res+=(char)(inp.charAt(i) ^ xorKey.charAt(i % xorKey.length()));
        System.out.println(res);
        // c. Gửi kết quả lại server
        sv.submitCharacter("B21DCCN008", "Xidb0Thk", res);
    }
}

