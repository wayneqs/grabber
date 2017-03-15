package com.labfabulous.money.service;

import com.labfabulous.money.api.Entity;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class IdentifierFinderServiceTests {

    @Test
    public void shouldExtractIsinCusipGroup() {
        String text = "the quick \nbrown US0378331005 fox jumped\n over 037833100 the lazy dog. quo est de corum.";
        List<Entity> entities = new IdentifierFinderService().findEntities(text);
        assertThat(entities).hasSize(1);
        assertThat(entities.get(0).getIdentifiers()).hasSize(2);
    }

    @Test
    public void shouldExtractIsinSedolGroup() {
        String text = "the quick \nbrown GB0002634946 fox jumped\n over 0263494 the lazy dog. quo est de corum.";
        List<Entity> entities = new IdentifierFinderService().findEntities(text);
        assertThat(entities).hasSize(1);
        assertThat(entities.get(0).getIdentifiers()).hasSize(2);
    }

    @Test
    public void shouldExtractIsinAndSedolAndCusip() {
        String text = "the B000009 quick \nbrown GB0002634946 fox jumped\n over 037833100 the lazy dog. quo est de corum.";
        List<Entity> entities = new IdentifierFinderService().findEntities(text);
        assertThat(entities).hasSize(3);
        assertThat(entities.get(0).getIdentifiers()).hasSize(1);
        assertThat(entities.get(1).getIdentifiers()).hasSize(1);
        assertThat(entities.get(2).getIdentifiers()).hasSize(1);
    }
}
