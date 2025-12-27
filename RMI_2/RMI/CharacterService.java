/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CharacterService extends Remote {
    String requestCharacter(String studentCode, String qCode) throws RemoteException;
    void submitCharacter(String studentCode, String qCode, String strSubmit) throws RemoteException;
}
