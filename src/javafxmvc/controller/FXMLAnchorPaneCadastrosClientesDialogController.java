package javafxmvc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxmvc.model.domain.Cliente;

public class FXMLAnchorPaneCadastrosClientesDialogController implements Initializable {

    @FXML
    private Label labelClienteNome;
    @FXML
    private Label labelClienteCPF;
    @FXML
    private Label labelClienteTelefone;
    @FXML
    private TextField textFieldClienteNome;
    @FXML
    private TextField textFieldClienteCPF;
    @FXML
    private TextField textFieldClienteTelefone;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage; //stage pq terá um elemento na classe de controle pra representar a tela aberta, pois em alguns momentos será posssível abrir e fechar a tela
    private boolean buttonConfirmarClicked = false;//Atributo existe apenas para saber se a pessoa clicou no botão confirmar ou no cancelar, importante para a classe anterior de controle de cadastros
    private Cliente cliente;//tipo de classe na qual irá se inserir todos os dados para serem mandados para o banco de dados

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
        this.buttonConfirmarClicked = buttonConfirmarClicked;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        this.textFieldClienteNome.setText(cliente.getNome());
        this.textFieldClienteCPF.setText(cliente.getCpf());
        this.textFieldClienteTelefone.setText(cliente.getTelefone());
    }

    @FXML
    public void handleButtonConfirmar() {//método convocado apartir do clique em confirmar
        if(validarEntradaDeDados()){
        //todos os textfield são de preenchimento de dados dos cliente
        cliente.setNome(textFieldClienteNome.getText());
        cliente.setCpf(textFieldClienteCPF.getText());
        cliente.setTelefone(textFieldClienteTelefone.getText());

        buttonConfirmarClicked = true;//atributo criado para saber que o usuário interagil usando o button confirmar
        dialogStage.close();//fecha a tela na qual o usuário está interagindo
        }
    }

    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
        //se o usuário desistir de acrescentar dados então ele simplesmente fecha a telinha
    }

    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        
        //método só para identticar se usuário vai digitar valores nulos, 
        String errorMessage = "";
        if (textFieldClienteNome.getText() == null || textFieldClienteNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (textFieldClienteCPF.getText() == null || textFieldClienteCPF.getText().length() == 0) {
            errorMessage += "CPF inválido!\n";
        }
        if (textFieldClienteTelefone.getText() == null || textFieldClienteTelefone.getText().length() == 0) {
            errorMessage += "Telefone inválido!\n";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
}
