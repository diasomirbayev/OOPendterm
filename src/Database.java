import classes.Student;
import classes.Subject;
import classes.Teacher;

import java.sql.*;
import java.util.Scanner;

public class Database {
    private Connection connection;
    private Scanner scanner = new Scanner(System.in);

    public void setConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moodle", "root", "");
    }

    public int getStudentId(String name) throws SQLException {
        int id = -1;
        String query = "select id from student where name = '" + name + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        return id;
    }

    public int getTeacherId(String name) throws SQLException {
        int id = -1;
        String query = "select id from teacher where name = '" + name + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        if (resultSet.next()) {
            id = resultSet.getInt("id");
        }
        return id;
    }


    public void addStudent() throws SQLException {
        System.out.println("Name:");
        String name = scanner.next();
        System.out.println("Email:");
        String email = scanner.next();
        System.out.println("Password:");
        String password = scanner.next();
        Student student = new Student(name, email, password);
        if (getStudentId(student.getName()) > 0) {
            return;
        }
        String query = "insert into student(name,email,password) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, student.getName());
        preparedStatement.setString(2, student.getEmail());
        preparedStatement.setString(3, student.getPassword());
        if (preparedStatement.executeUpdate() > 0) {
            System.out.println("Successfully added!");
        } else {
            System.out.println("Error occurred!");
        }
    }

    public void addTeacher() throws SQLException {
        System.out.println("Name:");
        String name = scanner.next();
        System.out.println("Email:");
        String email = scanner.next();
        System.out.println("Password:");
        String password = scanner.next();
        Teacher teacher = new Teacher(name, email, password);
        if (getTeacherId(teacher.getName()) > 0) {
            return;
        }
        String query = "insert into teacher(name,email,password) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, teacher.getName());
        preparedStatement.setString(2, teacher.getEmail());
        preparedStatement.setString(3, teacher.getPassword());
        preparedStatement.executeUpdate();
    }

    public void addSubject() throws SQLException {
        System.out.println("Name:");
        String name = scanner.next();
        System.out.println("Credit:");
        int credit = scanner.nextInt();
        Subject subject = new Subject(name, credit);
        String query = "insert into subject values(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, subject.getName());
        preparedStatement.setInt(2, subject.getCredit());
        preparedStatement.executeUpdate();
    }

    public void addStudentToTeacher() throws SQLException {
        System.out.println("Teacher name:");
        String teacherName = scanner.next();
        System.out.println("Student name:");
        String studentName = scanner.next();
        int studentId = getStudentId(studentName);
        int teacherId = getTeacherId(teacherName);
        if (studentId > 0 && teacherId > 0) {
            String query = "insert into teacher_student_connection values(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, teacherId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();
        }
    }

    public void addSubjectToTeacher() throws SQLException {
        System.out.println("Teacher name:");
        String teacherName = scanner.next();
        System.out.println("Subject name:");
        String subjectName = scanner.next();
        int teacherId = getTeacherId(teacherName);
        if (teacherId > 0) {
            String query = "insert into teacher_subject_connection values(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, teacherId);
            preparedStatement.setString(2, subjectName);
            preparedStatement.executeUpdate();
        }
    }

    public void addSubjectToStudent() throws SQLException {
        System.out.println("Student name:");
        String studentName = scanner.next();
        System.out.println("Subject name:");
        String subjectName = scanner.next();
        int studentId = getStudentId(studentName);
        if (studentId > 0) {
            String query = "insert into student_subject_connection values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, subjectName);
            preparedStatement.setInt(3, -1);
            preparedStatement.executeUpdate();
        }
    }


}
