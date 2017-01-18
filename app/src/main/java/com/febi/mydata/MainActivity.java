package com.febi.mydata;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TextView mTotalDescriptionText;

    private ArrayList<MyData> mMyDataList = new ArrayList<>();
    private ArrayList<MyData> mAllDataList= new ArrayList<>();

    private float[] mMonthTotalArray = new float[12];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView           = (RecyclerView) findViewById(R.id.my_recycler_view);
        mTotalDescriptionText   = (TextView) findViewById(R.id.id_month_total_desc_text);
        ImageView addView       = (ImageView) findViewById(R.id.id_add_row);

        mLayoutManager  = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter        = new MyListAdapter(MainActivity.this, mMyDataList);
        mRecyclerView.setAdapter(mAdapter);

        addView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddEntryActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        new GetValuesTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.month_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_jan:
                updateViews(false, 1);
                return true;
            case R.id.id_feb:
                updateViews(false, 2);
                return true;
            case R.id.id_mar:
                updateViews(false, 3);
                return true;
            case R.id.id_apr:
                updateViews(false, 4);
                return true;
            case R.id.id_may:
                updateViews(false, 5);
                return true;
            case R.id.id_jun:
                updateViews(false, 6);
                return true;
            case R.id.id_jul:
                updateViews(false, 7);
                return true;
            case R.id.id_aug:
                updateViews(false, 8);
                return true;
            case R.id.id_sep:
                updateViews(false, 9);
                return true;
            case R.id.id_oct:
                updateViews(false, 10);
                return true;
            case R.id.id_nov:
                updateViews(false, 11);
                return true;
            case R.id.id_dec:
                updateViews(false, 12);
                return true;
            case R.id.id_clear:
                showAlert();
                return true;
            case R.id.id_graph:
                showGraphActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class GetValuesTask extends AsyncTask<Void, Void, ArrayList<MyData>> {

        @Override
        protected ArrayList<MyData> doInBackground(Void... params) {
            return MyData.getValuesFromDb();
        }

        @Override
        protected void onPostExecute(ArrayList<MyData> myDatas) {
            super.onPostExecute(myDatas);

            if(myDatas != null) {
                mAllDataList.clear();
                mAllDataList.addAll(myDatas);

                Calendar calendar   = Calendar.getInstance();
                int month           = calendar.get(Calendar.MONTH) + 1;
                updateViews(true, month);
            }
        }
    }

    private void updateViews(boolean isUpdated, int month) {
        mMyDataList.clear();

        int totalAmount = 0, totalQuantity = 0, janTotal = 0, febTotal = 0, marTotal = 0,
                aprTotal = 0, mayTotal = 0, junTotal = 0, julTotal = 0, augTotal = 0, sepTotal = 0,
                octTotal = 0, novTotal = 0, decTotal = 0;
        for(MyData myData : mAllDataList) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(myData.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int monthNumber = Integer.parseInt(DateFormat.format("MM", convertedDate).toString());
            if (monthNumber == month) {
                totalAmount += Integer.parseInt(myData.getAmount());
                totalQuantity += myData.getQuantity();
                mMyDataList.add(myData);
            }

            //Calculate month wise data
            if (isUpdated) {
                int value = Integer.parseInt(myData.getAmount());
                if (monthNumber == 1) {
                    janTotal += value;
                } else if (monthNumber == 2) {
                    febTotal += value;
                } else if (monthNumber == 3) {
                    marTotal += value;
                } else if (monthNumber == 4) {
                    aprTotal += value;
                } else if (monthNumber == 5) {
                    mayTotal += value;
                } else if (monthNumber == 6) {
                    junTotal += value;
                } else if (monthNumber == 7) {
                    julTotal += value;
                } else if (monthNumber == 8) {
                    augTotal += value;
                } else if (monthNumber == 9) {
                    sepTotal += value;
                } else if (monthNumber == 10) {
                    octTotal += value;
                } else if (monthNumber == 11) {
                    novTotal += value;
                } else if (monthNumber == 12) {
                    decTotal += value;
                }
            }

            mMonthTotalArray[0] = janTotal;
            mMonthTotalArray[1] = febTotal;
            mMonthTotalArray[2] = marTotal;
            mMonthTotalArray[3] = aprTotal;
            mMonthTotalArray[4] = mayTotal;
            mMonthTotalArray[5] = junTotal;
            mMonthTotalArray[6] = julTotal;
            mMonthTotalArray[7] = augTotal;
            mMonthTotalArray[8] = sepTotal;
            mMonthTotalArray[9] = octTotal;
            mMonthTotalArray[10] = novTotal;
            mMonthTotalArray[11] = decTotal;
        }

        mAdapter.notifyDataSetChanged();

        String monthString = "";
        if(month == 1) {
            monthString = "January";
        } else if(month == 2) {
            monthString = "February";
        } else if(month == 3) {
            monthString = "March";
        } else if(month == 4) {
            monthString = "April";
        } else if(month == 5) {
            monthString = "May";
        } else if(month == 6) {
            monthString = "June";
        } else if(month == 7) {
            monthString = "July";
        } else if(month == 8) {
            monthString = "August";
        } else if(month == 9) {
            monthString = "September";
        } else if(month == 10) {
            monthString = "October";
        } else if(month == 11) {
            monthString = "November";
        } else if(month == 12) {
            monthString = "December";
        }
        mTotalDescriptionText.setText(monthString + " : " + totalQuantity
                + " Litres for Rs. " + totalAmount);

        if(totalAmount > 5000) {
            mTotalDescriptionText.setTextColor(Color.RED);
        } else {
            mTotalDescriptionText.setTextColor(Color.BLACK);
        }
    }

    private void showAlert() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Are you sure you want to clear the whole database?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new ClearDbTask().execute();
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    private class ClearDbTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            MyData.clearDatabase();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            mAllDataList.clear();
            Calendar calendar   = Calendar.getInstance();
            int month           = calendar.get(Calendar.MONTH) + 1;
            updateViews(true, month);
        }
    }

    private void showGraphActivity() {
        Intent intent = new Intent(MainActivity.this, GraphActivity.class);
        intent.putExtra("values", mMonthTotalArray);
        startActivity(intent);
    }

    public void onListClick(int position) {
        MyData myData = mMyDataList.get(position);
        if(myData != null) {
            Intent intent = new Intent(MainActivity.this, AddEntryActivity.class);
            intent.putExtra("is_edit", true);
            intent.putExtra("amount", myData.getAmount());
            intent.putExtra("_id", myData.getId());
            intent.putExtra("place", myData.getPlace());
            intent.putExtra("date", myData.getDate());
            intent.putExtra("quantity", myData.getQuantity());
            startActivity(intent);
        }
    }
}
