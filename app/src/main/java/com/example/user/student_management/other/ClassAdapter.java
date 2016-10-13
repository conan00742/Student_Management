package com.example.user.student_management.other;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.student_management.R;
import com.example.user.student_management.model.Classes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 10/13/2016.
 */
public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyViewHolder> {

    private List<Classes> classList = new ArrayList<>();

    public ClassAdapter(List<Classes> classList) {
        this.classList = classList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.class_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Classes _class = classList.get(position);
        holder.className.setText(_class.get_name());
        holder.classQuantity.setText(String.valueOf(_class.get_quantity()));
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView className, classQuantity;
        public MyViewHolder(View itemView) {
            super(itemView);
            className = (TextView) itemView.findViewById(R.id.className);
            classQuantity = (TextView) itemView.findViewById(R.id.classQuantity);
        }
    }
}
