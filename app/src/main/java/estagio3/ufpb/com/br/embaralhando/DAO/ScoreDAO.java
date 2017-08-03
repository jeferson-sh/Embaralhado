package estagio3.ufpb.com.br.embaralhando.DAO;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import estagio3.ufpb.com.br.embaralhando.Score;

/**
 * Created by Jeferson on 03/08/2017.
 */

public class ScoreDAO extends BaseDaoImpl<Score,Integer> {
    protected ScoreDAO(ConnectionSource connectionSource) throws SQLException {
        super(Score.class);
        setConnectionSource(connectionSource);
        initialize();
    }
    @Override
    public QueryBuilder<Score, Integer> queryBuilder() {
        return super.queryBuilder();
    }

    @Override
    public List<Score> query(PreparedQuery<Score> preparedQuery) throws SQLException {
        return super.query(preparedQuery);
    }
}
