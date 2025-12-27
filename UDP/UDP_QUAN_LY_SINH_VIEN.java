/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 7. BÀI TOÁN QUẢN LÝ SINH VIÊN [001]
//[Mã câu hỏi (qCode): QABGDFxd]. Một chương trình server cho phép giao tiếp qua giao thức
//UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo
//kịch bản sau:
//Đối tượng trao đổi là thể hiện của lớp UDP.Student được mô tả:
//• Tên đầy đủ lớp: UDP.Student
//• Các thuộc tính: id String,code String, name String, email String
//• 02 Hàm khởi tạo:
//o public Student(String id, String code, String name, String email)
//o public Student(String code)
//• Trường dữ liệu: private static final long serialVersionUID = 20171107
//Thực hiện:
//• Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng
//“;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”
//b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối
//tượng là thể hiện của lớp Student từ server. Trong đó, các thông tin được thiết lập gồm id và name.
//c. Yêu cầu:
//- Chuẩn hóa tên theo quy tắc: Chữ cái đầu tiên in hoa, các chữ cái còn lại in thường và gán
//lại thuộc tính name của đối tượng
//- Tạo email ptit.edu.vn từ tên người dùng bằng cách lấy tên và các chữ cái bắt đầu của họ và
//tên đệm. Ví dụ: nguyen van tuan nam → namnvt@ptit.edu.vn. Gán giá trị này cho thuộc tính email
//của đối tượng nhận được
//- Gửi thông điệp chứa đối tượng xử lý ở bước c lên Server với cấu trúc: 08 byte đầu chứa
//chuỗi requestId và các byte còn lại chứa đối tượng Customer đã được sửa đổi.
//d. Đóng socket và kết thúc chương trình.

package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_QUAN_LY_SINH_VIEN {

    public static void main(String[] args) throws Exception {

        InetAddress serverAddress = InetAddress.getByName("172.188.19.218");
        int port = 2209;

        DatagramSocket socket = new DatagramSocket();

        String studentCode = "B15DCCN001";
        String qCode = "QABGDFxd";

        /* a. Gửi studentCode;qCode */
        String msg = ";" + studentCode + ";" + qCode;
        byte[] sendData = msg.getBytes();
        socket.send(new DatagramPacket(sendData, sendData.length, serverAddress, port));

        /* b. Nhận requestId + object Student */
        byte[] buffer = new byte[4096];
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
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
        Student student = (Student) ois.readObject();

        /* c. Xử lý dữ liệu */

        // --- Chuẩn hóa tên ---
        String[] parts = student.getName().trim().split("\\s+");
        StringBuilder fullName = new StringBuilder();
        for (String p : parts) {
            fullName.append(Character.toUpperCase(p.charAt(0)))
                    .append(p.substring(1).toLowerCase())
                    .append(" ");
        }
        student.setName(fullName.toString().trim());

        // --- Tạo email ---
        String lastName = parts[parts.length - 1].toLowerCase();
        StringBuilder email = new StringBuilder(lastName);

        for (int i = 0; i < parts.length - 1; i++) {
            email.append(Character.toLowerCase(parts[i].charAt(0)));
        }

        email.append("@ptit.edu.vn");
        student.setEmail(email.toString());

        /* d. Gửi lại requestId + object */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestIdBytes);
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(student);

        byte[] sendBack = baos.toByteArray();
        socket.send(new DatagramPacket(sendBack, sendBack.length, serverAddress, port));

        socket.close();
    }
}
