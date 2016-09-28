package com.bp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {

    Toolbar toolbar;

    public final static String EXTRA_MESSAGE = "Starting the initial analysis";

    /**
     * Called when the user clicks the Send button
     */
    public void acceptAnalysis(View view) {
        Intent startAnalysis = new Intent(this, Analysis.class);
        TextView textView = (TextView) findViewById(R.id.button_accept);
        String message = textView.getText().toString();
        startAnalysis.putExtra(EXTRA_MESSAGE, message);
        startActivity(startAnalysis);
    }

    /**
     * skip-button
     * adding the toolbar
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // sets our toolbar as the actionbar
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Toolbar");
//      getSupportActionBar().setTitle("My custom toolbar!");

        Button skipBtn = (Button) findViewById(R.id.skipAnalysis);
        Button acceptAna = (Button) findViewById(R.id.button_accept);

        skipBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),StartScreen.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_activities, menu);
        menu.findItem(R.id.goOn).setVisible(false);
        menu.findItem(R.id.goBack).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
        //    return true;
        //}
        if (id == R.id.action_options) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}