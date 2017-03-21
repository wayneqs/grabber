package com.labfabulous.money;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class Grabber {

    protected abstract Optional<Identifier> identifierFromText(String id);
    protected abstract Pattern getPattern();

    public List<Identifier> grab(String text) {
        return extract(text).parallelStream()
                .map(possibleIdText -> identifierFromText(possibleIdText))
                .filter(id -> id.isPresent())
                .map(id -> id.get())
                .collect(Collectors.toList());
    }

    private Collection<String> extract(String text) {
        Matcher matches = getPattern().matcher(text);
        Set<String> ids = new HashSet<>();
        while( matches.find() ) {
            ids.add(matches.group().toUpperCase());
        }
        return ids;
    }
}
