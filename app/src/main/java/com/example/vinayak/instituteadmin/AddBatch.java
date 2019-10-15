package com.example.vinayak.instituteadmin;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddBatch extends AppCompatActivity implements View.OnClickListener {

    EditText eBatchName, eBatchABV, eBatchNumber, eBatchDuration, eBatchFees,eBatchDescription;
    Spinner sDaySched,sTimeSched;
    Button bAdd,bCancel;
    DatabaseHelper dObj=new DatabaseHelper(this,"DB1",null,101);
    String strBatchName, strBacthABV,strDescription;
    int iNum,iDuration,iFees,iDaySched,iTimesched,bID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_batch);
        Intent i=getIntent();
        bID=i.getIntExtra("ID",-1);
        System.out.println("Batch is is:"+bID);

        eBatchName =(EditText) findViewById(R.id.BatchTitle_EdtTxt);
        eBatchABV =(EditText) findViewById(R.id.BatchAbbreviation_EdtTxt);
        eBatchNumber=(EditText) findViewById(R.id.BatchNumber_EdtTxt);
        eBatchDuration=(EditText) findViewById(R.id.BatchDuration_EdtTxt);
        eBatchFees=(EditText) findViewById(R.id.BatchFees_EdtTxt);
        eBatchDescription=(EditText) findViewById(R.id.BatchDescription_EdtTxt);
        sDaySched=(Spinner) findViewById(R.id.DaySchedule_spinner);
        sTimeSched=(Spinner) findViewById(R.id.BatchTiming_spinner);
        bAdd=(Button) findViewById(R.id.AddBatch_btn);
        bCancel=(Button) findViewById(R.id.goBack_btn);

        if(bID!=(-1))
        {
            Cursor c=dObj.getBatchHandle(bID);
            c.moveToFirst();
            eBatchName.setText(c.getString(1));
            eBatchName.setEnabled(false);
            eBatchABV.setText(c.getString(2));
            eBatchABV.setEnabled(false);
            eBatchNumber.setText(c.getInt(3)+"");
            eBatchDuration.setText(c.getInt(4)+"");
            sDaySched.setSelection(c.getInt(5));
            sTimeSched.setSelection(c.getInt(6));
            eBatchFees.setText(c.getInt(7)+"");
            eBatchDescription.setText(c.getString(8));
            bAdd.setText("EDIT");
        }

        bAdd.setOnClickListener(this);
        bCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        int iVar1=-1,iVar2=-1,iVar3=-1;
        switch (v.getId())
        {
            case R.id.AddBatch_btn:
                String temp;
                strBatchName=eBatchName.getText().toString().trim();
                strBacthABV=eBatchABV.getText().toString().trim().toUpperCase();
                temp=eBatchNumber.getText().toString().trim();
                if(temp.length()!=0)
                {
                    iVar1=Integer.parseInt(temp);
                }
                iNum=iVar1;
                temp=eBatchDuration.getText().toString().trim();
                if(temp.length()!=0)
                {
                    iVar2=Integer.parseInt(temp);
                }
                iDuration=iVar2;
                iDaySched=sDaySched.getSelectedItemPosition();
                System.out.println("Selected item index:"+sDaySched.getSelectedItemPosition());
                iTimesched=sTimeSched.getSelectedItemPosition();
                temp=eBatchFees.getText().toString().trim();
                if(temp.length()!=0)
                {
                    iVar3=Integer.parseInt(temp);
                }
                iFees=iVar3;
                strDescription=eBatchDescription.getText().toString().trim();
                System.out.println(strBatchName);
                System.out.println(strBacthABV);
                System.out.println(iNum);
                System.out.println(iDuration);
                System.out.println(iDaySched);
                System.out.println(iTimesched);
                System.out.println(iFees);
                System.out.println(strDescription);
                long lRet=0;
                if(ValidDetails())
                {
                    if(bID!=(-1))
                    {
                        lRet=(long) dObj.updateBatch(bID,strBatchName,strBacthABV,iNum,iDuration,iDaySched,iTimesched,iFees,strDescription);
                    }
                    else
                    {
                        if (dObj.BatchIsNotDuplicate(strBacthABV))
                        {
                            lRet = dObj.addBatch(strBatchName, strBacthABV, iNum, iDuration, iDaySched, iTimesched, iFees, strDescription);
                        }
                        else
                        {
                            System.out.println("Reach10");
                            Toast.makeText(this, "Error: Duplicate entry!\nPlease enter unique batch abbreviation!", Toast.LENGTH_LONG).show();
                        }
                    }
                    if ( (lRet>0) && (bID==(-1)) )
                    {
                        Toast.makeText(this, "Success: Batch Added!", Toast.LENGTH_SHORT).show();
                        this.finish();
                    }
                    else if( (lRet>=0) && (bID!=(-1)))
                    {
                        Toast.makeText(this, "Success: Batch Edited!", Toast.LENGTH_SHORT).show();
                        this.finish();
                    }
                    else if (lRet==(-1))
                    {
                        Toast.makeText(this, "Error: An unknown error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "Error: Empty fields are not allowed!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.goBack_btn:
                this.finish();
                break;
        }
    }

    public boolean ValidDetails()
    {
        if((strBatchName.length()==0) || (strBacthABV.length()==0) || (iNum<=0) || (iDuration<=0) || (iDaySched<=0) || (iTimesched<=0) || (iFees<=0) || (strDescription.length()==0) )
        {
            return false;
        }
        return true;
    }

}