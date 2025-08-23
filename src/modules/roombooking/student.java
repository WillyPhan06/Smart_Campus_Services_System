
import java.util.Scanner;

public class student {
    private String studentName;
    private String studentID;
    public student(String studentName, String studentID) {
        this.studentName = studentName;
        this.studentID = studentID;
    }

    public student() {
        this.studentName = "";
        this.studentID = String.valueOf(0);
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentID() {
        return studentID;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    public void inputStudentDetails() {
        Scanner sr= new Scanner(System.in);
        System.out.println("Enter student name: ");
        this.studentName = sr.nextLine();
        System.err.println("Enter student ID: ");
        this.studentID = String.valueOf(sr.nextInt());
    }

}
