// BÀI 4. [Object] SẢN PHẨM – 2
// [Mã câu hỏi (qCode): PY43T66m]. Một chương trình (tạm gọi là RMI Server) cung cấp các mã
// khuyến mãi sản phẩm ngẫu nhiên cho sinh viên, được mô tả như sau:
// Giao diện từ xa
// public interface ObjectService extends Remote {
//  public Serializable requestObject(String studentCode, String qAlias) throws
// RemoteException;
//  public void submitObject(String studentCode, String qAlias, Serializable object) throws
// RemoteException;
// }
// Lớp ProductX gồm các thuộc tính id String, code String, discountCode String, discount int.
// - Một hàm khởi dựng với đầy đủ các thuộc tính liệt kê ở trên
// - Trường dữ liệu: private static final long serialVersionUID = 20171107;
// * Đối tượng triệu gọi từ xa được đăng ký RegistryServer với tên: RMIObjectService
// * Tất cả các lớp được viết trong package RMI
// Yêu cầu là xây dựng một chương trình client thực hiện các tương tác với hệ thống phần mềm ở
// trên theo kịch bản dưới đây:
// 1. Triệu gọi phương thức từ xa requestObject từ RMI Server với tham số đầu vào là mã sinh viên,
// mã câu để nhận về đối tượng ProductX
// 2. Nhận về đối tượng ProductX từ RMI Server với giá trị ban đầu đã được thiết lập. Tính tổng các
// chữ số nằm trong chuỗi mã giảm giá (discountCode) để ra giá trị được khuyến mãi của sản phẩm
// và cập nhật giá trị của khuyến mãi (discount)
// 3. Triệu gọi phương thức từ xa submitObject với tham số đầu vào là đối tượng Product đã được cập
// nhật đầy đủ thông tin giá trị khuyến mãi
// 4. Kết thúc chương trình

package RMI.B21DCCN564;
import java.rmi.*;
import java.rmi.registry.*;
import RMI.ObjectService;
import RMI.ProductX;
public class XuLySanPham2 {
    public static void main(String[] args) throws Exception {
        //a. Nhận sản phẩm từ server
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        ObjectService sv = (ObjectService) rg.lookup("RMIObjectService");
        ProductX product = (ProductX) sv.requestObject("B21DCCN564", "PY43T66m");
        System.out.println(product);
        // b. Thực hiện chuẩn hóa sản phẩm:
        int tong = 0;
        for(char x: product.getDiscountCode().toCharArray()){
            if(Character.isDigit(x)) tong+=Character.getNumericValue(x);
        }
        product.setDiscount(tong);
        // c. Triệu gọi phương thức submitObject để gửi đối tượng đã chuẩn hóa trở lại server
        System.out.println(product);
        sv.submitObject("B21DCCN564", "PY43T66m", product);
    }
}



