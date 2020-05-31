package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafxmvc.model.dao.ItemDeVendaDAO;
import javafxmvc.model.dao.ProdutoDAO;
import javafxmvc.model.dao.VendaDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Venda;

public class FXMLAnchorPaneProcessosVendasController implements Initializable {

    @FXML
    private TableView<Venda> tableViewVendas;
    @FXML
    private TableColumn<Venda, Integer> tableColumnVendaCodigo;
    @FXML
    private TableColumn<Venda, LocalDate> tableColumnVendaData;
    @FXML
    private TableColumn<Venda, Venda> tableColumnVendaCliente;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelVendaCodigo;
    @FXML
    private Label labelVendaData;
    @FXML
    private Label labelVendaPago;
    @FXML 
    private Label labelVendaValor;
    @FXML
    private Label labelVendaCliente;

    //Atributo necessários para se poder listar todos itens armazenados no banco de dados
    private List<Venda> listVendas; //por que as classes DAO retornam listas não observableLists
    private ObservableList<Venda> observableListVendas;
    /*por que quando for setar os dados da tableview no javafx é necessário que esses dados sejam setados por uma 
    estrutura chamada observableList*/
    
    //Atributos para manipulação de banco de dados
    //Abaixo são atributos justamente do tipo DAO
    private final Database database = DatabaseFactory.getDatabase("postgresql");//p/ solicitar uma conexão com Banco de dados no caso o postgre
    private final Connection connection = database.conectar();
    private final VendaDAO vendaDAO = new VendaDAO();//classe de resistência com os métodos inserir, alterar, remover. E nesse caso específico LISTAR
    private final ItemDeVendaDAO itemDeVendaDAO = new ItemDeVendaDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vendaDAO.setConnection(connection);//seta a conexão do vendaDAO
        carregarTableViewVendas();//criado para ficar atualizando os dados da tabela
        
        selecionarItemTableViewVendas(null);
        //Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewVendas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewVendas(newValue));
        
    }
    
    public void selecionarItemTableViewVendas(Venda venda){
        if (venda != null){
            labelVendaCodigo.setText(String.valueOf(venda.getCdVenda()));
            labelVendaData.setText(String.valueOf(venda.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            labelVendaValor.setText(String.format("%.2f", venda.getValor()));
            labelVendaPago.setText(String.valueOf(venda.getPago()));
            labelVendaCliente.setText(venda.getCliente().toString());
       }else{
            labelVendaCodigo.setText("");
            labelVendaData.setText("");
            labelVendaPago.setText("");
            labelVendaValor.setText("");
            labelVendaCliente.setText("");
        }
    }
    
    public void carregarTableViewVendas(){
        //objetivo de configurar minha tabela, 
        tableColumnVendaCodigo.setCellValueFactory(new PropertyValueFactory<>("cdVenda"));//seja exibido codigo da venda
        tableColumnVendaData.setCellValueFactory(new PropertyValueFactory<>("data"));//seja exibido data da venda
        tableColumnVendaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));//seja exibido dados do cliente da venda
        
        listVendas = vendaDAO.listar();
        
        observableListVendas = FXCollections.observableArrayList(listVendas);//aqui se converte um list de vendas em um observable array list
        tableViewVendas.setItems(observableListVendas);
    }
    
    
}
