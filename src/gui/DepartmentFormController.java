package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;

public class DepartmentFormController implements Initializable{

	private Department entity;
	
	@FXML
	private TextField textId;
	@FXML
	private TextField textName;
	@FXML
	private Label labelErroName;
	@FXML
	private Button btSave;
	@FXML
	private Button btCancel;
	
	
	public void setDepartment(Department entity){
		
		this.entity = entity;
		
	}
	
	
	@FXML
	private void onBtSaveAction() {
		
		System.out.println("onBtSaveAction");
		
	}
	@FXML
	private void onBtCancelAction() {
	
		System.out.println("onBtCancelAction");
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	
		initializeNode();
	}

	private void initializeNode() {
		
		Constraints.setTextFieldInteger(textId);
		Constraints.setTextFieldMaxLength(textName, 30);
		
	}
	
	public void updateFormData(){
		if (entity == null) {
			throw new IllegalStateException("Entity não encontrado");
		}
		textId.setText(String.valueOf(entity.getId()));
		textName.setText(entity.getName());
		
	}
	
}
