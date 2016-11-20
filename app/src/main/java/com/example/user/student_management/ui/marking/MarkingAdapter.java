package com.example.user.student_management.ui.marking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.student_management.R;
import com.example.user.student_management.db.DatabaseHandler;
import com.example.user.student_management.model.Classes;
import com.example.user.student_management.model.Marking;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.model.Subject;

/**
 * Created by Khiem Ichigo on 10/30/2016.
 */

public class MarkingAdapter extends RecyclerView.Adapter<MarkingAdapter.ViewHolder> {
    public static final int HEADER = 0;
    public static final int INPUTROW = 1;

    Context context;


    private Classes _class;
    private Subject _subject;
    private Student student;


    private int[] mDataViewType;
    private double mark;

    public MarkingAdapter(Context context, Classes _class, Subject _subject,
                          Student student, int[] mDataViewType) {
        this.context = context;
        this._class = _class;
        this._subject = _subject;
        this.student = student;
        this.mDataViewType = mDataViewType;
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
            header.tvClassNameHeader.setText(String.format(context.getString(R.string.class_details_name), _class.get_name()));
            header.tvClassQuantityHeader.setText(String.format(context.getString(R.string.class_details_quantity),""+_class.get_quantity()));
            header.tvSemester.setText(String.format(context.getString(R.string.marking_semester), ""+_subject.getSubjectSemester()));
            header.tvSubject.setText(String.format(context.getString(R.string.marking_subject), _subject.getSubjectName()));
            header.tvMarkType.setText(String.format(context.getString(R.string.marking_type), _subject.getSubjectTypeOfMark()));

        } else if(holder.getItemViewType() == INPUTROW){
            final MarkingAdapter.MarkingInputRow inputRow = (MarkingInputRow) holder;
            inputRow.studentName.setText(student.getStudentName());
            inputRow.studentId.setText(student.getStudentId());

            inputRow.btnSaveMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    mark = Double.parseDouble(inputRow.edtMark.getText().toString());
                    inputRow.edtMark.setText(String.valueOf(mark));
                    Marking marking = new Marking(student,_class,_subject,mark);
                    db.markingStudent(marking);
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 2;
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
        Button btnSaveMark;
        public MarkingInputRow(View itemView) {
            super(itemView);
            studentName = (TextView) itemView.findViewById(R.id.studentMarkingName);
            studentId = (TextView) itemView.findViewById(R.id.studentMarkingId);
            edtMark = (EditText) itemView.findViewById(R.id.edtMark);
            btnSaveMark = (Button) itemView.findViewById(R.id.btnSaveMark);
        }
    }
}
