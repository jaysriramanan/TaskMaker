package com.example.test.taskmaker.Model;

public class Task {
    private String taskName,taskDetails, taskDueDate;
    private Task[] subTaskArray;
    private  Task subTask;


    public Task(String taskName, String taskDetails, String taskDueDate) {
        this.taskName = taskName;
        this.taskDetails = taskDetails;
        this.taskDueDate=taskDueDate;

    }

    public Task(String taskName, String taskDetails, String taskDueDate,Task ... subTaskArray){
        this.taskName = taskName;
        this.taskDetails = taskDetails;
        this.taskDueDate=taskDueDate;
        this.subTaskArray=subTaskArray;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public String getTaskDueDate() {
        return taskDueDate;
    }

    public Task[] getSubTaskArray(){
        return subTaskArray;
    }

    public  boolean hasSubTask(){
        if(subTask!=null){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean hasSubTaskArray(){
        if(subTaskArray.length==0){
            return false;
        }
        else {
            return true;
        }
    }

    public Task getSubTask() {
        return subTask;
    }

    public void setSubTask(Task subTask) {
        this.subTask = subTask;
    }
}
