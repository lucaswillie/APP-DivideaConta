package com.example.willie.desenvolvimentoapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class Registrar extends AppCompatActivity {


    Intent launchNextActivity;
    EditText edtNome;
    EditText edtEmail;
    EditText edtSenha;
    CircularProgressButton circularProgressButton;

    ModelRegistrar modelRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        circularProgressButton = (CircularProgressButton)findViewById(R.id.btnRegistrar);

    }

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



    public void clickRegistrar(View v)
    {

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);

        if (edtNome.length() == 0){
            edtNome.setError("O campo Nome é obrigatório!");
            edtNome.requestFocus();
            return;
        }
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

        final ModelRegistrar modelRegistrar = new ModelRegistrar(edtNome.getText().toString(),
                edtEmail.getText().toString(),
                Integer.parseInt(edtSenha.getText().toString()));



        final RegistrarDAO registrarDAO = new RegistrarDAO();

        // FAZER A VALIDAÇÃO SE EXISTE EMAIL CADASTRADO , SE EXISTIR DA MESAGEM DE ERRO , SE NAO REGISTRADO COM SUCESSO
        if (!isOnline())
        {
            Toast.makeText(getBaseContext(), "É necessario ter acesso a internet para cadastrar!", Toast.LENGTH_LONG).show();
            return;
        }



        AsyncTask<String,String,String> demoDownload = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... voids) {
                try {
                    Thread.sleep(3000);


                } catch (Exception e) {
                    e.printStackTrace();
                }

                return "valid";
            }
            @Override
            protected void onPostExecute(String s) {
                if (registrarDAO.selecionarUsuarios(modelRegistrar.getEmail()) == null)
                {
                    registrarDAO.inserirUsuarios(modelRegistrar.getNome(), modelRegistrar.getEmail(), modelRegistrar.getSenha());

                    launchNextActivity = new Intent(Registrar.this, MainActivity.class);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    Bundle b = new Bundle();
                    b.putInt("key", 1);
                    launchNextActivity.putExtra("backRegister", 1);
                    startActivity(launchNextActivity);

                    Toast.makeText(getBaseContext(), "Registrado com sucesso!", Toast.LENGTH_LONG).show();


                }
                else
                {
                    circularProgressButton.stopAnimation();
                    circularProgressButton.revertAnimation();
                    Toast.makeText(getApplicationContext(), "Já possui usuario cadastrado com esse email.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        };
        circularProgressButton.startAnimation();
        demoDownload.execute();























      /* if (registrarDAO.selecionarUsuarios(modelRegistrar.getEmail()) == null)
      {
          registrarDAO.inserirUsuarios(modelRegistrar.getNome(), modelRegistrar.getEmail(), modelRegistrar.getSenha());

          launchNextActivity = new Intent(Registrar.this, MainActivity.class);
          launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
          launchNextActivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
          Bundle b = new Bundle();
          b.putInt("key", 1);
          launchNextActivity.putExtra("backRegister", 1);
          startActivity(launchNextActivity);

          Toast.makeText(getBaseContext(), "Registrado com sucesso!", Toast.LENGTH_LONG).show();



      }
      else
      {
          Toast.makeText(getApplicationContext(), "Já possui usuario cadastrado com esse email.", Toast.LENGTH_LONG).show();

      }
    */





    }


}
