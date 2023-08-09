package Service;

import DAO.AccountDAO;
import Model.Account;

//import java.util.List;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        if(account.getUsername().length() == 0 || (account.getPassword()).length() < 4 || accountDAO.getAccount(account) != null ) {
            return null;
        }
        return accountDAO.insertAccount(account);
    }

    public boolean checkForAccount(Account account) {
        return accountDAO.checkForAccount(account);
    }
}