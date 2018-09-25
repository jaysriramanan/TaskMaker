package com.example.test.taskmaker;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.taskmaker.Model.Task;
import com.example.test.taskmaker.Views.SubTaskView;

public class DisplayTask extends AppCompatActivity {
    private TextView dispTaskName,dispTaskDetails,dispDate;
    private TaskStorage taskStorage=TaskStorage.getInstance();
    private LinearLayout root;
    private ImageButton subTaskButton,saveButton;
    private View child;
    private TextInputLayout subName,subDetails;
    private TextView subDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);


        root=findViewById(R.id.dispTaskLayout);
        dispTaskName=findViewById(R.id.dispTaskName);
        dispTaskDetails=findViewById(R.id.dispTaskDetails);
        dispDate=findViewById(R.id.dispDate);
        subTaskButton=findViewById(R.id.subTaskButton);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayShowHomeEnabled(true);


        int pos=getIntent().getIntExtra("taskPosition",0);
        Task task=taskStorage.getPosition(pos);
        if(task!=null){
            dispTaskName.setText(task.getTaskName());
            dispTaskDetails.setText(task.getTaskDetails());
            dispDate.setText(task.getTaskDueDate());
            if(task.hasSubTask()){
                /*for(Task t:task.getSubTaskArray()){
                    //LayoutInflater inflater=getLayoutInflater();
                    View child= LayoutInflater.from(this).inflate(R.layout.display_sub_task,root,false);
                    TextView subName,subDetails;
                    TextView dateDisplay;
                    subName=child.findViewById(R.id.dispSubTaskName);
                    subDetails=child.findViewById(R.id.dispSubTaskDetails);
                    dateDisplay=child.findViewById(R.id.dispSubDate);
                    String name=t.getTaskName();
                    subName.setText(t.getTaskName());
                    subDetails.setText(t.getTaskDetails());
                    dateDisplay.setText(t.getTaskDueDate());
                    root.addView(child);
                }*/

                subTaskButton.setClickable(false);
                subTaskButton.setVisibility(View.GONE);

                View subChild=LayoutInflater.from(this).inflate(R.layout.display_sub_task,root,false);
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
    }
    protected void addSubTask(){
        child=new SubTaskView(DisplayTask.this);
        root.addView(child);
        subName = child.findViewById(R.id.newSubTask);
        subDetails = child.findViewById(R.id.subAddDetails);
        subDate = child.findViewById(R.id.subAddDateDisplay);
        //addSaveButton();
        //final Button saveButton=new Button(this);
        //saveButton.setText("Save");
        //saveButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
       // saveButton.setTextColor(getResources().getColor(R.color.colorWhite));
        //root.addView(saveButton,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        /*saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });*/

    }

    /*protected void addSaveButton(){
        saveButton=new ImageButton(this);
        saveButton.setBackgroundResource(R.drawable.ic_save_black_48dp);
        root.addView(saveButton,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void save(){
        if(child!=null) {
            if(subName.getEditText().getText().toString().length()==0 && subDetails.getEditText().getText().toString().length()==0 && subDate.getText().toString().length()==0){
                finish();
            }
            else {
                Task temp = new Task(subName.getEditText().getText().toString(), subDetails.getEditText().getText().toString(), subDate.getText().toString());
                int pos = getIntent().getIntExtra("taskPosition", 0);
                Task task = taskStorage.getPosition(pos);
                task.setSubTask(temp);
            }
        }
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(child!=null){
            outState.putInt("state",1);
            outState.putString("subTaskName", subName.getEditText().getText().toString());
            outState.putString("subTaskDetails", subDetails.getEditText().getText().toString());
            outState.putString("subDate",subDate.getText().toString());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.getInt("state")==1){
            subTaskButton.setVisibility(View.GONE);
            child=new SubTaskView(DisplayTask.this);
            root.addView(child);
            subName = child.findViewById(R.id.newSubTask);
            subDetails = child.findViewById(R.id.subAddDetails);
            subDate = child.findViewById(R.id.subAddDateDisplay);
            subName.getEditText().setText(savedInstanceState.getString("subTaskName"));
            subDetails.getEditText().setText(savedInstanceState.getString("subTaskDetails"));
            subDate.setText(savedInstanceState.getString("subDate"));
            //addSaveButton();
        }
    }

    @Override
    public void onBackPressed() {
        save();
    }
}
