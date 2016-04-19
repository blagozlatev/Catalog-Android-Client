package org.catalog.android.client;

import java.net.URI;
import java.util.ArrayList;

import org.catalog.model.Bottle;
import org.catalog.web.WebHelperClass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ShowAllBottlesActivity extends Activity {
    ArrayList<Bottle> bottles;
    ListView lstAllBottles;
    ProgressDialog progressDialog;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_bottles_layout);

        lstAllBottles = (ListView) findViewById(R.id.lstAllBottles);
        new DownloadBottles().execute();

        lstAllBottles.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                                    long arg3) {
                Intent showSingleBottle = new Intent(
                        ShowAllBottlesActivity.this, ShowBottleActivity.class);
                showSingleBottle.putExtra(ShowAllBottlesActivity.this.getString(R.string.bottleid), bottles.get(pos).getID());
                showSingleBottle.putExtra(ShowAllBottlesActivity.this.getString(R.string.isBottleDelete), false);
                ShowAllBottlesActivity.this.startActivity(showSingleBottle);
            }
        });
    }

    public class DownloadBottles extends AsyncTask<URI, Void, ArrayList<Bottle>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ShowAllBottlesActivity.this);
            progressDialog.setTitle(ShowAllBottlesActivity.this.getString(R.string.please_wait));
            progressDialog.setMessage(ShowAllBottlesActivity.this.getString(R.string.getting_bottle_info));
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        @Override
        protected ArrayList<Bottle> doInBackground(URI... url) {
            return WebHelperClass.getAllBottles();
        }

        @Override
        protected void onPostExecute(ArrayList<Bottle> result) {
            super.onPostExecute(bottles);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            bottles = result;
            setListViewAdapterNames();
        }

        private void setListViewAdapterNames() {
            if (bottles != null) {
                ArrayList<String> bottleNamesForAdatper = new ArrayList<String>();
                for (int i = 0; i < bottles.size(); i++) {
                    bottleNamesForAdatper.add(bottles.get(i).getName());
                }
                ArrayAdapter<String> bottleNames = new ArrayAdapter<String>(
                        ShowAllBottlesActivity.this,
                        android.R.layout.simple_list_item_1, bottleNamesForAdatper);
                lstAllBottles.setAdapter(bottleNames);
            }
        }
    }
}
