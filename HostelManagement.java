import java.util.ArrayList;
import java.util.Scanner;

class Room {
    int roomNumber;
    int capacity;
    boolean isOccupied;

    Room(int roomNumber, int capacity, boolean isOccupied) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.isOccupied = isOccupied;
    }

    void displayRoom() {
        System.out.println("Room Number: " + roomNumber);
        System.out.println("Capacity: " + capacity);
        System.out.println("Occupied: " + (isOccupied ? "Yes" : "No"));
    }

    boolean isAvailable() {
        return !isOccupied;
    }
}

class Student {
    String name;
    int rollNumber;
    Room room;

    Student(String name, int rollNumber, Room room) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.room = room;
    }

    void displayStudent() {
        System.out.println("Student Name: " + name);
        System.out.println("Roll Number: " + rollNumber);
        if (room != null) {
            room.displayRoom();
        } else {
            System.out.println("No Room Assigned");
        }
    }

    void needsRoom() {
        if (room == null) {
            System.out.println("Needs Room");
        }
    }
}

class Warden {
    String wardenName;
    ArrayList<Student> students = new ArrayList<>();

    Warden(String wardenName) {
        this.wardenName = wardenName;
    }

    void addStudent(Student s) {
        students.add(s);
    }

    void displayWarden() {
        System.out.println("\nWarden Name: " + wardenName);
        for (Student s : students) {
            s.displayStudent();
            s.needsRoom();
            System.out.println("----");
        }
    }

    int totalStudents() {
        return students.size();
    }
}

class Hostel {
    String hostelName;
    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Warden> wardens = new ArrayList<>();

    Hostel(String hostelName) {
        this.hostelName = hostelName;
    }

    void addRoom(Room r) {
        rooms.add(r);
    }

    void addWarden(Warden w) {
        wardens.add(w);
    }

    void displayHostel() {
        System.out.println("\n=== Hostel Name: " + hostelName + " ===");

        System.out.println("\nAll Rooms:");
        for (Room r : rooms) {
            r.displayRoom();
            System.out.println("----");
        }

        System.out.println("\nAll Wardens and Students:");
        for (Warden w : wardens) {
            w.displayWarden();
            System.out.println("Total Students: " + w.totalStudents());
            System.out.println("==========");
        }
    }

    int totalRooms() {
        return rooms.size();
    }

    int totalWardens() {
        return wardens.size();
    }
}

public class HostelManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hostel hostel = new Hostel("Al-Noor Hostel");

        while (true) {
            System.out.println("\n=== Hostel Management Menu ===");
            System.out.println("1. Add Room");
            System.out.println("2. Add Warden");
            System.out.println("3. Add Student to Warden");
            System.out.println("4. Display Hostel Details");
            System.out.println("5. Exit");
            System.out.print("Choose Option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Enter Room Number: ");
                    int roomNum = sc.nextInt();
                    System.out.print("Enter Capacity: ");
                    int cap = sc.nextInt();
                    System.out.print("Is Occupied? (true/false): ");
                    boolean occ = sc.nextBoolean();
                    Room room = new Room(roomNum, cap, occ);
                    hostel.addRoom(room);
                    System.out.println("Room Added.");
                    break;

                case 2:
                    System.out.print("Enter Warden Name: ");
                    String wardenName = sc.nextLine();
                    Warden warden = new Warden(wardenName);
                    hostel.addWarden(warden);
                    System.out.println("Warden Added.");
                    break;

                case 3:
                    if (hostel.wardens.isEmpty()) {
                        System.out.println("No wardens available. Add a warden first.");
                        break;
                    }

                    System.out.print("Enter Student Name: ");
                    String sName = sc.nextLine();
                    System.out.print("Enter Roll Number: ");
                    int roll = sc.nextInt();
                    sc.nextLine();

                    Room assignedRoom = null;
                    if (!hostel.rooms.isEmpty()) {
                        System.out.print("Assign a room? (yes/no): ");
                        String assign = sc.nextLine();
                        if (assign.equalsIgnoreCase("yes")) {
                            System.out.println("Available Rooms:");
                            for (Room r : hostel.rooms) {
                                if (!r.isOccupied) {
                                    System.out.println("Room " + r.roomNumber);
                                }
                            }
                            System.out.print("Enter Room Number to Assign: ");
                            int rno = sc.nextInt();
                            sc.nextLine();

                            for (Room r : hostel.rooms) {
                                if (r.roomNumber == rno && !r.isOccupied) {
                                    assignedRoom = r;
                                    r.isOccupied = true;
                                    break;
                                }
                            }
                        }
                    }

                    Student student = new Student(sName, roll, assignedRoom);

                    // Select warden
                    System.out.println("Select Warden to Assign:");
                    for (int i = 0; i < hostel.wardens.size(); i++) {
                        System.out.println((i + 1) + ". " + hostel.wardens.get(i).wardenName);
                    }
                    int wardenIndex = sc.nextInt() - 1;
                    sc.nextLine();

                    if (wardenIndex >= 0 && wardenIndex < hostel.wardens.size()) {
                        hostel.wardens.get(wardenIndex).addStudent(student);
                        System.out.println("Student Added to Warden.");
                    } else {
                        System.out.println("Invalid Warden Selection.");
                    }
                    break;

                case 4:
                    hostel.displayHostel();
                    System.out.println("Total Rooms: " + hostel.totalRooms());
                    System.out.println("Total Wardens: " + hostel.totalWardens());
                    break;

                case 5:
                    System.out.println("Exiting... Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
