package Controller;

import Classes.Carro;
import DAO.CarroDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class CarroController {

    @FXML
    private TextField marcaField;

    @FXML
    private TextField corField;

    // Ação para o botão salvar
    @FXML
    private void salvarCarro() {
        try {
            String marca = marcaField.getText();
            String cor = corField.getText();

            Carro carro = new Carro(marca, cor, 1);

            CarroDAO dao = new CarroDAO();
            dao.criar(carro); //salva no bd

            mostrarAlerta("Carro salvo com sucesso: \n" + carro.getMarca());

            limparCampos();

        } catch (Exception e) {
            mostrarAlerta ("Erro ao criar carro." + e.getMessage());
        }
    }

    // Ação para o botão cancelar
    @FXML
    private void limparCampos() {
        marcaField.clear();
        corField.clear();
    }

    // Exibir alertas

    private void mostrarAlerta (String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }


}