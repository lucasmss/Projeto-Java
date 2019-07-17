package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import dbb.DbException;
import gui.listeners.DataChangelListener;
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
import model.exceptions.ValidationException;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable{

	private Department entity;
	
	private DepartmentService service;
	
	private List<DataChangelListener> dataChangeListener  = new ArrayList<>();
	
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
	
	public void subscribeDataChangeListener(DataChangelListener listener) {
		
		dataChangeListener.add(listener);
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
			notifyDataChangeListeners();
			Utils.currentStage(event).close();
		
		}
		catch(ValidationException e) {
			setErrorMessages(e.getErrors());
			
		}
		catch(DbException e) {
			
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notifyDataChangeListeners() {
		for (DataChangelListener listener: dataChangeListener) {
			
			listener.onDataChanged();
		}
		
	}

	private Department getFormData() {
		
		Department obj = new Department();
		
		ValidationException exception = new ValidationException("ValidacioException");
		
		obj.setId(Utils.tryParseToInt(textId.getText()));
		
		if (textName.getText() == null || textName.getText().trim().equals("")) {
			exception.addError("name", "Field cant´t be empty");
		}
		obj.setName(textName.getText());
		
		if(exception.getErrors().size() > 0) {
			throw exception;
		}
		
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
			throw new IllegalStateException("Entity não encontrado");
		}
		textId.setText(String.valueOf(entity.getId()));
		textName.setText(entity.getName());
		
	}
	
	private void setErrorMessages(Map<String, String> errors) {
		
		Set<String> fields = errors.keySet();
		
		if (fields.contains("name")) {
			labelErroName.setText(errors.get("name"));
		}
		
		
	}
	
}
