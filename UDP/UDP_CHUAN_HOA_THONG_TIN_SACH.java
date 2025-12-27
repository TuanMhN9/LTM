/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 3. CHUẨN HOÁ THÔNG TIN SÁCH
//[Mã câu hỏi (qCode): LFACr5Bi].  Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau:
//Đối tượng trao đổi là thể hiện của lớp UDP.Book được mô tả:
//-	Tên đầy đủ lớp: UDP.Book
//-	Các thuộc tính: id (String), title (String), author (String), isbn (String), publishDate (String)
//-	Hàm khởi tạo:
//        public Book(String id, String title, String author, String isbn, String publishDate)
//-	Trường dữ liệu: private static final long serialVersionUID = 20251107L
//Thực hiện:
//a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B23DCCN005;eQkvAeId"
//b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Book từ server. Trong đó, các thuộc tính id, title, author, isbn, và publishDate đã được thiết lập sẵn.
//c. Thực hiện:
//        1. Chuẩn hóa title: Với mỗi từ, viết hoa chữ cái đầu tiên, viết thường các chữ cái còn lại.
//        2. Chuẩn hóa author theo định dạng "Họ, Tên". 
//- Trong đó họ được hiểu là từ đầu tiên của tác giả, tất cả các từ còn lại là tên.
//- Họ: Viết hoa tất cả các chữ cái
//- Tên: Với mỗi từ, viết hoa chữ cái đầu tiên, viết thường các chữ cái còn lại, giữa mỗi từ chỉ có đúng một dấu cách
//        3. Chuẩn hóa mã ISBN theo định dạng "978-3-16-148410-0"
//        4. Chuyển đổi publishDate từ yyyy-mm-dd sang mm/yyyy.
//d. Gửi lại đối tượng đã được chuẩn hóa về server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Book đã được sửa đổi.
//e. Đóng socket và kết thúc chương trình.

import UDP.Book;
import java.io.*;
import java.net.*;

public class UDP_CHUAN_HOA_THONG_TIN_SACH {

    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket();
        InetAddress server = InetAddress.getByName("203.162.10.109");
        int port = 2209;

        // a. gửi studentCode;qCode
        String studentCode = "B22DCCN760";
        String qCode = "YH1bxrRd";
        String send = ";" + studentCode + ";" + qCode;

        socket.send(new DatagramPacket(send.getBytes(), send.length(), server, port));

        // b. nhận requestId + Book
        byte[] buffer = new byte[4096];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        byte[] data = packet.getData();

        // lấy requestId (8 byte)
        byte[] reqBytes = new byte[8];
        System.arraycopy(data, 0, reqBytes, 0, 8);
        String requestId = new String(reqBytes);

        // đọc object Book
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data, 8, packet.getLength() - 8));
        Book book = (Book) ois.readObject();

        // c. chuẩn hóa
        book.setTitle(formatTitle(book.getTitle()));
        book.setAuthor(formatAuthor(book.getAuthor()));
        book.setIsbn(formatISBN(book.getIsbn()));
        book.setPublishDate(formatDate(book.getPublishDate()));

        // d. gửi lại
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(book);
        oos.flush();

        byte[] bookBytes = baos.toByteArray();
        byte[] sendBack = new byte[8 + bookBytes.length];

        System.arraycopy(reqBytes, 0, sendBack, 0, 8);
        System.arraycopy(bookBytes, 0, sendBack, 8, bookBytes.length);

        socket.send(new DatagramPacket(sendBack, sendBack.length, server, port));
        socket.close();
    }

    // ===== HÀM XỬ LÝ =====

    static String formatTitle(String s) {
        String[] w = s.toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String x : w)
            sb.append(Character.toUpperCase(x.charAt(0)))
              .append(x.substring(1)).append(" ");
        return sb.toString().trim();
    }

    static String formatAuthor(String s) {
        String[] w = s.trim().split("\\s+");
        String ho = w[0].toUpperCase();
        StringBuilder ten = new StringBuilder();
        for (int i = 1; i < w.length; i++)
            ten.append(Character.toUpperCase(w[i].charAt(0)))
               .append(w[i].substring(1).toLowerCase()).append(" ");
        return ho + ", " + ten.toString().trim();
    }

    static String formatISBN(String s) {
        String d = s.replaceAll("[^0-9]", "");
        if (d.length() != 13) return s;
        return d.substring(0,3) + "-" +
               d.substring(3,4) + "-" +
               d.substring(4,6) + "-" +
               d.substring(6,12) + "-" +
               d.substring(12);
    }

    static String formatDate(String s) {
        String[] p = s.split("-");
        return p[1] + "/" + p[0];
    }
}

