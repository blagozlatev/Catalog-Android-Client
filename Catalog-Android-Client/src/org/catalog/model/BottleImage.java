package org.catalog.model;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class BottleImage {
	private Bitmap image;
	private int bottleId;
	private URI postImageUrl;

	public BottleImage() {
		image = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
		bottleId = 0;
		try {
			postImageUrl = new URI("");
		} catch (URISyntaxException ex) {

		}
	}

	public BottleImage(Bitmap bi, int bid, URI uri) {
		image = bi;
		bottleId = bid;
		postImageUrl = uri;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public int getBottleId() {
		return bottleId;
	}

	public void setBottleId(int bottleId) {
		this.bottleId = bottleId;
	}

	public URI getPostImageUrl() {
		return postImageUrl;
	}

	public void setPostImageUrl(URI postImageUrl) {
		this.postImageUrl = postImageUrl;
	}

	private byte[] getBytesFromBitmap() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		image.compress(CompressFormat.JPEG, 70, stream);
		return stream.toByteArray();
	}

	private void setBitmapFromBytes(byte[] bytes) {
		image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	public String getBitmapBase64Encode() {
		return Base64.encodeToString(this.getBytesFromBitmap(), Base64.DEFAULT);
	}

	public void setBitmapBase64Decode(String base64) {
		setBitmapFromBytes(Base64.decode(base64, Base64.DEFAULT));
	}
}
