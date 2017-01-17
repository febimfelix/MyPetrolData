package com.febi.mydata;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class AddEntryActivity extends AppCompatActivity {
    private EditText mAmountTextView;
    private EditText mPlaceTextView;
    private TextView mDateTextView;
    private EditText mQuantityTextView;

    private boolean mIsEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        Button submitButton = (Button) findViewById(R.id.id_submit_button);
        mAmountTextView     = (EditText) findViewById(R.id.id_amount_edit_text);
        mPlaceTextView      = (EditText) findViewById(R.id.id_place_edit_text);
        mDateTextView       = (TextView) findViewById(R.id.id_date_edit_text);
        mQuantityTextView   = (EditText) findViewById(R.id.id_quantity_edit_text);

        if (getIntent() != null) {
            mIsEditMode     = getIntent().getBooleanExtra("is_edit", false);
        }

        if (mIsEditMode) {
            Intent intent   = getIntent();
            mAmountTextView.setText(intent.getStringExtra("amount"));
            mPlaceTextView.setText(intent.getStringExtra("place"));
            mDateTextView.setText(intent.getStringExtra("date"));
            mQuantityTextView.setText("" + intent.getIntExtra("quantity", 0));
        }

        mDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new StartDatePicker();
                dialogFragment.show(getFragmentManager(), "start_date_picker");
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateValues()) {
                    new InsertValuesTask().execute(mAmountTextView.getText().toString(),
                            mPlaceTextView.getText().toString(),
                            mDateTextView.getText().toString(),
                            Integer.parseInt(mQuantityTextView.getText().toString()));
                } else {
                    showErrorToFillFields();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_delete:
                if(mIsEditMode) {
                    new DeleteRowTask().execute(getIntent().getIntExtra("_id", -1));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean validateValues() {
        if(!mAmountTextView.getText().toString().isEmpty()) {
            if(!mQuantityTextView.getText().toString().isEmpty()) {
                if(!mPlaceTextView.getText().toString().isEmpty()) {
                    if(!mDateTextView.getText().toString().isEmpty()) {
                        if(!mDateTextView.getText().toString().equals("Select Date")) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private class InsertValuesTask extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... params) {
            MyData myData = new MyData();
            myData.setAmount((String) params[0]);
            myData.setPlace((String) params[1]);
            myData.setDate((String) params[2]);
            myData.setQuantity((Integer) params[3]);

            if (mIsEditMode) {
                myData.setId(getIntent().getIntExtra("_id", -1));
                MyData.editEntry(myData);
            } else {
                MyData.insertEntry(myData);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            finish();
        }
    }

    private void showErrorToFillFields() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddEntryActivity.this);
        alertDialog.setMessage("Please fill fields properly!!!");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    private class DeleteRowTask extends AsyncTask<Object, Void, Void> {

        @Override
        protected Void doInBackground(Object... params) {

            new MyData().deleteRow((Integer) params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            finish();
        }
    }
}
