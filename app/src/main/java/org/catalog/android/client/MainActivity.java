package org.catalog.android.client;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
    private boolean isAuth = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Bundle extras = getIntent().getExtras();
        try {
            isAuth = extras.getBoolean(getString(R.string.isAuth));
        } catch (RuntimeException e){
            e.printStackTrace();
        }
        final Button btnAddBottle = (Button) findViewById(R.id.btnAddBottle);
        btnAddBottle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isAuth) {
                    Intent intent = new Intent(MainActivity.this,
                            AddBottleActivity.class);
                    startActivity(intent);
                } else {
                    authDialog(MainActivity.this);
                }
            }
        });

        final Button btnDeleteBottle = (Button) findViewById(R.id.btnDeleteBottle);
        btnDeleteBottle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isAuth) {
                    Intent intent = new Intent(MainActivity.this,
                            DeleteBottleActivity.class);
                    startActivity(intent);
                } else {
                    authDialog(MainActivity.this);
                }
            }
        });

        final Button btnShowAllBottles = (Button) findViewById(R.id.btnShowAllBottles);
        btnShowAllBottles.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        ShowAllBottlesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void authDialog(final Context context) {
        final EditText inputUser = new EditText(context);
        final EditText inputPass = new EditText(context);
        inputPass.setTransformationMethod(new PasswordTransformationMethod());

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(inputUser);
        linearLayout.addView(inputPass);
        final AlertDialog.Builder authDialogBuilder = new AlertDialog.Builder(
                context);
        authDialogBuilder.setTitle(getString(R.string.user_auth));
        authDialogBuilder
                .setMessage(getString(R.string.enter_username));
        authDialogBuilder.setView(linearLayout);
        inputPass.setOnKeyListener(new OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode,
                                 KeyEvent event) {
                return (event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER);
            }
        });

        authDialogBuilder.setPositiveButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (inputUser.getText().toString().equals(getString(R.string.user))
                                && inputPass.getText().toString()
                                .equals(getString(R.string.pass))) {
                            isAuth = true;

                        } else {
                            Toast.makeText(context,
                                    getString(R.string.wrong_user),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

        authDialogBuilder.setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
        authDialogBuilder.show();
    }
}
