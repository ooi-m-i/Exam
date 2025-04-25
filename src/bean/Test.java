package bean;

public class Test implements java.io.Serializable {
    private Student student;
    private String classNum;
    private Subject subject;
    private School school;
    private int no;
    private int point;

    // Student
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    // ClassNum
    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    // Subject
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    // School
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    // No
    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    // Point
    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}