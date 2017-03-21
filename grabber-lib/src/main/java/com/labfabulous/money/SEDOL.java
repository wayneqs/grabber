package com.labfabulous.money;

import java.util.Optional;
import java.util.regex.Pattern;

public final class SEDOL extends Grabber {

    private static Pattern pattern;

    static {
        pattern = Pattern.compile("\\b[0-9[A-Z&&[^AEIOU]]]{6}[0-9]\\b", Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected Optional<Identifier> identifierFromText(String id) {
        int[] weights = {1, 3, 1, 7, 3, 9, 1};
        int sum = 0;
        for(int i = 0; i < 6; i++) {
            sum += (Character.digit(id.charAt(i), 36) * weights[i]);
        }
        int check = (10 - sum % 10) % 10;
        if (check == Character.digit(id.charAt(6), 36)) {
            return Optional.of(new Identifier(id, "SEDOL"));
        }
        return Optional.empty();
    }

    @Override
    protected Pattern getPattern() {
        return pattern;
    }

}
