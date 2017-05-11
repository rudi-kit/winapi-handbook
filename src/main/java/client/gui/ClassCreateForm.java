package client.gui;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.WinApiUserElement;
import model.WinApiFunction;
import org.reactfx.EventSource;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;


public abstract class ClassCreateForm extends VBox {

    final WinApiHandbookReactor reactor;
    final EventSource<WinApiUserElement> winApiClass;
    private final VBox vBox;
    VBox functionForms;
    TextArea description;
    TextField name;
    long id;

    ClassCreateForm(WinApiHandbookReactor reactor) {
        vBox = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);
        scrollPane.setPadding(new Insets(5, 5, 5, 5));
//        scrollPane.setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
        scrollPane.setPrefSize(640, 480);
        scrollPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//        scrollPane.setFitToHeight(true);
        getChildren().add(scrollPane);
        this.reactor = reactor;
        winApiClass = new EventSource<>();
        setPadding(new Insets(5, 10, 10, 10));
        winApiClass
                .map(WinApiUserElement::getId)
                .subscribe(e -> id = e);
        reactor.getClassEventSource().feedTo(winApiClass);
        createNameBlock();
        createDescriptionBlock();
        createFunctionsFields();
        createSaveButton();
    }

    void createNameBlock() {
        Label label = new Label("Name");
        name = new TextField();
        winApiClass
                .map(WinApiUserElement::getName)
                .feedTo(name.textProperty());
        HBox hBox = new HBox(label, name);
        hBox.setSpacing(10);
        vBox.getChildren().add(hBox);
    }

    void createDescriptionBlock() {
        Label label = new Label("Description");
        description = new TextArea();
        description.setWrapText(true);
        winApiClass
                .map(WinApiUserElement::getDescription)
                .feedTo(description.textProperty());

        VBox vBox = new VBox(label, description);
        this.vBox.getChildren().add(vBox);
    }

    void createFunctionsFields() {
        functionForms = new VBox();
        Button addFunction = new Button("addFunction");
        addFunction.setOnAction(this::addNewFunction);

        VBox vBox = new VBox(addFunction, functionForms);
        winApiClass
                .map(WinApiUserElement::getFunctions)
                .map(f -> f.stream().map(this::functionCreateForm).collect(toList()))
                .hook(list -> {
                    for (int i = 0; i < list.size(); i++) {
                        int num = i;
                        list.get(i).removeAction(() -> removeFunction(num));
                    }
                })
                .subscribe(functionForms.getChildren()::setAll);
        this.vBox.getChildren().add(vBox);
    }

    void createSaveButton() {
        Button create = new Button("Save");
        create.setOnAction(this::submit);
        Button delete = new Button("Delete");
        delete.setOnAction(this::delete);
        Button back = new Button("Back");
        back.setOnAction(this::back);

        HBox hBox = new HBox(create, delete, back);
        getChildren().addAll(hBox);
    }

    void removeFunction(int number) {
        WinApiUserElement winApiUserElement = getWinApiClass();
        WinApiFunction function = winApiUserElement.getFunctions().get(number);
        reactor.removeFunction(function);
        pushClass(winApiUserElement);
    }

    WinApiUserElement getWinApiClass() {
        return new WinApiUserElement(
                id,
                name.getText(),
                description.getText(),
                getClassFunctions());
    }

    void pushClass(WinApiUserElement winApiUserElement) {
        this.winApiClass.push(winApiUserElement);
    }

    List<WinApiFunction> getClassFunctions() {
        return functionForms.getChildren()
                .stream()
                .map(FunctionCreateForm.class::cast)
                .map(FunctionCreateForm::getFunction)
                .collect(toList());
    }

    private void delete(ActionEvent actionEvent) {
        reactor.delete(getWinApiClass());
    }

    void back(ActionEvent actionEvent) {
        reactor.getFindEventSource().push(new ActionEvent());
    }

    void addNewFunction(ActionEvent e) {
        WinApiUserElement winApiUserElement = getWinApiClass();

//        winApiUserElement.getFunctions().add(
//                new WinApiFunction(0l, "", "",  new ArrayList<>()));
//        pushClass(winApiUserElement);
    }

    void submit(ActionEvent e) {
        reactor.save(getWinApiClass());
    }

    FunctionCreateForm functionCreateForm(WinApiFunction function) {
        FunctionCreateForm functionCreateForm = functionCreateForm();
        functionCreateForm.pushFunction(function);
        return functionCreateForm;
    }

    abstract FunctionCreateForm functionCreateForm();
}
