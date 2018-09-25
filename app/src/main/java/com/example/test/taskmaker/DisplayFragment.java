package com.example.test.taskmaker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.taskmaker.Model.Task;
import com.example.test.taskmaker.Views.SubTaskView;

public class DisplayFragment extends Fragment {
    private TextView dispTaskName,dispTaskDetails,dispDate;
    private TaskStorage taskStorage=TaskStorage.getInstance();
    private LinearLayout root;
    private ImageButton subTaskButton,saveButton;
    private View child;
    private TextInputLayout subName,subDetails;
    private TextView subDate;
    private int itemPos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_display_task,container,false);
        root=view.findViewById(R.id.dispTaskLayout);
        dispTaskName=view.findViewById(R.id.dispTaskName);
        dispTaskDetails=view.findViewById(R.id.dispTaskDetails);
        dispDate=view.findViewById(R.id.dispDate);
        subTaskButton=view.findViewById(R.id.subTaskButton);

        Bundle bundle=getArguments();
        itemPos=bundle.getInt("itemPosition");

        Task task=taskStorage.getPosition(itemPos);
        if(task!=null) {
            dispTaskName.setText(task.getTaskName());
            dispTaskDetails.setText(task.getTaskDetails());
            dispDate.setText(task.getTaskDueDate());
            if(task.hasSubTask()){
                subTaskButton.setClickable(false);
                subTaskButton.setVisibility(View.GONE);

                View subChild=LayoutInflater.from(getContext()).inflate(R.layout.display_sub_task,root,false);
                TextView subTaskName,subTaskDetails,dateDisplay;
                subTaskName=subChild.findViewById(R.id.dispSubTaskName);
                subTaskDetails=subChild.findViewById(R.id.dispSubTaskDetails);
                dateDisplay=subChild.findViewById(R.id.dispSubDate);
                Task t=task.getSubTask();
                subTaskName.setText(t.getTaskName());
                subTaskDetails.setText(t.getTaskDetails());
                dateDisplay.setText(t.getTaskDueDate());
                root.addView(subChild);
            }
        }

        subTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subTaskButton.setVisibility(View.GONE);
                subTaskButton.setClickable(false);
                addSubTask();
            }
        });
        return view;
    }

    protected void addSubTask() {
        child = new SubTaskView(getContext());
        root.addView(child);
        subName = child.findViewById(R.id.newSubTask);
        subDetails = child.findViewById(R.id.subAddDetails);
        subDate = child.findViewById(R.id.subAddDateDisplay);
        addSaveButton();
    }

    protected void addSaveButton(){
        saveButton=new ImageButton(getContext());
        saveButton.setBackgroundResource(R.drawable.ic_save_black_48dp);
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity= Gravity.CENTER_HORIZONTAL;
        root.addView(saveButton,layoutParams);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    public void save(){
        if(child!=null) {
            if(subName.getEditText().getText().toString().length()==0 && subDetails.getEditText().getText().toString().length()==0 && subDate.getText().toString().length()==0){

            }
            else {
                Task temp = new Task(subName.getEditText().getText().toString(), subDetails.getEditText().getText().toString(), subDate.getText().toString());
                Task task = taskStorage.getPosition(itemPos);
                task.setSubTask(temp);
            }
        }

    }
    @Override
    public void onStart() {
        super.onStart();
    }

}
