/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): LcNBgzzN].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý chuỗi.
//Giao diện từ xa:
//public interface CharacterService extends Remote {
//public String requestCharacter(String studentCode, String qCode) throws RemoteException;
//public void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
//}
//Trong đó:
//•	Interface CharacterService được viết trong package RMI.
//•	Đối tượng cài đặt giao diện từ xa CharacterService được đăng ký với RegistryServer với tên là: RMICharacterService.
//Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với chuỗi được nhận từ RMI Server:
//a. Triệu gọi phương thức requestCharacter để nhận chuỗi ngẫu nhiên từ server với định dạng: "Chuỗi đầu vào".
//b. Thực hiện đếm tần số xuất hiện của mỗi ký tự trong chuỗi đầu vào và tạo ra chuỗi kết quả theo định dạng <Ký tự><Số lần xuất hiện>, sắp xếp theo thứ tự xuất hiện của các ký tự trong chuỗi.
//Ví dụ: Chuỗi đầu vào "AAABBC" -> Kết quả: "A3B2C1".
//c. Triệu gọi phương thức submitCharacter để gửi chuỗi kết quả trở lại server.
//d. Kết thúc chương trình client.

package Client;

import RMI.CharacterService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedHashMap;
import java.util.Map;

public class CHARACTER_DEM_TAN_SO_XUAT_HIEN_CUA_KY_TU {

    public static void main(String[] args) {
        String studentCode = "B22DCCN760"; // đổi MSSV
        String qCode = "LcNBgzzN";

        try {
            /* ===== 1. Kết nối RMI ===== */
            Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);
            CharacterService service =
                    (CharacterService) registry.lookup("RMICharacterService");

            /* ===== 2. Nhận chuỗi ===== */
            String input = service.requestCharacter(studentCode, qCode);

            /* ===== 3. Đếm tần số ký tự ===== */
            Map<Character, Integer> freq = new LinkedHashMap<>();

            for (char c : input.toCharArray()) {
                freq.put(c, freq.getOrDefault(c, 0) + 1);
            }

            /* ===== 4. Tạo chuỗi kết quả ===== */
            StringBuilder result = new StringBuilder();
            for (Map.Entry<Character, Integer> e : freq.entrySet()) {
                result.append(e.getKey()).append(e.getValue());
            }

            /* ===== 5. Gửi kết quả ===== */
            service.submitCharacter(studentCode, qCode, result.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
