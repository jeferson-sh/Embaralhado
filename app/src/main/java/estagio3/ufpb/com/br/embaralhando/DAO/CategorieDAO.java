package estagio3.ufpb.com.br.embaralhando.DAO;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import estagio3.ufpb.com.br.embaralhando.Categorie;

/**
 * Created by Jeferson on 03/08/2017.
 */

public class CategorieDAO extends GenericDAO<Categorie> {
    protected CategorieDAO(ConnectionSource connectionSource) throws SQLException {
        super(Categorie.class,connectionSource);
    }
}
