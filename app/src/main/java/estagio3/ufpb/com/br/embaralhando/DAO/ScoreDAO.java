package estagio3.ufpb.com.br.embaralhando.DAO;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import estagio3.ufpb.com.br.embaralhando.Score;

/**
 * Created by Jeferson on 03/08/2017.
 */

public class ScoreDAO extends GenericDAO<Score> {
    protected ScoreDAO(ConnectionSource connectionSource) throws SQLException {
        super(Score.class,connectionSource);
    }
}
