import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    static Scanner scanner = new Scanner(System.in);
    static Database database = new Database();
    //  git add .
    //  git commit -m "second commit"
    //  git push -u origin
    public static void adminInterface() throws SQLException, ClassNotFoundException {
        database.setConnection();
        int number = 0;
        while (number != 7) {
            System.out.println("===================================");
            System.out.println("Menu:");
            System.out.println("1. Add student");
            System.out.println("2. Add teacher");
            System.out.println("3. Add subject");
            System.out.println("4. Add student to teacher's student list");
            System.out.println("5. Add subject to student's taken subjects");
            System.out.println("6. Add subject to teacher's leading subjects");
            System.out.println("7. Exit");
            number = scanner.nextInt();
            switch (number) {
                case 1:
                    database.addStudent();
                    break;
                case 2:
                    database.addTeacher();
                    break;
                case 3:
                    database.addSubject();
                    break;
                case 4:
                    database.addStudentToTeacher();
                    break;

                case 5:
                    database.addSubjectToStudent();
                    break;
                case 6:
                    database.addSubjectToTeacher();
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Enter correct number!");
                    break;
            }
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("Choose your type: admin(a)/teacher(t)/student(s)");
        char c = scanner.next().charAt(0);
        switch (c){
            case 'a':
                adminInterface();
            case 't':
//                teacherInterface();
            case 's':
//                studentInterface();
        }
    }
}
