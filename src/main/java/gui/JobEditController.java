package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class JobEditController {
    final private TextField titleField = new TextField();
    final private TextField companyField = new TextField();
    final private TextField linkField = new TextField();
    final private TextField statusField = new TextField();

    final private Label titleLabel = new Label("Title");
    final private Label companyLabel = new Label("Company");
    final private Label linkLabel = new Label("Link");
    final private Label statusLabel = new Label("Status");

    private HBox buttonBox = new HBox();
    final private Button okButton = new Button("OK");
    final private Button cancelButton = new Button("Cancel");

    private Scene jobEditorScene;
    private GridPane jobEditorPane;
    public Stage jobEditorStage;
    private JobNode jobNode;
    private boolean okClicked = false;
    private Text errorMessage;

    /**
     * Constructor
     */
    public JobEditController(boolean isNewJob) {
        initJobEditorStage(isNewJob);
        initEditorPane();
        initEditorScene();
        initButtons();
    }

    /**
     * Sets jobNode
     * @param jobNode
     */
    public void setJobNode(JobNode jobNode) {
        this.jobNode = jobNode;

        titleField.setText(jobNode.getRole());
        companyField.setText(jobNode.getCompany());
        linkField.setText(jobNode.getLink());
        statusField.setText(jobNode.getStatus());
    }

    /**
     * Initializes jobEditorStage
     */
    private void initJobEditorStage(boolean isNewJob) {
        jobEditorStage = new Stage();
        if(isNewJob) {
            jobEditorStage.setTitle("Adding");
        }
        else {
            jobEditorStage.setTitle("Editing");
        }
        jobEditorStage.setResizable(false);
    }

    /**
     * Initializes fields
     */
    private void initEditorPane() {
        jobEditorPane = new GridPane();
        jobEditorPane.setPadding(new Insets(20, 10, 10, 10));
        jobEditorPane.setPrefSize(600, 250);
        jobEditorPane.setVgap(10);
        jobEditorPane.getStylesheets().add("DarkTheme.css");

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        jobEditorPane.getColumnConstraints().addAll(col1, col2);

        jobEditorPane.add(titleLabel, 0, 0);
        jobEditorPane.add(companyLabel, 0, 1);
        jobEditorPane.add(linkLabel, 0, 2);
        jobEditorPane.add(statusLabel, 0, 3);
        jobEditorPane.add(titleField, 1, 0);
        jobEditorPane.add(companyField, 1, 1);
        jobEditorPane.add(linkField,1, 2);
        jobEditorPane.add(statusField, 1, 3);

        errorMessage = new Text();
        errorMessage.setStyle("-fx-font-family: Segoe UI Semibold; -fx-font-size: 20; -fx-fill: red");

        jobEditorPane.add(errorMessage, 0, 4, 2, 1);
        jobEditorPane.add(buttonBox, 0, 5, 2, 1);
    }

    /**
     * Initializes editorScene
     */
    private void initEditorScene() {
        jobEditorScene = new Scene(jobEditorPane);
        jobEditorStage.setScene(jobEditorScene);
    }

    /**
     * Initializes buttonBox and set button actoins
     */
    private void initButtons() {
        buttonBox.getChildren().addAll(okButton, cancelButton);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.setSpacing(30);

        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleOK();
            }
        });
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleCancel();
            }
        });

        jobEditorScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER) {
                    handleOK();
                }
            }
        });
    }

    /**
     * Sets properties for jobNode and close the editorStage
     */
    private void handleOK() {
        if(isInputValid()) {
            jobNode.setRole(titleField.getText());
            jobNode.setCompany(companyField.getText());
            jobNode.setLink(linkField.getText());
            jobNode.setStatus(statusField.getText());

            okClicked = true;
            jobEditorStage.close();
        }
    }

    /**
     * Closes the editorStage
     */
    private void handleCancel() {
        jobEditorStage.close();
    }

    /**
     * @return if the input is valid
     */
    private boolean isInputValid() {
        if(titleField.getText() == null || titleField.getText().length() == 0 ||
                companyField.getText() == null || companyField.getText().length() == 0 ||
                linkField.getText() == null || linkField.getText().length() == 0) {
            errorMessage.setText("Invalid Input, Please Verify");
            return false;
        }
        else {
            errorMessage.setText("");
            return true;
        }
    }

    /**
     * @return is ok clicked
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * @return jobEditorStage
     */
    public Stage getJobEditorStage() {
        return jobEditorStage;
    }
}
