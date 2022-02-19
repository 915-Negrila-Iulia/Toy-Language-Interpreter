package app.View;

public abstract class Command {

    /*
        Abstract class 'Command'
        Command objects describe the commands available in the text menu

        Abstract methods:
        - execute -> executes the command
     */

    private final String key;
    private final String description;

    public Command(String key, String description){
        /*
            Parametrised constructor which creates a Command object
            :param key: identifies the command (string type)
            :param description: describes what command does (string type)
         */
        this.key = key;
        this.description = description;
    }

    public String getKey(){
        return this.key;
    };
    public String getDescription(){
        return this.description;
    };
    public abstract void execute();
}
