package Controller;

import Classes.Passaro;
import DAO.PassaroDAO;
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

public class PassaroController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TextField nomeField, idadeField, especieField, corField;

    @FXML
    private TableView<Passaro> tabelaPassaros;

    @FXML
    private TableColumn<Passaro, Integer> colId;
    @FXML
    private TableColumn<Passaro, String> colNome;
    @FXML
    private TableColumn<Passaro, Integer> colIdade;
    @FXML
    private TableColumn<Passaro, String> colEspecie;
    @FXML
    private TableColumn<Passaro, String> colCor;
    @FXML
    private TableColumn<Passaro, Void> colEditar;
    @FXML
    private TableColumn<Passaro, Void> colExcluir;

    private final ObservableList<Passaro> listaPassaros = FXCollections.observableArrayList();
    private final PassaroDAO passaroDAO = new PassaroDAO();
    private Passaro passaroSelecionado = null;

    @FXML
    public void initialize() {
        // Configurar as colunas da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("idPassaro"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));

        // Adicionar botões de ação
        adicionarBotaoEditar();
        adicionarBotaoExcluir();

        // Carregar dados do banco
        carregarDados();

        // Vincular lista à tabela
        tabelaPassaros.setItems(listaPassaros);
    }

    private void carregarDados() {
        listaPassaros.clear();
        listaPassaros.addAll(passaroDAO.listarTodos());
    }

    @FXML
    private void salvarPassaro() {
        try {
            // Obter valores dos campos
            String nome = nomeField.getText().trim();
            String idadeStr = idadeField.getText().trim();
            String especie = especieField.getText().trim();
            String cor = corField.getText().trim();

            // Validar campos
            if (nome.isEmpty() || idadeStr.isEmpty() || especie.isEmpty() || cor.isEmpty()) {
                mostrarAlerta("Por favor, preencha todos os campos.");
                return;
            }

            // Converter idade para inteiro
            int idade;
            try {
                idade = Integer.parseInt(idadeStr);
                if (idade < 0 || idade > 100) {
                    throw new NumberFormatException("Idade deve estar entre 0 e 100 anos");
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Idade deve ser um número válido entre 0 e 100 anos!");
                return;
            }

            if (passaroSelecionado == null) {
                // Criar novo pássaro
                Passaro novoPassaro = new Passaro(0, nome, idade, especie, cor);
                if (passaroDAO.criar(novoPassaro)) {
                    mostrarAlerta("Pássaro cadastrado com sucesso!");
                } else {
                    mostrarAlerta("Erro ao cadastrar pássaro!");
                    return;
                }
            } else {
                // Atualizar pássaro existente
                passaroSelecionado.setNome(nome);
                passaroSelecionado.setIdade(idade);
                passaroSelecionado.setEspecie(especie);
                passaroSelecionado.setCor(cor);
                if (passaroDAO.atualizar(passaroSelecionado)) {
                    mostrarAlerta("Pássaro atualizado com sucesso!");
                    passaroSelecionado = null;
                } else {
                    mostrarAlerta("Erro ao atualizar pássaro!");
                    return;
                }
            }

            // Recarregar dados e limpar campos
            carregarDados();
            limparCampos();

        } catch (Exception e) {
            mostrarAlerta("Erro ao salvar pássaro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void limparCampos() {
        nomeField.clear();
        idadeField.clear();
        especieField.clear();
        corField.clear();
        passaroSelecionado = null;
    }

    private void adicionarBotaoEditar() {
        Callback<TableColumn<Passaro, Void>, TableCell<Passaro, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Passaro, Void> call(final TableColumn<Passaro, Void> param) {
                        return new TableCell<>() {
                            private final Button btnEditar = new Button("Editar");
                            {
                                btnEditar.setOnAction(event -> {
                                    passaroSelecionado = getTableView().getItems().get(getIndex());
                                    nomeField.setText(passaroSelecionado.getNome());
                                    idadeField.setText(String.valueOf(passaroSelecionado.getIdade()));
                                    especieField.setText(passaroSelecionado.getEspecie());
                                    corField.setText(passaroSelecionado.getCor());
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
        Callback<TableColumn<Passaro, Void>, TableCell<Passaro, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Passaro, Void> call(final TableColumn<Passaro, Void> param) {
                        return new TableCell<>() {
                            private final Button btnExcluir = new Button("Excluir");
                            {
                                btnExcluir.setOnAction(event -> {
                                    Passaro passaro = getTableView().getItems().get(getIndex());

                                    Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                                    confirmacao.setTitle("Confirmar Exclusão");
                                    confirmacao.setHeaderText("Deseja realmente excluir este pássaro?");
                                    confirmacao.setContentText(passaro.getNome() + " - " + passaro.getEspecie());

                                    if (confirmacao.showAndWait().get() == ButtonType.OK) {
                                        try {
                                            if (passaroDAO.excluir(passaro.getIdPassaro())) {
                                                carregarDados();
                                                mostrarAlerta("Pássaro excluído com sucesso!");
                                            } else {
                                                mostrarAlerta("Erro ao excluir pássaro!");
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
