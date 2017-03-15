package com.labfabulous.money;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SEDOLTests {

    @Test
    public void grabsLowerCase() {
        String text = "b000009";
        List<Identifier> identifiers = new SEDOL().grab(text);
        assertThat(identifiers).hasSize(1);
    }

    @Test
    public void grabsUpperCase() {
        String text = "B000009";
        List<Identifier> identifiers = new SEDOL().grab(text);
        assertThat(identifiers).hasSize(1);
    }

    @Test
    public void grabsDespiteWordSeparators() {
        assertThat(new SEDOL().grab("com.labfabulous.money.SEDOL:B000009")).hasSize(1);
        assertThat(new SEDOL().grab("com.labfabulous.money.SEDOL: B000009")).hasSize(1);
        assertThat(new SEDOL().grab(": B000009")).hasSize(1);
        assertThat(new SEDOL().grab(":B000009")).hasSize(1);
        assertThat(new SEDOL().grab("com.labfabulous.money.SEDOL-B000009")).hasSize(1);
        assertThat(new SEDOL().grab("com.labfabulous.money.SEDOL- B000009")).hasSize(1);
        assertThat(new SEDOL().grab("- B000009")).hasSize(1);
        assertThat(new SEDOL().grab("-B000009")).hasSize(1);
    }

    @Test
    public void isCaseInsensitive() {
        String text = "B000009 b000009";
        List<Identifier> identifiers = new SEDOL().grab(text);
        assertThat(identifiers).hasSize(1);
    }

    @Test
    public void grabsMultiple() {
        String text = "B000009 B0er409 0263494 a000009";
        List<Identifier> identifiers = new SEDOL().grab(text);
        assertThat(identifiers).hasSize(2);
    }

    @Test
    public void filtersOutWrongUns() {
        String text = "B000009 B0er409 a000009 fofofof";
        List<Identifier> identifiers = new SEDOL().grab(text);
        assertThat(identifiers).hasSize(1);
    }
}
