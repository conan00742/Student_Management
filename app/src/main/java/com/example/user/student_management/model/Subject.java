package com.example.user.student_management.model;

/**
 * Created by Khiem Ichigo on 10/26/2016.
 */

public class Subject {
    private int subjectID;
    private String subjectName;
    private int subjectSemester;
    private String subjectTypeOfMark;

    public Subject() {
    }

    public Subject(int subjectID, String subjectName) {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
    }

    public int getSubjectSemester() {
        return subjectSemester;
    }

    public void setSubjectSemester(int subjectSemester) {
        this.subjectSemester = subjectSemester;
    }

    public String getSubjectTypeOfMark() {
        return subjectTypeOfMark;
    }

    public void setSubjectTypeOfMark(String subjectTypeOfMark) {
        this.subjectTypeOfMark = subjectTypeOfMark;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**TODO: Sử dụng Recycler view chia view ra làm 2 phần:
     * HEADER: chứa TÊN LỚP, SĨ SỐ (ClassDetailsActivity)
     *         chứa TÊN LỚP, MÔN, HỌC KỲ, LOẠI ĐIỂM (MarkingActivity)
     * INPUT ROW: chứa danh sách Student (họ tên, giới tính, năm sinh, mã số) thông qua student_list_row(ClassDetailsActivity)
     *            chứa danh sách Student (họ tên, edit text nhập điểm) thông qua marking_student_row(MarkingActivity)
     */
}
