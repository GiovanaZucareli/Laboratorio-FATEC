package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class RoboController {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField modeloField;

    @FXML
    private TextField anoFabricacaoField;

    @FXML
    private TableView<Robo> tabelaRobos;

    @FXML
    private TableColumn<Robo, Integer> colId;

    @FXML
    private TableColumn<Robo, String> colNome;

    @FXML
    private TableColumn<Robo, String> colModelo;

    @FXML
    private TableColumn<Robo, Integer> colAnoFabricacao;

    @FXML
    private TableColumn<Robo, Void> colEditar;

    @FXML
    private TableColumn<Robo, Void> colExcluir;

    private final ObservableList<Robo> listaRobos = FXCollections.observableArrayList();

    private int idCounter = 1;  // Para gerar IDs simples

    @FXML
    public void initialize() {
        // Configura as colunas para os campos do Robo
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colAnoFabricacao.setCellValueFactory(new PropertyValueFactory<>("anoFabricacao"));

        // Configura as colunas com botões
        adicionarBotoesEditar();
        adicionarBotoesExcluir();

        // Seta a lista na tabela
        tabelaRobos.setItems(listaRobos);
    }

    @FXML
    private void salvarRobo(ActionEvent event) {
        String nome = nomeField.getText().trim();
        String modelo = modeloField.getText().trim();
        String anoStr = anoFabricacaoField.getText().trim();

        if (nome.isEmpty() || modelo.isEmpty() || anoStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Preencha todos os campos!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        int ano;
        try {
            ano = Integer.parseInt(anoStr);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Ano de fabricação deve ser um número válido!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Robo novoRobo = new Robo(idCounter++, nome, modelo, ano);
        listaRobos.add(novoRobo);
        limparCampos(null);
    }

    @FXML
    private void limparCampos(ActionEvent event) {
        nomeField.clear();
        modeloField.clear();
        anoFabricacaoField.clear();
    }

    private void adicionarBotoesEditar() {
        Callback<TableColumn<Robo, Void>, TableCell<Robo, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");

            {
                btnEditar.setOnAction(event -> {
                    Robo robo = getTableView().getItems().get(getIndex());
                    nomeField.setText(robo.getNome());
                    modeloField.setText(robo.getModelo());
                    anoFabricacaoField.setText(String.valueOf(robo.getAnoFabricacao()));
                    listaRobos.remove(robo);
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

        colEditar.setCellFactory(cellFactory);
    }

    private void adicionarBotoesExcluir() {
        Callback<TableColumn<Robo, Void>, TableCell<Robo, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnExcluir = new Button("Excluir");

            {
                btnExcluir.setOnAction(event -> {
                    Robo robo = getTableView().getItems().get(getIndex());
                    listaRobos.remove(robo);
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

        colExcluir.setCellFactory(cellFactory);
    }

    // Classe interna para representar o Robo
    public static class Robo {
        private final int id;
        private final String nome;
        private final String modelo;
        private final int anoFabricacao;

        public Robo(int id, String nome, String modelo, int anoFabricacao) {
            this.id = id;
            this.nome = nome;
            this.modelo = modelo;
            this.anoFabricacao = anoFabricacao;
        }

        public int getId() {
            return id;
        }

        public String getNome() {
            return nome;
        }

        public String getModelo() {
            return modelo;
        }

        public int getAnoFabricacao() {
            return anoFabricacao;
        }
    }

    // Métodos de exemplo para os menus (você pode implementar depois)
    @FXML
    private void abrirCRUD1() { System.out.println("Abrir CRUD Cachorro"); }

    @FXML
    private void abrirCRUD2() { System.out.println("Abrir CRUD Carro"); }

    @FXML
    private void abrirCRUD3() { System.out.println("Abrir CRUD Ciclista"); }

    @FXML
    private void abrirCRUD4() { System.out.println("Abrir CRUD Criança"); }

    @FXML
    private void abrirCRUD5() { System.out.println("Abrir CRUD Gato"); }

    @FXML
    private void abrirCRUD6() { System.out.println("Abrir CRUD Jogador"); }

    @FXML
    private void abrirCRUD7() { System.out.println("Abrir CRUD Pássaro"); }

    @FXML
    private void abrirCRUD8() { System.out.println("Abrir CRUD Pessoa"); }

    @FXML
    private void abrirCRUD9() { System.out.println("Abrir CRUD Pintores"); }

    @FXML
    private void abrirCRUD10() { System.out.println("Abrir CRUD Robô"); }
}
