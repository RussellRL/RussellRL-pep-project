package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    //get all accounts
    public List<Account> getAllAccounts() {
        Connection connection = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Account";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }

        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    //insertion into table
    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Account (username, password) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            return account;
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


     public boolean checkForAccount (Account account) {
        Connection connection = ConnectionUtil.getConnection();
        List<String> usernames = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Account ";
            PreparedStatement pS = connection.prepareStatement(sql);

            ResultSet rs = pS.executeQuery();
            while(rs.next()) {
                String name = rs.getString("username");
                usernames.add(name);
            }
            
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        for(String s : usernames) {
            if(s == account.getUsername()) return true;
        }
        return false;   
    }

} 
