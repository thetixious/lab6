import commands.Command;
import commands.CommandResult;
import data.SpaceMarine;
import exeptions.EmptyElement;
import exeptions.IncorrectData;
import utility.CollectionManager;
import utility.CommandPool;
import utility.Message;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ServerManager {
    CollectionManager collectionManager;
    CommandPool commandPool;
    Integer port;
    InetAddress addr;
    Boolean serverState = true;
    SendManager sendManager;
    ReceiveManager receiveManager;
    Message message;
    public ServerManager(CommandPool commandPool, InetAddress addr,Integer port) throws SocketException {
        this.commandPool = commandPool;
        this.port = port;
        this.addr = addr;
    }

    public void run() throws EmptyElement, IncorrectData {

        try {
            DatagramSocket server = new DatagramSocket(port);
            collectionManager = new CollectionManager(commandPool);
            sendManager = new SendManager(server);
            receiveManager = new ReceiveManager(server);
            CommandResult result;
            while (serverState){
                message = receiveManager.receiveMessage();
                if (message.equals(null)){
                    continue;
                }
                result = execute(message);
                System.out.println(receiveManager.getPort());
                System.out.println(receiveManager.getAddr());
                sendManager.sendMessage(result,receiveManager.getPort(),receiveManager.getAddr());
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("sosi huy bidlo");
            e.printStackTrace();
        }




    }
    public CommandResult execute(Message mes) throws EmptyElement, IncorrectData {
        Command curCommand = mes.getCommand();
        Object data = mes.getData();
        SpaceMarine item = mes.getSpaceMarine();
        return curCommand.run(collectionManager,data,item);
    }
}
