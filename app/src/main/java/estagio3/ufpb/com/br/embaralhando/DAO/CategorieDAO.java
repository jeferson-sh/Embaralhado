package estagio3.ufpb.com.br.embaralhando.DAO;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import estagio3.ufpb.com.br.embaralhando.Categorie;

/**
 * Created by Jeferson on 03/08/2017.
 */

public class CategorieDAO extends BaseDaoImpl<Categorie,Integer> {
    protected CategorieDAO(ConnectionSource connectionSource) throws SQLException {
        super(Categorie.class);
        setConnectionSource(connectionSource);
        initialize();
    }
    @Override
    public QueryBuilder<Categorie, Integer> queryBuilder() {
        return super.queryBuilder();
    }

    @Override
    public List<Categorie> query(PreparedQuery<Categorie> preparedQuery) throws SQLException {
        return super.query(preparedQuery);
    }
}
