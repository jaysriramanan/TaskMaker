package com.example.test.taskmaker;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.taskmaker.Adapters.TaskAdapter;
import com.example.test.taskmaker.Model.Task;
import com.example.test.taskmaker.Views.SubTaskView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddNewTask extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private ImageButton addNewSubTask;
    private ImageView addDate;
    private TextView addDateDisplay;
    private TextInputLayout newTask,addDetail;
    private LinearLayout root;
    private List<Task> subTaskList=new ArrayList<>();
    TaskStorage taskStorage=TaskStorage.getInstance();
    private View child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        actionBar.setDisplayShowHomeEnabled(true);
        root=(LinearLayout)findViewById(R.id.addTaskLayout);
        addDate=(ImageView)findViewById(R.id.addDate);
        addDateDisplay=(TextView)findViewById(R.id.addDateDisplay);
        //addNewSubTask=(ImageButton)findViewById(R.id.addNewSubTask);
        newTask=findViewById(R.id.newTask);
        addDetail=findViewById(R.id.addDetails);

        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });
        addDateDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });

        newTask.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(newTask.getEditText().getText().toString().trim().length()>0){
                    newTask.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
       /* addNewSubTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSubTask();
            }
        });*/
    }

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

    private void addSubTask(){
        child=new SubTaskView(AddNewTask.this);
        root.addView(child);
        EditText taskName=child.findViewById(R.id.newSubTask);
        EditText taskDetails=child.findViewById(R.id.subAddDetails);
        TextView taskDate=child.findViewById(R.id.subAddDateDisplay);
        addToSubTaskList(taskName.getText().toString(),taskDetails.getText().toString(),taskDate.getText().toString());
    }

    private void addToSubTaskList(String name,String details,String date){
        Task temp=new Task(name,details,date);
        subTaskList.add(temp);
    }

    private void pickDate(){
        DialogFragment datePicker=new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(),"date picker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,i);
        calendar.set(Calendar.MONTH,i1);
        calendar.set(Calendar.DAY_OF_MONTH,i2);
        String currentDate= DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
        addDateDisplay.setText(currentDate);

    }

    @Override
    public void onBackPressed() {
        addTask();
    }

    private boolean validate(){
        if(newTask.getEditText().getText().toString().trim().length()==0){
            if(addDetail.getEditText().getText().toString().length()==0 && addDateDisplay.getText().toString().length()==0){
                finish();
                return false;
            }
            else{
                return false;
            }
            //newTask.setError("This field is mandatory");
        }
        else{
            //newTask.setError(null);
            return true;
        }
    }

    public void addTask(){
        //Task[] subTasks=subTaskList.toArray(new Task[subTaskList.size()]);
        if(validate()) {
            newTask.setError(null);
            Task task = new Task(newTask.getEditText().getText().toString(), addDetail.getEditText().getText().toString(), addDateDisplay.getText().toString());
            taskStorage.addToList(task);
            Intent taskAdded = new Intent();
            //String[] extras={newTask.getText().toString(),addDetail.getText().toString(),addDateDisplay.getText().toString()};
            //taskAdded.putExtra("newTask",extras);
            setResult(Activity.RESULT_OK, taskAdded);
            finish();
        }
        else{
            newTask.setError("This field is mandatory");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("taskName",newTask.getEditText().getText().toString());
        outState.putString("taskDetails",addDetail.getEditText().getText().toString());
        outState.putString("taskDate",addDateDisplay.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        newTask.getEditText().setText(savedInstanceState.getString("taskName"));
        addDetail.getEditText().setText(savedInstanceState.getString("taskDetails"));
        addDateDisplay.setText(savedInstanceState.getString("taskDate"));
    }
}
