package Controller;

import Classes.Ciclista;
import DAO.CiclistaDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CiclistaController {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField bicicletaField;

    @FXML
    private TextField idadeField;

    // Ação para o botão salvar
    @FXML
    private void salvarCiclista() {
        try {
            String nome = nomeField.getText();
            String bicicleta = bicicletaField.getText();
            int idade = Integer.parseInt(idadeField.getText());

            Ciclista ciclista = new Ciclista(1, nome, bicicleta, idade);

            CiclistaDAO dao = new CiclistaDAO();
            dao.criar(ciclista); //salva no bd

            mostrarAlerta("Ciclista salvo com sucesso: \n" + ciclista.getNome());

            limparCampos();

        }catch (NumberFormatException e) {
            mostrarAlerta("Erro: idade deve ser um número.");
        }
    }

    // Ação para o botao cancelar
    @FXML
    private void limparCampos() {
        nomeField.clear();
        bicicletaField.clear();
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