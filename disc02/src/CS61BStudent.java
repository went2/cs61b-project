public class CS61BStudent { // Class Declaration
    public int idNumber; // Instance Variables
    public int grade;
    public static String instructor = "Hug"; // Class (Static) Variables
    public CS61BStudent(int id) { // Constructor
        this.idNumber = id;
        this.grade = 100;
    }
    public void watchLecture() { // Instance Method

    }
    public static String getInstructor() { // Static method
        return CS61BStudent.instructor;
    }
}