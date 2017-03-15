package com.labfabulous.money.service;

import com.labfabulous.money.CUSIP;
import com.labfabulous.money.ISIN;
import com.labfabulous.money.Identifier;
import com.labfabulous.money.SEDOL;
import com.labfabulous.money.api.Entity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IdentifierFinderService {

    public List<Entity> findEntities(String text) {
        List<Identifier> sedols = new SEDOL().grab(text);
        List<Identifier> cusips = new CUSIP().grab(text);
        List<Identifier> isins = new ISIN().grab(text);
        return findEntities(sedols, cusips, isins);
    }



    private List<Entity> findEntities(List<Identifier> sedols, List<Identifier> cusips, List<Identifier> isins) {
        List<Entity> entities = new ArrayList<>();
        Set<Identifier> unmatchedNsins = new HashSet<>();
        for( Identifier isin : isins ) {
            List<Identifier> groupedIds = new ArrayList<>();
            groupedIds.add(isin);
            partitionIdentifiers(isin, sedols, unmatchedNsins, groupedIds);
            partitionIdentifiers(isin, cusips, unmatchedNsins, groupedIds);
            entities.add(new Entity("", 1, groupedIds));
        }
        for(Identifier id : unmatchedNsins) {
            entities.add(new Entity("", 0.98, Collections.singletonList(id)));
        }
        return entities;
    }

    private void partitionIdentifiers(Identifier isin,
                                      List<Identifier> nsins,
                                      Set<Identifier> misses,
                                      List<Identifier> hits) {
        for( Identifier nsin : nsins ) {
            if( isin.getValue().contains(nsin.getValue()) ) {
                hits.add(nsin);
            } else {
                misses.add(nsin);
            }
        }
    }
}
