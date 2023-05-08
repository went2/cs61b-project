import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CS61B {
    public List<Number> ids;
    public String semester;
    public CS61BStudent[] students;
    public Map<Integer,CS61BStudent> idToStudent = new HashMap<>();

    public static String university = "UC Berkeley";

    public CS61B(int capacity, List<Integer> ids, String semester) {
        students = new CS61BStudent[capacity];
        for(int i=0; i<capacity; i++) {
            int stuId = ids.get(i);
            CS61BStudent newStu = new CS61BStudent(stuId);
            students[i] = newStu;
            idToStudent.put(stuId, newStu);
        }
        this.semester = semester;
    }

    public void makeAllStudentWatchLecture() {
        for(CS61BStudent student : students) {
            student.watchLecture();
        }
    }

    public int updateGrade(int sid, int points) {
        CS61BStudent stu = idToStudent.get(sid);
        stu.grade += points;
        return stu.grade;
    }

    public static void changeUniversity(String newUniversity) {
        university = newUniversity;
    }
}