package classes;

import java.util.ArrayList;
import java.util.HashMap;

public class Student extends Person {
    private final ArrayList<Subject> takenSubjects;
    private final ArrayList<Integer> grades;

    public Student(String name, String email, String password) {
        super(name, email, password);
        takenSubjects = new ArrayList<>();
        grades = new ArrayList<>();
    }

    public ArrayList<Subject> getTakenSubjects() {
        return takenSubjects;
    }

    public ArrayList<Integer> getGrades() {
        return grades;
    }

    public void addSubject(Subject subject) {
        takenSubjects.add(subject);
        grades.add(-1);
    }

    //    public void removeSubject(String subjectName){
//        for(Subject subject : takenSubjects.keySet()){
//            if(subject.getName().equals(subjectName)){
//                takenSubjects.remove(subject);
//                break;
//            }
//        }
//    }
    public void removeSubject(String subjectName) {
        for (int i = 0; i < takenSubjects.size(); i++) {
            if (takenSubjects.get(i).getName().equals(subjectName)) {
                takenSubjects.remove(i);
                grades.remove(i);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Student{" + super.toString() +
                "takenSubjects=" + takenSubjects +
                '}';
    }
}
