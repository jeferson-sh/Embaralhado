package estagio3.ufpb.com.br.embaralhando.DAO;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import estagio3.ufpb.com.br.embaralhando.Word;

/**
 * Created by Jeferson on 03/08/2017.
 */

public class WordDAO extends BaseDaoImpl<Word,Integer> {
    protected WordDAO(ConnectionSource connectionSource) throws SQLException {
        super(Word.class);
        setConnectionSource(connectionSource);
        initialize();
    }
    @Override
    public QueryBuilder<Word, Integer> queryBuilder() {
        return super.queryBuilder();
    }

    @Override
    public List<Word> query(PreparedQuery<Word> preparedQuery) throws SQLException {
        return super.query(preparedQuery);
    }
}
