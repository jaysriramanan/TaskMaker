package com.example.test.taskmaker;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.test.taskmaker.Model.Task;

import java.util.ArrayList;

public class TaskStorage {

    private static TaskStorage taskStorage=null;
    private ArrayList<Task> taskArrayList;

    private TaskStorage(){
        taskArrayList=new ArrayList<>();
    }

    public static  TaskStorage getInstance(){
        if(taskStorage==null){
            taskStorage=new TaskStorage();
        }
        return taskStorage;
    }

    public void addToList(Task task){
            taskArrayList.add(task);
    }

    public int listSize(){
            return taskArrayList.size();
    }

    public Task getPosition(int position){
            return taskArrayList.get(position);
    }

    public void sort(){

    }

}
