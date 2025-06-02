package Controller;

import Classes.Cachorro;
import DAO.CachorroDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CachorroController {


    @FXML
    private TextField nomeField;

    @FXML
    private TextField racaField;

    @FXML
    private TextField corField;

    @FXML
    private TextField idadeField;

    // Ação para o botão "Salvar"
    @FXML
    private void salvarCachorro() {
        try {
            String nome = nomeField.getText();
            String raca = racaField.getText();
            String cor = corField.getText();
            int idade = Integer.parseInt(idadeField.getText());

            Cachorro cachorro = new Cachorro(raca, cor, nome, 1, idade);

            CachorroDAO dao = new CachorroDAO();
            dao.criar(cachorro); //salva no bd

            mostrarAlerta("Cachorro salvo com sucesso:\n" + cachorro.getNome());

            limparCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro: idade deve ser um número.");
        }
    }

    // Ação para o botão "Cancelar"
    @FXML
    private void limparCampos() {
        nomeField.clear();
        racaField.clear();
        corField.clear();
        idadeField.clear();
    }

    // Exibir alertas
    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

}
