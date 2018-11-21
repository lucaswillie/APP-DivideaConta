package com.example.willie.desenvolvimentoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Home extends AppCompatActivity {

    EditText edConsumo;
    EditText edCouvert ;
    EditText edQtdPessoa ;
    TextView txtTaxa;
    TextView txtConta;
    TextView txtVlr;
    DecimalFormat DecimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void  calcular(View v)
    {
         edConsumo= (EditText)findViewById(R.id.edConsumo);
          edCouvert = (EditText)findViewById(R.id.edCouvert);
         edQtdPessoa = (EditText)findViewById(R.id.edQtdPessoas);
         txtTaxa = (TextView)findViewById(R.id.txtTaxa);
         txtConta = (TextView)findViewById(R.id.txtConta);
         txtVlr = (TextView)findViewById(R.id.txtVlr);

        if (edConsumo.getText().toString().length() ==   0)
        {
            Toast.makeText(this,"Informe o valor que foi consumido",Toast.LENGTH_LONG).show();
            edConsumo.requestFocus();
            return;
        }

         if (edCouvert.getText().toString().length()  ==  0)
        {
            Toast.makeText(this,"Informe o  valor do Couvert",Toast.LENGTH_LONG).show();
            edCouvert.requestFocus();
            return;
        }
         if (edQtdPessoa.getText().toString().length() ==    0)
        {
            Toast.makeText(this,"Informe a quantidade de pessoas",Toast.LENGTH_LONG).show();
            edQtdPessoa.requestFocus();
            return;
        }


        else
        {

            double Consumo =  Double.parseDouble(edConsumo.getText().toString());
            double Couvert = Double.parseDouble(edCouvert.getText().toString());
             double QtdPessoa = 0;
            QtdPessoa =   Double.parseDouble(edQtdPessoa.getText().toString());
            double SomaTotal =0;
            double Taxa =  0.10;
            double TotalTaxa;

            if (QtdPessoa == 0)
            {
                Toast.makeText(this,"A quantidade de pessoas não pode ser zero",Toast.LENGTH_LONG).show();

                return;
            }


            TotalTaxa = ((Couvert * QtdPessoa) + Consumo) * Taxa;
            String TotalTaxaF = new DecimalFormat("#,##0.00").format(TotalTaxa);
            txtTaxa.setText("R$:" + TotalTaxaF);

            SomaTotal = ((Couvert * QtdPessoa) + Consumo + TotalTaxa);
            String SomaTotalF = new DecimalFormat("#,##0.00").format(SomaTotal);
            txtConta.setText("R$:" + SomaTotalF);

            QtdPessoa = SomaTotal / QtdPessoa;
            String QtdPessoaF = new DecimalFormat("#,##0.00").format(QtdPessoa);
            txtVlr.setText("R$:" +  QtdPessoaF);
        }

    }

}
