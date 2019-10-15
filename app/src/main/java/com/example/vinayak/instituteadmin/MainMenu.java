package com.example.vinayak.instituteadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    Button btn_BatchDetails, btn_StudAddmission, btn_StudList, btn_Rating;
    String str_uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Intent i=getIntent();
        str_uname=i.getExtras().getString("username");

        btn_BatchDetails=(Button) findViewById(R.id.BatchDetails_btn);
        btn_StudAddmission=(Button) findViewById(R.id.Admission_btn);
        btn_StudList=(Button) findViewById(R.id.studList_btn);
        btn_Rating=(Button) findViewById(R.id.rate_btn);

        btn_BatchDetails.setOnClickListener(this);
        btn_StudAddmission.setOnClickListener(this);
        btn_StudList.setOnClickListener(this);
        btn_Rating.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"Sign-Out");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case 1:
                this.finish();
                Intent i=new Intent(MainMenu.this, MainActivity.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId())
        {
            case R.id.BatchDetails_btn:
                i=new Intent(MainMenu.this,BatchDetails.class);
                startActivity(i);
                break;

            case R.id.Admission_btn:
                i=new Intent(MainMenu.this,StudentAddmission.class);
                startActivity(i);
                break;

            case R.id.studList_btn:

                break;

            case R.id.rate_btn:

                break;
        }
    }
}