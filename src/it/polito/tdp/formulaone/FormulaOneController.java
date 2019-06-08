package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.ResourceBundle;



import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormulaOneController {

	Model model = new Model();
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Season> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="btnSelezionaStagione"
    private Button btnSelezionaStagione; // Value injected by FXMLLoader

    @FXML // fx:id="boxGara"
    private ComboBox<Race> boxGara; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulaGara"
    private Button btnSimulaGara; // Value injected by FXMLLoader

    @FXML // fx:id="textInputK"
    private TextField textInputK; // Value injected by FXMLLoader

    @FXML // fx:id="textInputK1"
    private TextField textInputK1; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doSelezionaStagione(ActionEvent event) {
    
    	txtResult.clear();
    	
    	Season season = boxAnno.getValue();
    	
    	if(season!=null) {
    		
    		model.creaGrafo(season.getYear());
    		
    		String dwe = model.getMaxWeight();
    		
    		if(dwe!=null)
    			txtResult.appendText(dwe);
    		
    		//popolo il menu a tendina
    		boxGara.getItems().addAll(model.getVertex());
    		btnSimulaGara.setDisable(false);
    		
    	}
    	else
    		txtResult.appendText("Si prega di selezionare una stagione");
    	
    }
    
    @FXML
    void doBloccaSimula(ActionEvent event) {

    	btnSimulaGara.setDisable(true);
    }

    @FXML
    void doSimulaGara(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	Season season = boxAnno.getValue();
    	Race race = boxGara.getValue();
    	String input = textInputK.getText();
    	String input1 = textInputK1.getText();
    	
    	if(season!=null) {
    		if(race!=null) {
    			if(input!=null && !input.trim().equals("")) {
    				if(input1!=null && !input1.trim().equals("")) {
    					
    					int T = 0;
    					double P = 0.0;
    					try {
    						
    						T = Integer.parseInt(input1);
    						P = Double.parseDouble(input);
    						
    					}catch(NumberFormatException nfe) {
    						
    						txtResult.appendText("I valori dei parametri devo essere, rispettivamente, un intero per T "
    								+ "ed un double per P ");
    						return;
    					}
    					
    					if(T>0) {
    						
    						if(P>=0.0 && P<=1.0) {
    							
    							//dopo na sfilza di controlli 
    							
    							model.simulate(race, P, T);
    							txtResult.appendText(model.getStats());
    						
    							
    						}
    						else
    							txtResult.appendText("P deve essere compreso tra 0.0 e 1.0 dal momento che è una probabilità");
    						
    					}
    					else
    						txtResult.appendText(" Non può esistere un tempo negativo");
    						
    						
    					
    				}
    				else	
    					txtResult.appendText("Inserire il parametro T");
    			}
    			else
    				txtResult.appendText("Inserire il parametro P");
    			
    		}
    		else 
    			txtResult.appendText("Si prega di selezionare una gara");	
    		
    	}
    	else 
    		txtResult.appendText("Si prega di selezionare una stagione e premere 'Seleziona stagione' ");
    	
    	
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSelezionaStagione != null : "fx:id=\"btnSelezionaStagione\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert boxGara != null : "fx:id=\"boxGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSimulaGara != null : "fx:id=\"btnSimulaGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK1 != null : "fx:id=\"textInputK1\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        
        boxAnno.getItems().addAll(model.getAllSeason());
        btnSimulaGara.setDisable(true);
    }

	public void setModel(Model model) {
		this.model = model;
		
	}
}
