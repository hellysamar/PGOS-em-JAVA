/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

    - nessa classe é introduzido Driver             | Caominho          | Nome                          | Autenticações do banco a ser utilizado
    -                            caminho do Drive   | Caminho do banco  | Usuario que acessar o banco   | senha para acesso ao banco
 */
package br.com.pgos.dal;

import java.sql.*;

/**
 *
 * @author Hellysamar
 */
public class ModuloConexao {
    // Método responsavel por estabelecer a conexão com o banco
    public static Connection conector() {
        java.sql.Connection conexao = null;
        
        // Chamada do Driver importado em Bibliotecas
        String driver = "com.mysql.cj.jdbc.Driver";
        // Armazenando informações referente ao banco
        String url = "jdbc:mysql://localhost:3306/dbpgos?characterEncoding=utf-8";
        String user = "dba";
        String password = "@pto1001";
        
        // Estabelecendo conexão com o Banco de dados
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            //a linha abaixo serve de apoio para o Dev
//            System.out.println(e);
            return null;
        }
    }
}
