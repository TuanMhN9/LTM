/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): ETuYOyn0].  Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý chuỗi.
//Giao diện từ xa:
//public interface CharacterService extends Remote {
//public String requestCharacter(String studentCode, String qCode) throws RemoteException;
//public void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
//}
//Trong đó:
//• Interface CharacterService được viết trong package RMI.
//• Đối tượng cài đặt giao diện từ xa CharacterService được đăng ký với RegistryServer với tên là: RMICharacterService.
//Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với chuỗi được nhận từ RMI Server:
//a. Triệu gọi phương thức requestCharacter để nhận chuỗi ngẫu nhiên từ server với định dạng: "Chuỗi văn bản đầu vào".
//b. Thực hiện thao tác đếm tần số xuất hiện của từng ký tự trong chuỗi đầu vào. Kết quả trả về là danh sách các ký tự kèm theo số lần xuất hiện của mỗi ký tự.
//Ví dụ: Chuỗi ban đầu "Hello world" -> Kết quả đếm tần số ký tự: {"H": 1, "e": 1, "l": 3, "o": 2, " ": 1, "w": 1, "r": 1, "d": 1}.
//c. Triệu gọi phương thức submitCharacter để gửi kết quả đếm tần số ký tự trở lại server dưới dạng chuỗi kết quả đã được định dạng.
//d. Kết thúc chương trình client.
package Client;

import RMI.CharacterService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedHashMap;
import java.util.Map;

public class CHARACTER_TAN_SO_XUAT_HIEN {

    public static void main(String[] args) {

        String studentCode = "B22DCCN088"; // đổi MSSV
        String qCode = "ETuYOyn0";

        try {
            /* ===== 1. Kết nối RMI ===== */
            Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);
            CharacterService service
                    = (CharacterService) registry.lookup("RMICharacterService");

            /* ===== 2. Nhận chuỗi từ server ===== */
            String input = service.requestCharacter(studentCode, qCode);

            LinkedHashMap<Character, Integer> freq = new LinkedHashMap<>();

            for (char c : input.toCharArray()) {
                freq.put(c, freq.getOrDefault(c, 0) + 1);
            }

            StringBuilder result = new StringBuilder();
            result.append("{");

            int count = 0;
            int size = freq.size();

            for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
                result.append("\"")
                        .append(entry.getKey())
                        .append("\": ")
                        .append(entry.getValue());

                count++;
                if (count < size) {
                    result.append(", ");
                }
            }

            result.append("}");


            /* ===== 5. Gửi kết quả ===== */
            service.submitCharacter(studentCode, qCode, result.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
