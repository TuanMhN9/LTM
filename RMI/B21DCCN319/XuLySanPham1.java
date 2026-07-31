// BÀI 4. [Object] SẢN PHẨM – 1
// [Mã câu hỏi (qCode): dx3nt4Ij]. Một chương trình (tạm gọi là RMI server) cung cấp giao diện cho
// phép triệu gọi từ xa với thông tin như sau:
// Giao diện từ xa
//  public interface ObjectService extends Remote {
//  public Serializable requestObject(String studentCode, String qAlias) throws
// RemoteException;
//  public void submitObject(String studentCode, String qAlias, Serializable object) throws
// RemoteException;
//  }
// Lớp Product gồm các thông tin: id String, code String, importPrice double, exportPrice double.
//  Trường dữ liệu: private static final long serialVersionUID = 20151107L;
//  02 hàm khởi dựng
//  public Product()
//  public Product(String id, String code, String ImportPrice, String ExportPrice)
// Trong đó:
// - interface ObjectService và lớp Product được viết trong package RMI
// - Đối tượng cài đặt giao diện từ xa ObjectService được đăng ký với RegistryServer:
// RMIObjectService
// Yêu cầu: viết chương trình tại máy trạm (RMI client) thực hiện chuẩn hóa sản phẩm theo thứ tự:
// a. Triệu gọi phương thức requestObject để lấy về đối tượng sản phẩm cần chuẩn hóa.
// b. Thực hiện chuẩn hóa đối tượng nhận được theo nguyên tắc:
//  - Chuyển mã sản phẩm thành in hoa.
//  - Cập nhật giá xuất (exportPrice) bằng giá nhập (importPrice) + 20%
// c. Triệu gọi phương thức submitObject để gửi dữ liệu đã chuẩn hóa
// d. Kết thúc chương trình client

package RMI.B21DCCN319;
import java.rmi.*;
import java.rmi.registry.*;
import RMI.ObjectService;
import RMI.Product;
public class XuLySanPham1 {
    public static void main(String[] args) throws Exception {
        //a. Nhận sản phẩm từ server
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        ObjectService sv = (ObjectService) rg.lookup("RMIObjectService");
        Product product = (Product) sv.requestObject("B21DCCN319", "dx3nt4Ij");
        System.out.println(product);
        // b. Thực hiện chuẩn hóa sản phẩm:
        product.setCode(product.getCode().toUpperCase());// Chuyển mã sản phẩm thành in hoa
        product.setExportPrice(product.getImportPrice() * 1.2f);
        System.out.println("Normalized product: " + product);
        // c. Triệu gọi phương thức submitObject để gửi đối tượng đã chuẩn hóa trở lại server
        sv.submitObject("B21DCCN319", "dx3nt4Ij", product);
    }
}
