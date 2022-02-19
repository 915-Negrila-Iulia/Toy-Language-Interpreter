package master.toylanguagegui;

import app.Controller.Controller;
import app.Model.ADT.IDictionary;
import app.Model.ADT.IList;
import app.Model.ADT.MyDictionary;
import app.Model.ADT.Pair;
import app.Model.ProgramState;
import app.Model.Statement.IStatement;
import app.Model.ToyValue.StringValue;
import app.Model.ToyValue.Value;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.net.URL;
import java.util.*;

public class PrgRunController implements Initializable {

    Controller controller;
    @FXML
    TextField textNrPrograms;
    @FXML
    Button oneStepButton;
    @FXML
    ListView<String> exeStackListView;
    @FXML
    ListView<String> fileTableListView;
    @FXML
    ListView<String> outListView;
    @FXML
    ListView<String> programStateIDList;
    @FXML
    TableView<Map.Entry<String, Value>> symTableView;
    @FXML
    TableColumn<Map.Entry<String, Value>, String> symTableVariableColumn;
    @FXML
    TableColumn<Map.Entry<String, Value>, String> symTableValueColumn;
    @FXML
    TableView<Map.Entry<Integer, Value>> heapTableView;
    @FXML
    TableColumn<Map.Entry<Integer, Value>, String> heapTableAddressColumn;
    @FXML
    TableColumn<Map.Entry<Integer, Value>, String> heapTableValueColumn;
    @FXML
    TableView<Map.Entry<Integer, Pair<Integer,ArrayList<Integer>>>> barrierTableView;
    @FXML
    TableColumn<Map.Entry<Integer, Pair<Integer,ArrayList<Integer>>>,String> barrierTableIndexColumn;
    @FXML
    TableColumn<Map.Entry<Integer, Pair<Integer,ArrayList<Integer>>>,String> barrierTableValueColumn;
    @FXML
    TableColumn<Map.Entry<Integer, Pair<Integer,ArrayList<Integer>>>,String> barrierTableListOfValuesColumn;
    @FXML
    TableView<Map.Entry<Integer, Integer>> latchTableView;
    @FXML
    TableColumn<Map.Entry<Integer, Integer>, String> latchTableLocationColumn;
    @FXML
    TableColumn<Map.Entry<Integer, Integer>, String> latchTableValueColumn;
    @FXML
    TableView<Map.Entry<Integer, Integer>> lockTableView;
    @FXML
    TableColumn<Map.Entry<Integer, Integer>, String> lockTableLocationColumn;
    @FXML
    TableColumn<Map.Entry<Integer, Integer>, String> lockTableValueColumn;
    @FXML
    TableView<Map.Entry<Integer, Pair<Integer,ArrayList<Integer>>>> semaphoreTableView;
    @FXML
    TableColumn<Map.Entry<Integer, Pair<Integer,ArrayList<Integer>>>,String> semaphoreTableIndexColumn;
    @FXML
    TableColumn<Map.Entry<Integer, Pair<Integer,ArrayList<Integer>>>,String> semaphoreTableValueColumn;
    @FXML
    TableColumn<Map.Entry<Integer, Pair<Integer,ArrayList<Integer>>>,String> semaphoreTableListOfValuesColumn;
    @FXML
    Label stackLabel;
    @FXML
    Label fileTableLabel;
    @FXML
    Label symTableLabel;
    @FXML
    Label outLabel;
    @FXML
    Label heapLabel;
    @FXML
    Label barrierTableLabel;
    @FXML
    Label latchLabel;
    @FXML
    Label lockTableLabel;
    @FXML
    Label semaphoreTableLabel;
    @FXML
    Label programIDLabel;

    public PrgRunController (Controller controller){

        this.controller = controller;
    }

    public Controller getController() {
        return controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialRun();
        programStateIDList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setSymTableViewAndExeStack();
            }
        });
        oneStepButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    controller.initialize_executor();
                    controller.oneStepForAllGUI();
                    updateUIComponents();
                } catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("program finished");
                    alert.setHeaderText(null);
                    alert.setContentText(e.getMessage());
                    Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                    confirm.setDefaultButton(false);
                    confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                    alert.showAndWait();
                    Stage stage = (Stage) heapTableView.getScene().getWindow();
                    stage.close();
                }
            }
        });
    }

    public void initialRun(){
        setTextNrPrograms();
        setHeapTableView();
        setOutListView();
        setFileTableListView();
        setBarrierTableView();
        setLatchTableView();
        setLockTableView();
        setSemaphoreTableView();
        setProgramStateIDList();
        programStateIDList.getSelectionModel().selectFirst();
        setSymTableViewAndExeStack();
    }

    public void updateUIComponents(){
        setTextNrPrograms();
        setHeapTableView();
        setOutListView();
        setFileTableListView();
        setBarrierTableView();
        setLatchTableView();
        setLockTableView();
        setSemaphoreTableView();
        setProgramStateIDList();
        if(programStateIDList.getSelectionModel().getSelectedItem() == null){
            programStateIDList.getSelectionModel().selectFirst();
        }
        setSymTableViewAndExeStack();
    }

    public void setTextNrPrograms(){
        textNrPrograms.setText("Number of program states: "+controller.getRepo().getProgramList().size());
    }

    public void setHeapTableView(){
        heapTableView.refresh();
        ObservableList<Map.Entry<Integer,Value>> heapTableList = FXCollections.observableArrayList();
        heapTableAddressColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey().toString()));
        heapTableValueColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        heapTableList.addAll(controller.getRepo().getProgramList().get(0).getHeap().getContent().entrySet());
        heapTableView.setItems(heapTableList);
    }

    public void setOutListView() {
        outListView.refresh();
        ObservableList<String> outObservableList = FXCollections.observableArrayList();
        IList<Value> outputList = controller.getRepo().getProgramList().get(0).getOutputList();
        for (int i = 0; i < outputList.size(); i++) {
            outObservableList.add(outputList.get(i).toString());
        }
        outListView.setItems(outObservableList);
    }

    public void setFileTableListView(){
        fileTableListView.refresh();
        ObservableList<String> fileObservableList = FXCollections.observableArrayList();
        IDictionary<StringValue, BufferedReader> table = controller.getRepo().getProgramList().get(0).getFileTable();
        for(StringValue key: table.keySet()) {
            fileObservableList.add(key.toString());
        }
        fileTableListView.setItems(fileObservableList);
    }

    public void setProgramStateIDList(){
        ObservableList<String> programStateIDObservableList = FXCollections.observableArrayList();
        for(ProgramState programState: controller.getRepo().getProgramList()){
            programStateIDObservableList.add(Integer.toString(programState.getID()));
        }
        programStateIDList.setItems(programStateIDObservableList);
    }

    public void setBarrierTableView(){
        barrierTableView.refresh();
        ObservableList<Map.Entry<Integer, Pair<Integer,ArrayList<Integer>>>> barrierTableObservableList = FXCollections.observableArrayList();
        barrierTableIndexColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey().toString()));
        barrierTableValueColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getFirst().toString()));
        barrierTableListOfValuesColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getSecond().toString()));
        barrierTableObservableList.addAll(controller.getRepo().getProgramList().get(0).getCyclicBarrier().getContent().entrySet());
        barrierTableView.setItems(barrierTableObservableList);
    }

    public void setLatchTableView(){
        latchTableView.refresh();
        ObservableList<Map.Entry<Integer,Integer>> latchTableList = FXCollections.observableArrayList();
        latchTableLocationColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey().toString()));
        latchTableValueColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        latchTableList.addAll(controller.getRepo().getProgramList().get(0).getLatchTable().getContent().entrySet());
        latchTableView.setItems(latchTableList);
    }

    public void setLockTableView(){
        lockTableView.refresh();
        ObservableList<Map.Entry<Integer,Integer>> lockTableObservableList = FXCollections.observableArrayList();
        lockTableLocationColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey().toString()));
        lockTableValueColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        lockTableObservableList.addAll(controller.getRepo().getProgramList().get(0).getLockTable().getContent().entrySet());
        lockTableView.setItems(lockTableObservableList);
    }

    public void setSemaphoreTableView(){
        semaphoreTableView.refresh();
        ObservableList<Map.Entry<Integer, Pair<Integer,ArrayList<Integer>>>> semaphoreTableObservableList = FXCollections.observableArrayList();
        semaphoreTableIndexColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey().toString()));
        semaphoreTableValueColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getFirst().toString()));
        semaphoreTableListOfValuesColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getSecond().toString()));
        semaphoreTableObservableList.addAll(controller.getRepo().getProgramList().get(0).getSemaphoreTable().getContent().entrySet());
        semaphoreTableView.setItems(semaphoreTableObservableList);
    };

    public void setSymTableViewAndExeStack(){
        symTableView.refresh();
        exeStackListView.refresh();
        ObservableList<Map.Entry<String,Value>> symTableObservableList = FXCollections.observableArrayList();
        ObservableList<String> exeStackObservableList = FXCollections.observableArrayList();
        symTableVariableColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey()));
        symTableValueColumn.setCellValueFactory(
                cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        List<ProgramState> allPrograms = controller.getRepo().getProgramList();
        ProgramState programResult = null;
        for(ProgramState programState: allPrograms){
            if(programState.getID() == Integer.parseInt(programStateIDList.getSelectionModel().getSelectedItem())){
                programResult = programState;
                break;
            }
        }
        if(programResult!=null){
            symTableObservableList.addAll(programResult.getSymbolTable().getContent().entrySet());
            Iterator<IStatement> iterator = programResult.getStack().iterator();
            while(iterator.hasNext()){

                IStatement statement = iterator.next();
                exeStackObservableList.add(statement.toString());
            }
            symTableView.setItems(symTableObservableList);
            exeStackListView.setItems(exeStackObservableList);
        }
    }
}
