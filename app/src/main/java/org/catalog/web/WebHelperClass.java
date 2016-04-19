package org.catalog.web;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

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

import org.catalog.model.Bottle;
import org.catalog.model.BottleImage;

public class WebHelperClass {
    public static final String DENIED = "0";

    public static String send(Bottle b) {
        return postSetUrlAndEntityAndSend(b.getPostUrl(), Bottle.Serialize(b));
    }

    public static String send(BottleImage bi) {
        return postSetUrlAndEntityAndSend(bi.getPostImageUrl(),
                bi.getBitmapBase64Encode());
    }

    public static Bottle recieveBottle(URI url) {
        String responseString = getSetUrlAndSend(url);
        if (!responseString.equals(DENIED)) {
            return Bottle.Deserialize(responseString);
        }
        return null;
    }

    public static BottleImage recieveBottleImage(int id, URI url) {
        try {
            String responseString = getSetUrlAndSend(url);
            return new BottleImage(responseString, id, url);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }


    @SuppressWarnings("SpellCheckingInspection")
    public static ArrayList<Bottle> getAllBottles() {
        ArrayList<Bottle> bottles = new ArrayList<Bottle>();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(new URI("http://bottlewebapp.apphb.com/Serialized/"));
            HttpResponse response = httpclient.execute(httpget);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                InputStream is = response.getEntity().getContent();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(is));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    bottles.add(Bottle.Deserialize(line));
                }
                return bottles;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteBottle(URI url) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpclient.execute(httpPost);
            return true;
        } catch (ClientProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getSetUrlAndSend(URI url) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            HttpResponse responseEntity;
            responseEntity = httpclient.execute(httpget);
            StatusLine statusLine = responseEntity.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                responseEntity.getEntity().writeTo(out);
                out.close();
                return out.toString();
            }
        } catch (ClientProtocolException e1) {
            e1.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return DENIED;
    }

    private static String postSetUrlAndEntityAndSend(URI url, String entity) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            HttpEntity httpEntity = new StringEntity(entity);
            httppost.setEntity(httpEntity);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity responseEntity = response.getEntity();
            InputStream is = responseEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            is.close();
            return out.toString();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DENIED;
    }
}