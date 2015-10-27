package com.example.alexandre.bar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView listView;
    private android.widget.ProgressBar progressBar;
    private ArrayList<TODO> todoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ParseUser.getCurrentUser() == null){
            Intent it = new Intent(this, LoginActivity.class);
            startActivity(it);
            finish();
        }

        this.progressBar = (ProgressBar) findViewById(R.id.progressBar);
        this.listView = (ListView) findViewById(R.id.listView);
        this.listView.setOnItemClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();

        progressBar.setVisibility(View.VISIBLE);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TODO");
        query.whereEqualTo("author", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    todoList.clear();
                    for (ParseObject object : list) {
                        TODO todo = new TODO();
                        todo.setObjectId(object.getObjectId());
                        todo.setTitle(object.getString("title"));
                        todo.setDescription(object.getString("description"));

                        ParseFile file = object.getParseFile("image");
                        todo.setImageUrl(file.getUrl());

                        todoList.add(todo);
                    }

                    ArrayAdapter<TODO> adapter = new ArrayAdapter<>(MainActivity.this,
                            R.layout.adapter_todo,
                            R.id.txTodoTitle,
                            todoList);
                    listView.setAdapter(adapter);

                    progressBar.setVisibility(View.GONE);
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
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.menuAdd) {
            Intent it = new Intent(this, AddToDoActivity.class);
            startActivity(it);
            return true;
        }
        else if (id == R.id.menuLogout) {
            ParseUser.logOut();
            Intent it = new Intent(this, LoginActivity.class);
            startActivity(it);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        TODO todo = (TODO) adapterView.getItemAtPosition(i);
        Intent it = new Intent(this, EditActivity.class);
        it.putExtra("todo", todo);
        startActivity(it);
    }
}
