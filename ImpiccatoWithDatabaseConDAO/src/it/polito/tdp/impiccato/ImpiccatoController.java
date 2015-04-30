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
	private String segreto;
	private boolean inMatch;
	
	public void setModel(ImpiccatoModel model){
		this.model = model;
		segreto = null;
		this.inMatch = false;
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
    	
    	segreto = model.nuovoSegreto();
    		
    	this.model.nuovaPartita(segreto);
    	this.inMatch = true;
    	this.txtSoluzione.clear();
    	this.txtErrori.clear();
    	this.txtParola.setText(this.model.getMaschera());
    	this.updateView();
    }

    private void updateView() {
		// TODO Auto-generated method stub
		if (inMatch){
			//in match
			this.boxSegreto.setVisible(false);
			this.btnStart.setDisable(true);
			this.comboLettera.setDisable(false);
			this.pbErrori.setDisable(false);
		} else {
			//not in match
			this.boxSegreto.setVisible(true);
			this.btnStart.setDisable(false);
			this.comboLettera.setDisable(true);
			this.pbErrori.setDisable(true);
		}
	}

	@FXML
    void doTry(ActionEvent event) {
    	
    	this.model.tentativo(this.comboLettera.getValue());
    	
    	this.txtParola.setText(this.model.getMaschera());
    	this.txtErrori.setText(String.format("%d", this.model.getErrori()));
    	
    	if ( this.model.isLoser() || this.model.isWinner() ){
    		this.txtSoluzione.setText(segreto);
    		this.inMatch = false;
    	}
    	
    	this.updateView();
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
