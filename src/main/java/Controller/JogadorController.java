package Controller;

import Classes.Ciclista;
import Classes.Jogador;
import DAO.CiclistaDAO;
import DAO.JogadorDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class JogadorController {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField idadeField;

    @FXML
    private TextField esporteField;

    // Ação para o botão salvar
    @FXML
    private void salvarJogador() {
        try {
            String nome = nomeField.getText();
            int idade = Integer.parseInt(idadeField.getText());
            String esporte = esporteField.getText();

            Jogador jogador = new Jogador(1, nome, idade, esporte);

            JogadorDAO dao = new JogadorDAO();
            dao.criar(jogador); //salva no bd

            mostrarAlerta("Jogador salvo com sucesso: \n" + jogador.getNome());

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
        esporteField.clear();
    }

    // Exibir alertas
    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

}
