package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TopBarController {
    private Button addButton = new Button("Add Job (a)");
    private Button newButton = new Button("New");
    private Button openButton = new Button("Open");
    private Button saveButton = new Button("Save");
    private Button saveAsButton = new Button("Save As");
    private Button exportButton = new Button("Export");

    private HBox topBar;
    private JobAppliApp mainApp;

    /**
     * Constructor
     */
    public TopBarController(JobAppliApp mainApp) {
        this.mainApp = mainApp;
        initTopBar();
        setButtons();
    }

    /**
     * Initializes topBar
     */
    private void initTopBar() {
        topBar = new HBox(addButton, newButton, openButton, saveButton, saveAsButton, exportButton);
        topBar.setSpacing(5);

    }

    private void setButtons() {
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleAdd();
            }
        });

        newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleNew();
            }
        });

        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleOpen();
            }
        });

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleSave();
            }
        });

        saveAsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleSaveAs();
            }
        });

        exportButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleExport();
            }
        });

        // Keyboard listener
        mainApp.getPrimaryStage().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.A) {
                    handleAdd();
                }
            }
        });
    }

    /**
     * Handles add button
     */
    private void handleAdd() {
        JobNode jobNode = new JobNode();
        boolean okClicked = mainApp.showJobEditor(jobNode, true);
        if(okClicked) {
            mainApp.getJobNodeObservableList().add(jobNode);
            handleSave();
        }
    }

    /**
     * Handles New button
     */
    private void handleNew() {
        mainApp.getJobNodeObservableList().clear();
        mainApp.setJobFilePath(null);
    }

    /**
     * Opens a FileChooser and loads data from .ser file
     */
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML/txt files (.xml/.txt)", "*.xml", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
        if(file != null) {
            if (file.getName().substring(file.getName().length() - 3).equals("xml")) {
                mainApp.loadJobDataFromFile(file);
            }
            else {
                mainApp.loadJobDataFromExport(file);
            }
        }
    }

    /**
     * Saves Job data to the file that is currently open
     */
    private void handleSave() {
        File jobFile = mainApp.getJobFilePath();
        if(jobFile != null) {
            mainApp.saveJobDataToFile(jobFile);
        }
        else {
            handleSaveAs();
        }
    }

    /**
     * Saves Job data to a new file
     */
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files (.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setInitialFileName("job_db");

        File jobFile = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
        if(jobFile != null) {
            if (!jobFile.getPath().endsWith(".xml")) {
                jobFile = new File(jobFile.getPath() + ".xml");
            }
            mainApp.saveJobDataToFile(jobFile);
        }
    }

    /**
     * Exports current Job data to .txt file
     */
    private void handleExport() {
        if(mainApp.getJobNodeObservableList().size() == 0) {
            return;
        }

        try {
            FileChooser fileChooser = new FileChooser();
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddyyyy");

            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("txt files (.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extensionFilter);
            fileChooser.setInitialFileName("Job_Applied_" + simpleDateFormat.format(date));

            File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
            PrintWriter printWriter = new PrintWriter(file);

            int counter = 0;
            for(JobNode jobNode: mainApp.getJobNodeObservableList()) {
                printWriter.println(++counter + ".");
                printWriter.println(jobNode.toString());
                printWriter.println();
            }

            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called by other classes to save automatically
     */
    public void autoSave() {
        handleSave();
    }

    /**
     * @return topBar
     */
    public HBox getTopBar() {
        return topBar;
    }
}
