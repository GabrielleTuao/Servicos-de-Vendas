package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.ClienteDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Cliente;

/**
 * FXML Controller class
 *
 * @author Gabrielle Tuão
 */
public class FXMLAnchorPaneCadastrosClientesController implements Initializable {

    @FXML
    private TableView<Cliente> tableViewClientes;
    @FXML
    private TableColumn<Cliente, String> tableColumnClienteNome;
    @FXML
    private TableColumn<Cliente, String> tableColumnClienteCPF;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelClienteCodigo;
    @FXML
    private Label labelClienteNome;
    @FXML
    private Label labelClienteCPF;
    @FXML
    private Label labelClienteTelefone;

    private List<Cliente> listClientes;
    private ObservableList<Cliente> observableListClientes;

    private final Database database = DatabaseFactory.getDatabase("postgresql"); //
    private final Connection connection = database.conectar(); //tem método conectar(DatabasePostgreSQL)
    private final ClienteDAO clienteDAO = new ClienteDAO();//instanciou uma classe cliente DAO utilizando o método listar, assim irá se ter uma lista com todos os clientes

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        carregarTableViewCliente();

        //Listen adicionando diante quaisquer alterações na seleção de itens do TableView
        tableViewClientes.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewClientes(newValue));
    }

    public void carregarTableViewCliente() {
        tableColumnClienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnClienteCPF.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        listClientes = clienteDAO.listar();//vai selecionar todos os meus clientes e guardar no listClientes

        observableListClientes = FXCollections.observableArrayList(listClientes);
        tableViewClientes.setItems(observableListClientes);

    }

    public void selecionarItemTableViewClientes(Cliente cliente) {
        // mostra o cliente selecionado no quadro saída e mostra quando selecionado
        // System.out.println("Cliente selecionado: "+cliente.toString());
        if (cliente != null) {
            labelClienteCodigo.setText(String.valueOf(cliente.getCdCliente()));
            labelClienteNome.setText(cliente.getNome());
            labelClienteCPF.setText(cliente.getCpf());
            labelClienteTelefone.setText(cliente.getTelefone());
        } else {//situação de não ter selecionado ninguém, pode haver situações em que não irá se ter clientes selecionados
            labelClienteCodigo.setText("");
            labelClienteNome.setText("");
            labelClienteCPF.setText("");
            labelClienteTelefone.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Cliente cliente = new Cliente();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosClientesDialog(cliente);
        if (buttonConfirmarClicked) {
            clienteDAO.inserir(cliente);
            carregarTableViewCliente();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosClientesDialog(cliente);
            if (buttonConfirmarClicked) {
                clienteDAO.alterar(cliente);
                carregarTableViewCliente();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um cliente na Tabela!");
            alert.show();
        }

    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            clienteDAO.remover(cliente);
            carregarTableViewCliente();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um cliente na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLAnchorPaneCadastrosClientesDialog(Cliente cliente) throws IOException {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(FXMLAnchorPaneCadastrosClientesDialogController.class.getResource(
                "/javafxmvc/view/FXMLAnchorPaneCadastrosClientesDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Clientes");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        // Setando o cliente no Controller.
        FXMLAnchorPaneCadastrosClientesDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCliente(cliente);
        
        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();
        
        return controller.isButtonConfirmarClicked();
    }
}
