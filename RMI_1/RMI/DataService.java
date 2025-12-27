/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DataService extends Remote {
    Object requestData(String studentCode, String qCode) throws RemoteException;
    void submitData(String studentCode, String qCode, Object data) throws RemoteException;
}
