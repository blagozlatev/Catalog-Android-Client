package org.catalog.web;

import java.io.IOException;

import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.catalog.model.Bottle;

public class WebAppConnection {

	public static void Connect(Bottle b) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(b.getPostUrl());

		try {
			HttpEntity entity = new StringEntity(Bottle.Serialize(b));
			httppost.setEntity(entity);
			// HttpResponse response;
			// response =
			httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
}