package Controller;

import Classes.Gato;
import DAO.GatoDAO;
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

public class GatoController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TextField nomeField, idadeField, racaField, corField;

    @FXML
    private TableView<Gato> tabelaGatos;

    @FXML
    private TableColumn<Gato, Integer> colId;
    @FXML
    private TableColumn<Gato, String> colNome;
    @FXML
    private TableColumn<Gato, Integer> colIdade;
    @FXML
    private TableColumn<Gato, String> colRaca;
    @FXML
    private TableColumn<Gato, String> colCor;
    @FXML
    private TableColumn<Gato, Void> colEditar;
    @FXML
    private TableColumn<Gato, Void> colExcluir;

    private final ObservableList<Gato> listaGatos = FXCollections.observableArrayList();
    private final GatoDAO gatoDAO = new GatoDAO();
    private Gato gatoSelecionado = null;

    @FXML
    public void initialize() {
        // Configurar as colunas da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colRaca.setCellValueFactory(new PropertyValueFactory<>("raca"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));

        // Adicionar botões de ação
        adicionarBotaoEditar();
        adicionarBotaoExcluir();

        // Carregar dados do banco
        carregarDados();

        // Vincular lista à tabela
        tabelaGatos.setItems(listaGatos);
    }

    private void carregarDados() {
        listaGatos.clear();
        listaGatos.addAll(gatoDAO.listarTodos());
    }

    @FXML
    private void salvarGato() {
        try {
            // Obter valores dos campos
            String nome = nomeField.getText().trim();
            String idadeStr = idadeField.getText().trim();
            String raca = racaField.getText().trim();
            String cor = corField.getText().trim();

            // Validar campos
            if (nome.isEmpty() || idadeStr.isEmpty() || raca.isEmpty() || cor.isEmpty()) {
                mostrarAlerta("Por favor, preencha todos os campos.");
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
                mostrarAlerta("Idade deve ser um número válido positivo!");
                return;
            }

            if (gatoSelecionado == null) {
                // Criar novo gato
                Gato novoGato = new Gato(0, nome, idade, raca, cor);
                if (gatoDAO.criar(novoGato)) {
                    mostrarAlerta("Gato cadastrado com sucesso!");
                } else {
                    mostrarAlerta("Erro ao cadastrar gato!");
                    return;
                }
            } else {
                // Atualizar gato existente
                gatoSelecionado.setNome(nome);
                gatoSelecionado.setIdade(idade);
                gatoSelecionado.setRaca(raca);
                gatoSelecionado.setCor(cor);
                if (gatoDAO.atualizar(gatoSelecionado)) {
                    mostrarAlerta("Gato atualizado com sucesso!");
                    gatoSelecionado = null;
                } else {
                    mostrarAlerta("Erro ao atualizar gato!");
                    return;
                }
            }

            // Recarregar dados e limpar campos
            carregarDados();
            limparCampos();

        } catch (Exception e) {
            mostrarAlerta("Erro ao salvar gato: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void limparCampos() {
        nomeField.clear();
        idadeField.clear();
        racaField.clear();
        corField.clear();
        gatoSelecionado = null;
    }

    private void adicionarBotaoEditar() {
        Callback<TableColumn<Gato, Void>, TableCell<Gato, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Gato, Void> call(final TableColumn<Gato, Void> param) {
                        return new TableCell<>() {
                            private final Button btnEditar = new Button("Editar");
                            {
                                btnEditar.setOnAction(event -> {
                                    gatoSelecionado = getTableView().getItems().get(getIndex());
                                    nomeField.setText(gatoSelecionado.getNome());
                                    idadeField.setText(String.valueOf(gatoSelecionado.getIdade()));
                                    racaField.setText(gatoSelecionado.getRaca());
                                    corField.setText(gatoSelecionado.getCor());
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
        Callback<TableColumn<Gato, Void>, TableCell<Gato, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Gato, Void> call(final TableColumn<Gato, Void> param) {
                        return new TableCell<>() {
                            private final Button btnExcluir = new Button("Excluir");
                            {
                                btnExcluir.setOnAction(event -> {
                                    Gato gato = getTableView().getItems().get(getIndex());

                                    Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                                    confirmacao.setTitle("Confirmar Exclusão");
                                    confirmacao.setHeaderText("Deseja realmente excluir este gato?");
                                    confirmacao.setContentText(gato.getNome() + " - " + gato.getRaca());

                                    if (confirmacao.showAndWait().get() == ButtonType.OK) {
                                        try {
                                            if (gatoDAO.excluir(gato.getId())) {
                                                carregarDados();
                                                mostrarAlerta("Gato excluído com sucesso!");
                                            } else {
                                                mostrarAlerta("Erro ao excluir gato!");
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
