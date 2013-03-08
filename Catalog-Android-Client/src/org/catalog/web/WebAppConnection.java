package org.catalog.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.catalog.model.Bottle;

public class WebAppConnection {

	public static void Connect(Bottle b) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response;	
		try {
			response = httpclient.execute(new HttpPost(b.getPostUrl()
					.toString()));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			response.setEntity(new StringEntity(Bottle.Serialize(b)));
			out.close();
			//responseString = out.toString();
		} catch (ClientProtocolException e) {
			// TODO Handle problems..
		} catch (IOException e) {
			// TODO Handle problems..
		}
	}
}