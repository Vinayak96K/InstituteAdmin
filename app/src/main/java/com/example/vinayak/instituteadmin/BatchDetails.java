package com.example.vinayak.instituteadmin;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BatchDetails extends AppCompatActivity {

    TextView listElement;
    ListView BatchList;
    MyAdapter adapter;
    Cursor c;
    DatabaseHelper dObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_details);
        BatchList=(ListView) findViewById(R.id.BatchList_lst);
        registerForContextMenu(BatchList);

    }

    @Override
    protected void onResume() {
        super.onResume();
        dObj = new DatabaseHelper(this, "DB1", null, 101);
        reloadList();
    }

    public class MyAdapter extends CursorAdapter
    {
        public MyAdapter(Context context, Cursor c) {
            super(context, c,0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater=LayoutInflater.from(context);
            View v=inflater.inflate(R.layout.row,parent,false);

            return v;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            listElement=(TextView) view.findViewById(R.id.row_id);

            listElement.setText(cursor.getString(cursor.getColumnIndex("bname")).toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0,"Add new batch");

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;

        switch (item.getItemId())
        {
            case 0:
                i=new Intent(BatchDetails.this,AddBatch.class);
                //i.putExtra("ID",10);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0,0,0,"Edit");
        menu.add(0,1,1,"Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId())
        {
            case 0:
                System.out.println(c.getInt(0)+"\t"+c.getString(1));
                i=new Intent(BatchDetails.this,AddBatch.class);
                i.putExtra("ID",c.getInt(0));
                startActivity(i);
                break;

            case 1:
                dObj.deleteBatch(c.getInt(0));
                reloadList();
                Toast.makeText(this, "Batch deleted!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    void reloadList()
    {
        c=dObj.getBatches();
        adapter=new MyAdapter(this,c);
        BatchList.setAdapter(adapter);
    }
}