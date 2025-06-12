package Controller;

import Classes.Crianca;
import DAO.CriancaDAO;
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

public class CriancaController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TextField nomeField, idadeField, turmaField;

    @FXML
    private TableView<Crianca> tabelaCriancas;

    @FXML
    private TableColumn<Crianca, Integer> colId;
    @FXML
    private TableColumn<Crianca, String> colNome;
    @FXML
    private TableColumn<Crianca, Integer> colIdade;
    @FXML
    private TableColumn<Crianca, String> colTurma;
    @FXML
    private TableColumn<Crianca, Void> colEditar;
    @FXML
    private TableColumn<Crianca, Void> colExcluir;

    private final ObservableList<Crianca> listaCriancas = FXCollections.observableArrayList();
    private final CriancaDAO criancaDAO = new CriancaDAO();
    private Crianca criancaSelecionada = null;

    @FXML
    public void initialize() {
        // Configurar as colunas da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("idCrianca"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colTurma.setCellValueFactory(new PropertyValueFactory<>("turma"));

        // Adicionar botões de ação
        adicionarBotaoEditar();
        adicionarBotaoExcluir();

        // Carregar dados do banco
        carregarDados();

        // Vincular lista à tabela
        tabelaCriancas.setItems(listaCriancas);
    }

    private void carregarDados() {
        listaCriancas.clear();
        listaCriancas.addAll(criancaDAO.listarTodos());
    }

    @FXML
    private void salvarCrianca() {
        try {
            // Obter valores dos campos
            String nome = nomeField.getText().trim();
            String idadeStr = idadeField.getText().trim();
            String turma = turmaField.getText().trim();

            // Validar campos
            if (nome.isEmpty() || idadeStr.isEmpty() || turma.isEmpty()) {
                mostrarAlerta("Por favor, preencha todos os campos.");
                return;
            }

            // Converter idade para inteiro
            int idade;
            try {
                idade = Integer.parseInt(idadeStr);
                if (idade < 0 || idade > 18) {
                    throw new NumberFormatException("Idade deve estar entre 0 e 18 anos");
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Idade deve ser um número válido entre 0 e 18 anos!");
                return;
            }

            if (criancaSelecionada == null) {
                // Criar nova criança
                Crianca novaCrianca = new Crianca(nome, idade, turma);
                if (criancaDAO.criar(novaCrianca)) {
                    mostrarAlerta("Criança cadastrada com sucesso!");
                } else {
                    mostrarAlerta("Erro ao cadastrar criança!");
                    return;
                }
            } else {
                // Atualizar criança existente
                criancaSelecionada.setNome(nome);
                criancaSelecionada.setIdade(idade);
                criancaSelecionada.setTurma(turma);
                if (criancaDAO.atualizar(criancaSelecionada)) {
                    mostrarAlerta("Criança atualizada com sucesso!");
                    criancaSelecionada = null;
                } else {
                    mostrarAlerta("Erro ao atualizar criança!");
                    return;
                }
            }

            // Recarregar dados e limpar campos
            carregarDados();
            limparCampos();

        } catch (Exception e) {
            mostrarAlerta("Erro ao salvar criança: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void limparCampos() {
        nomeField.clear();
        idadeField.clear();
        turmaField.clear();
        criancaSelecionada = null;
    }

    private void adicionarBotaoEditar() {
        Callback<TableColumn<Crianca, Void>, TableCell<Crianca, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Crianca, Void> call(final TableColumn<Crianca, Void> param) {
                        return new TableCell<>() {
                            private final Button btnEditar = new Button("Editar");
                            {
                                btnEditar.setOnAction(event -> {
                                    criancaSelecionada = getTableView().getItems().get(getIndex());
                                    nomeField.setText(criancaSelecionada.getNome());
                                    idadeField.setText(String.valueOf(criancaSelecionada.getIdade()));
                                    turmaField.setText(criancaSelecionada.getTurma());
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
        Callback<TableColumn<Crianca, Void>, TableCell<Crianca, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Crianca, Void> call(final TableColumn<Crianca, Void> param) {
                        return new TableCell<>() {
                            private final Button btnExcluir = new Button("Excluir");
                            {
                                btnExcluir.setOnAction(event -> {
                                    Crianca crianca = getTableView().getItems().get(getIndex());

                                    Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                                    confirmacao.setTitle("Confirmar Exclusão");
                                    confirmacao.setHeaderText("Deseja realmente excluir esta criança?");
                                    confirmacao.setContentText(crianca.getNome() + " - " + crianca.getTurma());

                                    if (confirmacao.showAndWait().get() == ButtonType.OK) {
                                        try {
                                            if (criancaDAO.excluir(crianca.getIdCrianca())) {
                                                carregarDados();
                                                mostrarAlerta("Criança excluída com sucesso!");
                                            } else {
                                                mostrarAlerta("Erro ao excluir criança!");
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
