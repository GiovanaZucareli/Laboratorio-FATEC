package Controller;

import Classes.Ciclista;
import Classes.Gato;
import DAO.CiclistaDAO;
import DAO.GatoDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GatoController {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField idadeField;

    @FXML
    private TextField racaField;

    @FXML
    private TextField corField;

    // Ação para o botão salvar
    @FXML
    private void salvarGato() {
        try {
            String nome = nomeField.getText();
            int idade = Integer.parseInt(idadeField.getText());
            String raca = racaField.getText();
            String cor = corField.getText();

            Gato gato = new Gato(1, nome, idade, raca, cor);

            GatoDAO dao = new GatoDAO();
            dao.criar(gato); //salva no bd

            mostrarAlerta("Gato salvo com sucesso: \n" + gato.getNome());

            limparCampos();

        }catch (NumberFormatException e) {
            mostrarAlerta("Erro: idade deve ser um número.");
        }
    }

    // Ação para o botao cancelar
    @FXML
    private void limparCampos() {
        nomeField.clear();
        idadeField.clear();
        racaField.clear();
        corField.clear();
    }

    // Exibir alertas
    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }


}
