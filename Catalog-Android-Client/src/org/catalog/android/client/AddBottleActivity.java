package org.catalog.android.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.catalog.model.Bottle;
import org.catalog.model.BottleImage;
import org.catalog.web.WebAppConnection;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;

public class AddBottleActivity extends Activity {

	private ImageView imgBottleImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_bottle_layout);

		Button btnSelectImage = (Button) findViewById(R.id.btnSelectImage);
		Button btnClearFields = (Button) findViewById(R.id.btnClearFields);
		Button btnSendBottle = (Button) findViewById(R.id.btnSendBottle);
		final EditText txtAge = (EditText) findViewById(R.id.txtAge);
		final EditText txtAlcohol = (EditText) findViewById(R.id.txtAlcohol);
		final EditText txtAlcoholType = (EditText) findViewById(R.id.txtAlcoholType);
		final EditText txtCity = (EditText) findViewById(R.id.txtCity);
		final EditText txtColour = (EditText) findViewById(R.id.txtColour);
		final EditText txtContent = (EditText) findViewById(R.id.txtContent);
		final EditText txtContinent = (EditText) findViewById(R.id.txtContinent);
		final EditText txtCountry = (EditText) findViewById(R.id.txtCountry);
		final EditText txtId = (EditText) findViewById(R.id.txtId);
		final EditText txtManufacturer = (EditText) findViewById(R.id.txtManufacturer);
		final EditText txtMaterial = (EditText) findViewById(R.id.txtMaterial);
		final EditText txtName = (EditText) findViewById(R.id.txtName);
		final EditText txtNote = (EditText) findViewById(R.id.txtNote);
		final EditText txtShape = (EditText) findViewById(R.id.txtShape);
		final EditText txtShell = (EditText) findViewById(R.id.txtShell);
		imgBottleImage = (ImageView) findViewById(R.id.imgBottleImage);

		btnSelectImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType(getString(R.string.select_image_intent_type));
				startActivityForResult(photoPickerIntent,
						getResources().getInteger(R.integer.select_photo));
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
				final BottleImage bottleImage = new BottleImage();

				boolean isNumberFormatError = false;
				try {

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
					bottle.setPostUrl(new URI(getString(R.string.url_web_app)));

					if (((BitmapDrawable) imgBottleImage.getDrawable()).getBitmap() != null) {
						bottleImage.setImage(((BitmapDrawable) imgBottleImage.getDrawable())
								.getBitmap());
						bottleImage.setBottleId(bottle.getID());
						bottleImage.setPostImageUrl(new URI(
								getString(R.string.url_web_app_image)
										+ String.valueOf(bottle.getID())));
					}
				} catch (NumberFormatException e) {
					isNumberFormatError = true;
				} catch (URISyntaxException e) {

				}

				if (!isNumberFormatError) {
					// New thread for the connecting operation.
					new Thread(new Runnable() {
						ProgressDialog dialog = new ProgressDialog(AddBottleActivity.this);

						@Override
						public void run() {
							// Start the indeterminate dialog.
							AddBottleActivity.this.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									dialog.setCancelable(false);
									dialog.setIndeterminate(true);
									dialog.setTitle(getString(R.string.please_wait));
									dialog.setMessage(getString(R.string.sending_message));
									dialog.show();
								}
							});

							// Sending the bottle to the bottlewebapp.apphb.com.
							final CharSequence sendResult = WebAppConnection.send(bottle);

							if (((BitmapDrawable) imgBottleImage.getDrawable()).getBitmap() != null) {
								WebAppConnection.send(bottleImage);
							}
							// Closing the indeterminate dialog.
							AddBottleActivity.this.runOnUiThread(new Runnable() {

								@Override
								public void run() {
									if (dialog.isShowing()) {
										dialog.cancel();
									}
								}
							});

							checkResultAndShowResultMessage(sendResult);
						}
					}).start();
				} else {
					showMessageOnUiThread(AddBottleActivity.this,
							getString(R.string.error),
							getString(R.string.parse_error_message));
				}
			}

		});
	}

	private void checkResultAndShowResultMessage(CharSequence result) {
		if (result.equals(getString(R.string.one))) {
			showMessageOnUiThread(AddBottleActivity.this,
					getString(R.string.add_successful),
					getString(R.string.bottle_add_succeed));
		} else {
			showMessageOnUiThread(AddBottleActivity.this,
					getString(R.string.server_error),
					getString(R.string.bottle_add_failed));
		}
	}

	private void showMessageOnUiThread(final Context context,
			final CharSequence title, final CharSequence message) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				AlertDialog dialog = new AlertDialog.Builder(context).create();
				dialog.setTitle(title);
				dialog.setMessage(message);
				dialog.setButton(getString(R.string.ok),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

							}
						});
				dialog.show();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
		int code = getResources().getInteger(R.integer.select_photo);
		if (requestCode == code) {
			if (resultCode == RESULT_OK) {
				String filePath = getImageFilePath(imageReturnedIntent);
				imgBottleImage.setImageBitmap(BitmapFactory.decodeFile(filePath));
			}
		}

	}

	private String getImageFilePath(Intent imageReturnedIntent) {
		Uri selectedImage = imageReturnedIntent.getData();
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,
				null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String filePath = cursor.getString(columnIndex);
		cursor.close();
		return filePath;
	}
}
