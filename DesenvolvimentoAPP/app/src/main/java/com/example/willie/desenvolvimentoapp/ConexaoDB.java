package com.example.willie.desenvolvimentoapp;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    @SuppressLint("NewAPI")
    public Connection connectionDB(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://azuredivideaconta.database.windows.net:1433;DatabaseName=DivideaConta;user=divideaconta@azuredivideaconta;password=Brasil#6;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            connection = DriverManager.getConnection(ConnectionURL);
        }catch (SQLException se){
            Log.e("Erro de conexão!", se.getMessage());
        }catch (ClassNotFoundException e){
            Log.e("Classe não encontrada!", e.getMessage());
        }catch (Exception e){
            Log.e("Exceção!", e.getMessage());
        }
        return connection;
    }

}
