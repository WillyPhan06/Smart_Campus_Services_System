import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class room {
    private  String roomName;
    private int roomID;
    private LocalDateTime datebooking;
    private LocalTime startbooking;
    private LocalTime endbooking;
    private String studentnamebooking;
    private  String studentIDbooking;

    public room() {
        this.roomName = "";
        this.roomID = 0;
        this.datebooking = null;
        this.startbooking = null;
        this.endbooking = null;
        this.studentnamebooking = "";
        this.studentIDbooking = "";
    }

    public room(int roomID, String roomName, int date, int startHours, int startMinutes, 
                int endHours, int endMinutes, String studentName, String studentID) {
        this.roomID = roomID;
        this.roomName = roomName;

        // Giả sử năm 2023, tháng 1 cho đơn giản
        this.datebooking = LocalDate.of(2023, 1, date).atStartOfDay();
        this.startbooking = LocalTime.of(startHours, startMinutes);
        this.endbooking = LocalTime.of(endHours, endMinutes);
        this.studentnamebooking = studentName;
        this.studentIDbooking = studentID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public LocalDateTime getDatebooking() {
        return datebooking;
    }

    public void setDatebooking(LocalDateTime datebooking) {
        this.datebooking = datebooking;
    }

    public LocalTime getStartbooking() {
        return startbooking;
    }

    public void setStartbooking(LocalTime startbooking) {
        this.startbooking = startbooking;
    }

    public LocalTime getEndbooking() {
        return endbooking;
    }

    public void setEndbooking(LocalTime endbooking) {
        this.endbooking = endbooking;
    }

    public String getStudentnamebooking() {
        return studentnamebooking;
    }

    public void setStudentnamebooking(String studentnamebooking) {
        this.studentnamebooking = studentnamebooking;
    }

    public String getStudentIDbooking() {
        return studentIDbooking;
    }

    public void setStudentIDbooking(String studentIDbooking) {
        this.studentIDbooking = studentIDbooking;
    }

    @Override
    public String toString() {
        return "Room Name: " + roomName + 
               "\nRoom ID: " + roomID +
               "\nBooking Date: " + (datebooking != null ? datebooking.toLocalDate() : "Not set") +
               "\nBooking Time: " + (startbooking != null ? startbooking : "Not set") + 
               " - " + (endbooking != null ? endbooking : "Not set") +
               "\nBooked by: " + studentnamebooking + 
               "\nStudent ID: " + studentIDbooking;
    }

    public void getRoomDeatils(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Room Name:");
        this.roomName=sc.nextLine();
        System.out.println("Enter Room ID:");
        this.roomID=sc.nextInt();
        student student1 = new student();
        sc.nextLine();
        System.out.println("Enter Student Name:");
        String name =  sc.nextLine();
        setStudentnamebooking(name);
        student1.setStudentName(name);
        System.out.println("Enter Student ID:");
        String ID =  sc.nextLine();
        setStudentIDbooking(ID);
        student1.setStudentID(ID);
        timeBookingRoom  timeAndDateBooking = new timeBookingRoom();
        System.out.println("Enter Start Date(yyyy-MM-dd):");
        LocalDateTime date = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
        setDatebooking(date);
        timeAndDateBooking.setDate(LocalDate.from(date));
        System.out.println("Enter Start Time (HH:mm):");
        LocalTime startTime = LocalTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));
        setStartbooking(startTime);
        timeAndDateBooking.setStartTime(startTime);
        System.out.println("Enter End Time (HH:mm):");
        LocalTime endTime = LocalTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));
        setEndbooking(endTime);
        timeAndDateBooking.setEndTime(endTime);

    }
    public void displayRoom(){
        System.out.println(toString());
    }

}