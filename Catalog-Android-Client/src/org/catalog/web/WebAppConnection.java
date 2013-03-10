package org.catalog.web;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import org.catalog.android.client.R;
import android.content.res.Resources;
import org.catalog.model.Bottle;
import org.catalog.model.BottleImage;

public class WebAppConnection {
	public static String send(Bottle b) {
		return setUrlAndEntityAndSend(b.getPostUrl(), Bottle.Serialize(b));
	}

	public static String send(BottleImage bi) {
		return setUrlAndEntityAndSend(bi.getPostImageUrl(),
				bi.getBitmapBase64Encode());
	}

	public static Bottle recieveBottle(int id, URI url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse responseEntity;
		try {
			responseEntity = httpclient.execute(httpget);
			StatusLine statusLine = responseEntity.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				responseEntity.getEntity().writeTo(out);
				out.close();
				String responseString = out.toString();
				return Bottle.Deserialize(responseString);
			}
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static BottleImage recieveBottleImage(int id, URI url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response;
		BottleImage bi = new BottleImage();
		try {
			response = httpclient.execute(httpget);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity responseEntity = response.getEntity();
				InputStream is = responseEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				StringBuilder out = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					out.append(line);
				}
				String responseString = out.toString();
				bi.setBitmapBase64Decode(responseString);
				bi.setBottleId(id);
				bi.setPostImageUrl(url);
				return bi;
			}
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static String setUrlAndEntityAndSend(URI url, String entity) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {
			HttpEntity httpEntity = new StringEntity(entity);
			httppost.setEntity(httpEntity);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity responseEntity = response.getEntity();
			InputStream is = responseEntity.getContent();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			StringBuilder out = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				out.append(line);
			}
			responseEntity.getContent().close();
			return out.toString();

		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}
		return Resources.getSystem().getString(R.string.denied);
	}
}