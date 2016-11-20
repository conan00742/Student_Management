package com.example.user.student_management.ui.student_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.student_management.RecyclerViewClickListener;
import com.example.user.student_management.db.DatabaseHandler;
import com.example.user.student_management.model.Classes;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.R;
import com.example.user.student_management.ui.class_list.ClassDetailsActivity;
import com.example.user.student_management.ui.class_list.ClassDetailsAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 10/11/2016.
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> implements Filterable{
    private List<Student> originalStudentList;
    private List<Student> filteredData;
    private Context context;
    RecyclerViewClickListener viewClickListener;
    ContextMenu.ContextMenuInfo info;

    public StudentAdapter() {
        originalStudentList = new ArrayList<>();
        filteredData = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.student_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Student student = filteredData.get(position);
        holder.bindData(student);
    }


    public void setViewClickListener(RecyclerViewClickListener viewClickListener) {
        this.viewClickListener = viewClickListener;
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }


    public Student getItemAtPosition(int position) {
        if (position >=0 && filteredData!= null ) {
        return filteredData.get(position);}
        else {
            return null;
        }
    }

    public void refreshData(List<Student> studentList) {
        this.originalStudentList = studentList;
        filteredData = originalStudentList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if(TextUtils.isEmpty(constraint.toString().trim())){
                    filterResults.values = originalStudentList;
                    filterResults.count = originalStudentList.size();
                }else{
                    List<Student> filterResultsData = new ArrayList<>();
                    for(Student student : originalStudentList){
                        if(student.getStudentName().toLowerCase().contains(constraint.toString().toLowerCase())){
                            filterResultsData.add(student);
                        }
                    }

                    filterResults.values = filterResultsData;
                    filterResults.count = filterResultsData.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (List<Student>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
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


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(viewClickListener != null) {
                        viewClickListener.recyclerViewListClick(getLayoutPosition());
                    }
                }
            });


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(viewClickListener != null){
                        viewClickListener.recyclerViewListLongClick(getLayoutPosition());
                    }
                    return false;
                }
            });
        }

        public void bindData(Student student){
            studentName.setText(student.getStudentName());
            studentId.setText(student.getStudentId());
            yearOfBirth.setText(student.getDateOfBirth());
            imgGender.setImageResource(student.isMale() ? R.drawable.ic_male : R.drawable.ic_female);
            btnaddToClass.setVisibility(!student.isChecked() ? View.GONE : View.VISIBLE);
            btnaddToClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = ((Activity)context).getIntent();
                    /**receive className & classQuantity from ClassDetailsActivity**/
                    String className = i.getStringExtra(ClassDetailsActivity.CLASS_NAME_TAG);
                    String classQuantity = i.getStringExtra(ClassDetailsActivity.CLASS_QUANTITY_TAG);
                    /**create a new List<Student> to store students added into selected class**/

                    /**put the list, className & classQuantity back to the ClassDetailsActivity**/
                    Classes _class = new Classes(className,Integer.parseInt(classQuantity));
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.addStudentToClass(_class,filteredData,getAdapterPosition());


                    i.putExtra(ClassDetailsActivity.CLASS_NAME_TAG, className);
                    i.putExtra(ClassDetailsActivity.CLASS_QUANTITY_TAG, classQuantity);
                    ((Activity) context).setResult(((Activity) context).RESULT_OK,i);
                    ((Activity) context).finish();

                }
            });

        }

    }

}
