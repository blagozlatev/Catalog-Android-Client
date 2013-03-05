package org.catalog.android.client;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

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
				Intent intent = new Intent(MainActivity.this,
						ShowBottleActivity.class);
				startActivity(intent);
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
