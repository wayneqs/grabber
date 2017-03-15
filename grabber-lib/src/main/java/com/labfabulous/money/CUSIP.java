package com.labfabulous.money;

import java.util.Optional;
import java.util.regex.Pattern;

public final class CUSIP extends Grabber {

    private static Pattern pattern;
    private final static int ID_MAX_LENGTH = 9;

    static {
        pattern = Pattern.compile("\\b[0-9[A-Z[*@#]]]{8}[0-9]\\b", Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected Optional<Identifier> create(String id) {
        int sum = 0;
        for(int i = 0; i < ID_MAX_LENGTH - 1; i++) {
            int digit = convertToDigit(id.charAt(i));
            if( i % 2 != 0 ) { // this is even digits with zero based array.
                digit = digit * 2;
            }
            if( digit > 9 ) { // digit is something like 14 therefore need to 1 + 4
                digit = digit / 10 + digit % 10;
            }
            sum += digit;
        }
        int check = Character.digit(id.charAt(ID_MAX_LENGTH-1), 10);
        if ((10 - sum % 10) % 10 == check) {
            return Optional.of(new Identifier(id, "CUSIP"));
        }
        return Optional.empty();
    }

    @Override
    protected Pattern getPattern() {
        return pattern;
    }

    private int convertToDigit(char character) {
        switch (character) {
            case '*':
                return 36;
            case '@':
                return 37;
            case '#':
                return 38;
            default:
                return Character.digit(character, 36);
        }
    }
}
