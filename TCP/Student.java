/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TCP;

import java.io.Serializable;

public class Student implements Serializable {

    private static final long serialVersionUID = 20151107L;

    private int id;
    private String code;
    private float gpa;
    private String gpaLetter;

    public Student() {
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public float getGpa() {
        return gpa;
    }

    public String getGpaLetter() {
        return gpaLetter;
    }

    public void setGpaLetter(String gpaLetter) {
        this.gpaLetter = gpaLetter;
    }
}
