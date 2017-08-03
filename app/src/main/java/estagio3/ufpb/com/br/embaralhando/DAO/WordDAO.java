package estagio3.ufpb.com.br.embaralhando.DAO;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import estagio3.ufpb.com.br.embaralhando.Word;

/**
 * Created by Jeferson on 03/08/2017.
 */

public class WordDAO extends GenericDAO<Word> {
    protected WordDAO(ConnectionSource connectionSource) throws SQLException {
        super(Word.class, connectionSource);
    }
}
