package gr.hua.dit.it21530.assignment1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;

public class ListAllActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);

        // load all rows and display them on SpinnerView using ArrayAdapter
        final Spinner spinner = findViewById(R.id.rowspinner);
        final DbHelper dbHelper = new DbHelper(ListAllActivity.this);
        List<String> rows = dbHelper.selectAllRows();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, rows);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }
}
