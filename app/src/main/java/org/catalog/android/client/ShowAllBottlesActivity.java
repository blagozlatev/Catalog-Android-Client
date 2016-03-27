package org.catalog.android.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.catalog.model.Bottle;
import org.catalog.web.DownloadBottles;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ShowAllBottlesActivity extends Activity {
    ArrayList<Bottle> bottles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_all_bottles_layout);

        final ListView lstAllBottles = (ListView) findViewById(R.id.lstAllBottles);
        DownloadBottles downloadBottles = new DownloadBottles(ShowAllBottlesActivity.this, lstAllBottles, false);
        try {
            downloadBottles.execute(new URI(getString(R.string.all_bottles)));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}
