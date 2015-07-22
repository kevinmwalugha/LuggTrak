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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText userName, passWord;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userName = (EditText) findViewById(R.id.userName);
        passWord = (EditText) findViewById(R.id.passWord);

        login = (Button) findViewById(R.id.button);
        login.setOnClickListener(this);

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
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.about) {
            Intent aboutIntent = new Intent(getApplicationContext(), aboutActivity.class);
            startActivity(aboutIntent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
            if (userName.getText().toString().equals("admin")&& passWord.getText().toString().equals("admin")){
                Intent intent=new Intent(getApplicationContext(),gpsCodeEntry.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"Please Provide the right Username and Password!",Toast.LENGTH_LONG).show();
                //Intent intentBack new Intent(getApplicationContext(),this);
            }

    }
}