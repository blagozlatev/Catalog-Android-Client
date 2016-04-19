package org.catalog.android.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.catalog.model.Bottle;
import org.catalog.model.BottleImage;
import org.catalog.web.WebHelperClass;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowBottleActivity extends Activity {

    Bottle bottle;
    BottleImage bottleImage;
    boolean toDelete;
    ProgressDialog progressDialog;
    TextView txtViewAge;
    TextView txtViewAlcohol;
    TextView txtViewAlcoholType;
    TextView txtViewCity;
    TextView txtViewColour;
    TextView txtViewContent;
    TextView txtViewContinent;
    TextView txtViewCountry;
    TextView txtViewId;
    TextView txtViewManufacturer;
    TextView txtViewMaterial;
    TextView txtViewName;
    TextView txtViewNote;
    TextView txtViewShape;
    TextView txtViewShell;
    ImageView imgShowBottleImage;
    Button btnDeleteBottle;
    int bottleId;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_bottle_layout);

        txtViewAge = (TextView) findViewById(R.id.txtViewAge);
        txtViewAlcohol = (TextView) findViewById(R.id.txtViewAlcohol);
        txtViewAlcoholType = (TextView) findViewById(R.id.txtViewAlcoholType);
        txtViewCity = (TextView) findViewById(R.id.txtViewCity);
        txtViewColour = (TextView) findViewById(R.id.txtViewColour);
        txtViewContent = (TextView) findViewById(R.id.txtViewContent);
        txtViewContinent = (TextView) findViewById(R.id.txtViewContinent);
        txtViewCountry = (TextView) findViewById(R.id.txtViewCountry);
        txtViewId = (TextView) findViewById(R.id.txtViewId);
        txtViewManufacturer = (TextView) findViewById(R.id.txtViewManufacturer);
        txtViewMaterial = (TextView) findViewById(R.id.txtViewMaterial);
        txtViewName = (TextView) findViewById(R.id.txtViewName);
        txtViewNote = (TextView) findViewById(R.id.txtViewNote);
        txtViewShape = (TextView) findViewById(R.id.txtViewShape);
        txtViewShell = (TextView) findViewById(R.id.txtViewShell);
        imgShowBottleImage = (ImageView) findViewById(R.id.imgShowBottleImage);
        btnDeleteBottle = (Button) findViewById(R.id.btnDeleteBottle);
        Bundle extras = getIntent().getExtras();
        bottleId = extras.getInt(getString(R.string.bottleid));
        toDelete = extras.getBoolean(getString(R.string.isBottleDelete));

        btnDeleteBottle.setVisibility(View.INVISIBLE);

        if(toDelete) {
            btnDeleteBottle.setVisibility(View.VISIBLE);
            btnDeleteBottle.setClickable(true);
            btnDeleteBottle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnDeleteBottle.setClickable(false);
                    new DeleteBottle().execute();
                }
            });
        }

        new GetBottle().execute();
    }

    public class GetBottle extends AsyncTask<URI, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ShowBottleActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setTitle(getString(R.string.please_wait));
            progressDialog.setMessage(getString(R.string.getting_bottle_info));
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(URI... url) {
            try {
                bottle = WebHelperClass.recieveBottle(new URI(
                        getString(R.string.url_get_bottle) + bottleId));
                bottleImage = WebHelperClass.recieveBottleImage(bottleId, new URI(
                        getString(R.string.url_get_bottle_image) + bottleId));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (bottle != null) {
                txtViewAge.setText(String.valueOf(bottle.getAge()));
                txtViewAlcohol.setText(bottle.getAlcohol());
                txtViewAlcoholType.setText(bottle.getAlcoholType());
                txtViewCity.setText(bottle.getCity());
                txtViewColour.setText(bottle.getColor());
                txtViewContent.setText(bottle.getContent());
                txtViewContinent.setText(bottle.getContinent());
                txtViewCountry.setText(bottle.getCountry());
                txtViewId.setText(String.valueOf(bottle.getID()));
                txtViewManufacturer.setText(bottle.getManufacturer());
                txtViewMaterial.setText(bottle.getMaterial());
                txtViewName.setText(bottle.getName());
                txtViewNote.setText(bottle.getNote());
                txtViewShape.setText(bottle.getShape());
                txtViewShell.setText(bottle.getShell());
            } else {
                final AlertDialog errorDialog = new AlertDialog.Builder(
                        ShowBottleActivity.this).create();
                errorDialog.setTitle(getString(R.string.error));
                errorDialog.setMessage(getString(R.string.no_bottle));
                errorDialog.setButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        errorDialog.dismiss();
                    }
                });
                errorDialog.show();
            }
            if (bottleImage != null) {
                imgShowBottleImage.setImageBitmap(bottleImage.getImage());
            }
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    public class DeleteBottle extends AsyncTask<URI, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ShowBottleActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setTitle(getString(R.string.please_wait));
            progressDialog.setMessage(getString(R.string.getting_bottle_info));
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(URI... url) {
            try {
                return WebHelperClass.deleteBottle(new URI(getString(R.string.url_delete_bottle) + bottleId));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if(result) {
                Toast.makeText(getApplicationContext(), getString(R.string.delete_successful), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.delete_unsuccessful), Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(ShowBottleActivity.this,
                    MainActivity.class);
            intent.putExtra(ShowBottleActivity.this.getString(R.string.isAuth),true);
            startActivity(intent);
        }
    }

}