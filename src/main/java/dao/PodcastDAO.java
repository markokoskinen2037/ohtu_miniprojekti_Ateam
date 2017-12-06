package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import tietokantaobjektit.Podcast;
import tietokantaobjektit.Vinkki;

public class PodcastDAO extends TyyppiDAO {

    public PodcastDAO(Tietokanta db) {
        this.db = db;
    }

    @Override
    protected String hakuSql() {
        return "SELECT * FROM Vinkki"
                + " JOIN Podcast ON vinkki.vinkki_id = podcast.vinkki"
                + " LEFT JOIN (SELECT * FROM Tag, VinkkiTag"
                + " WHERE Tag.tag_id = VinkkiTag.tag) AS R"
                + " ON Vinkki.vinkki_id = R.vinkki"
                + " WHERE vinkki.vinkki_id = ?";
    }

    @Override
    protected String lisaysSql() {
        return "INSERT INTO Podcast (vinkki, tekija, nimi, url, pvm) values (?, ?, ?, ?, ?)";
    }

    @Override
    protected Vinkki luoVinkkiResultista(ResultSet result) throws SQLException {
        return new Podcast(result.getString("otsikko"),
                result.getString("kuvaus"),
                result.getString("tekija"),
                result.getString("nimi"),
                result.getString("url"),
                result.getString("pvm"));
    }

    @Override
    protected void asetaVinkinTiedotParametreiksi(PreparedStatement st, Vinkki lisattava) throws SQLException {
        Podcast podcast = (Podcast) lisattava;
        st.setString(2, podcast.getTekija());
        st.setString(3, podcast.getNimi());
        st.setString(4, podcast.getUrl());
        st.setString(5, podcast.getPvm());
    }
}
