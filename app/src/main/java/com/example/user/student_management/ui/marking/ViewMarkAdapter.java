package com.example.user.student_management.ui.marking;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.student_management.R;
import com.example.user.student_management.model.Classes;
import com.example.user.student_management.model.Marking;
import com.example.user.student_management.model.Student;
import com.example.user.student_management.model.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 11/13/2016.
 */

public class ViewMarkAdapter extends RecyclerView.Adapter<ViewMarkAdapter.ViewHolder> {
    public static final int HEADER = 0;
    public static final int INPUTROW = 1;

    Context context;
    private List<Marking> markingList = new ArrayList<>();
    private int[] mDataViewType;
    Classes _class;
    Subject _subject;

    public ViewMarkAdapter(){
        markingList = new ArrayList<>();
    }

    public ViewMarkAdapter(Context context, List<Marking> markingList, int[] mDataViewType,
                           Classes _class, Subject _subject) {
        this.context = context;
        this.markingList = markingList;
        this.mDataViewType = mDataViewType;
        this._class = _class;
        this._subject = _subject;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == HEADER){
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_marking_header,parent,false);
            return new ViewMarkAdapter.ViewMarkHeader(v);
        }else{
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_mark_list_row, parent, false);
            return new ViewMarkAdapter.ViewMarkInputRow(v);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(holder.getItemViewType() == HEADER){
            ViewMarkAdapter.ViewMarkHeader header = (ViewMarkHeader) holder;
            //className
            header.tvClassNameHeader.setText(String.format(context.getString(R.string.class_details_name), _class.get_name()));
            header.tvClassNameHeader.setTextColor(Color.BLUE);
            //classQuantity
            header.tvClassQuantityHeader.setText(String.format(context.getString(R.string.class_details_quantity), String.valueOf(_class.get_quantity())));
            header.tvClassQuantityHeader.setTextColor(Color.BLUE);
            //Semester
            header.tvSemester.setText(String.format(context.getString(R.string.marking_semester), String.valueOf(_subject.getSubjectSemester())));
            header.tvSemester.setTextColor(Color.RED);
            //Subject
            header.tvSubject.setText(String.format(context.getString(R.string.marking_subject), _subject.getSubjectName()));
            header.tvSubject.setTextColor(Color.RED);
            //Type Of Mark
            header.tvMarkType.setText(String.format(context.getString(R.string.marking_type), _subject.getSubjectTypeOfMark()));
            header.tvMarkType.setTextColor(Color.RED);

        } else if(holder.getItemViewType() == INPUTROW){
            Marking marking = markingList.get(position - 1);
            ViewMarkAdapter.ViewMarkInputRow inputRow = (ViewMarkInputRow) holder;
            inputRow.studentName.setText(marking.getStudent().getStudentName());
            inputRow.studentId.setText(marking.getStudent().getStudentId());
            if(marking.getMarkValue() < 5.0){
                inputRow.tvViewMark.setTextColor(Color.RED);
                inputRow.tvViewMark.setText(String.valueOf(marking.getMarkValue()));
            }else{
                inputRow.tvViewMark.setText(String.valueOf(marking.getMarkValue()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return markingList.size() + 1;
    }

    public void refreshData(List<Marking> markingList) {
        this.markingList = markingList;
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


    public class ViewMarkHeader extends ViewHolder {
        TextView tvClassNameHeader;
        TextView tvClassQuantityHeader;
        TextView tvSemester;
        TextView tvSubject;
        TextView tvMarkType;
        public ViewMarkHeader(View itemView) {
            super(itemView);
            tvClassNameHeader = (TextView) itemView.findViewById(R.id.tvClassNameHeader);
            tvClassQuantityHeader = (TextView) itemView.findViewById(R.id.tvClassQuantityHeader);
            tvSemester = (TextView) itemView.findViewById(R.id.tvSemester);
            tvSubject = (TextView) itemView.findViewById(R.id.tvSubject);
            tvMarkType = (TextView) itemView.findViewById(R.id.tvMarkType);
        }
    }

    public class ViewMarkInputRow extends ViewHolder {
        TextView studentId, studentName;
        TextView tvViewMark;
        public ViewMarkInputRow(View itemView) {
            super(itemView);
            studentName = (TextView) itemView.findViewById(R.id.studentMarkingName);
            studentId = (TextView) itemView.findViewById(R.id.studentMarkingId);
            tvViewMark = (TextView) itemView.findViewById(R.id.tvViewMark);
        }
    }
}
