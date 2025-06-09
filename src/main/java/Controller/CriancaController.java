package Controller;
import Classes.Crianca;
import DAO.CriancaDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class CriancaController {

    @FXML
    private TextField nomeField;

    @FXML
    private TextField idadeField;

    // Ação para o botão salvar

    @FXML
    private void salvarCrianca() {
        try {
            String nome = nomeField.getText();
            int idade = Integer.parseInt(idadeField.getText());

            Crianca crianca = new Crianca(1, nome, idade);

            CriancaDAO dao = new CriancaDAO();
            dao.criar(crianca); //salva no bd

            mostrarAlerta("Crianca salva com sucesso: \n" + crianca.getNome());

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
    }

    // Exibir alertas
    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

}
