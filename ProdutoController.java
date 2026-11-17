import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/** Controla o cadastro e a atualizacao da tabela. */
public class ProdutoController {
    private final ProdutoDAO dao;
    private final TextField campoNome;
    private final TextField campoPreco;
    private final TextField campoQuantidade;
    private final TableView<Produto> tabela;
    private final Label mensagem;
    private final ObservableList<Produto> produtos;

    public ProdutoController(TextField campoNome,
                             TextField campoPreco,
                             TextField campoQuantidade,
                             TableView<Produto> tabela,
                             Label mensagem) throws SQLException {
        this.campoNome = campoNome;
        this.campoPreco = campoPreco;
        this.campoQuantidade = campoQuantidade;
        this.tabela = tabela;
        this.mensagem = mensagem;

        dao = new ProdutoDAO();
        produtos = FXCollections.observableArrayList();
        tabela.setItems(produtos);
        carregarTabela();
    }

    public void cadastrar() {
        String nome = campoNome.getText().trim();
        String textoPreco = campoPreco.getText().trim().replace(',', '.');
        String textoQuantidade = campoQuantidade.getText().trim();

        if (nome.isEmpty() || textoPreco.isEmpty() || textoQuantidade.isEmpty()) {
            mensagem.setText("Preencha os tres campos.");
            return;
        }

        try {
            double preco = Double.parseDouble(textoPreco);
            int quantidade = Integer.parseInt(textoQuantidade);

            if (preco < 0 || quantidade < 0) {
                mensagem.setText("Preco e quantidade nao podem ser negativos.");
                return;
            }

            dao.salvar(new Produto(nome, preco, quantidade));
            carregarTabela();
            campoNome.clear();
            campoPreco.clear();
            campoQuantidade.clear();
            mensagem.setText("Produto cadastrado com sucesso.");
        } catch (NumberFormatException erro) {
            mensagem.setText("Preco ou quantidade invalido.");
        } catch (SQLException erro) {
            mensagem.setText("Erro ao salvar: " + erro.getMessage());
        }
    }

    private void carregarTabela() {
        try {
            produtos.setAll(dao.listarTodos());
        } catch (SQLException erro) {
            mensagem.setText("Erro ao carregar produtos: " + erro.getMessage());
        }
    }

    public void fechar() throws Exception {
        try {
            dao.fechar();
        } catch (IOException erro) {
            mensagem.setText("Erro ao fechar o banco: " + erro.getMessage());
        }
    }
}
