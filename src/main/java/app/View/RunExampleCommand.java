package app.View;

import app.Controller.Controller;

public class RunExampleCommand extends Command{

    private final Controller controller;

    public RunExampleCommand(String key, String description, Controller controller){
        /*
            Parametrised constructor which creates a RunExampleCommand object
            :param key: identifies the command (string type)
            :param description: describes what command does (string type)
            :param controller: controller which will execute the command (Controller type)
         */
        super(key,description);
        this.controller = controller;
    }

    @Override
    public void execute(){
        /*
            Performs type checking
            Runs the program
         */
        try{
            controller.typecheck();
            controller.allSteps();
            //System.out.println(controller.getRepo().getCurrentProgram().getOriginalProgram().toString());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
