package com.example.vinayak.instituteadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView regAdmin,frgtPass;
    EditText uname,pass;
    Button btn_submit,btn_cancel;
    CheckBox chkSignIn;
    DatabaseHelper dObj=new DatabaseHelper(this,"DB1",null,101);

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        regAdmin=(TextView)findViewById(R.id.regAdmin_lbl);
        frgtPass=(TextView) findViewById(R.id.fPass_lbl);
        uname=(EditText) findViewById(R.id.Uname_EdtTxt);
        pass=(EditText) findViewById(R.id.Pass_EdtTxt);
        btn_submit=(Button) findViewById(R.id.submit_btn);
        btn_cancel=(Button) findViewById(R.id.cancel_btn);

        btn_submit.setOnClickListener(this);
        regAdmin.setOnClickListener(this);

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.regAdmin_lbl:
                //this.finish();
                Intent i=new Intent(MainActivity.this,Register_Admin.class);
                startActivity(i);
                break;

            case R.id.submit_btn:
                //if(dObj.isValidLoginDetails(uname.getText().toString(),pass.getText().toString()))
                {
                    System.out.println("Login successful!");
                    //this.finish();
                    Intent in=new Intent(MainActivity.this,MainMenu.class);
                    in.putExtra("username",uname.getText().toString());
                    startActivity(in);
                }
                /*else
                {
                    System.out.println("Login failed!");
                }*/
                break;
        }
    }
}