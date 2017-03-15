package com.labfabulous.money;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CUSIPTests {

    @Test
    public void grabsLowerCase() {
        String text = "17275r102";
        List<Identifier> identifiers = new CUSIP().grab(text);
        assertThat(identifiers).hasSize(1);
    }

    @Test
    public void grabsUpperCase() {
        String text = "17275R102";
        List<Identifier> identifiers = new CUSIP().grab(text);
        assertThat(identifiers).hasSize(1);
    }

    @Test
    public void grabsDespiteWordSeparators() {
        assertThat(new CUSIP().grab("CUSIP:17275R102")).hasSize(1);
        assertThat(new CUSIP().grab("CUSIP: 17275R102")).hasSize(1);
        assertThat(new CUSIP().grab(": 17275R102")).hasSize(1);
        assertThat(new CUSIP().grab(":17275R102")).hasSize(1);
        assertThat(new CUSIP().grab("CUSIP-17275R102")).hasSize(1);
        assertThat(new CUSIP().grab("CUSIP- 17275R102")).hasSize(1);
        assertThat(new CUSIP().grab("- 17275R102")).hasSize(1);
        assertThat(new CUSIP().grab("-17275R102")).hasSize(1);
    }

    @Test
    public void isCaseInsensitive() {
        String text = "17275R102 17275r102";
        List<Identifier> identifiers = new CUSIP().grab(text);
        assertThat(identifiers).hasSize(1);
    }

    @Test
    public void grabsMultiple() {
        String text = "037833100 392690QT3";
        List<Identifier> identifiers = new CUSIP().grab(text);
        assertThat(identifiers).hasSize(2);
    }

    @Test
    public void handlesAtSymbolInIdentifier() {
        String text = "833@33102";
        List<Identifier> identifiers = new CUSIP().grab(text);
        assertThat(identifiers).hasSize(1);
    }

    @Test
    public void handlesHashInIdentifier() {
        String text = "833#33100";
        List<Identifier> identifiers = new CUSIP().grab(text);
        assertThat(identifiers).hasSize(1);
    }

    @Test
    public void handlesStarInIdentifier() {
        String text = "833*33104";
        List<Identifier> identifiers = new CUSIP().grab(text);
        assertThat(identifiers).hasSize(1);
    }

    @Test
    public void filtersOutWrongUns() {
        String text = "B000009 17275R102 a000009 AU0000XVGZA3 4didud98dii9";
        List<Identifier> identifiers = new CUSIP().grab(text);
        assertThat(identifiers).hasSize(1);
    }

}
