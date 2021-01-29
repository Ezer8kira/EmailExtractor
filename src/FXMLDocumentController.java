/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Asus
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Button info,fileButton, folderButton,cancel1,cancel2;
    @FXML
    private TextField fileText, folderText, keyword,generated;
    @FXML
    private ComboBox comboBox;

    Stage stage;

    @FXML
    private void handleButtonAction(ActionEvent event) {
       
       
         if(fileText.getText().equals("")){
        Alert alert = new Alert(Alert.AlertType.ERROR);
                          alert.setTitle("Error");
                          alert.setHeaderText("The File containing Mailing list is not chosen !");
                          alert.showAndWait();}
        else if (folderText.getText().equals("")){
        Alert alert = new Alert(Alert.AlertType.ERROR);
                          alert.setTitle("Error");
                          alert.setHeaderText("The directory where the new file will be created is not chosen !");
                          alert.showAndWait();}
         if (generated.getText().equals("")){
        Alert alert = new Alert(Alert.AlertType.ERROR);
                          alert.setTitle("Error");
                          alert.setHeaderText("The Name of the generated file cannot be empty !");
                          alert.showAndWait();}
        else{
         String s;
        String[] parts;
        Scanner scan;
        PrintWriter out;
        File f = new File(folderText.getText()+File.separator + generated.getText()+".txt");
        f.getParentFile().mkdirs(); 
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        try {

            out = new PrintWriter(new BufferedWriter(new FileWriter(folderText.getText()+File.separator + generated.getText()+".txt")));
            scan = new Scanner(new File(fileText.getText()));
            if (((String)comboBox.getSelectionModel().getSelectedItem()).equals("Extension")){
            while (scan.hasNext()) {
                s = scan.next();
                parts = s.split(":");
                if (parts[0].toLowerCase().endsWith(keyword.getText())) {

                    out.println(parts[0]);

                }

            }}
            else if(((String)comboBox.getSelectionModel().getSelectedItem()).equals("Keyword")){
            while (scan.hasNext()) {
                s = scan.next();
                parts = s.split(":");
                if (parts[0].contains(keyword.getText())) {

                    out.println(parts[0]);

                }

            }}
                    
                    
            out.close();
            scan.close();

        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch (IOException ex) {
        }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                          alert.setTitle("File Created");
                          alert.setHeaderText("Your File named "+generated.getText()+" has been created under "+folderText.getText());
                          alert.showAndWait();
        
        
        
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        info.setOnAction(e->{
        
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
                          alert.setTitle("Informations");
                          alert.setHeaderText("1) Select the type of Filter : \n"
                                  + "2) Select The File where you have e-mails to Filter \n"
                                  + "3) Select the Folder where you want your new generated file \n"
                                  + "4) Name your generated File \n"
                                  + "5) Click Extract ! and enjoy with your new list \n"
                                  + "PS : This program only works for a Mailing list with this pattern email:password");
                                  
                                   
                          alert.showAndWait();
        });
         comboBox.setItems(FXCollections.observableArrayList("Extension","Keyword"));
        comboBox.getSelectionModel().selectFirst();
        comboBox.valueProperty().addListener((e, v1, v2) -> {
        
        switch((String)v2){
            case ("Extension"): 
                label.setText("Enter Extension filter");
                keyword.setPromptText("Example : .com");
                break;
             case ("Keyword"): label.setText("Enter Keyword filter"); 
                 keyword.setPromptText("Any Filter Text");
                
        }
        });
        stage = new Stage();
        fileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(stage);
            if(file!=null)
            fileText.setText(file + "");
        });
        folderButton.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);
             if(selectedDirectory!=null)
            folderText.setText(selectedDirectory.getAbsolutePath());
        });
        cancel1.setOnAction(e->{
        fileText.setText("");});
        cancel2.setOnAction(e->{
        folderText.setText("");});
        
        
    }

}
