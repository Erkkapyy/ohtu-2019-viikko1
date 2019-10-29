package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    Varasto negatiivinenVarasto;
    Varasto varastoAlkuSaldolla;
    Varasto varastoVäärälläAlkuSaldolla;
    Varasto vääräVarasto;
    Varasto overflowVarasto;
    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
        negatiivinenVarasto = new Varasto(-10);
        varastoAlkuSaldolla = new Varasto(20, 10);
        varastoVäärälläAlkuSaldolla = new Varasto(20, -1);
        vääräVarasto = new Varasto(-1, -1);
        overflowVarasto = new Varasto(10, 20);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void konstruktoriEiLuoNegatiivistaVarastoa() {
        assertEquals(0, negatiivinenVarasto.getTilavuus(), vertailuTarkkuus);
    }
   
    @Test
    public void konstruktoriLuoTyhjanVarastonAlkuSaldolla() {
        assertEquals(10, varastoAlkuSaldolla.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void konstruktoriHoitaaNegatiivisenSaldon() {
        assertEquals(0, varastoVäärälläAlkuSaldolla.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void konstruktoriLuoKapasiteetittomanVarastonNegatiivisellaArvolla() {
        assertEquals(0, vääräVarasto.getTilavuus(), vertailuTarkkuus);
    }
    
    @Test
    public void ylijäämäSaldoEiYlitäKapasiteettia() {
        assertEquals(10, overflowVarasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }
    
    
    @Test
    public void liikaLisäysEiYlitäMaksimia() {
        varasto.otaVarastosta(2);
        varasto.lisaaVarastoon(999);

        assertEquals(10, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void liikaPoistoEiPoistaEnempääKuinNykyinenTilavuus() {
        varasto.lisaaVarastoon(10);
        varasto.otaVarastosta(123);

        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void negatiivinenPoistoEiToimi() {
        varasto.lisaaVarastoon(10);
        varasto.otaVarastosta(-1);

        assertEquals(10, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void negatiivinenLisäysEiToimi() {
        varasto.lisaaVarastoon(5);
        varasto.lisaaVarastoon(-1);

        assertEquals(5, varasto.getSaldo(), vertailuTarkkuus);
    }
    
     @Test
    public void toStringToimii() {
        assertEquals("saldo = 10.0, vielä tilaa 10.0", varastoAlkuSaldolla.toString());
    }
}