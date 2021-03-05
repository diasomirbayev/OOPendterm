package classes;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person{
    private ArrayList<Student> students;
    private ArrayList<Subject> subjects;

    public Teacher(String name, String email, String password) {
        super(name, email, password);
        this.students = new ArrayList<Student>();
        this.subjects = new ArrayList<Subject>();
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }
    public void addStudent(Student student){
        students.add(student);
    }
    public void removeStudent(Student student){
        students.remove(student);
    }

    @Override
    public String toString() {
        return "Teacher{" + super.toString() +
                "students=" + students +
                ", subjects=" + subjects +
                '}';
    }
}

