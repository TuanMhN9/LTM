/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//BÀI 3. SẢN PHẨM
//[Mã câu hỏi (qCode): 151GNZvT]. Một chương trình server cho phép kết nối qua giao thức TCP
//tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một
//chương trình client tương tác với server sử dụng các luồng đối tượng
//(ObjectOutputStream/ObjectInputStream) theo kịch bản dưới đây:
//Biết lớp TCP.Product gồm các thuộc tính (id int, name String, price double, int discount) và private
//static final long serialVersionUID = 20231107;
//a. Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode".
//Ví dụ: "B15DCCN999;1E08CA31"
//b. Nhận một đối tượng là thể hiện của lớp TCP.Product từ server.
//c. Tính toán giá trị giảm giá theo price theo nguyên tắc: Giá trị giảm giá (discount) bằng tổng các
//chữ số trong phần nguyên của giá sản phẩm (price). Thực hiện gán giá trị cho thuộc tính discount
//và gửi lên đối tượng nhận được lên server.
//d. Đóng kết nối và kết thúc chương trình.

package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCP_GIA_TRI_GIAM_GIA_SAN_PHAM_PRODUCT {

    public static void main(String[] args) throws Exception {

        String server = "172.188.19.218";
        int port = 2209;

        Socket socket = new Socket(server, port);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        String studentCode = "B15DCCN999"; // đổi mã SV
        String qCode = "151GNZvT";

        /* a. Gửi object String */
        out.writeObject(studentCode + ";" + qCode);
        out.flush();

        /* b. Nhận object Product */
        Product product = (Product) in.readObject();

        /* c. Tính discount */
        int integerPart = (int) product.getPrice();
        int discount = 0;

        while (integerPart > 0) {
            discount += integerPart % 10;
            integerPart /= 10;
        }

        product.setDiscount(discount);

        /* Gửi lại object Product */
        out.writeObject(product);
        out.flush();

        socket.close();
    }
}
