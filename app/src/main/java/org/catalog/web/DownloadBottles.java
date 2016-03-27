package org.catalog.web;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.catalog.android.client.R;
import org.catalog.android.client.ShowBottleActivity;
import org.catalog.model.Bottle;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Blagovest Zlatev on 13.3.2016 г..
 */
public class DownloadBottles extends AsyncTask<URI, Void, ArrayList<Bottle>> {

    private ArrayList<Bottle> bottles;
    private Context context;
    private ProgressDialog mProgressDialog;
    private ListView listView;
    private boolean isForDelete;

    public DownloadBottles(Context ActivityContext, ListView LV, boolean isForDelete) {
        bottles = null;
        context = ActivityContext;
        listView = LV;
        this.isForDelete = isForDelete;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle(context.getString(R.string.please_wait));
        mProgressDialog.setMessage(context.getString(R.string.getting_bottle_info));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

    @Override
    protected ArrayList<Bottle> doInBackground(URI... url) {
        return WebAppConnection.getAllBottles(url[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Bottle> result) {
        super.onPostExecute(result);
        setBottles(result);
        if (mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
        setListViewAdapterNames();
    }

    private void setBottles(ArrayList<Bottle> result) {
        bottles = result;
    }

    private void setListViewAdapterNames() {
        if (bottles != null) {
            ArrayList<String> bottleNamesForAdatper = new ArrayList<String>();
            for (int i = 0; i < bottles.size(); i++) {
                bottleNamesForAdatper.add(bottles.get(i).getName());
            }
            ArrayAdapter<String> bottleNames = new ArrayAdapter<String>(
                    context,
                    android.R.layout.simple_list_item_1, bottleNamesForAdatper);
            listView.setAdapter(bottleNames);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                                    long arg3) {
                Intent showSingleBottle = new Intent(
                        context, ShowBottleActivity.class);
                showSingleBottle.putExtra(context.getString(R.string.bottleid), bottles.get(pos).getID());
                showSingleBottle.putExtra(context.getString(R.string.isBottleDelete), isForDelete);
                context.startActivity(showSingleBottle);
            }
        });
    }
}
