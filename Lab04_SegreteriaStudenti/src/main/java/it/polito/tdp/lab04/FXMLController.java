package it.polito.tdp.lab04;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> ccbCorsi;
    
    @FXML
    private CheckBox cbxCompile;

    @FXML
    private TextField txtMatricola;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private TextArea txtResult;

    @FXML
    void doAutoCompile(ActionEvent event) {
    	
    	String stringaMatricola = txtMatricola.getText();
        Integer matricola;
    	
    	try {
    		matricola = Integer.parseInt(stringaMatricola);
    	}
    	catch (NumberFormatException ne) {
    		txtResult.setText("Devi inserire una matricola");
    		return;
    	}
    	catch(NullPointerException npe) {
    		txtResult.setText("Devi inserire una matricola");
    		return;
    	}
    	
    	for(Studente s : model.getTuttiStudenti()) {
    		if(s.getMatricola().equals(matricola)) {
    			txtCognome.setText(s.getCognome());
    			txtNome.setText(s.getNome());
    		}
    	}
    	

    }

    @FXML
    void doCercaCorsi(ActionEvent event) {
    	
    	String stringaMatricola = txtMatricola.getText();
        Integer matricola;
        Studente studente = null;
        boolean flag = false;
    	
    	try {
    		matricola = Integer.parseInt(stringaMatricola);
    	}
    	catch (NumberFormatException ne) {
    		txtResult.setText("Devi inserire una matricola");
    		return;
    	}
    	catch(NullPointerException npe) {
    		txtResult.setText("Devi inserire una matricola");
    		return;
    	}
    	
    	for(Studente s : model.getTuttiStudenti()) {
    		if(s.getMatricola().equals(matricola)) {
    			studente = s;
    			flag = true;
    		}
    	}
    	
    	if(flag == true) {
    		
    		for(Corso c : model.getCorsiPerStudente(studente))
    		txtResult.appendText(c.getCodins() +" "+ c.getCrediti() +" "+ c.getNome() +" "+ c.getPd() + "\n");;
    	}
    	else {
    		txtResult.setText("Studente non presente nel database");
    	}

    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	
    	String stringaCorso = ccbCorsi.getValue();
    	String stringaMatricola = txtMatricola.getText();
        Integer matricola;
        Corso corso = null;
        Studente studente = null;
        boolean flag = false;
        boolean flagStudente = false;
        int i= 0;
        
        
    	if(stringaCorso.equals("")) {
    		txtResult.setText("Devi selezionare un corso");
    	}
    	
    	for(Corso c : model.getCorso()) {
    		if(c.toString().equals(stringaCorso)) {
    			corso = c;
    			break;
    		}
    	}
    	
    	    	
    	
    	try {
    		matricola = Integer.parseInt(stringaMatricola);
    	}
    	catch (NumberFormatException ne) {
    		txtResult.setText("Devi inserire una matricola");
    		return;
    	}
    	catch(NullPointerException npe) {
    		txtResult.setText("Devi inserire una matricola");
    		return;
    	}
    	
    	for(Studente s : model.getTuttiStudenti()) {
    		if(s.getMatricola().equals(matricola)) {
    			studente = s;
    			flagStudente = true;
    			break;
    		}
    	}
    	if(flagStudente == false) {
    		txtResult.setText("Studente non presente");
    		return;
    	}
    	
    	if(model.getStudentiIscrittiAlCorso(corso) != null) {
    	if(model.getCorsiPerStudente(studente) != null) {
    	for(Corso c : model.getCorsiPerStudente(studente)) {
    		
    		if(c.equals(corso)) {
    			txtResult.setText("Studente già iscritto a questo corso");
    			flag = true;
    			
    		}
    	}
    	if(flag == false) {
    		txtResult.setText("Studente iscritto ad altri corsi");
    	}
    	}
    	else {
    		txtResult.setText("Studente non iscritto a nessun corso");
    	}
    	}
    	else {
    		txtResult.setText("Corso senza iscritti");
    	}
    	    	
    	
    	if(flag == false) {
    		model.inscriviStudenteACorso(studente, corso);
    		txtResult.setText("Studente iscritto correttamente");
    		
    	}
    	
    	

    }

    @FXML
    void doReset(ActionEvent event) {
    	ccbCorsi.setValue("");
    	txtMatricola.clear();
    	txtCognome.clear();
    	txtNome.clear();
    	txtResult.clear();
    	cbxCompile.setSelected(false);
    	


    }

    @FXML
    void doSearchIscrittiCorso(ActionEvent event) {

    	String stringaCorso = ccbCorsi.getValue();
    	if(stringaCorso.equals("")) {
    		txtResult.setText("Devi selezionare un corso");
    	}
    	Corso corso = null;
    	String stringaStudente;
    	
    	
    	for(Corso c : model.getCorso()) {
    		if(c.toString().equals(stringaCorso)) {
    			corso = c;
    			break;
    		}
    	}
    	for(Studente s : model.getStudentiIscrittiAlCorso(corso)) {
    		
    		stringaStudente = s.getMatricola() +" "+ s.getCognome() +" "+ s.getNome() +" "+ s.getCDS() + "\n";
    	    txtResult.appendText(stringaStudente);
    	}
    }

    @FXML
    void initialize() {
        assert ccbCorsi != null : "fx:id=\"ccbCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cbxCompile != null : "fx:id=\"cbxCompile\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model m) {
		this.model = m ;
		ccbCorsi.getItems().add("");
		for(Corso c :  model.getCorso()) {
		ccbCorsi.getItems().add(c.toString());
		}
	}
}
