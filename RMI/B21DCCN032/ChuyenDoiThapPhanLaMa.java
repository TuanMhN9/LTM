// BÀI 2. [Character] CHUYỂN ĐỔI THẬP PHÂN – LA MÃ
// [Mã câu hỏi (qCode): ADu6zRYE]. Một chương trình (tạm gọi là RMI Server) cung cấp giao
// diện cho phép triệu gọi từ xa để xử lý chuỗi.
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
// a. Triệu gọi phương thức requestCharacter để nhận chuỗi ngẫu nhiên từ server với định dạng: "Số
// thập phân đầu vào".
// b. Thực hiện chuyển đổi số thập phân nhận được thành chuỗi số La Mã (Roman).
// Luyện tập RMI
// _____________________________________________________________________________________________
// Trang 25
// Quy tắc chuyển đổi:
//  Các ký tự La Mã chính bao gồm: I=1, V=5, X=10, L=50, C=100, D=500, M=1000.
//  Ví dụ: 58 → "LVIII".
// c. Triệu gọi phương thức submitCharacter để gửi chuỗi số La Mã đã chuyển đổi trở lại server.
// d. Kết thúc chương trình client.




package RMI.B21DCCN032;
import RMI.CharacterService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class ChuyenDoiThapPhanLaMa {
    public static void main(String[] args)throws Exception{
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        CharacterService sv = (CharacterService) rg.lookup("RMICharacterService");
        String s = sv.requestCharacter("B21DCCN032", "ADu6zRYE");
        System.out.println(s);
        //b.
        String rm = "";
        int dec = Integer.parseInt(s);
        int[] tp = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};//thập phân
        String[] lm = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};// la mã
        for(int i = 0;i<tp.length;i++){
            while(dec >=tp[i]){
                rm+=lm[i];
                dec-=tp[i];
            }
        }
        System.out.println(rm);     
        //c.
        sv.submitCharacter("B21DCCN032", "ADu6zRYE", rm);
    }
}
