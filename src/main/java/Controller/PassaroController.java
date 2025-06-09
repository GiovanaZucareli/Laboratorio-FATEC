package Controller;

import Classes.Ciclista;
import Classes.Passaro;
import DAO.CiclistaDAO;
import DAO.PassaroDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PassaroController {

    @FXML
    private TextField especieField;

    @FXML
    private TextField corField;

    // Ação para o botão salvar
    @FXML
    private void salvarPassaro() {
        try {
            String especie = especieField.getText();
            String cor = corField.getText();

            Passaro passaro = new Passaro(1, especie, cor);

            PassaroDAO dao = new PassaroDAO();
            dao.criar(passaro); //salva no bd

            mostrarAlerta("Passaro salvo com sucesso: \n" + passaro.getespecie());

            limparCampos();

        } catch (Exception e) {
            mostrarAlerta ("Erro ao criar passaro." + e.getMessage());
        }
    }

    // Ação para o botao cancelar
    @FXML
    private void limparCampos() {
        especieField.clear();
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
