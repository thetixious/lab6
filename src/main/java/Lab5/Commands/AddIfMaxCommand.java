package Lab5.Commands;

import Lab5.Data.SpaceMarine;
import Lab5.Exeptions.*;
import Lab5.Utility.ArgumentLoader;
import Lab5.Utility.CollectionManager;
import Lab5.Utility.IOManager;
import Lab5.Utility.SpaceMarineArgumentLoader;

/**
 * "add_if_max" command, add item in collection if item bigger than max element in collection
 */
public class AddIfMaxCommand extends Command {
    CollectionManager collectionManager;
    IOManager ioManager;

    public AddIfMaxCommand(CollectionManager collectionManager, IOManager ioManager) {
        this.collectionManager = collectionManager;
        this.ioManager = ioManager;
    }

    /**
     * execute command
     * @param arguments
     * @throws IncorrectData
     * @throws EmptyElement
     */
    @Override
    public void run(ArgumentLoader arguments) throws IncorrectData, EmptyElement {
        try {
            arguments.validateCount(0);
            SpaceMarine candidate = ((SpaceMarineArgumentLoader) arguments).loadSpaceMarin();

            if (collectionManager.AllowAddIfMax(candidate))
                ioManager.println("Объект успешно добавлен в коллекцию ");
            else
                ioManager.println("Объект не больше максимального элемента в коллекции");
        } catch (IllegalArgumentException e) {
            ioManager.println("Неверное количество аргументов");
        }


    }

    @Override
    public String getDescription() {
        return "Добавляет объект в коллекцию если он больше максимального";
    }

    @Override
    public String getName() {
        return "add_if_max";
    }
}
