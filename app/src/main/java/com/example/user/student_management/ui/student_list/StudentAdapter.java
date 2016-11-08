package com.example.user.student_management.ui.student_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.student_management.db.DatabaseHandler;
import com.example.user.student_management.model.Classes;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.R;
import com.example.user.student_management.ui.class_list.ClassDetailsActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 10/11/2016.
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder>{
    private List<Student> studentList;
    private Context context;

    public StudentAdapter() {
        studentList = new ArrayList<>();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.student_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.bindData(student);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void refreshData(List<Student> studentList) {
        this.studentList = studentList;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView studentId, studentName, yearOfBirth;
        private ImageView imgGender;
        private Button btnaddToClass;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            studentName = (TextView) itemView.findViewById(R.id.studentName);
            studentId = (TextView) itemView.findViewById(R.id.studentId);
            yearOfBirth = (TextView) itemView.findViewById(R.id.yearOfBirth);
            imgGender = (ImageView) itemView.findViewById(R.id.imgGender);
            btnaddToClass = (Button) itemView.findViewById(R.id.btnAddToClass);
        }

        public void bindData(final Student student ){
            studentName.setText(student.getStudentName());
            studentId.setText(String.valueOf(student.getStudentId()));
            yearOfBirth.setText(String.valueOf(student.getYearOfBirth()));
            imgGender.setImageResource(student.isMale() ? R.drawable.ic_male : R.drawable.ic_female);
            btnaddToClass.setVisibility(student.isChecked() ? View.VISIBLE : View.GONE);
            btnaddToClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = ((Activity)context).getIntent();
                    /**receive className & classQuantity from ClassDetailsActivity**/
                    String className = i.getStringExtra(ClassDetailsActivity.CLASS_NAME_TAG);
                    String classQuantity = i.getStringExtra(ClassDetailsActivity.CLASS_QUANTITY_TAG);
                    /**create a new List<Student> to store students added into selected class**//*
                    List<Student> listOfStudent = new ArrayList<Student>();
                    Student student = studentList.get(getAdapterPosition());
                    listOfStudent.add(student);
                    *//**put the list, className & classQuantity back to the ClassDetailsActivity**//*
                    i.putExtra("studentList", (Serializable) listOfStudent);*/
                    Classes _class = new Classes(className,Integer.parseInt(classQuantity));
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.addStudentToClass(_class,studentList,getAdapterPosition());


                    i.putExtra(ClassDetailsActivity.CLASS_NAME_TAG, className);
                    i.putExtra(ClassDetailsActivity.CLASS_QUANTITY_TAG, classQuantity);
                    ((Activity) context).setResult(((Activity) context).RESULT_OK,i);
                    ((Activity) context).finish();

                }
            });

        }
    }

}
