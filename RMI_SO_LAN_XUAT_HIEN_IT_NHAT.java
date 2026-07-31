// Một chương trình (tạm gọi là RMI Server) cung cấp giao diện cho phép triệu gọi từ xa để xử lý dữ liệu nhị phân.  Giao diện từ xa:  public interface ByteService extends Remote {  public byte[] requestData(String studentCode, String qCode) throws RemoteException;  public void submitData(String studentCode, String qCode, byte[] data) throws RemoteException;  }  Trong đó:  • Interface ByteService được viết trong package RMI.  Đối tượng cài đặt giao diện từ xa ByteService được đăng ký với RegistryServer với tên là: RMIByteService.  Yêu cầu: Viết chương trình tại máy trạm (RMI client) để thực hiện các công việc sau với dữ liệu nhị phân nhận được từ RMI Server:  a. Triệu gọi phương thức requestData để nhận một mảng dữ liệu nhị phân (byte[]) từ server.  b. Tìm phần tử có số lần xuất hiện ít nhất trong mảng, nếu có nhiều phần tử có cùng số lần xuất hiện ít nhất, chỉ cần trả về phần tử đầu tiên xuất hiện trong các phần tử đó  Ví dụ: Nếu mảng dữ liệu nhận được là [1, 2, 3, 2, 1], chương trình sẽ tìm ra phần tử xuất hiện ít nhất là 3.  c. Triệu gọi phương thức submitData để gửi mảng byte kết quả chứa phẩn tử có số lần xuất hiện ít nhất và số lần xuất hiện trở lại server.  d. Kết thúc chương trình client.

package javaapplication2;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author admin
 */

import RMI.ByteService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedHashMap;
import java.util.Map;

public class RMI_SO_LAN_XUAT_HIEN_IT_NHAT {
    public static void main(String[] args) throws Exception {

        String host = "36.50.135.242";
        int port = 1099;

        String studentCode = "B22DCCN760";
        String qCode = "YMCrCbyE";

        Registry registry =
                LocateRegistry.getRegistry(host, port);

        ByteService service =
                (ByteService) registry.lookup("RMIByteService");

        byte[] data =
                service.requestData(studentCode, qCode);

        LinkedHashMap<Byte,Integer> map =
                new LinkedHashMap<>();

        for(byte b : data){
            map.put(b, map.getOrDefault(b,0)+1);
        }

        byte value = 0;
        int min = Integer.MAX_VALUE;

        for(Map.Entry<Byte,Integer> e : map.entrySet()){

            if(e.getValue() < min){

                min = e.getValue();
                value = e.getKey();

            }

        }

        byte[] result = new byte[2];

        result[0] = value;
        result[1] = (byte)min;

        service.submitData(studentCode,qCode,result);

    }

}
