/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 8. ĐỊA CHỈ KHÁCH HÀNG
//[Mã câu hỏi (qCode): XtWjagNp].  Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 809 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectOutputStream/ObjectInputStream) để gửi/nhận và chuẩn hóa thông tin địa chỉ của khách hàng.
//Biết rằng lớp TCP.Address có các thuộc tính (id int, code String, addressLine String, city String, postalCode String) và trường dữ liệu private static final long serialVersionUID = 20180801L.
//a. Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;A1B2C3D4"
//b. Nhận một đối tượng là thể hiện của lớp TCP.Address từ server. Thực hiện chuẩn hóa thông tin addressLine bằng cách:
//•	Chuẩn hóa addressLine: Viết hoa chữ cái đầu mỗi từ, in thường các chữ còn lại, loại bỏ ký tự đặc biệt và khoảng trắng thừa (ví dụ: "123 nguyen!!! van cu" → "123 Nguyen Van Cu") 
//•	Chuẩn hóa postalCode: Chỉ giữ lại số và ký tự "-" ví dụ: "123-456"
//c. Gửi đối tượng đã được chuẩn hóa thông tin địa chỉ lên server.
//d. Đóng kết nối và kết thúc chương trình.


package TCP;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCP_OBJECT_STREAM_DIA_CHI_KHACH_HANG {
    public static void main(String[] args) throws Exception {

        // 1. Kết nối tới server
        Socket socket = new Socket("203.162.10.109", 809);

        ObjectOutputStream oos =
                new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois =
                new ObjectInputStream(socket.getInputStream());

        // 2. Gửi mã sinh viên và mã câu hỏi
        String studentCode = "B15DCCN999";
        String qCode = "XtWjagNp";
        oos.writeObject(studentCode + ";" + qCode);
        oos.flush();

        // 3. Nhận đối tượng Address từ server
        Address addr = (Address) ois.readObject();

        // ====== CHUẨN HÓA addressLine ======
        String addressLine = addr.getAddressLine();

        // Giữ chữ, số, khoảng trắng – bỏ ký tự đặc biệt
        addressLine = addressLine.replaceAll("[^a-zA-Z0-9\\s]", " ");

        // Bỏ khoảng trắng thừa
        addressLine = addressLine.trim().replaceAll("\\s+", " ");

        // Viết hoa chữ cái đầu mỗi từ
        String[] words = addressLine.split(" ");
        StringBuilder normalized = new StringBuilder();

        for (String w : words) {
            if (!w.isEmpty()) {
                normalized.append(
                        Character.toUpperCase(w.charAt(0))
                        + w.substring(1).toLowerCase()
                ).append(" ");
            }
        }

        addr.setAddressLine(normalized.toString().trim());

        // ====== CHUẨN HÓA postalCode ======
        String postal = addr.getPostalCode();
        postal = postal.replaceAll("[^0-9-]", "");
        addr.setPostalCode(postal);

        // 4. Gửi lại đối tượng đã chuẩn hóa
        oos.writeObject(addr);
        oos.flush();

        // 5. Đóng kết nối
        ois.close();
        oos.close();
        socket.close();
    }
}
