/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RMI;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObjectService extends Remote {
    Serializable requestObject(String studentCode, String qAlias) throws RemoteException;
    void submitObject(String studentCode, String qAlias, Serializable object) throws RemoteException;
}
