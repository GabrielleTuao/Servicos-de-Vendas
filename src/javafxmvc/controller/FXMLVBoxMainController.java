
package javafxmvc.controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Gabrielle Tuão
 */
public class FXMLVBoxMainController implements Initializable {
    @FXML
    private MenuItem menuItemProcessosVendas;
    @FXML
    private MenuItem menuItemGraficosVendasPorMes;
    @FXML
    private MenuItem menuItemRelatoriosQuantidadeProdutosEstoque;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private MenuItem menuItemCadastrosClientes;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    //handle usado para indicar uma ação
    //Ação que faz com que tenha um chamamento de tela de cadastro
    @FXML
    public void handleMenuItemCadastrosClientes() throws IOException{ //a exception é pelo fato de que talvez não encontre o pacote que eu estou procurando
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosClientes.fxml"));
        anchorPane.getChildren().setAll(a); //a representa a telinha que eu quero abrir.
    }
    
    @FXML
    public void handleMenuItemProcessosVendas() throws IOException{ //a exception é pelo fato de que talvez não encontre o pacote que eu estou procurando
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneProcessosVendas.fxml"));
        anchorPane.getChildren().setAll(a); //a representa a telinha que eu quero abrir.
    }
  
}