// BÀI 4. [Object] QUẢN LÝ VÉ SỰ KIỆN
// [Mã câu hỏi (qCode): Uct8bABt]. Một chương trình (tạm gọi là RMI Server) cung cấp giao diện
// cho phép triệu gọi từ xa để quản lý thông tin vé trong hệ thống bán vé sự kiện. Chương trình sẽ
// ngẫu nhiên tạo ra đối tượng Ticket với các giá trị ban đầu và cung cấp cho RMI client như sau:
//  Giao diện từ xa:
// public interface ObjectService extends Remote {
//  public Serializable requestObject(String studentCode, String qCode) throws RemoteException;
//  public void submitObject(String studentCode, String qCode, Serializable object) throws
// RemoteException;
// }
// Lớp Ticket gồm các thuộc tính: id String, eventName String, saleDate String, ticketCode String.
// //Điều chỉnh
// • Trường dữ liệu: private static final long serialVersionUID = 20241133L;
// • 02 hàm khởi dựng:
//  public Ticket()
//  public Ticket(id String, String eventName, String saleDate)
// Trong đó:
// • Interface ObjectService và lớp Ticket được viết trong package RMI.
// • Đối tượng cài đặt giao diện từ xa ObjectService được đăng ký với RegistryServer:
// RMIObjectService.
// Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với đối tượng
// vé được nhận từ RMI Server:
// a. Triệu gọi phương thức requestObject để nhận đối tượng Ticket ngẫu nhiên từ server.
// b. Tạo mã ticketCode cho vé dựa trên các quy tắc sau:
// • Bắt đầu với hai chữ cái là: Chữ cái đầu tiên và cuối cùng của eventName, viết hoa.
// • Thêm ngày và tháng từ saleDate (theo định dạng "MMdd")
// • Kết thúc bằng hai chữ số là: Chữ số lớn nhất và nhỏ nhất không xuất hiện trong saleDate.
//  Ví dụ: tên sự kiện là "Charity Concert", và ngày bán là "15/06/2024", mã ticketCode sẽ là
// "CT061593".
// c. Cập nhật giá trị ticketCode trong đối tượng Ticket.
// d. Triệu gọi phương thức submitObject để gửi đối tượng Ticket đã được xử lý trở lại server.
// e. Kết thúc chương trình client.



package RMI.B21DCCN048;
import java.rmi.*;
import java.rmi.registry.*;
import RMI.ObjectService;
import RMI.Ticket;
import java.util.*;
public class QuanLySuKien {
    public static void main(String[] args) throws Exception{
        Registry rg = LocateRegistry.getRegistry("203.162.10.109", 1099);
        ObjectService sv = (ObjectService) rg.lookup("RMIObjectService");
        Ticket ticket = (Ticket) sv.requestObject("B21DCCN048", "Uct8bABt");
        //Ticket ticket = new Ticket(1, "Charity Concert", "15/06/2024");
        System.out.println(ticket);
        //Lấy tên
        String s = ticket.getEventName().toUpperCase();
        String res = "";
        res+=String.format("%c%c", Character.toUpperCase(s.charAt(0)), (char)Character.toUpperCase(s.charAt(s.length() - 1)));
        //Lấy ngày sinh
        String s1 = ticket.getSaleDate();
        s1 = s1.replace("/", " ");
        String []tmp = s1.trim().split("\\s+");
        res+=tmp[1] + tmp[0];
        //Lấy 2 chữ số
        Set<Integer>si = new HashSet<>();
        for(char x: s1.toCharArray()){
            if(Character.isDigit(x)) si.add(x - '0');
        }
        for(int i = 9;i >= 0;i--){
            if(!si.contains(i)){
                res+=i;
                break;
            }
        }
        for(int i = 0;i<=9;i++){
            if(!si.contains(i)){
                res+=i;
                break;
            }
        }
        ticket.setTicketCode(res);
        System.out.println(ticket);
        //c.
        sv.submitObject("B21DCCN048", "Uct8bABt", ticket);
    }
}

