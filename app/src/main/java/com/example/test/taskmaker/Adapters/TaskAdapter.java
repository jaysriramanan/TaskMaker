package com.example.test.taskmaker.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.taskmaker.DisplayFragment;
import com.example.test.taskmaker.DisplayTask;
import com.example.test.taskmaker.MainActivity;
import com.example.test.taskmaker.Model.Task;
import com.example.test.taskmaker.R;
import com.example.test.taskmaker.TaskStorage;

public class TaskAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private TaskStorage taskStorage;
    private static final int TYPE_FOOTER=1;
    private static final int TYPE_ITEM=2;
    private int layoutId;

    public TaskAdapter(Context context, TaskStorage taskStorage,int layoutId){
        this.context=context;
        this.taskStorage=taskStorage;
        this.layoutId=layoutId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i==TYPE_FOOTER){
            View view=LayoutInflater.from(context).inflate(R.layout.footer,viewGroup,false);
            return new FooterHolder(view);
        }
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_task_display,viewGroup,false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        if(holder instanceof TaskHolder){
            final TaskHolder taskHolder=(TaskHolder)holder;
            Task task=getTask(i);
            if(task!=null){
                taskHolder.rDispTaskName.setText(task.getTaskName());
                taskHolder.rDispTaskDetails.setText(task.getTaskDetails());
                taskHolder.rDispDate.setText(task.getTaskDueDate());

                if(taskHolder.rDispTaskDetails.getText().toString().equals("")){
                    taskHolder.rDispTaskDetails.setVisibility(View.GONE);
                }
                if(taskHolder.rDispDate.getText().toString().equals("")){
                    taskHolder.rDispDate.setVisibility(View.GONE);
                    taskHolder.rDateImage.setVisibility(View.GONE);

                }
            /* if(task.hasSubTasks()){
                for(Task t:task.getSubTasks()){

                }
            }*/
                //final String[] dispTask={taskHolder.rDispTaskName.getText().toString(),taskHolder.rDispTaskDetails.getText().toString(),taskHolder.rDispDate.getText().toString()};
                taskHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(layoutId==1) {
                            //AppCompatActivity activity = (AppCompatActivity) view.getContext();
                            MainActivity mainActivity=(MainActivity)view.getContext();
                            mainActivity.addFragment(taskHolder.getAdapterPosition());
                            /*FragmentManager fm=mainActivity.getSupportFragmentManager();
                            FragmentManager fragmentManager = activity.getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            DisplayFragment displayFragment=new DisplayFragment();
                            fragmentManager.popBackStack();
                            fragmentTransaction.add(R.id.fragmentContainer,displayFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();*/
                        }
                        else {
                            Intent intent = new Intent(view.getContext(), DisplayTask.class);
                            //intent.putExtra("task",dispTask);
                            intent.putExtra("taskPosition", taskHolder.getAdapterPosition());
                            view.getContext().startActivity(intent);
                        }
                    }
                });
            }
        }
        if(holder instanceof FooterHolder){
            FooterHolder footerHolder=(FooterHolder)holder;

        }
    }

    @Override
    public int getItemViewType(int position){
        if(position==getItemCount()-1){
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return taskStorage.listSize()+1;
    }

    public Task getTask(int position){
        return taskStorage.getPosition(position);
    }


    public class TaskHolder extends RecyclerView.ViewHolder {

        TextView rDispTaskName,rDispTaskDetails,rDispDate;
        ImageView rDateImage;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);

            rDispTaskName=itemView.findViewById(R.id.rTaskName);
            rDispTaskDetails=itemView.findViewById(R.id.rTaskDetails);
            rDispDate=itemView.findViewById(R.id.rDate);
            rDateImage=itemView.findViewById(R.id.rDateImage);
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder{

        public FooterHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
