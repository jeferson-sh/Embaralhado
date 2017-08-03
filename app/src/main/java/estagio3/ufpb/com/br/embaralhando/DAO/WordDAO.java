package estagio3.ufpb.com.br.embaralhando.DAO;

import java.sql.SQLException;

import estagio3.ufpb.com.br.embaralhando.Word;

/**
 * Created by Jeferson on 03/08/2017.
 */

public class WordDAO extends GenericDAO {
    protected WordDAO() throws SQLException {
        super(Word.class);
    }
}
