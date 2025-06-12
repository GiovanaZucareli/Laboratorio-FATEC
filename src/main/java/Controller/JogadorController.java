package Controller;

import Classes.Jogador;
import DAO.JogadorDAO;
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

public class JogadorController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TextField nomeField, idadeField, posicaoField, timeField;

    @FXML
    private TableView<Jogador> tabelaJogadores;

    @FXML
    private TableColumn<Jogador, Integer> colId;
    @FXML
    private TableColumn<Jogador, String> colNome;
    @FXML
    private TableColumn<Jogador, Integer> colIdade;
    @FXML
    private TableColumn<Jogador, String> colPosicao;
    @FXML
    private TableColumn<Jogador, String> colTime;
    @FXML
    private TableColumn<Jogador, Void> colEditar;
    @FXML
    private TableColumn<Jogador, Void> colExcluir;

    private final ObservableList<Jogador> listaJogadores = FXCollections.observableArrayList();
    private final JogadorDAO jogadorDAO = new JogadorDAO();
    private Jogador jogadorSelecionado = null;

    @FXML
    public void initialize() {
        // Configurar as colunas da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("idJogador"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colPosicao.setCellValueFactory(new PropertyValueFactory<>("posicao"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        // Adicionar botões de ação
        adicionarBotaoEditar();
        adicionarBotaoExcluir();

        // Carregar dados do banco
        carregarDados();

        // Vincular lista à tabela
        tabelaJogadores.setItems(listaJogadores);
    }

    private void carregarDados() {
        listaJogadores.clear();
        listaJogadores.addAll(jogadorDAO.listarTodos());
    }

    @FXML
    private void salvarJogador() {
        try {
            // Obter valores dos campos
            String nome = nomeField.getText().trim();
            String idadeStr = idadeField.getText().trim();
            String posicao = posicaoField.getText().trim();
            String time = timeField.getText().trim();

            // Validar campos
            if (nome.isEmpty() || idadeStr.isEmpty() || posicao.isEmpty() || time.isEmpty()) {
                mostrarAlerta("Por favor, preencha todos os campos.");
                return;
            }

            // Converter idade para inteiro
            int idade;
            try {
                idade = Integer.parseInt(idadeStr);
                if (idade < 16 || idade > 50) {
                    throw new NumberFormatException("Idade deve estar entre 16 e 50 anos");
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Idade deve ser um número válido entre 16 e 50 anos!");
                return;
            }

            if (jogadorSelecionado == null) {
                // Criar novo jogador
                Jogador novoJogador = new Jogador(nome, idade, posicao, time);
                if (jogadorDAO.criar(novoJogador)) {
                    mostrarAlerta("Jogador cadastrado com sucesso!");
                } else {
                    mostrarAlerta("Erro ao cadastrar jogador!");
                    return;
                }
            } else {
                // Atualizar jogador existente
                jogadorSelecionado.setNome(nome);
                jogadorSelecionado.setIdade(idade);
                jogadorSelecionado.setPosicao(posicao);
                jogadorSelecionado.setTime(time);
                if (jogadorDAO.atualizar(jogadorSelecionado)) {
                    mostrarAlerta("Jogador atualizado com sucesso!");
                    jogadorSelecionado = null;
                } else {
                    mostrarAlerta("Erro ao atualizar jogador!");
                    return;
                }
            }

            // Recarregar dados e limpar campos
            carregarDados();
            limparCampos();

        } catch (Exception e) {
            mostrarAlerta("Erro ao salvar jogador: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void limparCampos() {
        nomeField.clear();
        idadeField.clear();
        posicaoField.clear();
        timeField.clear();
        jogadorSelecionado = null;
    }

    private void adicionarBotaoEditar() {
        Callback<TableColumn<Jogador, Void>, TableCell<Jogador, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Jogador, Void> call(final TableColumn<Jogador, Void> param) {
                        return new TableCell<>() {
                            private final Button btnEditar = new Button("Editar");
                            {
                                btnEditar.setOnAction(event -> {
                                    jogadorSelecionado = getTableView().getItems().get(getIndex());
                                    nomeField.setText(jogadorSelecionado.getNome());
                                    idadeField.setText(String.valueOf(jogadorSelecionado.getIdade()));
                                    posicaoField.setText(jogadorSelecionado.getPosicao());
                                    timeField.setText(jogadorSelecionado.getTime());
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
        Callback<TableColumn<Jogador, Void>, TableCell<Jogador, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Jogador, Void> call(final TableColumn<Jogador, Void> param) {
                        return new TableCell<>() {
                            private final Button btnExcluir = new Button("Excluir");
                            {
                                btnExcluir.setOnAction(event -> {
                                    Jogador jogador = getTableView().getItems().get(getIndex());

                                    Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                                    confirmacao.setTitle("Confirmar Exclusão");
                                    confirmacao.setHeaderText("Deseja realmente excluir este jogador?");
                                    confirmacao.setContentText(jogador.getNome() + " - " + jogador.getTime());

                                    if (confirmacao.showAndWait().get() == ButtonType.OK) {
                                        try {
                                            if (jogadorDAO.excluir(jogador.getIdJogador())) {
                                                carregarDados();
                                                mostrarAlerta("Jogador excluído com sucesso!");
                                            } else {
                                                mostrarAlerta("Erro ao excluir jogador!");
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
