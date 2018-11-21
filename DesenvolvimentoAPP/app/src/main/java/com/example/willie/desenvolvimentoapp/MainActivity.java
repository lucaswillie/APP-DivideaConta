package com.example.willie.desenvolvimentoapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


import java.text.DecimalFormat;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class MainActivity extends AppCompatActivity {

    EditText edtEmail;
    EditText edtSenha;
    Boolean validado;
    RegistrarDAO registrarDAO = new RegistrarDAO();
    ModelRegistrar modelRegistrar;



    CircularProgressButton circularProgressButton;
    RelativeLayout relative1, relative2;

    Intent launchNextActivity;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            relative1.setVisibility(View.VISIBLE);
            relative2.setVisibility(View.VISIBLE);

        }

    };
    private boolean validateEmailFormat(final String email) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        return false;
    }
    public boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return manager.getActiveNetworkInfo() != null &&
                manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public void valida()
    {

        if (registrarDAO.ValidarLogin(modelRegistrar.getEmail() , modelRegistrar.getSenha()) == false)
        {
            validado = false;


        }
        else
            validado = true;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relative1 = (RelativeLayout)findViewById(R.id.relative1);
        relative2 = (RelativeLayout)findViewById(R.id.relative2);


        handler.postDelayed(runnable,2000);

        circularProgressButton = (CircularProgressButton)findViewById(R.id.btnLogin);


        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                edtEmail = (EditText) findViewById(R.id.edtEmail);
                edtSenha = (EditText) findViewById(R.id.edtSenha);



                if (edtEmail.length() == 0){
                    edtEmail.setError("Campo Email é obrigatório!");
                    edtEmail.requestFocus();
                    return;
                }
                else if (!validateEmailFormat(edtEmail.getText().toString()))
                {
                    edtEmail.setError("Email com formato invalido!");
                    edtEmail.requestFocus();
                    return;

                }
                else if (edtSenha.length() == 0){
                    edtSenha.setError("Campo Senha é obrigatório!");
                    edtSenha.requestFocus();
                    return;
                }

                if (!isOnline())
                {
                    Toast.makeText(getBaseContext(), "É necessario ter acesso a internet para Logar!", Toast.LENGTH_LONG).show();
                    return;
                }







                AsyncTask<String,String,String> demoDownload = new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... voids) {
                        try {
                            Thread.sleep(3000);
                            modelRegistrar = new ModelRegistrar(null,edtEmail.getText().toString(),
                                    Integer.parseInt(edtSenha.getText().toString()));
                            valida();


                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        if (validado)
                            return "valid";
                        else
                            return "not valid";

                    }

                    @Override
                    protected void onPostExecute(String s) {
                        if (s.equals("valid"))
                        {
                            Toast.makeText(MainActivity.this, "Login com Sucesso!", Toast.LENGTH_LONG).show();
                            //circularProgressButton.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));
                            //Intent intent = new Intent(MainActivity.this, Home.class);
                            //startActivity(intent);
                            launchNextActivity = new Intent(MainActivity.this, Home.class);
                            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(launchNextActivity);

                        }
                        else
                        {
                            circularProgressButton.stopAnimation();
                            circularProgressButton.revertAnimation();
                            Toast.makeText(getBaseContext(), "Login ou senha incorretos ", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                };
                circularProgressButton.startAnimation();
                demoDownload.execute();
            }
        });
    }

    public void Registrar (View v)
    {
        Intent intent = new Intent(MainActivity.this, Registrar.class);
        startActivity(intent);
    }






}
