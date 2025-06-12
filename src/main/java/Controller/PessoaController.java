package Controller;

import Classes.Pessoa;
import DAO.PessoaDAO;
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

public class PessoaController {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private TextField nomeField, idadeField, cpfField;

    @FXML
    private TableView<Pessoa> tabelaPessoas;

    @FXML
    private TableColumn<Pessoa, Integer> colId;
    @FXML
    private TableColumn<Pessoa, String> colNome;
    @FXML
    private TableColumn<Pessoa, Integer> colIdade;
    @FXML
    private TableColumn<Pessoa, String> colCpf;
    @FXML
    private TableColumn<Pessoa, Void> colEditar;
    @FXML
    private TableColumn<Pessoa, Void> colExcluir;

    private final ObservableList<Pessoa> listaPessoas = FXCollections.observableArrayList();
    private final PessoaDAO pessoaDAO = new PessoaDAO();
    private Pessoa pessoaSelecionada = null;

    @FXML
    public void initialize() {
        // Configurar as colunas da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("idPessoa"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        // Adicionar botões de ação
        adicionarBotaoEditar();
        adicionarBotaoExcluir();

        // Carregar dados do banco
        carregarDados();

        // Vincular lista à tabela
        tabelaPessoas.setItems(listaPessoas);
    }

    private void carregarDados() {
        listaPessoas.clear();
        listaPessoas.addAll(pessoaDAO.listarTodos());
    }

    @FXML
    private void salvarPessoa() {
        try {
            // Obter valores dos campos
            String nome = nomeField.getText().trim();
            String idadeStr = idadeField.getText().trim();
            String cpf = cpfField.getText().trim();

            // Validar campos
            if (nome.isEmpty() || idadeStr.isEmpty() || cpf.isEmpty()) {
                mostrarAlerta("Por favor, preencha todos os campos.");
                return;
            }

            // Validar CPF
            if (!validarCPF(cpf)) {
                mostrarAlerta("CPF inválido! Digite apenas números (11 dígitos).");
                return;
            }

            // Converter idade para inteiro
            int idade;
            try {
                idade = Integer.parseInt(idadeStr);
                if (idade < 0 || idade > 120) {
                    throw new NumberFormatException("Idade deve estar entre 0 e 120 anos");
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Idade deve ser um número válido entre 0 e 120 anos!");
                return;
            }

            // Verificar se CPF já existe
            int idExcluir = pessoaSelecionada != null ? pessoaSelecionada.getIdPessoa() : 0;
            if (pessoaDAO.cpfJaExiste(cpf, idExcluir)) {
                mostrarAlerta("Este CPF já está cadastrado!");
                return;
            }

            if (pessoaSelecionada == null) {
                // Criar nova pessoa
                Pessoa novaPessoa = new Pessoa(0, nome, idade, cpf);
                if (pessoaDAO.criar(novaPessoa)) {
                    mostrarAlerta("Pessoa cadastrada com sucesso!");
                } else {
                    mostrarAlerta("Erro ao cadastrar pessoa!");
                    return;
                }
            } else {
                // Atualizar pessoa existente
                pessoaSelecionada.setNome(nome);
                pessoaSelecionada.setIdade(idade);
                pessoaSelecionada.setCpf(cpf);
                if (pessoaDAO.atualizar(pessoaSelecionada)) {
                    mostrarAlerta("Pessoa atualizada com sucesso!");
                    pessoaSelecionada = null;
                } else {
                    mostrarAlerta("Erro ao atualizar pessoa!");
                    return;
                }
            }

            // Recarregar dados e limpar campos
            carregarDados();
            limparCampos();

        } catch (Exception e) {
            mostrarAlerta("Erro ao salvar pessoa: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void limparCampos() {
        nomeField.clear();
        idadeField.clear();
        cpfField.clear();
        pessoaSelecionada = null;
    }

    private boolean validarCPF(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        return true; // Validação básica - pode ser melhorada com algoritmo completo do CPF
    }

    private void adicionarBotaoEditar() {
        Callback<TableColumn<Pessoa, Void>, TableCell<Pessoa, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Pessoa, Void> call(final TableColumn<Pessoa, Void> param) {
                        return new TableCell<>() {
                            private final Button btnEditar = new Button("Editar");
                            {
                                btnEditar.setOnAction(event -> {
                                    pessoaSelecionada = getTableView().getItems().get(getIndex());
                                    nomeField.setText(pessoaSelecionada.getNome());
                                    idadeField.setText(String.valueOf(pessoaSelecionada.getIdade()));
                                    cpfField.setText(pessoaSelecionada.getCpf());
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
        Callback<TableColumn<Pessoa, Void>, TableCell<Pessoa, Void>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<Pessoa, Void> call(final TableColumn<Pessoa, Void> param) {
                        return new TableCell<>() {
                            private final Button btnExcluir = new Button("Excluir");
                            {
                                btnExcluir.setOnAction(event -> {
                                    Pessoa pessoa = getTableView().getItems().get(getIndex());

                                    Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
                                    confirmacao.setTitle("Confirmar Exclusão");
                                    confirmacao.setHeaderText("Deseja realmente excluir esta pessoa?");
                                    confirmacao.setContentText(pessoa.getNome() + " - CPF: " + pessoa.getCpf());

                                    if (confirmacao.showAndWait().get() == ButtonType.OK) {
                                        try {
                                            if (pessoaDAO.excluir(pessoa.getIdPessoa())) {
                                                carregarDados();
                                                mostrarAlerta("Pessoa excluída com sucesso!");
                                            } else {
                                                mostrarAlerta("Erro ao excluir pessoa!");
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
