package gr.hua.dit.it21530.assignment3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static String userid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                //when button pressed, get userID and start MapsActivity
        Button button = findViewById(R.id.buttonProceed);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.editText);
                userid = editText.getText().toString();
                Intent intent = new Intent();
                intent.setClassName("gr.hua.dit.it21530.assignment3", "gr.hua.dit.it21530.assignment3.MapsActivity");
                startActivity(intent);
            }
        });
    }
}
