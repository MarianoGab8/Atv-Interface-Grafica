import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/** Camada de persistencia: grava e consulta produtos no banco H2. */
public class ProdutoDAO {
    private final ConnectionSource conexao;
    private final Dao<Produto, Integer> dao;

    public ProdutoDAO() throws SQLException {
        conexao = new JdbcConnectionSource("jdbc:h2:./loja");
        TableUtils.createTableIfNotExists(conexao, Produto.class);
        dao = DaoManager.createDao(conexao, Produto.class);
    }

    public void salvar(Produto produto) throws SQLException {
        dao.create(produto);
    }

    public List<Produto> listarTodos() throws SQLException {
        return dao.queryForAll();
    }

    public void fechar() throws Exception, IOException {
        conexao.close();
    }
}
