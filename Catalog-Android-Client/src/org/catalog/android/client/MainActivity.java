package org.catalog.android.client;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		Button btnAddBottle = (Button) findViewById(R.id.btnAddBottle);
		btnAddBottle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						AddBottleActivity.class);
				startActivity(intent);
			}
		});

		Button btnShowBottle = (Button) findViewById(R.id.btnShowBottle);
		btnShowBottle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final AlertDialog.Builder bottleIdDialogBuilder = new AlertDialog.Builder(
						MainActivity.this);
				final EditText input = new EditText(MainActivity.this);

				bottleIdDialogBuilder
						.setTitle(getString(R.string.enter_bottle_id));
				bottleIdDialogBuilder
						.setMessage(getString(R.string.please_enter_bottle_id));
				bottleIdDialogBuilder.setView(input);
				bottleIdDialogBuilder.setPositiveButton(getString(R.string.ok),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								try {
									Intent intent = new Intent(
											MainActivity.this,
											ShowBottleActivity.class);
									intent.putExtra("BottleID", Integer
											.parseInt(input.getText()
													.toString()));
									startActivity(intent);
								} catch (NumberFormatException ex) {

								}
							}
						});

				bottleIdDialogBuilder.setNegativeButton(
						getString(R.string.cancel),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								dialog.cancel();
							}
						});
				bottleIdDialogBuilder.show();
			}
		});

		Button btnShowAllBottles = (Button) findViewById(R.id.btnShowAllBottles);
		btnShowAllBottles.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						ShowAllBottlesActivity.class);
				startActivity(intent);
			}
		});
	}
}
