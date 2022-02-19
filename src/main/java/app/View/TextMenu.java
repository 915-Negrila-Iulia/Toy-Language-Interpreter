package app.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {

    /*
        TextMenu object stores commands
     */

    private final Map<String, Command> commands;

    public TextMenu(){
        /*
            Constructor which creates a TextMenu object
            Creates a new HashMap object to store commands
         */
        commands = new HashMap<>();
    }

    public void addCommand(Command c){
        /*
            Adds a command to the TextMenu
            :param c: command to be added (Command type)
         */

        commands.put(c.getKey(),c);
    }

    private void printMenu(){
        /*
            Displays the TextMenu in the console
         */

        for(Command command: this.commands.values()){
            String line = String.format("%4s: %s",
                    command.getKey(),command.getDescription());
            System.out.println(line);
        }
    }

    public void show(){
        /*
            Print the menu and let the user input a command
            If command is valid, execute it
            Otherwise, print an error message
            :return: -
         */

        Scanner scanner = new Scanner(System.in);
        while(true){
            printMenu();

            System.out.println("Input the option: ");
            String key = scanner.nextLine();
            Command command = commands.get(key);

            if(command == null){
                System.out.println("Invalid Option");
                continue;
            }
            command.execute();
        }
    }

}
