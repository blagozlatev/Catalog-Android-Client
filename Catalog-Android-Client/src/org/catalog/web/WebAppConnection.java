package org.catalog.web;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.catalog.model.Bottle;

public class WebAppConnection {

	public static void send(Bottle b) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(b.getPostUrl());

		try {
			HttpEntity entity = new StringEntity(Bottle.Serialize(b));
			httppost.setEntity(entity);
			httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

	}
}