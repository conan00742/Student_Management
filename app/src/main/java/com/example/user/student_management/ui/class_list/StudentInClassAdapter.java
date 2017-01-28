package com.example.user.student_management.ui.class_list;

import android.content.Context;
import android.graphics.Color;
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

import com.example.user.student_management.MarkingClickListener;
import com.example.user.student_management.R;
import com.example.user.student_management.RecyclerViewClickListener;
import com.example.user.student_management.model.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khiem Ichigo on 10/27/2016.
 */

public class StudentInClassAdapter extends RecyclerView.Adapter<StudentInClassAdapter.ViewHolder> implements Filterable{
    public static final int HEADER = 0;
    public static final int INPUTROW = 1;

    Context context;
    private List<Student> studentList = new ArrayList<>();
    private List<Student> filteredData;
    private int[] mDataViewType;
    private String className;
    private int classQuantity;
    RecyclerViewClickListener viewClickListener;
    MarkingClickListener markingClickListener;


    public StudentInClassAdapter(Context context, List<Student> studentList,
                                 int[] mDataViewType, String className, int classQuantity) {
        this.context = context;
        this.studentList = studentList;
        this.mDataViewType = mDataViewType;
        this.className = className;
        this.classQuantity = classQuantity;
        filteredData = new ArrayList<>();
    }

    public void setViewClickListener(RecyclerViewClickListener viewClickListener) {
        this.viewClickListener = viewClickListener;
    }

    public void setMarkingClickListener(MarkingClickListener markingClickListener) {
        this.markingClickListener = markingClickListener;
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
            header.tvClassName.setTextColor(Color.BLUE);
            header.tvClassQuantity.setText(String.format(context.getString(R.string.class_details_quantity),""+classQuantity));
            header.tvClassQuantity.setTextColor(Color.BLUE);

        } else if(holder.getItemViewType() == INPUTROW){
            Student student = filteredData.get(position - 1);
            ClassDetailsInputRowViewHolder inputRow = (ClassDetailsInputRowViewHolder) holder;
            inputRow.studentName.setText(student.getStudentName());
            inputRow.studentId.setText(student.getStudentId());
            inputRow.yearOfBirth.setText(student.getDateOfBirth());
            inputRow.imgGender.setImageResource(student.isMale() ? R.drawable.ic_male : R.drawable.ic_female);
            inputRow.btnaddToClass.setVisibility(View.GONE);
            inputRow.btnMarking.setVisibility(View.VISIBLE);
            inputRow.btnCalculate.setVisibility(View.VISIBLE);
        }
    }


    public Student getItemAtPosition(int position) {
        if (position >=0 && filteredData!= null ) {
            return filteredData.get(position);}
        else {
            return null;
        }
        /*if(position >= 0 && studentList != null){
            return studentList.get(position);
        }else{
            return null;
        }*/
    }

    @Override
    public int getItemCount() {
        return filteredData.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER : INPUTROW;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if(TextUtils.isEmpty(constraint.toString().trim())){
                    filterResults.values = studentList;
                    filterResults.count = studentList.size();
                }else{
                    List<Student> filterResultsData = new ArrayList<>();
                    for(Student student : studentList){
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
        filteredData = this.studentList;
        notifyDataSetChanged();
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
        Button btnaddToClass,btnMarking, btnCalculate;
        public ClassDetailsInputRowViewHolder(View itemView) {
            super(itemView);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(viewClickListener != null){
                        viewClickListener.recyclerViewListClick(getLayoutPosition() - 1);
                    }
                }
            });


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
            btnMarking = (Button) itemView.findViewById(R.id.btnMarking);

            btnMarking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(markingClickListener != null){
                        markingClickListener.recyclerViewButtonClickListener(getLayoutPosition() - 1);
                    }
                }
            });

            btnCalculate = (Button) itemView.findViewById(R.id.btnCalculate);

            btnCalculate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(markingClickListener != null){
                        markingClickListener.recyclerViewCalculateButtonClickListener(getLayoutPosition() - 1);
                    }
                }
            });


        }


    }

}
