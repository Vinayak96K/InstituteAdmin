package com.example.vinayak.instituteadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Register_Admin extends AppCompatActivity implements View.OnClickListener {

    EditText adminName, adminUname, adminPass, adminRpass,adminContact, adminEmail,adminSecAns;
    Button btn_register,btn_back;
    Spinner spinner_secQuestion;
    String strAdminName,strAdminUname,strAdminPass,strAdminRpass,strAdminContact,strAdminEmail,strAdminSecQuestion,strAdminSecAns;

    DatabaseHelper dObj=new DatabaseHelper(this,"DB1",null,101);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__admin);
        adminName =(EditText) findViewById(R.id.ARname);
        adminUname =(EditText) findViewById(R.id.ARuname);
        adminPass =(EditText) findViewById(R.id.ARpass);
        adminRpass =(EditText) findViewById(R.id.ARrepass);
        adminContact =(EditText) findViewById(R.id.ARcontact);
        adminEmail =(EditText) findViewById(R.id.ARemail);
        adminSecAns =(EditText) findViewById(R.id.ARanswer);
        btn_register=(Button)findViewById(R.id.register_btn);
        btn_back=(Button)findViewById(R.id.back_btn);

        spinner_secQuestion = (Spinner) findViewById(R.id.secQuestion_spin);
        btn_register.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.register_btn:
                strAdminName=adminName.getText().toString().trim();
                strAdminUname=adminUname.getText().toString().trim();
                strAdminPass=adminPass.getText().toString().trim();
                strAdminRpass=adminRpass.getText().toString().trim();
                strAdminContact=adminContact.getText().toString().trim();
                strAdminEmail=adminEmail.getText().toString().trim();
                strAdminSecQuestion=spinner_secQuestion.getSelectedItem().toString();
                strAdminSecAns=adminSecAns.getText().toString().trim();
                System.out.println(strAdminName);
                System.out.println(strAdminUname);
                System.out.println(strAdminPass);
                System.out.println(strAdminRpass);
                System.out.println(strAdminContact);
                System.out.println(strAdminEmail);
                System.out.println(strAdminSecQuestion);
                System.out.println(strAdminSecAns);
                if(isInfoValid())
                {
                    System.out.println("All information is valid to register!");
                    int iret=dObj.registerAdmin(strAdminName,strAdminUname,strAdminPass,strAdminContact,strAdminEmail,strAdminSecQuestion,strAdminSecAns);
                    if(iret==1)
                    {
                        System.out.println("Admin registration successful!");
                        Toast.makeText(this, "Success: Admin registered successful!", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent i=new Intent(Register_Admin.this,MainActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(this, "Error: An unknown error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    System.out.println("Invalid information!");
                }
                break;

            case R.id.back_btn:
                finish();
                Intent i=new Intent(Register_Admin.this,MainActivity.class);
                startActivity(i);
                break;
        }
    }

    public boolean isInfoValid()
    {
        if((strAdminName.length()==0)||(strAdminUname.length()==0)||(strAdminPass.length()==0)||(strAdminRpass.length()==0)||(strAdminContact.length()==0)||(strAdminEmail.length()==0)||(strAdminSecAns.length()==0))
        {
            Toast.makeText(this, "Error: Empty fields!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if((strAdminUname.contains(" "))||(strAdminEmail.contains(" ")))
        {
            Toast.makeText(this, "Error: Whitespaces are not allowed in username and email id!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(strAdminPass.equals(strAdminRpass)!=true)
        {
            Toast.makeText(this, "Error: Password does not match!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if((strAdminContact.length()!=10))
        {
            Toast.makeText(this, "Error: Incorrect contact number!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(strAdminSecQuestion.equals("--Select question--"))
        {
            Toast.makeText(this, "Error: Select a security question!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(dObj.getusercount(strAdminUname)!=0)
        {
            Toast.makeText(this, "Error: Username already exists!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
