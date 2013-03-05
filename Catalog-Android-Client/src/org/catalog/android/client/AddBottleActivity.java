package org.catalog.android.client;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class AddBottleActivity extends Activity {

	private static final int SELECT_PHOTO = 100;	
	private ImageView imgBottleImage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_bottle_layout);

		Button btnSelectImage = (Button) findViewById(R.id.btnSelectImage);
		imgBottleImage = (ImageView) findViewById(R.id.imgBottleImage);
		btnSelectImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
				photoPickerIntent.setType("image/*");
				startActivityForResult(photoPickerIntent, SELECT_PHOTO);				
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		switch (requestCode) {
		case SELECT_PHOTO:
			if (resultCode == RESULT_OK) {
				String filePath = getImageFilePath(imageReturnedIntent);				
				imgBottleImage.setImageBitmap(BitmapFactory.decodeFile(filePath));
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
