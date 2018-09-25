package com.example.test.taskmaker;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.test.taskmaker.Adapters.Divider;
import com.example.test.taskmaker.Adapters.TaskAdapter;
import com.example.test.taskmaker.Model.Task;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    static final int ADD_TASK_REQUEST=1;
    private TaskAdapter taskAdapter;
    TaskStorage taskStorage;
    private int layoutId;
    private  FragmentManager fragmentManager;
    private  FragmentTransaction fragmentTransaction;
    private FloatingActionButton fab;
    private BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.layout_landscape)!=null){
            layoutId=1;
        }

        bottomAppBar=(BottomAppBar)findViewById(R.id.bottomAppBar);
        setSupportActionBar(bottomAppBar);


        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new Divider(this,LinearLayoutManager.VERTICAL,layoutId));

        fragmentManager=getSupportFragmentManager();
        loadTasks();

        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTask();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(newState==RecyclerView.SCROLL_STATE_IDLE){
                    fab.show();
                    bottomAppBar.setHideOnScroll(false);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(dy>0 || dy<0 && fab.isShown()){
                    fab.hide();
                    bottomAppBar.setHideOnScroll(true);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sort) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadTasks(){
        taskStorage=TaskStorage.getInstance();
        if(taskStorage.listSize()!=0) {
            taskAdapter = new TaskAdapter(this, taskStorage,layoutId);
            recyclerView.setAdapter(taskAdapter);
        }
    }

    private void addNewTask(){
        Intent addNewTask=new Intent(this,AddNewTask.class);
        startActivityForResult(addNewTask,ADD_TASK_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent getNewTask){
        super.onActivityResult(requestCode, resultCode, getNewTask);
        if(requestCode==ADD_TASK_REQUEST) {
            if (resultCode == RESULT_OK) {
                /*String[] newTask=getNewTask.getStringArrayExtra("newTask");
                Task task=new Task(newTask[0],newTask[1],newTask[2]);
                taskStorage.addToList(task);*/
                //taskStorage=getNewTask.getParcelableExtra("newNewTask");
                //taskStorage=TaskStorage.getInstance();
            }
        }
        if(taskAdapter!=null){
            taskAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkParams();
        loadTasks();
    }

    public void checkParams(){
        if(layoutId==1){
            if(fragmentManager.getBackStackEntryCount()==0){
                recyclerView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            else{
                recyclerView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }
        else{
            recyclerView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    public void addFragment(int itemPosition){
        FrameLayout frameLayout=new FrameLayout(this);
        RelativeLayout relativeLayout=findViewById(R.id.relativeLayout);
        LinearLayout linearLayout=new LinearLayout(this);
        Log.d("Inside add Fragment","inside add fragment before setting params\n\n\n\n");


        RelativeLayout.LayoutParams childParams=new RelativeLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT);
        childParams.addRule(RelativeLayout.RIGHT_OF,R.id.recyclerView);
        linearLayout.setId(R.id.divider_vertical);
        relativeLayout.addView(linearLayout,childParams);

        childParams=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        childParams.addRule(RelativeLayout.END_OF,R.id.divider_vertical);
        frameLayout.setId(R.id.container_fragment);
        relativeLayout.addView(frameLayout,childParams);


        fragmentTransaction=fragmentManager.beginTransaction();
        DisplayFragment displayFragment=new DisplayFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("itemPosition",itemPosition);
        displayFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.container_fragment,displayFragment);
        fragmentTransaction.addToBackStack("Fragment");
        //int i=fragmentManager.getBackStackEntryCount();
        checkParams();
        fragmentTransaction.commit();
        checkParams();

    }

    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount()!=0){
            fragmentManager.popBackStack(0,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            checkParams();
        }
        else{
            super.onBackPressed();
        }
    }
}
