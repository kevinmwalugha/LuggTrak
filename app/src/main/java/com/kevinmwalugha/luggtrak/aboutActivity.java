package com.kevinmwalugha.luggtrak;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class aboutActivity extends AppCompatActivity {

    //here is where we declare our variables and buttons and textviews

    private TextView txtAboutTitle , txtDescription;
    private ImageView imageView;
    private PackageInfo pi=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView=(ImageView) findViewById(R.id.logo);
        txtAboutTitle = (TextView) findViewById(R.id.aboutText);
        txtDescription = (TextView) findViewById(R.id.description);


        try {
            pi = getPackageManager().getPackageInfo(
                    getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        txtAboutTitle.setText("Version " + getVersionName() + " (" + getVersionCode() + ")");
        txtDescription.setText("Developed By Kevin Mwalugha");
        imageView.setImageResource(R.mipmap.logo2);

    }


    /**
     * version number shown to users
     *
     * @return
     */
    public String getVersionName() {
        PackageInfo pi = getPackageInfo();
        if (pi != null) {
            return pi.versionName;
        } else {
            return "";
        }
    }

    /**
     * internal version number. Used to determine whether one version is more recent than the other. It is not shown to users. The value is an integer
     * <p/>
     * NB: higher numbers indicates more recent versions
     *
     * @return
     */
    public int getVersionCode() {
        PackageInfo pi = getPackageInfo();
        if (pi != null) {
            return pi.versionCode;
        } else {
            return 0;
        }
    }


    public PackageInfo getPackageInfo() {
        return pi;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
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
}
