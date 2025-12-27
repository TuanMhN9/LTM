/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 6. KHÁCH HÀNG [008]
//[Mã câu hỏi (qCode): 4MpxJ947]. Thông tin khách hàng được yêu cầu thay đổi định dạng lại cho
//phù hợp với khu vực, cụ thể:
//a. Tên khách hàng cần được chuẩn hóa theo định dạng mới. Ví dụ: nguyen van hai duong ->
//DUONG, Nguyen Van Hai
//b. Ngày sinh của khách hàng đang ở dạng mm-dd-yyyy, cần được chuyển thành định dạng
//dd/mm/yyyy. Ví dụ: 10-11-2012 → 11/10/2012
//c. Tài khoản khách hàng được tạo từ các chữ cái in thường được sinh tự động từ họ tên khách
//hàng. Ví dụ: nguyen van hai duong → nvhduong
//Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng
//một chương trình client giao tiếp với server theo mô tả sau:
//a. Đối tượng trao đổi là thể hiện của lớp UDP.Customer được mô tả như sau
//• Tên đầy đủ của lớp: UDP.Customer
//• Các thuộc tính: id String, code String, name String, , dayOfBirth String, userName String
//• Một Hàm khởi tạo với đầy đủ các thuộc tính được liệt kê ở trên
//• Trường dữ liệu: private static final long serialVersionUID = 20151107;
//b. Client giao tiếp với server theo các bước
//• Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng
//“;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”
//• Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối
//tượng là thể hiện của lớp Customer từ server. Trong đó, các thuộc tính id, code, name,dayOfBirth
//đã được thiết lập sẵn.
//• Yêu cầu thay đổi thông tin các thuộc tính như yêu cầu ở trên và gửi lại đối tượng khách hàng
//đã được sửa đổi lên server với cấu trúc:
//08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Customer đã được sửa đổi.
//Luyện thi UDP
//_____________________________________________________________________________________________
//Trang 4
//• Đóng socket và kết thúc chương trình.

package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_QUAN_LY_KHACH_HANG {

    public static void main(String[] args) throws Exception {

        InetAddress serverAddress = InetAddress.getByName("172.188.19.218");
        int port = 2209;

        DatagramSocket socket = new DatagramSocket();

        String studentCode = "B15DCCN001";
        String qCode = "4MpxJ947";

        /* a. Gửi studentCode;qCode */
        String sendMsg = ";" + studentCode + ";" + qCode;
        byte[] sendData = sendMsg.getBytes();
        socket.send(new DatagramPacket(sendData, sendData.length, serverAddress, port));

        /* b. Nhận requestId + Object */
        byte[] receiveBuffer = new byte[4096];
        DatagramPacket receivePacket =
                new DatagramPacket(receiveBuffer, receiveBuffer.length);
        socket.receive(receivePacket);

        byte[] data = receivePacket.getData();

        // tách requestId
        byte[] requestIdBytes = new byte[8];
        System.arraycopy(data, 0, requestIdBytes, 0, 8);
        String requestId = new String(requestIdBytes);

        // đọc object
        ByteArrayInputStream bais =
                new ByteArrayInputStream(data, 8, receivePacket.getLength() - 8);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Customer customer = (Customer) ois.readObject();

        /* c. Chuẩn hóa dữ liệu */

        // --- Chuẩn hóa tên ---
        String[] parts = customer.getName().trim().split("\\s+");
        String lastName = parts[parts.length - 1].toUpperCase();

        StringBuilder middle = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) {
            middle.append(Character.toUpperCase(parts[i].charAt(0)))
                  .append(parts[i].substring(1).toLowerCase())
                  .append(" ");
        }

        String newName = lastName + ", " + middle.toString().trim();
        customer.setName(newName);

        // --- Chuẩn hóa ngày sinh ---
        String[] dob = customer.getDayOfBirth().split("-");
        customer.setDayOfBirth(dob[1] + "/" + dob[0] + "/" + dob[2]);

        // --- Tạo username ---
        StringBuilder username = new StringBuilder();
        for (int i = 0; i < parts.length - 1; i++) {
            username.append(parts[i].charAt(0));
        }
        username.append(parts[parts.length - 1]);
        customer.setUserName(username.toString().toLowerCase());

        /* d. Gửi lại requestId + object */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestIdBytes);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(customer);

        byte[] sendBack = baos.toByteArray();
        socket.send(new DatagramPacket(sendBack, sendBack.length, serverAddress, port));

        socket.close();
    }
}
