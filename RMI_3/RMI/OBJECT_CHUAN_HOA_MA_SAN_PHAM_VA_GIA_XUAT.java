/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//[Mã câu hỏi (qCode): 7Xs5ZJ70].  Một chương trình (tạm gọi là RMI server) cung cấp giao diện cho phép triệu gọi từ xa với thông tin như sau:
//-	Giao diện từ xa
//    public interface ObjectService extends Remote {
//        public Serializable requestObject(String studentCode, String qAlias) throws RemoteException;
//
//        public void submitObject(String studentCode, String qAlias, Serializable object) throws RemoteException;
//    }
//-	Lớp Product gồm các thông tin: id String, code String, importPrice double, exportPrice double.
//    Trường dữ liệu: private static final long serialVersionUID = 20151107L;
//    02 hàm khởi dựng 
//        public Product()
//        public Product(id String, String code,double ImportPrice, double ExportPrice)
//Trong đó:
//-	interface ObjectService và lớp Product được viết trong package RMI
//-	Đối tượng cài đặt giao diện từ xa ObjectService được đăng ký với RegistryServer: RMIObjectService
//
//Yêu cầu yêu cầu viết chương trình tại máy trạm (RMI client) thực hiện chuẩn hóa sản phẩm theo thứ tự:
//a.	Triệu gọi phương thức requestObject để lấy về đối tượng sản phẩm cần chuẩn hóa.
//b.	Thực hiện chuẩn hóa đối tượng nhận được theo nguyên tắc:
//        - Chuyển mã sản phẩm thành in hoa.
//        - Cập nhật giá xuất (exportPrice) bằng giá nhập (importPrice) + 20%
//
//c.  Triệu gọi phương thức submitObject để gửi dữ liệu đã chuẩn hóa
//d.  Kết thúc chương trình client

package RMI;

import java.rmi.Naming;

public class OBJECT_CHUAN_HOA_MA_SAN_PHAM_VA_GIA_XUAT {

    public static void main(String[] args) {
        try {
            String studentCode = "B22DCCN016"; // 🔁 đổi mã SV của bạn
            String qCode = "7Xs5ZJ70";

            // Kết nối RMI Server
            ObjectService service = (ObjectService)
                    Naming.lookup("rmi://203.162.10.109/RMIObjectService");

            // a. Nhận đối tượng Product
            Product product = (Product)
                    service.requestObject(studentCode, qCode);

            // b. Chuẩn hóa
            // 1. Code in hoa
            product.setCode(product.getCode().toUpperCase());

            // 2. ExportPrice = ImportPrice + 20%
            double newExportPrice = product.getImportPrice() * 1.2;
            product.setExportPrice(newExportPrice);

            // c. Gửi lại server
            service.submitObject(studentCode, qCode, product);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
