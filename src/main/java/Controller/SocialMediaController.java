package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Service.AccountService;
import Service.MessageService;

//import Application.DAO.AccountDAO
//import Model.Message;



import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountDAO accountDAO = new AccountDAO();
    MessageDAO messageDAO = new MessageDAO();
    AccountService accountService = new AccountService(accountDAO);
    MessageService messageService = new MessageService(messageDAO, accountDAO);
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    //create endpoint on register
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerUserHandler);
        app.post("/login", this::loginUserHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("accounts/{account_id}/messages", this::getAllMessagesFromUserHandler);

        return app;
    }

    /**
     * This is an registerUser handler for an registerUser endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registerUserHandler(Context context) throws JsonProcessingException {
        //create a new Account on enpoint POST localhost:8080/register 
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account newAccount = accountService.addAccount(account);
        if(newAccount == null) {
            context.status(400);
        } else {
            context.json(newAccount);
            context.status(200);
        }
    }

    //login User handler for login endpoint
    private void loginUserHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account gotAccount = accountService.getAccount(account);
        if (gotAccount != null){
            context.json(gotAccount);
            context.status(200);
        } else context.status(401);
    }

    //create message handler for creating messages endpoint
    private void createMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message newMessage = messageService.insertMessage(message);
        if(newMessage == null) {
            context.status(400);
        } else context.json(newMessage);
    }

    //get messages handler for getting all messages in database
    private void getMessagesHandler(Context context) throws JsonProcessingException {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
    }

    //get messages by id handler for getting messages by a message id
    private void getMessageByIDHandler(Context context) throws JsonProcessingException {
        int fetchID = Integer.parseInt(context.pathParam("message_id"));
        Message fetchMessage = messageService.getMessageByID(fetchID);
        if(fetchMessage != null) context.json(fetchMessage);
    }
    
    //delete message by id handler deletes a message by message id
    private void deleteMessageByIDHandler(Context context) throws JsonProcessingException {
        int fetchID = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageDAO.deleteMessageByID(fetchID);
        if(deletedMessage != null) context.json(deletedMessage);
    }

    //update message handler for updating an existing message
    private void updateMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message updatedMessage = mapper.readValue(context.body(), Message.class);
        int fetchID = Integer.parseInt(context.pathParam("message_id"));
        Message newUpdatedMessage = messageService.updateMessage(fetchID, updatedMessage.getMessage_text());
        if(newUpdatedMessage != null) {
            context.json(newUpdatedMessage);
        }
        else context.status(400);
    }

    //get all messages from user handler for getting all messages from one user
    private void getAllMessagesFromUserHandler(Context context) throws JsonProcessingException {
        int fetchID = Integer.parseInt(context.pathParam("account_id"));
        context.json(messageDAO.getAllMessagesFromUser(fetchID));
    }
}