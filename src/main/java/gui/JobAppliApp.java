package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.prefs.Preferences;

/**
 * MainApp to run JobAppli GUI
 *
 * @author Vincent Chang
 */
public class JobAppliApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<JobNode> jobNodeObservableList = FXCollections.observableArrayList();
    private FilteredList<JobNode> filteredList = new FilteredList<>(jobNodeObservableList, p -> true);
    private TopBarController topBarController;


    /**
     * Constructor
     */
    public JobAppliApp() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initialize(primaryStage);
        showJobOverview();
        showTopBar();
    }

    /**
     * Initializes rootLayout and primaryStage
     */
    public void initialize(Stage primaryStage) {
        rootLayout = new BorderPane();
        rootLayout.setPrefSize(900, 500);
        rootLayout.getStylesheets().add("DarkTheme.css");

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("JobAppli");
        this.primaryStage.setResizable(false);
        this.primaryStage.setScene(new Scene(rootLayout));
        this.primaryStage.show();
    }

    /**
     * Creates AnchorPane for job overview
     */
    public void showJobOverview() {
        SplitPane jobPane = new SplitPane();
        rootLayout.setCenter(jobPane);
        JobOverviewController controller = new JobOverviewController(this);
        jobPane.getItems().addAll(controller.getTableVbox(), controller.getDetailLayoutPane());
    }

    /**
     * @return jobNodeObservableList
     */
    public ObservableList<JobNode> getJobNodeObservableList() {
        return this.jobNodeObservableList;
    }

    /**
     * Prompts jobEditor and add/edit a jobNode
     *
     * @param jobNode target jobNode
     * @return is ok clicked
     */
    public boolean showJobEditor(JobNode jobNode, boolean isNewJob) {
        JobEditController controller = new JobEditController(isNewJob);
        Stage editorStage = controller.getJobEditorStage();
        controller.setJobNode(jobNode);

        editorStage.showAndWait();
        return controller.isOkClicked();
    }

    /**
     * Displays topBar
     */
    public void showTopBar() {
        topBarController = new TopBarController(this);
        rootLayout.setTop(topBarController.getTopBar());
    }

    /**
     * @return primaryStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Returns the Job FilePath preference
     *
     * @return
     */
    public File getJobFilePath() {
        Preferences preferences = Preferences.userNodeForPackage(JobAppliApp.class);
        String filePath = preferences.get("filePath", null);
        if(filePath != null) {
            return new File(filePath);
        }
        else {
            return null;
        }
    }

    /**
     * Set Job FilePath preference
     *
     * @param file
     */
    public void setJobFilePath(File file) {
        Preferences preferences = Preferences.userNodeForPackage(JobAppliApp.class);
        if(file != null) {
            String filePath = file.getPath().replace("txt", "xml");
            preferences.put("filePath", filePath);
            primaryStage.setTitle("JobAppli - " + file.getName());
        }
        else {
            preferences.remove("filePath");
            primaryStage.setTitle("JobAppli");
        }
    }

    /**
     * Loads Job data from saved .xml file
     *
     * @param file
     */
    public void loadJobDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(JobListWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            JobListWrapper wrapper = (JobListWrapper) unmarshaller.unmarshal(file);

            jobNodeObservableList.clear();
            jobNodeObservableList.addAll(wrapper.getJobs());
            setJobFilePath(file);
        }
        catch(javax.xml.bind.JAXBException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Can not load data from " + file.getName());
        }
    }

    /**
     * Saves Job data to .xml file
     * @param file
     */
    public void saveJobDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(JobListWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            JobListWrapper wrapper = new JobListWrapper();
            wrapper.setJobs(jobNodeObservableList);

            marshaller.marshal(wrapper, file);
            setJobFilePath(file);
        }
        catch(Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Can not save data to " + file.getName());
        }
    }

    /**
     * Loads Job data from saved .txt file
     *
     * @param file
     */
    public void loadJobDataFromExport(File file) {
        try {
            jobNodeObservableList.clear();

            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (bufferedReader.ready()) {
                bufferedReader.readLine();
                String role = bufferedReader.readLine();
                String company = bufferedReader.readLine();
                String link = bufferedReader.readLine();
                String status = bufferedReader.readLine();

                jobNodeObservableList.add(new JobNode(role, company, link, status));
                bufferedReader.readLine();
            }
            setJobFilePath(file);
        }
        catch(Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Can not load data from " + file.getName());
        }
    }

    public TopBarController getTopBarController() {
        return topBarController;
    }



}
