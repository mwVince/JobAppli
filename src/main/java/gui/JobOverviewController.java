package gui;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.text.Text;

import java.util.Optional;

/**
 * Controller class for jobPane
 */
public class JobOverviewController {
    private TableView<JobNode> jobNodeTableView;
    private TableColumn<JobNode, String> companyColumn = new TableColumn("Company");
    private TableColumn<JobNode, String> titleColumn = new TableColumn("Title");

    private Label indexLabel = new Label();
    private Label titleLabel = new Label();
    private Label companyLabel = new Label();
    private Label linkLabel = new Label();
    private Label statusLabel = new Label();

    private Button editButton = new Button("Edit");
    private Button rejectButton = new Button("Reject");
    private Button deleteButton = new Button("Delete");
    private Button copyButton = new Button("Copy");

    // Allow filtering
    private FilteredList<JobNode> filteredJobNodeList;
    private TextField filterField = new TextField();

    private VBox tableVbox;

    private HBox buttonBox;
    private GridPane detailLayoutPane;

    private Alert deleteConfirm;

    private JobAppliApp mainApp;

    public JobOverviewController(JobAppliApp mainApp) {
        setMainApp(mainApp);
        initFilter();
        initJobNodeTableView();
        initTableVBox();
        initController();
        initButton();
        initDetailLayoutPane();

    }

    /**
     * reference to mainApp
     *
     * @param mainApp
     */
    private void setMainApp(JobAppliApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Initialize filter on jobNodeObservableList
     */
    private void initFilter() {
        filteredJobNodeList = new FilteredList<>(mainApp.getJobNodeObservableList());
        filterField.setStyle("-fx-background-color: #D3D3D3;");
        filterField.setPromptText("Search Company");
        filterField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                filterField.clear();
            }
        });
        filterField.textProperty().addListener(((observable, oldValue, newValue) -> {
            filteredJobNodeList.setPredicate(jobNode -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                } else if (jobNode.getCompany().toLowerCase().contains(newValue.toLowerCase())) {
                    return true;
                } else {
                    return false;
                }
            });
        }));
    }

    /**
     * Initializes jobNodeTableView
     */
    private void initJobNodeTableView() {
        jobNodeTableView = new TableView();
        jobNodeTableView.setItems(filteredJobNodeList);
        jobNodeTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        jobNodeTableView.getColumns().addAll(companyColumn, titleColumn);
    }

    private void initTableVBox() {
        tableVbox = new VBox(filterField, jobNodeTableView);
        tableVbox.setMaxWidth(300);
        tableVbox.setMinWidth(300);
        tableVbox.setPadding(new Insets(7, 0, 0, 0));
    }

    /**
     * Initializes JobOverviewController
     */
    private void initController() {
        companyColumn.setCellValueFactory(new PropertyValueFactory<JobNode, String>("company"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<JobNode, String>("role"));
        showJobDetail(null, -1);
        jobNodeTableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showJobDetail(newValue, jobNodeTableView.getSelectionModel().getSelectedIndex()))) ;
    }

    /**
     * Initializes detailLayoutPane
     */
    private void initDetailLayoutPane() {
        detailLayoutPane = new GridPane();

        // Column constraints
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        detailLayoutPane.getColumnConstraints().addAll(col1, col2);

        // Row constraints
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(15);
        row.setValignment(VPos.BOTTOM);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(7);
        row1.setValignment(VPos.BOTTOM);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(8);
        row2.setValignment(VPos.BOTTOM);
        RowConstraints rowLast = new RowConstraints();
        rowLast.setVgrow(Priority.ALWAYS);
        detailLayoutPane.getRowConstraints().addAll(row1, row2, row, row, row, rowLast);
        detailLayoutPane.setPadding(new Insets(10, 10, 10, 10));

        // Set text
        String textStyle = "-fx-font-family: Segoe UI Semibold; -fx-font-size: 20; -fx-fill: white; -fx-opacity: 1;";
        Text titleText = new Text("Title");
        Text companyText = new Text("Company");
        Text linkText = new Text("Link");
        Text statusText = new Text("Status");
        titleText.setStyle(textStyle);
        companyText.setStyle(textStyle);
        linkText.setStyle(textStyle);
        statusText.setStyle(textStyle);

        detailLayoutPane.add(titleText, 0, 1, 1, 1);
        detailLayoutPane.add(companyText, 0, 2, 1, 1);
        detailLayoutPane.add(linkText, 0, 3, 1, 1);
        detailLayoutPane.add(statusText, 0, 4, 1, 1);


        indexLabel.setStyle("-fx-font-size: 15;");
        String labelStyle = "-fx-font-family: Segoe UI Semibold; -fx-font-size: 30;";
        titleLabel.setStyle(labelStyle);
        companyLabel.setStyle(labelStyle);
        statusLabel.setStyle(labelStyle);

        detailLayoutPane.add(indexLabel, 0, 0, 1, 1);
        detailLayoutPane.add(titleLabel, 1, 1, 3, 1);
        detailLayoutPane.add(companyLabel, 1, 2, 3, 1);
        detailLayoutPane.add(linkLabel, 1, 3, 3, 1);
        detailLayoutPane.add(statusLabel, 1, 4, 3, 1);

        detailLayoutPane.add(buttonBox, 0, 5, 4, 1);

    }

    private void initButton() {
        // Initialize buttonBox
        editButton.setPrefSize(100, 25);
        rejectButton.setPrefSize(100, 25);
        deleteButton.setPrefSize(100, 25);
        copyButton.setPrefSize(100, 25);

        buttonBox = new HBox(editButton, rejectButton, deleteButton, copyButton);
        buttonBox.setSpacing(25);
        buttonBox.setPadding(new Insets(0, 0, 15, 0));
        buttonBox.setAlignment(Pos.BOTTOM_CENTER);

        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleEdit();
            }
        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleDelete();
            }
        });

        rejectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleReject();
            }
        });

        copyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleCopy();
            }
        });

    }

    /**
     * Initializes delete confirmation dialog
     */
    private boolean initDeleteConfirm(JobNode jobNode) {
        deleteConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        deleteConfirm.getDialogPane().getStylesheets().add("DarkTheme.css");
        deleteConfirm.setTitle("Delete Confirmation");
        deleteConfirm.setHeaderText(null);
        deleteConfirm.setGraphic(null);
        deleteConfirm.setContentText("Confirm to delete " + jobNode.getRole() + ": " + jobNode.getCompany() + "?");
        Optional<ButtonType> result = deleteConfirm.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Displays details of the selected job from the list
     *
     * @param jobNode
     */
    public void showJobDetail(JobNode jobNode, int index) {
        if(jobNode != null) {
            indexLabel.setText(Integer.toString(index + 1));
            titleLabel.setText(jobNode.getRole());
            companyLabel.setText(jobNode.getCompany());
            linkLabel.setText(jobNode.getLink());
            statusLabel.setText(jobNode.getStatus());
        }
        else {
            titleLabel.setText(null);
            companyLabel.setText(null);
            linkLabel.setText(null);
            statusLabel.setText(null);
        }
    }


    /**
     * Handles edit button
     */
    private void handleEdit() {
        JobNode selectedJobNode = jobNodeTableView.getSelectionModel().getSelectedItem();
        if(selectedJobNode != null) {
            boolean isOkClicked = mainApp.showJobEditor(selectedJobNode, false);
            if(isOkClicked) {
                showJobDetail(selectedJobNode, jobNodeTableView.getSelectionModel().getSelectedIndex());
                mainApp.getTopBarController().autoSave();
            }
        }
    }

    /**
     * Deletes selected jobNode from the list
     */
    private void handleDelete() {
        JobNode selectedJobNode = jobNodeTableView.getSelectionModel().getSelectedItem();
        if(selectedJobNode != null) {
            if(initDeleteConfirm(selectedJobNode)) {
                mainApp.getJobNodeObservableList().remove(jobNodeTableView.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Sets status to "Rejected" for selected jobNode
     */
    private void handleReject() {
        JobNode selectedJobNode = jobNodeTableView.getSelectionModel().getSelectedItem();
        if(selectedJobNode != null) {
            selectedJobNode.setStatus("Rejected");
            showJobDetail(selectedJobNode, jobNodeTableView.getSelectionModel().getSelectedIndex());
            mainApp.getTopBarController().autoSave();
        }
    }

    /**
     * Sends link of selected jobNode to system copy board
     */
    private void handleCopy() {
        JobNode selectedJobNode = jobNodeTableView.getSelectionModel().getSelectedItem();
        if(selectedJobNode != null) {
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(selectedJobNode.getLink());
            clipboard.setContent(clipboardContent);
        }
    }

    /**
     * @return jobNodeTableView
     */
    public TableView<JobNode> getJobNodeTableView() {
        return jobNodeTableView;
    }

    /**
     * @return detailLayoutPane
     */
    public GridPane getDetailLayoutPane() {
        return detailLayoutPane;
    }

    /**
     * @return tableVBox
     */
    public VBox getTableVbox() {
        return tableVbox;
    }
}
