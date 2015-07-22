package com.kevinmwalugha.luggtrak;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class gpsCodeEntry extends AppCompatActivity implements View.OnClickListener {
    private Button btnTrack;
    private EditText gpsCodeEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_code_entry);

        btnTrack=(Button)findViewById(R.id.btn_track);
        btnTrack.setOnClickListener(this);
        gpsCodeEntry=(EditText)findViewById(R.id.editText);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gps_code_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (gpsCodeEntry.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Please provide a code",Toast.LENGTH_LONG).show();
        }else{
//           startActivity(new Intent(getApplicationContext(),luggagePosition.class));
            Intent position=new Intent(getApplicationContext(),pPosition.class);
            startActivity(position);
        }

    }
}
