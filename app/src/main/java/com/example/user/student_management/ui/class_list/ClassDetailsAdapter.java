package com.example.user.student_management.ui.class_list;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.student_management.R;
import com.example.user.student_management.model.Classes;
import com.example.user.student_management.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khiem Ichigo on 10/27/2016.
 */

public class ClassDetailsAdapter extends RecyclerView.Adapter<ClassDetailsAdapter.ViewHolder> {
    public static final int HEADER = 0;
    public static final int INPUTROW = 1;

    /*private Classes _class = new Classes();*/
    private List<Student> studentList = new ArrayList<>();
    private int[] mDataViewType;
    private String className;
    private int classQuantity;

    public ClassDetailsAdapter(List<Student> studentList, int[] mDataViewType, String className, int classQuantity) {
        this.studentList = studentList;
        this.mDataViewType = mDataViewType;
        this.className = className;
        this.classQuantity = classQuantity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == HEADER){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_class_details_header,parent,false);
            return new ClassDetailsHeaderViewHolder(v);
        }else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_row, parent, false);
            return new ClassDetailsInputRowViewHolder(v);
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(holder.getItemViewType() == HEADER){
            ClassDetailsHeaderViewHolder header = (ClassDetailsHeaderViewHolder) holder;
            header.tvClassName.setText(className);
            header.tvClassQuantity.setText(classQuantity);

        } else if(holder.getItemViewType() == INPUTROW){
            Student student = studentList.get(position);
            ClassDetailsInputRowViewHolder inputRow = (ClassDetailsInputRowViewHolder) holder;
            inputRow.studentName.setText(student.getStudentName());
            inputRow.studentId.setText(String.valueOf(student.getStudentId()));
            inputRow.yearOfBirth.setText(String.valueOf(student.getYearOfBirth()));
            inputRow.imgGender.setImageResource(student.isMale() ? R.drawable.ic_male : R.drawable.ic_female);
            inputRow.btnaddToClass.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return studentList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : INPUTROW;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ClassDetailsHeaderViewHolder extends ViewHolder {
        TextView tvClassName;
        TextView tvClassQuantity;
        public ClassDetailsHeaderViewHolder(View itemView) {
            super(itemView);
            this.tvClassName = (TextView) itemView.findViewById(R.id.tvClassName);
            this.tvClassQuantity = (TextView) itemView.findViewById(R.id.tvClassQuantity);
        }
    }

    public class ClassDetailsInputRowViewHolder extends ViewHolder {
        TextView studentId, studentName, yearOfBirth;
        ImageView imgGender;
        Button btnaddToClass;
        public ClassDetailsInputRowViewHolder(View itemView) {
            super(itemView);
            studentName = (TextView) itemView.findViewById(R.id.studentName);
            studentId = (TextView) itemView.findViewById(R.id.studentId);
            yearOfBirth = (TextView) itemView.findViewById(R.id.yearOfBirth);
            imgGender = (ImageView) itemView.findViewById(R.id.imgGender);
            btnaddToClass = (Button) itemView.findViewById(R.id.btnAddToClass);
        }
    }
}
