// BÀI 4. [Object] TỔ CHỨC SỰ KIỆN
// [Mã câu hỏi (qCode): FKrGvwLM]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện
// cho phép triệu gọi từ xa để quản lý thông tin các sự kiện trong hệ thống tổ chức sự kiện. Chương
// trình sẽ ngẫu nhiên tạo ra đối tượng Event với các giá trị ban đầu và cung cấp cho RMI client như
// sau:
//  Giao diện từ xa:
// public interface ObjectService extends Remote {
//  public Serializable requestObject(String studentCode, String qCode) throws RemoteException;
//  public void submitObject(String studentCode, String qCode, Serializable object) throws
// RemoteException;
// }
// Lớp Event gồm các thuộc tính: id String, eventName String, eventDate String, expectedAttendance
// int, eventCode String.
// • Trường dữ liệu: private static final long serialVersionUID = 20241131L;
// • 02 hàm khởi dựng:
// Luyện tập RMI
// _____________________________________________________________________________________________
// Trang 24
// public Event()
//  public Event(int id, String eventName, String eventDate, int expectedAttendance)
// Trong đó:
// • Interface ObjectService và lớp Event được viết trong package RMI.
// • Đối tượng cài đặt giao diện từ xa ObjectService được đăng ký với RegistryServer:
// RMIObjectService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với đối tượng
// sự kiện được nhận từ RMI Server:
// a. Triệu gọi phương thức requestObject để nhận đối tượng Event ngẫu nhiên từ server.
// b. Tạo mã eventCode cho sự kiện dựa trên các quy tắc sau:
// • Nếu expectedAttendance >= 1000, thêm "L" vào eventCode;
// Nếu expectedAttendance từ 500 đến 999, thêm "M" vào eventCode;
// Nếu expectedAttendance dưới 500, thêm "S" vào eventCode.
// • Thêm chữ cái đầu và cuối của eventName, tất cả viết hoa.
// • Thêm ngày và tháng từ eventDate (theo định dạng "ddMM").
// Ví dụ: Nếu sự kiện có tên là "Charity Run", ngày diễn ra là "2024-05-12" và số lượng người tham
// gia dự kiến là 1200, mã eventCode sẽ là "LCR1205".
// c. Cập nhật giá trị eventCode trong đối tượng Event.
// d. Triệu gọi phương thức submitObject để gửi đối tượng Event đã được xử lý trở lại server.
// e. Kết thúc chương trình client.




package RMI.B21DCCN029;
import java.rmi.*;
import java.rmi.registry.*;
import RMI.ObjectService;
import RMI.Event;
public class ToChucSuKien {
    public static void main(String[] args) throws Exception{
        //a.
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        ObjectService sv = (ObjectService) rg.lookup("RMIObjectService");
        Event event = (Event) sv.requestObject("B21DCCN029", "FKrGvwLM");
        System.out.println(event);
        //b.
        String ans = "";
        int x = event.getExpectedAttendance();
        //
        if(x>=1000) ans = "L";
        else if (x>=500 && x<=999) ans = "M";
        else ans = "S";
        //
        String []tmp = event.getEventName().trim().split("\\s+");
        ans+=Character.toUpperCase(tmp[0].charAt(0));
        ans+=Character.toUpperCase(tmp[tmp.length - 1].charAt(tmp[tmp.length - 1].length() - 1));
        //
        String tmp1 = event.getEventDate();
        tmp1 = tmp1.replace("-", " ");
        String []tmp2 = tmp1.trim().split("\\s+");
        ans+=tmp2[2] + tmp2[1];
        event.setEventCode(ans);
        //c.
        System.out.println(event);
        sv.submitObject("B21DCCN029", "FKrGvwLM", event);
    } 
}
