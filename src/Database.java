import classes.Student;
import classes.Subject;
import classes.Teacher;

import javax.swing.plaf.nimbus.State;
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
        if (preparedStatement.executeUpdate() > 0) {
            System.out.println("Successfully added!");
        } else {
            System.out.println("Error occurred!");
        }
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

    public boolean teacherLogin(String teacherName, String password) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from teacher where name='"+teacherName+"' and password='"+password+"'");
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    public boolean studentLogin(String studentName, String password) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from student where name='"+studentName+"' and password='"+password+"'");
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    public void gradeStudent(String teacherName) throws SQLException {
        System.out.println("Student name:");
        String studentName = scanner.next();
        System.out.println("Subject name:");
        String subjectName = scanner.next();
        int studentId = getStudentId(studentName);
        int teacherId = getTeacherId(teacherName);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from student_subject_connection where student_id="+studentId+" and subject_name='"+subjectName+"'");
        Statement statement1 = connection.createStatement();
        ResultSet resultSet1 = statement1.executeQuery("select * from teacher_student_connection where student_id="+studentId+" and teacher_id="+teacherId);
        Statement statement2 = connection.createStatement();
        ResultSet resultSet2 = statement2.executeQuery("select * from teacher_subject_connection where teacher_id="+teacherId+" and subject_name='"+subjectName+"'");

        if (resultSet.next() && resultSet1.next() && resultSet2.next()) {
            System.out.println("Grade:");
            int grade = scanner.nextInt();
            String query = "update student_subject_connection set grade=? where student_id=? and subject_name=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, grade);
            preparedStatement.setInt(2, studentId);
            preparedStatement.setString(3, subjectName);
            if (preparedStatement.executeUpdate() > 0) {
                System.out.println("Successfully graded!");
            } else {
                System.out.println("Error occurred!");
            }

        }
    }

    public Teacher getTeacherInfo(String teacherName) throws SQLException {
        Teacher teacher = null;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from teacher where name='" + teacherName + "'");
        if (resultSet.next()) {
            teacher = new Teacher(resultSet.getString("name"),resultSet.getString("email"),resultSet.getString("password"));
        }
        int teacherId = getTeacherId(teacherName);
        statement = connection.createStatement();
        resultSet = statement.executeQuery("select * from teacher_student_connection where teacher_id=" + teacherId);
        while (resultSet.next()) {
            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("select * from student where id="+resultSet.getInt("student_id"));
            if (resultSet1.next()) {
                teacher.addStudent(new Student(resultSet1.getString("name"),resultSet1.getString("email"),resultSet1.getString("password")));
            }
        }
        return teacher;
    }

    public Student getStudentInfo(String studentName) throws SQLException {
        Student student = null;
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from student where name='" + studentName + "'");
        if (resultSet.next()) {
            student = new Student(resultSet.getString("name"),resultSet.getString("email"),resultSet.getString("password"));
        }
        if (student != null) {
            int studentId = getStudentId(studentName);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from student_subject_connection where student_id=" + studentId);
            while (resultSet.next()) {
                Statement statement1 = connection.createStatement();
                ResultSet resultSet1 = statement1.executeQuery("select * from subject where name='"+resultSet.getString("subject_name") + "'");
                if (resultSet1.next()) {
                    student.addSubject(new Subject(resultSet1.getString("name"),resultSet1.getInt("credit")),resultSet.getInt("grade"));
                }
            }
        }
        return student;
    }
}
