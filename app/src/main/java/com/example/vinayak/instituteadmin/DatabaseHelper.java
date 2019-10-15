package com.example.vinayak.instituteadmin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists AdminTable(_id Integer primary key autoincrement,name text,username text, password text,contact text, email text, sec_ques text, sec_ans text)");
        db.execSQL("create table if not exists BatchTable(_id Integer primary key autoincrement,bname text,abreiviation text, number Integer, duration Integer,daySched Integer,timeSched Integer,fees number,description text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int getusercount(String funame)
    {
        int cnt;
        System.out.println("In getusercount Fun()");
        Cursor c;
        SQLiteDatabase db=getReadableDatabase();

        // String[] read_clm={"username","password"};
        String[] where_row={funame};

        c=db.query("AdminTable",null,"username=?",where_row,null,null,null);
        c.moveToFirst();
        cnt=c.getCount();
        System.out.println("User count:"+cnt);
        return cnt;
    }

    public int registerAdmin(String Aname,String Auname,String Apass, String Acontact,String Aemail,String sec_ques,String sec_ans)
    {
        int iRet=0;
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",Aname);
        cv.put("username",Auname);
        cv.put("password",Apass);
        cv.put("contact",Acontact);
        cv.put("email",Aemail);
        cv.put("sec_ques",sec_ques);
        cv.put("sec_ans",sec_ans);
        db.insert("AdminTable",null,cv);
        System.out.println("Details added to database!");

        iRet=getusercount(Auname);
        return iRet;
    }

    public boolean isValidLoginDetails(String Auname,String Apass)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor c;
        String[] where_col={Auname,Apass};
        String[] read_col={"username","password"};
        c=db.query("AdminTable",read_col,"username=? and password=?",where_col,null,null,null);
        c.moveToFirst();
        if(c.getCount()==1)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public long addBatch(String Bname,String abv,int iNum,int iDuration,int iDaySched,int iTimeSched,int iFees,String strDescription)
    {
        long lRet=0;
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("bname",Bname);
        cv.put("abreiviation",abv);
        cv.put("number",iNum);
        cv.put("duration",iDuration);
        cv.put("daySched",iDaySched);
        cv.put("timeSched",iTimeSched);
        cv.put("fees",iFees);
        cv.put("description",strDescription);
        lRet= db.insert("BatchTable",null,cv);
        return lRet;
    }

    public boolean BatchIsNotDuplicate(String abv)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor c;
        String[] where_col={abv};
        //String[] read_col={"abreiviation"};
        c=db.query("BatchTable",null,"abreiviation=?",where_col,null,null,null);
        System.out.println("Inside chkBatchIsNotDuplicate");
        if(c.getCount()>=1)
        {
            System.out.println("Reach1");
            return false;
        }
        else
        {
            System.out.println("Reach2");
            return true;
        }
    }

    public Cursor getBatches()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor c=db.query("BatchTable",null,null,null,null,null,null);
        c.moveToFirst();
        for (int i=0;i<c.getCount();i++)
        {
            System.out.println(c.getInt(0)+"\t"+c.getString(1)+"\t"+c.getString(2)+"\t"+c.getInt(3)+"\t"+c.getInt(4)+"\t"+c.getInt(5)+"\t"+c.getInt(6)+"\t"+c.getInt(7)+"\t"+c.getString(8));
            c.moveToNext();
        }
        c.moveToFirst();
        return c;
    }

    public Cursor getBatchHandle(int id)
    {
        SQLiteDatabase db=getReadableDatabase();
        String[] where={String.valueOf(id)};
        Cursor c=db.query("BatchTable",null,"_id=?",where,null,null,null);
        System.out.println(c.getCount());
        return c;
    }

    public int updateBatch(int id,String Bname,String abv,int iNum,int iDuration,int iDaySched,int iTimeSched,int iFees,String strDescription)
    {
        int iRet=0;
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("bname",Bname);
        cv.put("abreiviation",abv);
        cv.put("number",iNum);
        cv.put("duration",iDuration);
        cv.put("daySched",iDaySched);
        cv.put("timeSched",iTimeSched);
        cv.put("fees",iFees);
        cv.put("description",strDescription);
        //lRet= db.insert("BatchTable",null,cv);
        String[] where={String.valueOf(id)};
        iRet=db.update("BatchTable",cv,"_id=?",where);
        return iRet;
    }

    public int deleteBatch(int ID)
    {
        int iRet = 0;
        SQLiteDatabase db=getWritableDatabase();
        String[] where={String.valueOf(ID)};
        iRet = db.delete("BatchTable","_id=?",where);
        return iRet;
    }
}