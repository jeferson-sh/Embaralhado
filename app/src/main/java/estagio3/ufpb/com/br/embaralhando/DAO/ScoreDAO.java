package estagio3.ufpb.com.br.embaralhando.DAO;

import java.sql.SQLException;

import estagio3.ufpb.com.br.embaralhando.Score;

/**
 * Created by Jeferson on 03/08/2017.
 */

public class ScoreDAO extends GenericDAO {
    protected ScoreDAO() throws SQLException {
        super(Score.class);
    }
}
