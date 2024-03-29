package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViwController  implements Initializable{

	@FXML
	private MenuItem menuItemVendedor;
	
	@FXML
	private MenuItem menuItemDepartamento; 
	
	@FXML	private MenuItem menuItemAbout;
	
	@FXML
	private void onMenuItemVendedorAction() {
		
		System.out.println("onMenuItemVendedorAction");
			
	}
	
	@FXML
	private void onMenuItemDepartamentoAction() {
		
		loadView("/gui/DepartmentList.fxml", (DepartmentListControlle controller) -> {
		controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();	
		});
			
	}
	
	@FXML
	private void onMenuItemAboutAction() {
		
		loadView("/gui/About.fxml", x -> {});
			
	}
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		
		
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializeAction) {
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();
			
			Scene mainScene = Main.getMainScene();
			VBox mainVBox =(VBox) ((ScrollPane)mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);	
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			T controller = loader.getController();
			initializeAction.accept(controller);
		
		}
		catch(IOException e) {
			Alerts.showAlert("IO Exception", "Error loading", e.getMessage(), AlertType.ERROR);
		}
		
	}
}
