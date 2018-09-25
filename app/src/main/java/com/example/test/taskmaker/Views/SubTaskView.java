package com.example.test.taskmaker.Views;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.test.taskmaker.R;

import java.text.DateFormat;
import java.util.Calendar;


public class SubTaskView extends LinearLayout {
    TextInputLayout newSubTask,subAddDetails;
    TextView subAddDateDisplay;
    ImageView subAddDate;
    private Context context;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    public SubTaskView(Context context) {
        super(context);
        this.context=context;
        init(null,0,0);
    }

    public SubTaskView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init(attrs,0,0);
    }

    public SubTaskView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init(attrs,defStyleAttr,0);
    }

    public SubTaskView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context=context;
        init(attrs,defStyleAttr,defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes){
        inflate(getContext(),R.layout.add_new_sub_task,this);
        newSubTask=findViewById(R.id.newSubTask);
        subAddDetails=findViewById(R.id.subAddDetails);
        subAddDate=findViewById(R.id.subAddDate);
        subAddDateDisplay=findViewById(R.id.subAddDateDisplay);

        subAddDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDueDate();
            }
        });

        subAddDateDisplay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDueDate();
            }
        });
        dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar cal=Calendar.getInstance();
                cal.set(Calendar.YEAR,i);
                cal.set(Calendar.MONTH,i1);
                cal.set(Calendar.DAY_OF_MONTH,i2);
                String currentDate= DateFormat.getDateInstance(DateFormat.SHORT).format(cal.getTime());
                subAddDateDisplay.setText(currentDate);
            }
        };
    }

    protected void pickDueDate(){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog=new DatePickerDialog(context,dateSetListener,year,month,day);
        dialog.show();
    }
}
