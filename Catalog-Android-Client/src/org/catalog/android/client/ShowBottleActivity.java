package org.catalog.android.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.catalog.model.Bottle;
import org.catalog.model.BottleImage;
import org.catalog.web.WebAppConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowBottleActivity extends Activity {

	Bottle bottle;
	BottleImage bottleImage;
	int bottleId = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_bottle_layout);

		TextView txtViewAge = (TextView) findViewById(R.id.txtViewAge);
		TextView txtViewAlcohol = (TextView) findViewById(R.id.txtViewAlcohol);
		TextView txtViewAlcoholType = (TextView) findViewById(R.id.txtViewAlcoholType);
		TextView txtViewCity = (TextView) findViewById(R.id.txtViewCity);
		TextView txtViewColour = (TextView) findViewById(R.id.txtViewColour);
		TextView txtViewContent = (TextView) findViewById(R.id.txtViewContent);
		TextView txtViewContinent = (TextView) findViewById(R.id.txtViewContinent);
		TextView txtViewCountry = (TextView) findViewById(R.id.txtViewCountry);
		TextView txtViewId = (TextView) findViewById(R.id.txtViewId);
		TextView txtViewManufacturer = (TextView) findViewById(R.id.txtViewManufacturer);
		TextView txtViewMaterial = (TextView) findViewById(R.id.txtViewMaterial);
		TextView txtViewName = (TextView) findViewById(R.id.txtViewName);
		TextView txtViewNote = (TextView) findViewById(R.id.txtViewNote);
		TextView txtViewShape = (TextView) findViewById(R.id.txtViewShape);
		TextView txtViewShell = (TextView) findViewById(R.id.txtViewShell);
		final ImageView imgShowBottleImage = (ImageView) findViewById(R.id.imgShowBottleImage);
		if (savedInstanceState != null) {
			bottleId = savedInstanceState.getInt("BottleID");
		}
		new Thread(new Runnable() {
			ProgressDialog dialog = new ProgressDialog(ShowBottleActivity.this);

			@Override
			public void run() {
				// Start the indeterminate dialog.
				ShowBottleActivity.this.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						dialog.setCancelable(false);
						dialog.setIndeterminate(true);
						dialog.setTitle(getString(R.string.sending));
						dialog.setMessage(getString(R.string.sending_message));
						dialog.show();
					}
				});

				// Sending the bottle to the bottlewebapp.apphb.com.
				try {
					bottle = WebAppConnection.recieveBottle(bottleId, new URI(
							"http://bottlewebapp.apphb.com/Serialized/GetBottle/"
									+ String.valueOf(12)));
					bottleImage = WebAppConnection.recieveBottleImage(bottleId,
							new URI(
									"http://bottlewebapp.apphb.com/Serialized/GetImageBase/"
											+ String.valueOf(12)));
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Closing the indeterminate dialog.
				ShowBottleActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						if (dialog.isShowing()) {
							dialog.cancel();
						}
						if (bottleImage != null) {
							imgShowBottleImage.setImageBitmap(bottleImage.getImage());
						}
					}
				});
			}
		}).start();
		if (bottle != null) {

		}
	}
}
