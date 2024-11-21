import java.sql.*;
import java.util.Scanner;

public class CollegeStudentManagementSystem {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/college_db"; // Replace with your database name
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = ""; // Replace with your MySQL password

    private Connection connection;
    private Scanner scanner;

    public CollegeStudentManagementSystem() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Database connected successfully.");
            scanner = new Scanner(System.in); // Initialize Scanner once
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addStudentRecord() {
        try {
            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Student Age: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Student Course: ");
            String course = scanner.nextLine();

            System.out.print("Enter Student Year: ");
            int year = Integer.parseInt(scanner.nextLine());

            String query = "INSERT INTO students (name, age, course, year) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, course);
            preparedStatement.setInt(4, year);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Student record added successfully.");
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void updateStudentRecord() {
        try {
            System.out.print("Enter ID of the student to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter New Student Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter New Student Age: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter New Student Course: ");
            String course = scanner.nextLine();

            System.out.print("Enter New Student Year: ");
            int year = Integer.parseInt(scanner.nextLine());

            String query = "UPDATE students SET name=?, age=?, course=?, year=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, course);
            preparedStatement.setInt(4, year);
            preparedStatement.setInt(5, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Student record updated successfully.");
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void viewStudentRecords() {
        try {
            String query = "SELECT * FROM students";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("ID | Name | Age | Course | Year");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String course = resultSet.getString("course");
                int year = resultSet.getInt("year");

                System.out.printf("%d | %s | %d | %s | %d%n", id, name, age, course, year);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudentRecord() {
        try {
            System.out.print("Enter ID of the student to delete: ");
            int id = Integer.parseInt(scanner.nextLine());

            String query = "DELETE FROM students WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Student record deleted successfully.");
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void menu() {
        while (true) {
            System.out.println("\nCollege Student Management System");
            System.out.println("1. Add Student Record");
            System.out.println("2. Update Student Record");
            System.out.println("3. View Student Records");
            System.out.println("4. Delete Student Record");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextLine()) {
                String input = scanner.nextLine();

                try {
                    int choice = Integer.parseInt(input);

                    switch (choice) {
                        case 1:
                            addStudentRecord();
                            break;
                        case 2:
                            updateStudentRecord();
                            break;
                        case 3:
                            viewStudentRecords();
                            break;
                        case 4:
                            deleteStudentRecord();
                            break;
                        case 5:
                            System.out.println("Exiting program.");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            } else {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    public static void main(String[] args) {
        CollegeStudentManagementSystem system = new CollegeStudentManagementSystem();
        system.menu();
    }
}
