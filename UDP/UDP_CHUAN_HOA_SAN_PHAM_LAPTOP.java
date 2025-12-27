/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_CHUAN_HOA_SAN_PHAM_LAPTOP {

    public static void main(String[] args) throws Exception {

        String serverHost = "203.162.10.109";
        int serverPort = 2209;

        String studentCode = "B22DCCN016";
        String qCode = "CRiaq6tV";

        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddress = InetAddress.getByName(serverHost);

        /* a. Gửi mã sinh viên + mã câu hỏi */
        String msg = ";" + studentCode + ";" + qCode;
        byte[] sendData = msg.getBytes();

        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
        socket.send(sendPacket);

        /* b. Nhận requestId + Product */
        byte[] receiveData = new byte[4096];
        DatagramPacket receivePacket =
                new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        byte[] data = receivePacket.getData();

        // Lấy requestId (8 byte đầu)
        byte[] requestIdBytes = new byte[8];
        System.arraycopy(data, 0, requestIdBytes, 0, 8);

        // Lấy object Product
        ByteArrayInputStream bais =
                new ByteArrayInputStream(data, 8, receivePacket.getLength() - 8);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Product product = (Product) ois.readObject();

        /* c. SỬA THÔNG TIN */

        // 🔹 Sửa tên: đảo từ đầu và từ cuối
        String[] words = product.getName().split(" ");
        if (words.length >= 2) {
            String temp = words[0];
            words[0] = words[words.length - 1];
            words[words.length - 1] = temp;
        }
        StringBuilder fixedName = new StringBuilder();
        for (String w : words) {
            fixedName.append(w).append(" ");
        }
        product.setName(fixedName.toString().trim());

        // 🔹 Sửa số lượng: đảo chữ số
        String reversedQty =
                new StringBuilder(String.valueOf(product.getQuantity()))
                        .reverse().toString();
        product.setQuantity(Integer.parseInt(reversedQty));

        /* d. Gửi lại requestId + Product đã sửa */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(product);
        oos.flush();

        byte[] productBytes = baos.toByteArray();
        byte[] finalData = new byte[8 + productBytes.length];

        System.arraycopy(requestIdBytes, 0, finalData, 0, 8);
        System.arraycopy(productBytes, 0, finalData, 8, productBytes.length);

        DatagramPacket finalPacket =
                new DatagramPacket(finalData, finalData.length, serverAddress, serverPort);
        socket.send(finalPacket);

        socket.close();
    }
}
