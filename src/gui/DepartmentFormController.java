package gui;

import java.net.URL;
import java.util.ResourceBundle;

import dbb.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable{

	private Department entity;
	
	private DepartmentService service;
	
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
	
	public void setDepartmentService(DepartmentService service) {
		
		this.service =service;
	}
	
	
	@FXML
	private void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		if (service == null) {
			throw new IllegalStateException("Entity was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			Utils.currentStage(event).close();
		
		}
		catch(DbException e) {
			
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private Department getFormData() {
		
		Department obj = new Department();
		
		obj.setId(Utils.tryParseToInt(textId.getText()));
		obj.setName(textName.getText());
		
		return obj;
	}
	
	
	@FXML
	private void onBtCancelAction(ActionEvent event) {
	
		Utils.currentStage(event).close();
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
			throw new IllegalStateException("Entity n�o encontrado");
		}
		textId.setText(String.valueOf(entity.getId()));
		textName.setText(entity.getName());
		
	}
	
}
