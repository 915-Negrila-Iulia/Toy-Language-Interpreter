package app.Repository;

import app.Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class Repository implements IRepository {

    /*
        Repository class implements abstract methods of IRepository interface
     */

    private List<ProgramState> programStates;
    private String logFilePath = "D:\\JAVAProjects\\InterpretorToyLanguage\\src\\programs.txt";

    public Repository(String logFilePath) {
        /*
            Parametrised constructor which creates a Repository object
            :param logFilePath: name of a logFile where program will be saved (String type)
         */

        programStates = new ArrayList<ProgramState>();
        this.logFilePath = logFilePath;
    }

    public Repository(ProgramState program, String logFilePath) {
        /*
            Parametrised constructor which creates a Repository object
            :param program: main program added to the list of programs (ProgramState type)
            :param logFilePath: name of a logFile where program will be saved (String type)
         */

        programStates = new ArrayList<ProgramState>();
        programStates.add(program);
        this.logFilePath = logFilePath;
    }

    @Override
    public String getLogFilePath() {
        return logFilePath;
    }

    @Override
    public void addProgram(ProgramState program){
        this.programStates.add(program);
    };

    @Override
    public List<ProgramState> getProgramList(){
        return this.programStates;
    };

    @Override
    public void setProgramList(List<ProgramState> newProgramList){
        this.programStates = newProgramList;
    };

    @Override
    public ProgramState getProgramState(int id) throws Exception{
        for(ProgramState program: this.programStates)
            if(program.getID() == id)
                return program;

        throw new Exception("program state with id: "+id+" not found");
    };

    @Override
    public ProgramState getMainProgram(){
        return this.programStates.get(0);
    };

    @Override
    public void logPrgStateExec(ProgramState givenProgram) throws Exception {
        // using try-with-resources
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.println(givenProgram.toString());
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }
}
