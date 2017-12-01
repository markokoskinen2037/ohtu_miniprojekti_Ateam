package tekstikayttis;

import dao.Tietokanta;
import java.util.List;
import logiikka.Logiikka;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import tietokantaobjektit.Tag;

/**
 *
 * @author petriheinonen
 */
public class TekstikayttisTest {

    private Tietokanta db;
    private Logiikka logiikka;
    
//    @BeforeClass
//    public void initTietokannanData() {
//        Tietokanta tietokanta = new Tietokanta("jdbc:sqlite:TestausTietokanta.db");
//        
//        // TODO: Tyhjennä tietokanta ja populoi se testidatalla ennen testejä.
//        tietokanta.poistaKaikki();
//        tietokanta.lisaaTestiData();
//    }

    @Before
    public void initTietokanta() {
        this.db = new Tietokanta("jdbc:sqlite:TestausTietokanta.db");
        this.logiikka = new Logiikka(db);
    }

    @Test
    public void tulostaToiminnallisuudetToimii() {
        IOStub io = new IOStub("1");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.tulostaToiminnallisuudet();

        assertEquals("Valitse toiminnallisuus:", io.outputs.get(4));
    }

    @Test
    public void kirjanLisayksenOtsikkoToimii() {
        IOStub io = new IOStub("1", "Marxin Pääoma", "paras", "ISBN 978-0-596-52068-7", "Marx", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.vinkinLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "kirja: Marxin Pääoma"));
    }

    @Test
    public void kirjanLisayksenKuvausToimii() {
        IOStub io = new IOStub("Marxin Pääoma", "paras", "ISBN 978-0-596-52068-7", "Marx", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kirjanLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kuvaus: paras"));
    }

    @Test
    public void kirjanLisayksenIsbnToimii() {
        IOStub io = new IOStub("Marxin Pääoma", "paras", "ISBN 978-0-596-52068-7", "Marx", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kirjanLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "ISBN 978-0-596-52068-7"));
    }

    @Test
    public void kirjanLisayksenKirjailijaToimii() {
        IOStub io = new IOStub("Marxin Pääoma", "paras", "ISBN 978-0-596-52068-7", "Marx", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kirjanLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kirjailija: Marx"));
    }

    @Test
    public void kirjanLisayksenTagitToimii() {
        String tagit = "tag, test";
        IOStub io = new IOStub("Marxin Pääoma", "paras", "ISBN 978-0-596-52068-7", "Marx", tagit);
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kirjanLisays();
        List<Tag> tagiLista = kayttis.tagienErottaminen(tagit);
        for (Tag tag : tagiLista) {
            assertTrue(arrayContainsSubstring(io.getOutputs(), tag.getTag()));
        }
    }
    
    @Test
    public void videonLisayksenOtsikkoToimii() {
        IOStub io = new IOStub("2", "Videon otsikko", "videon kuvaus", "videon tekijä", "videon url", "2017-12-01", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.vinkinLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "video: Videon otsikko"));
    }

    @Test
    public void videonLisayksenKuvausToimii() {
        IOStub io = new IOStub("Videon otsikko", "videon kuvaus", "videon tekijä", "videon url", "2017-12-01", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.videonLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kuvaus: videon kuvaus"));
    }
    
    @Test
    public void videonLisayksenTekijaToimii() {
        IOStub io = new IOStub("Videon otsikko", "videon kuvaus", "videon tekijä", "videon url", "2017-12-01", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.videonLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Tekijä: videon tekijä"));
    }
    
    @Test
    public void videonLisayksenUrlToimii() {
        IOStub io = new IOStub("Videon otsikko", "videon kuvaus", "videon tekijä", "videon url", "2017-12-01", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.videonLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Url: videon url"));
    }
    
    @Test
    public void videonLisayksenPvmToimii() {
        IOStub io = new IOStub("Videon otsikko", "videon kuvaus", "videon tekijä", "videon url", "2017-12-01", "tag");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.videonLisays();
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Pvm: 2017-12-01"));
    }


    @Test
    public void videonLisayksenTagitToimii() {
        String tagit = "tagi1, tagi2";
        IOStub io = new IOStub("Videon otsikko", "videon kuvaus", "videon tekijä", "videon url", "2017-12-01", tagit);
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.videonLisays();
        List<Tag> tagiLista = kayttis.tagienErottaminen(tagit);
        for (Tag tag : tagiLista) {
            assertTrue(arrayContainsSubstring(io.getOutputs(), tag.getTag()));
        }
    }

    @Test
    public void kayttoliittymaToimii() {
        IOStub io = new IOStub("0");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.kayttoliittyma();

        assertEquals("Valitse toiminnallisuus:", io.outputs.get(4));
    }

    @Test
    public void vinkkienSelausToimii() {
        IOStub io = new IOStub("2");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.vinkkienTulostus();

        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kaikki vinkit:"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "kirja: testikirja\n  Kuvaus: testikuvaus"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "kirja: Marxin Pääoma\n  Kuvaus: paras"));
    }
    
    @Test
    public void vinkkienJaTietojenTulostusToimii() {
        IOStub io = new IOStub("2");
        Tekstikayttis kayttis = new Tekstikayttis(logiikka, io);
        kayttis.vinkkienJaTietojenTulostus();

        assertTrue(arrayContainsSubstring(io.getOutputs(), "Kaikki vinkit:"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "kirja: Marxin Pääoma\n  Kuvaus: paras"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "ISBN: ISBN 978-0-596-52068-7\n  Kirjailija: Marx"));
        assertTrue(arrayContainsSubstring(io.getOutputs(), "Tagit: tag test"));
    }
        
    private boolean arrayContainsSubstring(List<String> list, String substr) {
        for (String str : list) {
            if (str.contains(substr)) {
                return true;
            }
        }
        return false;
    }
}
