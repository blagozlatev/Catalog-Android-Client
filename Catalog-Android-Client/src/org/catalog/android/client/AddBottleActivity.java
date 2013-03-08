package org.catalog.android.client;

import org.catalog.constants.ImageConstants;
import org.catalog.model.Bottle;
import org.catalog.web.WebAppConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AddBottleActivity extends Activity {

	private ImageView imgBottleImage;
	private ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_bottle_layout);

		Button btnSelectImage = (Button) findViewById(R.id.btnSelectImage);
		Button btnClearFields = (Button) findViewById(R.id.btnClearFields);
		Button btnSendBottle = (Button) findViewById(R.id.btnSendBottle);
		final TextView txtAge = (TextView) findViewById(R.id.txtAge);
		final TextView txtAlcohol = (TextView) findViewById(R.id.txtAlcohol);
		final TextView txtAlcoholType = (TextView) findViewById(R.id.txtAlcoholType);
		final TextView txtCity = (TextView) findViewById(R.id.txtCity);
		final TextView txtColour = (TextView) findViewById(R.id.txtColour);
		final TextView txtContent = (TextView) findViewById(R.id.txtContent);
		final TextView txtContinent = (TextView) findViewById(R.id.txtContinent);
		final TextView txtCountry = (TextView) findViewById(R.id.txtCountry);
		final TextView txtId = (TextView) findViewById(R.id.txtId);
		final TextView txtManufacturer = (TextView) findViewById(R.id.txtManufacturer);
		final TextView txtMaterial = (TextView) findViewById(R.id.txtMaterial);
		final TextView txtName = (TextView) findViewById(R.id.txtName);
		final TextView txtNote = (TextView) findViewById(R.id.txtNote);
		final TextView txtShape = (TextView) findViewById(R.id.txtShape);
		final TextView txtShell = (TextView) findViewById(R.id.txtShell);
		dialog = new ProgressDialog(getApplicationContext());

		imgBottleImage = (ImageView) findViewById(R.id.imgBottleImage);
		btnSelectImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent,
						ImageConstants.SELECT_PHOTO);
			}
		});

		btnClearFields.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				txtAge.setText(null);
				txtAlcohol.setText(null);
				txtAlcoholType.setText(null);
				txtCity.setText(null);
				txtColour.setText(null);
				txtContent.setText(null);
				txtContinent.setText(null);
				txtCountry.setText(null);
				txtId.setText(null);
				txtManufacturer.setText(null);
				txtMaterial.setText(null);
				txtName.setText(null);
				txtNote.setText(null);
				txtShape.setText(null);
				txtShell.setText(null);
				imgBottleImage.setImageBitmap(null);
			}
		});

		btnSendBottle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Bottle bottle = new Bottle();
				bottle.setAge(Integer.parseInt(txtAge.getText().toString()));
				bottle.setAlcohol(txtAlcohol.getText().toString());
				bottle.setAlcoholType(txtAlcoholType.getText().toString());
				bottle.setCity(txtCity.getText().toString());
				bottle.setColor(txtColour.getText().toString());
				bottle.setContent(txtContent.getText().toString());
				bottle.setContinent(txtContinent.getText().toString());
				bottle.setCountry(txtCountry.getText().toString());
				bottle.setID(Integer.parseInt(txtId.getText().toString()));
				bottle.setManufacturer(txtManufacturer.getText().toString());
				bottle.setMaterial(txtMaterial.getText().toString());
				bottle.setName(txtName.getText().toString());
				bottle.setNote(txtNote.getText().toString());
				bottle.setShape(txtShape.getText().toString());
				bottle.setShell(txtShell.getText().toString());
				bottle.setPostUrl(Uri
						.parse("http://bottlewebapp.apphb.com/Serialized/Post"));
				new Thread(new Runnable() {

					@Override
					public void run() {
//						runOnUiThread(new Runnable() {
//
//							@Override
//							public void run() {
//								dialog.show(getApplicationContext(), "Wait",
//										"Wait", true, false);
//							}
//						});
						WebAppConnection.Connect(bottle);
//						runOnUiThread(new Runnable() {
//
//							@Override
//							public void run() {
//								if (dialog.isShowing()) {
//									dialog.cancel();
//								}
//							}
//						});
					}
				}).start();
			}
		});
		// Bitmap bit = ((BitmapDrawable) imgBottleImage.getDrawable())
		// .getBitmap();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		switch (requestCode) {
		case ImageConstants.SELECT_PHOTO:
			if (resultCode == RESULT_OK) {
				String filePath = getImageFilePath(imageReturnedIntent);
				imgBottleImage.setImageBitmap(BitmapFactory
						.decodeFile(filePath));
			}
		}

	}

	private String getImageFilePath(Intent imageReturnedIntent) {
		Uri selectedImage = imageReturnedIntent.getData();
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = getContentResolver().query(selectedImage,
				filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String filePath = cursor.getString(columnIndex);
		cursor.close();
		return filePath;
	}
}
