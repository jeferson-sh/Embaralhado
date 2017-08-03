package estagio3.ufpb.com.br.embaralhando.DAO;

import java.sql.SQLException;

import estagio3.ufpb.com.br.embaralhando.Categorie;

/**
 * Created by Jeferson on 03/08/2017.
 */

public class CategorieDAO extends GenericDAO {
    protected CategorieDAO() throws SQLException {
        super(Categorie.class);
    }
}
