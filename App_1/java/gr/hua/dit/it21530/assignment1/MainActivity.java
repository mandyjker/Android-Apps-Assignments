package gr.hua.dit.it21530.assignment1;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DbHelper dbHelper = new DbHelper(MainActivity.this);

        //for every EditTextView, when user clicks, text becomes blank
        final EditText userID = findViewById(R.id.editTextUserid);
        userID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID.setText("");
            }
        });
        final EditText latitude = findViewById(R.id.editTextLatitude);
        latitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitude.setText("");
            }
        });
        final EditText longitude = findViewById(R.id.editTextLongitude);
        longitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                longitude.setText("");
            }
        });

        //for the first button, when user clicks then instance is saved on database
        Button button1 = findViewById(R.id.buttonSave);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create object which will be saved to database
                DBContract dbContract;
                try {
                    dbContract = new DBContract(
                            userID.getText().toString(), Double.valueOf(latitude.getText().toString()),
                            Double.valueOf(longitude.getText().toString()));
                // catch Exception in case user doesn't input correct values for Longitude & Latitude
                } catch (NumberFormatException e) {
                    dbContract = new DBContract(
                            userID.getText().toString(), 0, 0);
                }
                //save to database
                long a = dbHelper.insert(dbContract);
                if ( a >= 0 ) {
                    //if insert was successful, then the text of the EditTextViews returns to previous state
                    userID.setText(R.string.text_userid);
                    latitude.setText(R.string.text_latitude);
                    longitude.setText(R.string.text_longitude);
                    Toast.makeText(MainActivity.this, "Saved in row "+a, Toast.LENGTH_SHORT).show();
                } else {
                    //else inform user that insert failed
                    Toast.makeText(MainActivity.this, "Error in saving. Returned "+a, Toast.LENGTH_SHORT).show();
                }
            }
        });


        Button button3 = findViewById(R.id.buttonList);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("gr.hua.dit.it21530.assignment1","gr.hua.dit.it21530.assignment1.ListAllActivity");
                startActivity(intent);
            }
        });
    }
}
