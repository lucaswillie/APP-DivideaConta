package com.example.willie.desenvolvimentoapp;

public class ModelRegistrar {

    private String Nome;
    private String Email;
    private int Senha;


    public ModelRegistrar(String Nome, String Email, int Senha) {
        this.Nome = Nome;
        this.Email = Email;
        this.Senha = Senha;
    }



    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getSenha() {
        return Senha;
    }

    public void setSenha(int senha) {
        Senha = senha;
    }
}
