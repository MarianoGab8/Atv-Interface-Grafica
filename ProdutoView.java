import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** Tela JavaFX. O BlueJ inicia esta classe como aplicacao JavaFX. */
public class ProdutoView extends Application {
    private ProdutoController controller;

    @Override
    public void start(Stage janela) {
        Label titulo = new Label("Cadastro de produtos");

        TextField campoNome = new TextField();
        campoNome.setPromptText("Nome do produto");

        TextField campoPreco = new TextField();
        campoPreco.setPromptText("Ex.: 12,50");

        TextField campoQuantidade = new TextField();
        campoQuantidade.setPromptText("Ex.: 10");

        GridPane formulario = new GridPane();
        formulario.setHgap(10);
        formulario.setVgap(8);
        formulario.add(new Label("Nome:"), 0, 0);
        formulario.add(campoNome, 1, 0);
        formulario.add(new Label("Preco:"), 0, 1);
        formulario.add(campoPreco, 1, 1);
        formulario.add(new Label("Quantidade:"), 0, 2);
        formulario.add(campoQuantidade, 1, 2);

        TableView<Produto> tabela = new TableView<>();
        TableColumn<Produto, Integer> colunaId = new TableColumn<>("ID");
        TableColumn<Produto, String> colunaNome = new TableColumn<>("Nome");
        TableColumn<Produto, Double> colunaPreco = new TableColumn<>("Preco");
        TableColumn<Produto, Integer> colunaQuantidade = new TableColumn<>("Quantidade");

        colunaId.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<Produto, String>("nome"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<Produto, Double>("preco"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<Produto, Integer>("quantidade"));

        colunaId.setPrefWidth(55);
        colunaNome.setPrefWidth(230);
        colunaPreco.setPrefWidth(110);
        colunaQuantidade.setPrefWidth(120);
        tabela.getColumns().add(colunaId);
        tabela.getColumns().add(colunaNome);
        tabela.getColumns().add(colunaPreco);
        tabela.getColumns().add(colunaQuantidade);
        tabela.setPrefHeight(280);

        Label mensagem = new Label("Pronto para cadastrar.");
        Button botaoCadastrar = new Button("Cadastrar produto");

        try {
            controller = new ProdutoController(campoNome, campoPreco,
                    campoQuantidade, tabela, mensagem);
            botaoCadastrar.setOnAction(evento -> controller.cadastrar());
        } catch (SQLException erro) {
            mensagem.setText("Nao foi possivel abrir o banco: " + erro.getMessage());
            botaoCadastrar.setDisable(true);
        }

        VBox raiz = new VBox(12, titulo, formulario, botaoCadastrar, mensagem, tabela);
        raiz.setPadding(new Insets(15));

        janela.setTitle("Produtos");
        janela.setScene(new Scene(raiz, 560, 470));
        janela.show();
    }

    @Override
    public void stop() throws Exception {
        if (controller != null) {
            controller.fechar();
        }
    }
}
