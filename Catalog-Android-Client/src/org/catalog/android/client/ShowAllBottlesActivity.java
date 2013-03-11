package org.catalog.android.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.catalog.model.Bottle;
import org.catalog.web.WebAppConnection;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ShowAllBottlesActivity extends Activity {
	ArrayList<Bottle> bottles;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_all_bottles_layout);

		final ListView lstAllBottles = (ListView) findViewById(R.id.lstAllBottles);

		new Thread(new Runnable() {
			ProgressDialog dialog = new ProgressDialog(ShowAllBottlesActivity.this);
			ArrayList<Bottle> bottles;

			@Override
			public void run() {
				// Start the indeterminate dialog.
				ShowAllBottlesActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						dialog.setCancelable(false);
						dialog.setIndeterminate(true);
						dialog.setTitle(getString(R.string.please_wait));
						dialog.setMessage(getString(R.string.getting_bottle_info));
						dialog.show();
					}
				});

				try {
					bottles = WebAppConnection.getAllBottles(new URI(
							"http://bottlewebapp.apphb.com/Serialized/"));
				} catch (URISyntaxException ex) {
					ex.printStackTrace();
				}

				// Cancel the dialog.
				// Setting the list with the Bottle names.
				ShowAllBottlesActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (dialog.isShowing()) {
							dialog.cancel();
						}
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

						lstAllBottles.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
									long arg3) {
								Intent showSingleBottle = new Intent(
										ShowAllBottlesActivity.this, ShowBottleActivity.class);
								showSingleBottle.putExtra(getString(R.string.bottleid), bottles
										.get(pos).getID());
								startActivity(showSingleBottle);
							}
						});
					}
				});
			}
		}).start();
	}
}
