package com.example.user.student_management.ui.marking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.student_management.R;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.ui.class_list.ClassDetailsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khiem Ichigo on 10/30/2016.
 */

public class MarkingAdapter extends RecyclerView.Adapter<MarkingAdapter.ViewHolder> {
    public static final int HEADER = 0;
    public static final int INPUTROW = 1;

    Context context;
    private List<Student> studentList = new ArrayList<>();
    private int[] mDataViewType;
    private String className;
    private int classQuantity;
    private int semester;
    private String subject;
    private String typeOfMark;
    private double mark;

    public MarkingAdapter(Context context, List<Student> studentList, int[] mDataViewType, String className,
                          int classQuantity, int semester, String subject, String typeOfMark) {
        this.context = context;
        this.studentList = studentList;
        this.mDataViewType = mDataViewType;
        this.className = className;
        this.classQuantity = classQuantity;
        this.semester = semester;
        this.subject = subject;
        this.typeOfMark = typeOfMark;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == HEADER){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_marking_header,parent,false);
            return new MarkingAdapter.MarkingHeader(v);
        }else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_list_row, parent, false);
            return new MarkingAdapter.MarkingInputRow(v);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(holder.getItemViewType() == HEADER){
            MarkingAdapter.MarkingHeader header = (MarkingHeader) holder;
            header.tvClassNameHeader.setText(String.format(context.getString(R.string.class_details_name), className));
            header.tvClassQuantityHeader.setText(String.format(context.getString(R.string.class_details_quantity),""+classQuantity));
            header.tvSemester.setText(String.format(context.getString(R.string.marking_semester), ""+semester));
            header.tvSubject.setText(String.format(context.getString(R.string.marking_subject), subject));
            header.tvMarkType.setText(String.format(context.getString(R.string.marking_type), typeOfMark));

        } else if(holder.getItemViewType() == INPUTROW){
            Student student = studentList.get(position - 1);
            MarkingAdapter.MarkingInputRow inputRow = (MarkingInputRow) holder;
            inputRow.studentName.setText(student.getStudentName());
            inputRow.studentId.setText(String.valueOf(student.getStudentId()));
            inputRow.edtMark.setText(String.valueOf(mark));
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

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class MarkingHeader extends ViewHolder {
        TextView tvClassNameHeader;
        TextView tvClassQuantityHeader;
        TextView tvSemester;
        TextView tvSubject;
        TextView tvMarkType;
        public MarkingHeader(View itemView) {
            super(itemView);
            tvClassNameHeader = (TextView) itemView.findViewById(R.id.tvClassNameHeader);
            tvClassQuantityHeader = (TextView) itemView.findViewById(R.id.tvClassQuantityHeader);
            tvSemester = (TextView) itemView.findViewById(R.id.tvSemester);
            tvSubject = (TextView) itemView.findViewById(R.id.tvSubject);
            tvMarkType = (TextView) itemView.findViewById(R.id.tvMarkType);
        }
    }

    public class MarkingInputRow extends ViewHolder {
        TextView studentId, studentName;
        EditText edtMark;
        public MarkingInputRow(View itemView) {
            super(itemView);
            studentName = (TextView) itemView.findViewById(R.id.studentMarkingName);
            studentId = (TextView) itemView.findViewById(R.id.studentMarkingId);
            edtMark = (EditText) itemView.findViewById(R.id.edtMark);
        }
    }
}
