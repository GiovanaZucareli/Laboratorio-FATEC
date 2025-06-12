package Controller;

import Classes.Pintores;
import DAO.PintoresDAO;
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

public class PintoresController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TextField nomeField, nacionalidadeField, idadeField;

    @FXML
    private TableView<Pintores> tabelaPintores;

    @FXML
    private TableColumn<Pintores, Integer> colId;
    @FXML
    private TableColumn<Pintores, String> colNome;
    @FXML
    private TableColumn<Pintores, String> colNacionalidade;
    @FXML
    private TableColumn<Pintores, Integer> colIdade;
    @FXML
    private TableColumn<Pintores, Void> colEditar;
    @FXML
    private TableColumn<Pintores, Void> colExcluir;

    private final ObservableList<Pintores> listaPintores = FXCollections.observableArrayList();
    private final PintoresDAO pintoresDAO = new PintoresDAO();
    private Pintores pintorSelecionado = null;

    @FXML
    public void initialize() {
        // Configurar as colunas da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("idPintor"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colNacionalidade.setCellValueFactory(new PropertyValueFactory<>("nacionalidade"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));

        // Adicionar botões de ação
        adicionarBotaoEditar();
        adicionarBotaoExcluir();

        // Carregar dados do banco
        carregarDados();

        // Vincular lista à tabela
        tabelaPintores.setItems(listaPintores);
    }

    private void carregarDados() {
        listaPintores.clear();
        listaPintores.addAll(pintoresDAO.listarTodos());
    }

    @FXML
    private void salvarPintor() {
        try {
            // Obter valores dos campos
            String nome = nomeField.getText().trim();
            String nacionalidade = nacionalidadeField.getText().trim();
            String idadeStr = idadeField.getText().trim();

            // Validar campos
            if (nome.isEmpty() || nacionalidade.isEmpty() || idadeStr.isEmpty()) {
                mostrarAlerta("Por favor, preencha todos os campos.");
                return;
            }

            // Converter idade para inteiro
            int idade;
            try {
                idade = Integer.parseInt(idadeStr);
                if (idade < 0 || idade > 150) {
                    throw new NumberFormatException("Idade deve estar entre 0 e 150 anos");
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Idade deve ser um número válido entre 0 e 150 anos!");
                return;
            }

            if (pintorSelecionado == null) {
                // Criar novo pintor
                Pintores novoPintor = new Pintores(0, nome, nacionalidade, idade);
                if (pintoresDAO.criar(novoPintor)) {
                    mostrarAlerta("Pintor cadastrado com sucesso!");
                } else {
                    mostrarAlerta("Erro ao cadastrar pintor!");
                    return;
                }
            } else {
                // Atualizar pintor existente
                pintorSelecionado.setNome(nome);
                pintorSelecionado.setNacionalidade(nacionalidade);
                pintorSelecionado.setIdade(idade);
                if (pintoresDAO.atualizar(pintorSelecionado)) {
                    mostrarAlerta("Pintor atualizado com sucesso!");
                    pintorSelecionado = null;
                } else {
                    mostrarAlerta("Erro ao atualizar pintor!");
                    return;
                }
            }

            // Recarregar dados e limpar campos
            carregarDados();
            limparCampos();

        } catch (Exception e) {
            mostrarAlerta("Erro ao salvar pintor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void limparCampos() {
        nomeField.clear();
        nacionalidadeField.clear();
        idadeField.clear();
        pintorSelecionado = null;
    }

    private void adicionarBotaoEditar() {
        Callback<TableColumn<Pintores, Void>, TableCell<Pintores, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Pintores, Void> call(final TableColumn<Pintores, Void> param) {
                        return new TableCell<>() {
                            private final Button btnEditar = new Button("Editar");
                            {
                                btnEditar.setOnAction(event -> {
                                    pintorSelecionado = getTableView().getItems().get(getIndex());
                                    nomeField.setText(pintorSelecionado.getNome());
                                    nacionalidadeField.setText(pintorSelecionado.getNacionalidade());
                                    idadeField.setText(String.valueOf(pintorSelecionado.getIdade()));
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
        Callback<TableColumn<Pintores, Void>, TableCell<Pintores, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Pintores, Void> call(final TableColumn<Pintores, Void> param) {
                        return new TableCell<>() {
                            private final Button btnExcluir = new Button("Excluir");
                            {
                                btnExcluir.setOnAction(event -> {
                                    Pintores pintor = getTableView().getItems().get(getIndex());

                                    Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                                    confirmacao.setTitle("Confirmar Exclusão");
                                    confirmacao.setHeaderText("Deseja realmente excluir este pintor?");
                                    confirmacao.setContentText(pintor.getNome() + " - " + pintor.getNacionalidade());

                                    if (confirmacao.showAndWait().get() == ButtonType.OK) {
                                        try {
                                            if (pintoresDAO.excluir(pintor.getIdPintor())) {
                                                carregarDados();
                                                mostrarAlerta("Pintor excluído com sucesso!");
                                            } else {
                                                mostrarAlerta("Erro ao excluir pintor!");
                                            }
                                        } catch (Exception e) {
                                            mostrarAlerta("Erro ao excluir: " + e.getMessage());
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

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
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
            mostrarAlerta("Erro ao abrir tela: " + nomeFXML);
        }
    }
}
