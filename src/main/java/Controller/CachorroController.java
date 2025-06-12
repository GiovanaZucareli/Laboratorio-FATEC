package Controller;

import Classes.Cachorro;
import DAO.CachorroDAO;
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

public class CachorroController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TextField racaField, corField, nomeField, idadeField;

    @FXML
    private TableView<Cachorro> tabelaCachorros;

    @FXML
    private TableColumn<Cachorro, Integer> colId;
    @FXML
    private TableColumn<Cachorro, String> colRaca;
    @FXML
    private TableColumn<Cachorro, String> colCor;
    @FXML
    private TableColumn<Cachorro, String> colNome;
    @FXML
    private TableColumn<Cachorro, Integer> colIdade;
    @FXML
    private TableColumn<Cachorro, Void> colEditar;
    @FXML
    private TableColumn<Cachorro, Void> colExcluir;

    private final ObservableList<Cachorro> listaCachorros = FXCollections.observableArrayList();
    private final CachorroDAO cachorroDAO = new CachorroDAO();
    private Cachorro cachorroSelecionado = null;

    @FXML
    public void initialize() {
        // Configurar as colunas da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("idCachorro"));
        colRaca.setCellValueFactory(new PropertyValueFactory<>("raca"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));

        // Adicionar botões de ação
        adicionarBotaoEditar();
        adicionarBotaoExcluir();

        // Carregar dados do banco
        carregarDados();

        // Vincular lista à tabela
        tabelaCachorros.setItems(listaCachorros);
    }

    private void carregarDados() {
        listaCachorros.clear();
        listaCachorros.addAll(cachorroDAO.listarTodos());
    }

    @FXML
    private void salvarCachorro(ActionEvent event) {
        // Obter valores dos campos
        String raca = racaField.getText().trim();
        String cor = corField.getText().trim();
        String nome = nomeField.getText().trim();
        String idadeStr = idadeField.getText().trim();

        // Validar campos
        if (raca.isEmpty() || cor.isEmpty() || nome.isEmpty() || idadeStr.isEmpty()) {
            exibirAlerta(Alert.AlertType.WARNING, "Campos obrigatórios",
                    "Preencha todos os campos!");
            return;
        }

        // Converter idade para inteiro
        int idade;
        try {
            idade = Integer.parseInt(idadeStr);
            if (idade < 0) {
                throw new NumberFormatException("Idade não pode ser negativa");
            }
        } catch (NumberFormatException e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro de validação",
                    "Idade deve ser um número inteiro positivo!");
            return;
        }

        try {
            if (cachorroSelecionado == null) {
                // Criar novo cachorro
                Cachorro novoCachorro = new Cachorro(raca, cor, nome, 0, idade);
                if (cachorroDAO.criar(novoCachorro)) {
                    exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso",
                            "Cachorro cadastrado com sucesso!");
                } else {
                    exibirAlerta(Alert.AlertType.ERROR, "Erro",
                            "Erro ao cadastrar cachorro!");
                    return;
                }
            } else {
                // Atualizar cachorro existente
                cachorroSelecionado.setRaca(raca);
                cachorroSelecionado.setCor(cor);
                cachorroSelecionado.setNome(nome);
                cachorroSelecionado.setIdade(idade);
                if (cachorroDAO.atualizar(cachorroSelecionado)) {
                    exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso",
                            "Cachorro atualizado com sucesso!");
                    cachorroSelecionado = null;
                } else {
                    exibirAlerta(Alert.AlertType.ERROR, "Erro",
                            "Erro ao atualizar cachorro!");
                    return;
                }
            }

            // Recarregar dados e limpar campos
            carregarDados();
            limparCampos(null);
        } catch (Exception e) {
            exibirAlerta(Alert.AlertType.ERROR, "Erro",
                    "Erro ao salvar: " + e.getMessage());
        }
    }

    @FXML
    private void limparCampos(ActionEvent event) {
        racaField.clear();
        corField.clear();
        nomeField.clear();
        idadeField.clear();
        cachorroSelecionado = null;
    }

    private void adicionarBotaoEditar() {
        Callback<TableColumn<Cachorro, Void>, TableCell<Cachorro, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Cachorro, Void> call(final TableColumn<Cachorro, Void> param) {
                        return new TableCell<>() {
                            private final Button btnEditar = new Button("Editar");
                            {
                                btnEditar.setOnAction(event -> {
                                    cachorroSelecionado = getTableView().getItems().get(getIndex());
                                    racaField.setText(cachorroSelecionado.getRaca());
                                    corField.setText(cachorroSelecionado.getCor());
                                    nomeField.setText(cachorroSelecionado.getNome());
                                    idadeField.setText(String.valueOf(cachorroSelecionado.getIdade()));
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
        Callback<TableColumn<Cachorro, Void>, TableCell<Cachorro, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Cachorro, Void> call(final TableColumn<Cachorro, Void> param) {
                        return new TableCell<>() {
                            private final Button btnExcluir = new Button("Excluir");
                            {
                                btnExcluir.setOnAction(event -> {
                                    Cachorro cachorro = getTableView().getItems().get(getIndex());

                                    Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                                    confirmacao.setTitle("Confirmar Exclusão");
                                    confirmacao.setHeaderText("Deseja realmente excluir este cachorro?");
                                    confirmacao.setContentText(cachorro.getNome() + " - " + cachorro.getRaca());

                                    if (confirmacao.showAndWait().get() == ButtonType.OK) {
                                        try {
                                            if (cachorroDAO.excluir(cachorro.getIdCachorro())) {
                                                carregarDados();
                                                exibirAlerta(Alert.AlertType.INFORMATION, "Sucesso",
                                                        "Cachorro excluído com sucesso!");
                                            } else {
                                                exibirAlerta(Alert.AlertType.ERROR, "Erro",
                                                        "Erro ao excluir cachorro!");
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
    @FXML private void abrirCRUD1() { abrirTela("Cachorro.fxml"); }
    @FXML private void abrirCRUD2() { abrirTela("Carro.fxml"); }
    @FXML private void abrirCRUD3() { abrirTela("Ciclista.fxml"); }
    @FXML private void abrirCRUD4() { abrirTela("Crianca.fxml"); }
    @FXML private void abrirCRUD5() { abrirTela("Gato.fxml"); }
    @FXML private void abrirCRUD6() { abrirTela("Jogador.fxml"); }
    @FXML private void abrirCRUD7() { abrirTela("Passaro.fxml"); }
    @FXML private void abrirCRUD8() { abrirTela("Pessoa.fxml"); }
    @FXML private void abrirCRUD9() { abrirTela("Pintores.fxml"); }
    @FXML private void abrirCRUD10() { abrirTela("Robo.fxml"); }

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