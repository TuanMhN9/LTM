/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 4. QUẢN LÝ THÔNG TIN NHÂN VIÊN
//[Mã câu hỏi (qCode): ySsumsIE].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:
//Đối tượng trao đổi là thể hiện của lớp UDP.Employee được mô tả:
//-	Tên đầy đủ lớp: UDP.Employee
//-	Các thuộc tính: id (String), name (String), salary (double), hireDate (String)
//-	Hàm khởi tạo:
//        public Employee(String id, String name, double salary, String hireDate)
//-	Trường dữ liệu: private static final long serialVersionUID = 20261107L
//Thực hiện:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B23DCCN006;ITleSdqV"
//b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Employee từ server. Trong đó, các thuộc tính id, name, salary và hireDate đã được thiết lập sẵn.
//c. Thực hiện:
//- Chuẩn hóa name: viết hoa chữ cái đầu của mỗi từ, ví dụ "john doe" thành "John Doe".
//- Tăng salary: tăng x% lương, với x bằng tổng các chữ số của năm sinh.
//- Chuyển đổi hireDate từ định dạng yyyy-mm-dd sang dd/mm/yyyy. Ví dụ: "2023-07-15" thành "15/07/2023".
//- Gửi lại đối tượng đã được chuẩn hóa về server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Employee đã được sửa đổi.
//d. Đóng socket và kết thúc chương trình.


package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_QUAN_LY_THONG_TIN_NHAN_VIEN {

    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddr = InetAddress.getByName("203.162.10.109");
        int serverPort = 2209;

        // a. Gửi studentCode;qCode
        String studentCode = "B22DCCN088";
        String qCode = "Y4Xdu8KO";
        String msg = ";" + studentCode + ";" + qCode;

        DatagramPacket sendPacket =
                new DatagramPacket(msg.getBytes(), msg.length(), serverAddr, serverPort);
        socket.send(sendPacket);

        // b. Nhận requestId + Employee
        byte[] buffer = new byte[8192];
        DatagramPacket receivePacket =
                new DatagramPacket(buffer, buffer.length);
        socket.receive(receivePacket);

        byte[] data = receivePacket.getData();

        // Lấy 8 byte requestId
        byte[] requestIdBytes = new byte[8];
        System.arraycopy(data, 0, requestIdBytes, 0, 8);

        // Đọc Employee object
        ByteArrayInputStream bais =
                new ByteArrayInputStream(data, 8, receivePacket.getLength() - 8);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Employee emp = (Employee) ois.readObject();

        // c. Chuẩn hoá dữ liệu
        emp.setName(normalizeName(emp.getName()));
        emp.setSalary(increaseSalary(emp.getSalary(), emp.getHireDate()));
        emp.setHireDate(normalizeDate(emp.getHireDate()));

        // d. Gửi lại requestId + Employee
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestIdBytes);

        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(emp);
        oos.flush();

        byte[] sendData = baos.toByteArray();
        DatagramPacket resultPacket =
                new DatagramPacket(sendData, sendData.length, serverAddr, serverPort);
        socket.send(resultPacket);

        socket.close();
    }

    // ===== HÀM XỬ LÝ =====

    static String normalizeName(String s) {
        String[] words = s.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            sb.append(Character.toUpperCase(w.charAt(0)))
              .append(w.substring(1))
              .append(" ");
        }
        return sb.toString().trim();
    }

    static double increaseSalary(double salary, String hireDate) {
        // hireDate: yyyy-mm-dd
        String year = hireDate.substring(0, 4);
        int sum = 0;
        for (char c : year.toCharArray()) {
            sum += c - '0';
        }
        return salary * (1 + sum / 100.0);
    }

    static String normalizeDate(String s) {
        // yyyy-mm-dd → dd/mm/yyyy
        String[] p = s.split("-");
        return p[2] + "/" + p[1] + "/" + p[0];
    }
}
