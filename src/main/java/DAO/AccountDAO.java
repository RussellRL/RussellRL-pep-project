package DAO;

import Util.ConnectionUtil;
import Model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    public AccountDAO () {}
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
    public Account getAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE username = ?, password = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) {
                return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));  
            }

        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    

    //insertion into table
    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Account (username, password) VALUES (?,?)";
            //generated keys to create ids
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();
            
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while(rs.next()) {
                int generated_account_id = (int) rs.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

} 
