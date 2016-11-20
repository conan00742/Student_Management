package com.example.user.student_management.ui.class_list;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.student_management.R;
import com.example.user.student_management.RecyclerViewClickListener;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.ui.student_list.StudentDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khiem Ichigo on 10/27/2016.
 */

public class ClassDetailsAdapter extends RecyclerView.Adapter<ClassDetailsAdapter.ViewHolder>{
    public static final int HEADER = 0;
    public static final int INPUTROW = 1;

    Context context;
    ContextMenu.ContextMenuInfo info;
    private List<Student> studentList = new ArrayList<>();
    private int[] mDataViewType;
    private String className;
    private int classQuantity;
    RecyclerViewClickListener viewClickListener;

    public ClassDetailsAdapter(Context context, List<Student> studentList,
                               int[] mDataViewType, String className, int classQuantity) {
        this.context = context;
        this.studentList = studentList;
        this.mDataViewType = mDataViewType;
        this.className = className;
        this.classQuantity = classQuantity;
    }

    public ClassDetailsAdapter(){

    }

    public void setViewClickListener(RecyclerViewClickListener viewClickListener) {
        this.viewClickListener = viewClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == HEADER){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_class_details_header,parent,false);
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
            header.tvClassName.setText(String.format(context.getString(R.string.class_details_name), className));
            header.tvClassQuantity.setText(String.format(context.getString(R.string.class_details_quantity),""+classQuantity));

        } else if(holder.getItemViewType() == INPUTROW){
            Student student = studentList.get(position - 1);
            ClassDetailsInputRowViewHolder inputRow = (ClassDetailsInputRowViewHolder) holder;
            inputRow.studentName.setText(student.getStudentName());
            inputRow.studentId.setText(student.getStudentId());
            inputRow.yearOfBirth.setText(student.getDateOfBirth());
            inputRow.imgGender.setImageResource(student.isMale() ? R.drawable.ic_male : R.drawable.ic_female);
            inputRow.btnaddToClass.setVisibility(!student.isChecked() ? View.GONE : View.VISIBLE);
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


    /**
     *
     *
     *
     *
     *
     *
     *
     * **/

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);

        }





    }

    public void refreshData(List<Student> studentList) {
        this.studentList = studentList;
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

    public class ClassDetailsInputRowViewHolder extends ViewHolder{
        TextView studentId, studentName, yearOfBirth;
        ImageView imgGender;
        Button btnaddToClass;
        public ClassDetailsInputRowViewHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(viewClickListener != null){
                        viewClickListener.recyclerViewListLongClick(getAdapterPosition());
                    }
                    return false;
                }
            });
            studentName = (TextView) itemView.findViewById(R.id.studentName);
            studentId = (TextView) itemView.findViewById(R.id.studentId);
            yearOfBirth = (TextView) itemView.findViewById(R.id.yearOfBirth);
            imgGender = (ImageView) itemView.findViewById(R.id.imgGender);
            btnaddToClass = (Button) itemView.findViewById(R.id.btnAddToClass);
        }


    }

}
