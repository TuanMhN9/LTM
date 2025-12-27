/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): t3wBpNxp].  Một chương trình (tạm gọi là RMI Server) cung cấp các mã khuyến mãi sản phẩm ngẫu nhiên cho sinh viên, được mô tả như sau:
//•	Giao diện từ xa
//    public interface ObjectService extends Remote {
//        public Serializable requestObject(String studentCode, String qAlias) throws RemoteException;
//
//        public void submitObject(String studentCode, String qAlias, Serializable object) throws RemoteException;
//    }
//•	Lớp ProductX gồm các thuộc tính id String, code String, discountCode String, discount int.
//o	Một hàm khởi dựng với đầy đủ các thuộc tính liệt kê ở trên
//o	Trường dữ liệu: private static final long serialVersionUID = 20171107; 
//•	Đối tượng triệu gọi từ xa được đăng ký RegistryServer với tên: RMIObjectService
//•	Tất cả các lớp được viết trong package RMI
//
//Yêu cầu là xây dựng một chương trình client thực hiện các tương tác với hệ thống phần mềm ở trên theo kịch bản dưới đây:
//1.	  Triệu gọi phương thức từ xa requestObject từ RMI Server với tham số đầu vào là mã sinh viên, mã câu để nhận về đối tượng ProductX
//2.    Nhận về đối tượng ProductX từ RMI Server với giá trị ban đầu đã được thiết lập. Tính tổng các chữ số nằm trong chuỗi mã giảm giá (discountCode) để ra giá trị được khuyến mãi của sản phẩm và cập nhật giá trị của khuyến mãi (discount)
//3.	Triệu gọi phương thức từ xa submitObject với tham số đầu vào là đối tượng Product đã được cập nhật đầy đủ thông tin giá trị khuyến mãi
//4.	Kết thúc chương trình

package Client;

import RMI.ObjectService;
import RMI.ProductX;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OBJECT_GIA_TRI_KHUYEN_MAI {

    public static void main(String[] args) {
        String studentCode = "B22DCCN760"; // đổi MSSV
        String qCode = "t3wBpNxp";

        try {
            /* ===== 1. Kết nối RMI ===== */
            Registry registry = LocateRegistry.getRegistry("203.162.10.109", 1099);
            ObjectService service =
                    (ObjectService) registry.lookup("RMIObjectService");

            /* ===== 2. Nhận object ProductX ===== */
            ProductX product = (ProductX) service.requestObject(studentCode, qCode);

            /* ===== 3. Tính tổng chữ số trong discountCode ===== */
            String discountCode = product.getDiscountCode();
            int sum = 0;

            for (char c : discountCode.toCharArray()) {
                if (Character.isDigit(c)) {
                    sum += Character.getNumericValue(c);
                }
            }

            /* ===== 4. Cập nhật discount ===== */
            product.setDiscount(sum);

            /* ===== 5. Gửi object về server ===== */
            service.submitObject(studentCode, qCode, product);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
