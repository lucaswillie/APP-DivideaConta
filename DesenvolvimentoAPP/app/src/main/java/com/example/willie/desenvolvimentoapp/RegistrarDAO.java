package com.example.willie.desenvolvimentoapp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegistrarDAO {

    ConexaoDB connect = new ConexaoDB();
    Connection con = connect.connectionDB();

    public void inserirUsuarios(String nome, String email, int senha){



        String query =  "INSERT INTO TBUSUARIOS(NOME, EMAIL, SENHA) "
                +"VALUES('"+ nome +"','"+ email +"','"+ senha +"')";


        try{
            Statement statement = con.createStatement();
            statement.executeUpdate(query);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
            //Log.d("SQL Erro", e.getMessage());
            //return;
        }

    }

    public String selecionarUsuarios(String email)
    {

        String query =  " select * from TBUSUARIOS where EMAIL = '" + email + "'";

        try{
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if(rs.next())
            {

                return rs.getString("EMAIL");
            }
            else
            {
                return null;
            }


        }
        catch (SQLException e){
            //throw new RuntimeException(e);
            //Log.d("SQL Erro", e.getMessage());
            //return null ;
            return null;
        }


    }

    public Boolean ValidarLogin(String email , int senha)
    {
        String query =  " select * from TBUSUARIOS where EMAIL = '" + email + "' and SENHA = '" + senha + "'";

        try{
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if(rs.next())
            {
                String Email = rs.getString("EMAIL" );
                String Senha  = rs.getString("SENHA");

                if (Email == null || Senha == null )
                {
                    return false;
                }
                else
                {
                    return true;
                }


            }
            else
            {
                return false;
            }


        }
        catch (SQLException e){
            //throw new RuntimeException(e);
            //Log.d("SQL Erro", e.getMessage());
            //return null ;
            return false;
        }
    }


}
