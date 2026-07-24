// BÀI 2. SẢN PHẨM [Object]
// [Mã câu hỏi (qCode): kZqFKEDL]. Thông tin sản phẩm vì một lý do nào đó đã bị sửa đổi thành
// không đúng, cụ thể:
// a. Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng, ví dụ: “lenovo thinkpad T520” bị
// chuyển thành “T520 thinkpad lenovo”
// b. Số lượng sản phẩm cũng bị đảo ngược giá trị, ví dụ từ 9981 thành 1899
// Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng
// một chương trình client giao tiếp với server để gửi/nhận các sản phẩm theo mô tả dưới đây:
// a. Đối tượng trao đổi là thể hiện của lớp Product được mô tả như sau
// • Tên đầy đủ của lớp: UDP.Product
// • Các thuộc tính: id String, code String, name String, quantity int
// • Một hàm khởi tạo có đầy đủ các thuộc tính được liệt kê ở trên
// • Trường dữ liệu: private static final long serialVersionUID = 20161107;
// b. Giao tiếp với server theo kịch bản
// • Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng
// “;studentCode;qCode”. Ví dụ: “;B15DCCN001;EE29C059”
// • Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối
// tượng là thể hiện của lớp Product từ server. Trong đối tượng này, các thuộc tính id, name và quantity
// đã được thiết lập giá trị.
// • Sửa các thông tin sai của đối tượng về tên và số lượng như mô tả ở trên và gửi đối tượng
// vừa được sửa đổi lên server theo cấu trúc:
// 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Product đã được sửa đổi.
// • Đóng socket và kết thúc chương trình.

package UDP;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient {

    public static void main(String[] args) throws Exception {

        String host = "172.188.19.218";
        int port = 2209;

        String studentCode = "B22DCCN760";
        String qCode = "kZqFKEDL";

        DatagramSocket socket = new DatagramSocket();
        InetAddress server = InetAddress.getByName(host);

        String message = ";" + studentCode + ";" + qCode;

        byte[] sendData = message.getBytes("UTF-8");

        DatagramPacket sendPacket = new DatagramPacket(
                sendData,
                sendData.length,
                server,
                port
        );

        socket.send(sendPacket);

        byte[] receiveData = new byte[4096];

        DatagramPacket receivePacket = new DatagramPacket(
                receiveData,
                receiveData.length
        );

        socket.receive(receivePacket);

        byte[] data = receivePacket.getData();

        String requestId = new String(data, 0, 8);

        ByteArrayInputStream bis = new ByteArrayInputStream(
                data,
                8,
                receivePacket.getLength() - 8
        );

        ObjectInputStream ois = new ObjectInputStream(bis);

        Product product = (Product) ois.readObject();

        product.setName(fixName(product.getName()));
        product.setQuantity(reverse(product.getQuantity()));

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bos.write(requestId.getBytes("UTF-8"));

        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(product);
        oos.flush();

        sendData = bos.toByteArray();

        sendPacket = new DatagramPacket(
                sendData,
                sendData.length,
                server,
                port
        );

        socket.send(sendPacket);

        socket.close();
    }

    private static String fixName(String name) {

        String[] words = name.trim().split("\\s+");

        if (words.length < 2) {
            return name;
        }

        String temp = words[0];
        words[0] = words[words.length - 1];
        words[words.length - 1] = temp;

        return String.join(" ", words);
    }

    private static int reverse(int n) {

        int result = 0;

        while (n > 0) {
            result = result * 10 + n % 10;
            n /= 10;
        }

        return result;
    }
}