package it.polito.tdp.impiccato;

import it.polito.tdp.impiccato.model.ImpiccatoModel;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ImpiccatoController {
	
	private ImpiccatoModel model;
	
	public void setModel(ImpiccatoModel model){
		this.model = model;
	}
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private TextField txtSegreto;
    
    @FXML
    private URL location;

    @FXML
    private TextField txtParola;

    @FXML
    private ProgressBar pbErrori;

    @FXML
    private Button btnStart;

    @FXML
    private HBox boxSegreto;

    @FXML
    private TextField txtErrori;

    @FXML
    private ComboBox<String> comboLettera;

    @FXML
    private TextField txtSoluzione;

    @FXML
    void doStart(ActionEvent event) {
    	
    	String segreto = this.txtSegreto.getText();
    	
    	//remove spaces and converts to uppercases
    	segreto = segreto.trim().toUpperCase();
    	
    	//must not be empty
    	if (segreto.length()==0){
    		this.txtSoluzione.setText("ERRORE parola non valida");
    		return ;
    	}
    	
    	//must only contains letters
    	for(int i=0; i<segreto.length();i++){
    		if (!Character.isUpperCase(segreto.charAt(i))){
    			this.txtSoluzione.setText("ERRORE parola non valida");
    			return ;
    		}
    	}
    	
    	this.model.nuovaPartita(segreto);
    	
    	this.comboLettera.setDisable(false);
    	this.boxSegreto.setVisible(false);
    	
    	this.txtParola.setText(this.model.getMaschera());
    }

    @FXML
    void doTry(ActionEvent event) {
    	
    	this.model.tentativo(this.comboLettera.getValue());
    	
    	this.txtParola.setText(this.model.getMaschera());
    	this.txtErrori.setText(String.format("%d", this.model.getErrori()));
    	
    	/*if ( this.model.isLoser() || this.model.isWinner() ){
    		this.txtSoluzione.setText(this.model.get);
    	}*/
    		
    	
    }

    @FXML
    void initialize() {
        assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'Impiccato.fxml'.";
        assert pbErrori != null : "fx:id=\"pbErrori\" was not injected: check your FXML file 'Impiccato.fxml'.";
        assert btnStart != null : "fx:id=\"btnStart\" was not injected: check your FXML file 'Impiccato.fxml'.";
        assert boxSegreto != null : "fx:id=\"boxSegreto\" was not injected: check your FXML file 'Impiccato.fxml'.";
        assert txtErrori != null : "fx:id=\"txtErrori\" was not injected: check your FXML file 'Impiccato.fxml'.";
        assert comboLettera != null : "fx:id=\"comboLettera\" was not injected: check your FXML file 'Impiccato.fxml'.";
        assert txtSoluzione != null : "fx:id=\"txtSoluzione\" was not injected: check your FXML file 'Impiccato.fxml'.";

        // Popola la combo box
     	for (char ch = 'A'; ch <= 'Z'; ch++) {
     		comboLettera.getItems().add(String.valueOf(ch));
     	}
    }
}
