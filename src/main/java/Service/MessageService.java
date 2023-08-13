package Service;

import java.util.List;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Account;
import Model.Message;


public class MessageService {
    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO, AccountDAO accountDAO) {
        this.messageDAO = messageDAO;
        this.accountDAO = accountDAO;
    }

    public Message insertMessage(Message message) {
        if(message.getMessage_text().length() == 0 || message.getMessage_text().length() > 254
            || message.getPosted_by() != accountDAO.getAccountID(message.getPosted_by())) {
            return null;
        } else return messageDAO.insertMessage(message);
        
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageByID(int id) {
        return messageDAO.getMessageByID(id);
    }

    public Message deleteMessageByID(int id) {
        return messageDAO.deleteMessageByID(id);
    }

    public Message updateMessage(int id, String text) {
        if(text.length() == 0 || text.length() > 255) return messageDAO.updateMessssage(id, text);
        else return null;
    }
}
