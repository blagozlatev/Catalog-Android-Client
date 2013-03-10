package org.catalog.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import org.catalog.android.client.R;
import android.content.res.Resources;
import org.catalog.model.Bottle;
import org.catalog.model.BottleImage;

public class WebAppConnection {
	public static String send(Bottle b) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(b.getPostUrl());

		try {
			HttpEntity entity = new StringEntity(Bottle.Serialize(b));
			httppost.setEntity(entity);
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
			return out.toString();

		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}
		return Resources.getSystem().getString(R.string.denied);
	}

	public static String send(BottleImage bi) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(bi.getPostImageUrl());

		try {
			HttpEntity entity = new StringEntity(bi.getBitmapBase64Encode());
			httppost.setEntity(entity);
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
			return out.toString();

		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}
		return Resources.getSystem().getString(R.string.denied);
	}
}