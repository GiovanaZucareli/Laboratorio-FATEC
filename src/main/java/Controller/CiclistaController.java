package Controller;

import Classes.Ciclista;
import DAO.CiclistaDAO;
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

public class CiclistaController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TextField nomeField, equipeField, nacionalidadeField, idadeField;

    @FXML
    private TableView<Ciclista> tabelaCiclistas;

    @FXML
    private TableColumn<Ciclista, Integer> colId;
    @FXML
    private TableColumn<Ciclista, String> colNome;
    @FXML
    private TableColumn<Ciclista, String> colEquipe;
    @FXML
    private TableColumn<Ciclista, String> colNacionalidade;
    @FXML
    private TableColumn<Ciclista, Integer> colIdade;
    @FXML
    private TableColumn<Ciclista, Void> colEditar;
    @FXML
    private TableColumn<Ciclista, Void> colExcluir;

    private final ObservableList<Ciclista> listaCiclistas = FXCollections.observableArrayList();
    private final CiclistaDAO ciclistaDAO = new CiclistaDAO();
    private Ciclista ciclistaSelecionado = null;

    @FXML
    public void initialize() {
        // Configurar as colunas da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("idCiclista"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEquipe.setCellValueFactory(new PropertyValueFactory<>("equipe"));
        colNacionalidade.setCellValueFactory(new PropertyValueFactory<>("nacionalidade"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));

        // Adicionar botões de ação
        adicionarBotaoEditar();
        adicionarBotaoExcluir();

        // Carregar dados do banco
        carregarDados();

        // Vincular lista à tabela
        tabelaCiclistas.setItems(listaCiclistas);
    }

    private void carregarDados() {
        listaCiclistas.clear();
        listaCiclistas.addAll(ciclistaDAO.listarTodos());
    }

    @FXML
    private void salvarCiclista() {
        try {
            // Obter valores dos campos
            String nome = nomeField.getText().trim();
            String equipe = equipeField.getText().trim();
            String nacionalidade = nacionalidadeField.getText().trim();
            String idadeStr = idadeField.getText().trim();

            // Validar campos
            if (nome.isEmpty() || equipe.isEmpty() || nacionalidade.isEmpty() || idadeStr.isEmpty()) {
                mostrarAlerta("Preencha todos os campos!");
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
                mostrarAlerta("Erro: idade deve ser um número inteiro positivo!");
                return;
            }

            if (ciclistaSelecionado == null) {
                // Criar novo ciclista
                Ciclista novoCiclista = new Ciclista(nome, equipe, nacionalidade, idade);
                if (ciclistaDAO.criar(novoCiclista)) {
                    mostrarAlerta("Ciclista cadastrado com sucesso!");
                } else {
                    mostrarAlerta("Erro ao cadastrar ciclista!");
                    return;
                }
            } else {
                // Atualizar ciclista existente
                ciclistaSelecionado.setNome(nome);
                ciclistaSelecionado.setEquipe(equipe);
                ciclistaSelecionado.setNacionalidade(nacionalidade);
                ciclistaSelecionado.setIdade(idade);
                if (ciclistaDAO.atualizar(ciclistaSelecionado)) {
                    mostrarAlerta("Ciclista atualizado com sucesso!");
                    ciclistaSelecionado = null;
                } else {
                    mostrarAlerta("Erro ao atualizar ciclista!");
                    return;
                }
            }

            // Recarregar dados e limpar campos
            carregarDados();
            limparCampos();

        } catch (Exception e) {
            mostrarAlerta("Erro ao salvar: " + e.getMessage());
        }
    }

    @FXML
    private void limparCampos() {
        nomeField.clear();
        equipeField.clear();
        nacionalidadeField.clear();
        idadeField.clear();
        ciclistaSelecionado = null;
    }

    private void adicionarBotaoEditar() {
        Callback<TableColumn<Ciclista, Void>, TableCell<Ciclista, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Ciclista, Void> call(final TableColumn<Ciclista, Void> param) {
                        return new TableCell<>() {
                            private final Button btnEditar = new Button("Editar");
                            {
                                btnEditar.setOnAction(event -> {
                                    ciclistaSelecionado = getTableView().getItems().get(getIndex());
                                    nomeField.setText(ciclistaSelecionado.getNome());
                                    equipeField.setText(ciclistaSelecionado.getEquipe());
                                    nacionalidadeField.setText(ciclistaSelecionado.getNacionalidade());
                                    idadeField.setText(String.valueOf(ciclistaSelecionado.getIdade()));
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
        Callback<TableColumn<Ciclista, Void>, TableCell<Ciclista, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Ciclista, Void> call(final TableColumn<Ciclista, Void> param) {
                        return new TableCell<>() {
                            private final Button btnExcluir = new Button("Excluir");
                            {
                                btnExcluir.setOnAction(event -> {
                                    Ciclista ciclista = getTableView().getItems().get(getIndex());

                                    Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                                    confirmacao.setTitle("Confirmar Exclusão");
                                    confirmacao.setHeaderText("Deseja realmente excluir este ciclista?");
                                    confirmacao.setContentText(ciclista.getNome() + " - " + ciclista.getEquipe());

                                    if (confirmacao.showAndWait().get() == ButtonType.OK) {
                                        try {
                                            if (ciclistaDAO.excluir(ciclista.getIdCiclista())) {
                                                carregarDados();
                                                mostrarAlerta("Ciclista excluído com sucesso!");
                                            } else {
                                                mostrarAlerta("Erro ao excluir ciclista!");
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
            mostrarAlerta("Erro ao abrir tela: " + nomeFXML);
        }
    }
}
