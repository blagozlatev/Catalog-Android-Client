package org.catalog.android.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.catalog.model.Bottle;
import org.catalog.web.DownloadBottles;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class DeleteBottleActivity extends Activity {
    private ArrayList<Bottle> bottles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_bottle);

        final ListView lstAllBottles = (ListView) findViewById(R.id.lstAllBottles);
        DownloadBottles downloadBottles = new DownloadBottles(DeleteBottleActivity.this, lstAllBottles, true);
        try {
            downloadBottles.execute(new URI(getString(R.string.all_bottles)));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
