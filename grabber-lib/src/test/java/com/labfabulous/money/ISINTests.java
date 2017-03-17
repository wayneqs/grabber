package com.labfabulous.money;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ISINTests {

    @Test
    public void grabsLowerCase() {
        String text = "us0378331005";
        List<Identifier> identifiers = new ISIN().grab(text);
        assertThat(identifiers).hasSize(1);
    }

    @Test
    public void grabsUpperCase() {
        String text = "US0378331005";
        List<Identifier> identifiers = new ISIN().grab(text);
        assertThat(identifiers).hasSize(1);
    }

    @Test
    public void grabsDespiteWordSeparators() {
        assertThat(new ISIN().grab("ISIN:US0378331005")).hasSize(1);
        assertThat(new ISIN().grab("ISIN: US0378331005")).hasSize(1);
        assertThat(new ISIN().grab(": US0378331005")).hasSize(1);
        assertThat(new ISIN().grab(":US0378331005")).hasSize(1);
        assertThat(new ISIN().grab("ISIN-US0378331005")).hasSize(1);
        assertThat(new ISIN().grab("ISIN- US0378331005")).hasSize(1);
        assertThat(new ISIN().grab("- US0378331005")).hasSize(1);
        assertThat(new ISIN().grab("-US0378331005")).hasSize(1);
    }

    @Test
    public void isCaseInsensitive() {
        String text = "US0378331005 us0378331005";
        List<Identifier> identifiers = new ISIN().grab(text);
        assertThat(identifiers).hasSize(1);
    }

    @Test
    public void grabsMultiple() {
        String text = "us0378331005 AU0000XVGZA3";
        List<Identifier> identifiers = new ISIN().grab(text);
        assertThat(identifiers).hasSize(2);
    }

    @Test
    public void filtersOutWrongUns() {
        String text = "B000009 UK0338321005 a000009 AU0000XVGZA3 4didud98dii9";
        List<Identifier> identifiers = new ISIN().grab(text);
        assertThat(identifiers).hasSize(1);
    }
}
