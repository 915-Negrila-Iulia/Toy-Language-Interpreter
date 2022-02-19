package app.Repository;

import app.Model.ProgramState;

import java.util.List;

public interface IRepository {

    /*
        Interface 'IRepository'
        IRepository object stores the Programs used in the interpreter

        Abstract methods:
        - addProgram -> adds a new program to the repository to be run
        - getProgramList -> returns the list of programs
        - setProgramList -> replaces the existing list of programs from repository with a given list
        - getProgramState -> returns the program with a given id
        - getMainProgram -> returns the main program (first one from the list of programs)
        - logPrgStateExec -> saves the program into a logFile
        - getLogFilePath -> returns the path of the logFile
     */

    void addProgram(ProgramState program);
    List<ProgramState> getProgramList();
    void setProgramList(List<ProgramState> newProgramList);
    ProgramState getProgramState(int id)throws Exception;
    ProgramState getMainProgram();
    void logPrgStateExec(ProgramState program) throws Exception;
    String getLogFilePath();
}
