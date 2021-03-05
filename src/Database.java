import classes.Student;
import classes.Subject;
import classes.Teacher;

import java.sql.*;

public class Database {
    private Connection connection;

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moodle", "root", "");
        return connection;
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


    public void addStudent(Student student) throws SQLException {
        if (getStudentId(student.getName()) > 0) {
            return;
        }
        String query = "insert into student(name,email,password) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, student.getName());
        preparedStatement.setString(2, student.getEmail());
        preparedStatement.setString(3, student.getPassword());
        preparedStatement.executeUpdate();
    }

    public void addTeacher(Teacher teacher) throws SQLException {
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

    public void addSubject(Subject subject) throws SQLException {
        String query = "insert into subject values(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, subject.getName());
        preparedStatement.setInt(2, subject.getCredit());
        preparedStatement.executeUpdate();
    }

    public void addStudentToTeacher(Student student, Teacher teacher) throws SQLException {
        int studentId = getStudentId(student.getName());
        int teacherId = getTeacherId(teacher.getName());
        if(studentId>0 && teacherId>0){
            String query = "insert into teacher_student_connection values(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, teacherId);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();
        }
    }
    public void addSubjectToTeacher(Subject subject, Teacher teacher) throws SQLException {
        int teacherId = getTeacherId(teacher.getName());
        if(teacherId>0){
            String query = "insert into teacher_subject_connection values(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, teacherId);
            preparedStatement.setString(2, subject.getName());
            preparedStatement.executeUpdate();
        }
    }
    public void addSubjectToStudent(Subject subject, Student student) throws SQLException {
        int studentId = getStudentId(student.getName());
        if(studentId>0){
            String query = "insert into student_subject_connection values(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setString(2, subject.getName());
            preparedStatement.setInt(3, -1);
            preparedStatement.executeUpdate();
        }
    }


}
