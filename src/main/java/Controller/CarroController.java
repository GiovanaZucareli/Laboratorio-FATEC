package Controller;

import Classes.Carro;
import DAO.CarroDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;

public class CarroController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TextField marcaField, corField;

    @FXML
    private TableView<Carro> tabelaCarros;

    @FXML
    private TableColumn<Carro, Integer> colId;
    @FXML
    private TableColumn<Carro, String> colMarca;
    @FXML
    private TableColumn<Carro, String> colCor;
    @FXML
    private TableColumn<Carro, Void> colEditar;
    @FXML
    private TableColumn<Carro, Void> colExcluir;

    private final ObservableList<Carro> listaCarros = FXCollections.observableArrayList();
    private final CarroDAO carroDAO = new CarroDAO();
    private Carro carroSelecionado = null;

    @FXML
    public void initialize() {
        // Configurar as colunas da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("idCarro"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));

        // Adicionar botões de ação
        adicionarBotaoEditar();
        adicionarBotaoExcluir();

        // Carregar dados do banco
        carregarDados();

        // Vincular lista à tabela
        tabelaCarros.setItems(listaCarros);
    }

    private void carregarDados() {
        listaCarros.clear();
        listaCarros.addAll(carroDAO.listarTodos());
    }

    @FXML
    private void salvarCarro() {
        // Obter valores dos campos
        String marca = marcaField.getText().trim();
        String cor = corField.getText().trim();

        // Validar campos
        if (marca.isEmpty() || cor.isEmpty()) {
            exibirAlerta(Alert.AlertType.WARNING, "Campos obrigatórios",
                    "Preencha todos os campos!");
            return;
        }

        try {
            if (carroSelecionado == null) {
                // Criar novo carro
                Carro novoCarro = new Carro(marca, cor, 0);
                if (carroDAO.criar(novoCarro)) {
                    exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso",
                            "Carro cadastrado com sucesso!");
                } else {
                    exibirAlerta(Alert.AlertType.ERROR, "Erro",
                            "Erro ao cadastrar carro!");
                    return;
                }
            } else {
                // Atualizar carro existente
                carroSelecionado.setMarca(marca);
                carroSelecionado.setCor(cor);
                if (carroDAO.atualizar(carroSelecionado)) {
                    exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso",
                            "Carro atualizado com sucesso!");
                    carroSelecionado = null;
                } else {
                    exibirAlerta(Alert.AlertType.ERROR, "Erro",
                            "Erro ao atualizar carro!");
                    return;
                }
            }

            // Recarregar dados e limpar campos
            carregarDados();
            limparCampos();
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro",
                    "Erro ao salvar: " + e.getMessage());
        }
    }

    @FXML
    private void limparCampos() {
        marcaField.clear();
        corField.clear();
        carroSelecionado = null;
    }

    private void adicionarBotaoEditar() {
        Callback<TableColumn<Carro, Void>, TableCell<Carro, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Carro, Void> call(final TableColumn<Carro, Void> param) {
                        return new TableCell<>() {
                            private final Button btnEditar = new Button("Editar");
                            {
                                btnEditar.setOnAction(event -> {
                                    carroSelecionado = getTableView().getItems().get(getIndex());
                                    marcaField.setText(carroSelecionado.getMarca());
                                    corField.setText(carroSelecionado.getCor());
                                });
                            }

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(btnEditar);
                                }
                            }
                        };
                    }
                };
        colEditar.setCellFactory(cellFactory);
    }

    private void adicionarBotaoExcluir() {
        Callback<TableColumn<Carro, Void>, TableCell<Carro, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Carro, Void> call(final TableColumn<Carro, Void> param) {
                        return new TableCell<>() {
                            private final Button btnExcluir = new Button("Excluir");
                            {
                                btnExcluir.setOnAction(event -> {
                                    Carro carro = getTableView().getItems().get(getIndex());

                                    Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                                    confirmacao.setTitle("Confirmar Exclusão");
                                    confirmacao.setHeaderText("Deseja realmente excluir este carro?");
                                    confirmacao.setContentText(carro.getMarca() + " - " + carro.getCor());

                                    if (confirmacao.showAndWait().get() == ButtonType.OK) {
                                        try {
                                            if (carroDAO.excluir(carro.getIdCarro())) {
                                                carregarDados();
                                                exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso",
                                                        "Carro excluído com sucesso!");
                                            } else {
                                                exibirAlerta(Alert.AlertType.ERROR, "Erro",
                                                        "Erro ao excluir carro!");
                                            }
                                        } catch (Exception e) {
                                            exibirAlerta(Alert.AlertType.ERROR, "Erro",
                                                    "Erro ao excluir: " + e.getMessage());
                                        }
                                    }
                                });
                            }

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(btnExcluir);
                                }
                            }
                        };
                    }
                };
        colExcluir.setCellFactory(cellFactory);
    }

    private void exibirAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    // Métodos de navegação para os menus
    @FXML public void abrirCRUD1() { abrirTela("Cachorro.fxml"); }
    @FXML public void abrirCRUD2() { abrirTela("Carro.fxml"); }
    @FXML public void abrirCRUD3() { abrirTela("Ciclista.fxml"); }
    @FXML public void abrirCRUD4() { abrirTela("Crianca.fxml"); }
    @FXML public void abrirCRUD5() { abrirTela("Gato.fxml"); }
    @FXML public void abrirCRUD6() { abrirTela("Jogador.fxml"); }
    @FXML public void abrirCRUD7() { abrirTela("Passaro.fxml"); }
    @FXML public void abrirCRUD8() { abrirTela("Pessoa.fxml"); }
    @FXML public void abrirCRUD9() { abrirTela("Pintores.fxml"); }
    @FXML public void abrirCRUD10() { abrirTela("Robo.fxml"); }

    private void abrirTela(String nomeFXML) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/" + nomeFXML));
            rootAnchorPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
            exibirAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao abrir tela: " + nomeFXML);
        }
    }
}
