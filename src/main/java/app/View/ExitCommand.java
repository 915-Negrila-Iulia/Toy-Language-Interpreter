package app.View;

public class ExitCommand extends Command{

    public ExitCommand(String key, String description){
        /*
            Parametrised constructor which creates a ExitCommand object
            :param key: identifies the command (string type)
            :param description: describes what command does (string type)
         */
        super(key,description);
    }

    @Override
    public void execute(){
        /*
            Exits application
         */
        System.exit(0);
    }
}
