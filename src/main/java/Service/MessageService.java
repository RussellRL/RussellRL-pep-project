package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;


public class MessageService {
    public MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message insertMessage(Message message) {
        if(message.getMessage_text().length() == 0 || message.getMessage_text().length() > 254) {
            return null;
        } else return messageDAO.insertMessage(message);
        
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
}
