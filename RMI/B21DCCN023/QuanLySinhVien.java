// BÀI 4. [Object] BÀI TOÁN QUẢN LÝ SINH VIÊN
// [Mã câu hỏi (qCode): lNV6xzmk]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện
// cho phép triệu gọi từ xa để xử lý thông tin sinh viên trong hệ thống quản lý giáo dục. Chương trình
// sẽ ngẫu nhiên tạo ra đối tượng Student với các giá trị ban đầu và cung cấp cho RMI client như sau:
// Giao diện từ xa:
// public interface ObjectService extends Remote {
//  public Serializable requestObject(String studentCode, String qCode) throws RemoteException;
//  public void submitObject(String studentCode, String qCode, Serializable object) throws
// RemoteException;
// }
// Lớp Student gồm các thuộc tính: id String, name String, enrollmentYear int, code String.
// Trường dữ liệu: private static final long serialVersionUID = 20241130L;
// 02 hàm khởi dựng:
//  public Student()
//  public Student(String id, String name, int enrollmentYear)
// Trong đó:
//  Interface ObjectService và lớp Student được viết trong package RMI.
//  Đối tượng cài đặt giao diện từ xa ObjectService được đăng ký với RegistryServer:
// RMIObjectService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với đối tượng
// sinh viên được nhận từ RMI Server:
// a. Triệu gọi phương thức requestObject để nhận đối tượng Student ngẫu nhiên từ server.
// b. Thực hiện
// Tạo mã code cho sinh viên dựa trên các quy tắc sau:
// • Bắt đầu bằng ký tự "B".
// • Kế đến là hai chữ số cuối của enrollmentYear.
// • Sau đó là TÊN của sinh viên, tất cả các ký tự của tên viết in hoa.
// • Kết thúc với các chữ cái đầu tiên của Họ và Họ lót, đều viết in hoa.
// Luyện tập RMI
// _____________________________________________________________________________________________
// Trang 21
// Ví dụ: Nếu sinh viên có tên là "Nguyen Van Tuan", năm nhập học là 2022, mã code sẽ là
// "B22TUAN_NV".
// Chuẩn hóa tên (name) theo quy tắc:
// • Chữ cái đầu tiên của Tên, Họ, và Tên lót phải viết hoa, các chữ cái khác viết thường.
// • Ví dụ: Nếu name ban đầu là "nguYEn van tAi tUAN", sau khi chuẩn hóa sẽ trở thành
// "Nguyen Van Tai TUAN".
// c. Cập nhật giá trị mã (code) và tên (name) trong đối tượng Student và
// d. Triệu gọi phương thức submitObject để gửi đối tượng Student đã được xử lý trở lại server.
// e. Kết thúc chương trình client.
// //Điều chỉnh kiểu dữ liệu hàm khởi tạo của id từ int thành String




package RMI.B21DCCN023;
import java.util.*;
import java.rmi.*;
import java.rmi.registry.*;
import RMI.ObjectService;
import RMI.Student;
public class QuanLySinhVien {
    public static String chuanhoa(String s) {
        return Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase();
    }
    public static String chuanhoa2(String s){
        String []tmp = s.trim().split("\\s+");
        String res = "";
        for(int i = 0;i<tmp.length - 1;i++) res+=chuanhoa(tmp[i]) + " ";
        res+=tmp[tmp.length - 1].toUpperCase();
        return res;
    }
    public static void main(String[] args) throws Exception{
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        ObjectService sv = (ObjectService) rg.lookup("RMIObjectService");
        Student student = (Student) sv.requestObject("B21DCCN023", "lNV6xzmk");
        System.out.println(student);
        student.setName(chuanhoa2(student.getName()));
        String code = String.format("B%d", student.getEnrollmentYear()%100);
        String []tmp = student.getName().split("\\s+");
        code+=tmp[tmp.length - 1].toUpperCase() + "_";
        for(int i = 0;i<tmp.length - 1;i++) code+=Character.toUpperCase(tmp[i].charAt(0));
        student.setCode(code);
        System.out.println(student);
        //c.
        sv.submitObject("B21DCCN023", "lNV6xzmk", student);
    }
}
