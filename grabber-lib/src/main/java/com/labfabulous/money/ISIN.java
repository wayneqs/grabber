package com.labfabulous.money;

import java.util.Optional;
import java.util.regex.Pattern;

public final class ISIN extends Grabber {

    private static Pattern pattern;

    static {
        pattern = Pattern.compile("\\b[A-Z]{2}[0-9[A-Z]]{9}[0-9]\\b", Pattern.CASE_INSENSITIVE);
    }

    @Override
    protected Optional<Identifier> identifierFromText(String id) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < id.length(); i++) {
            builder.append(Character.digit(id.charAt(i), 36));
        }
        if (luhnTest(builder.toString())) {
            return Optional.of(new Identifier(id, "ISIN"));
        }
        return Optional.empty();
    }

    @Override
    protected Pattern getPattern() {
        return pattern;
    }

    private boolean luhnTest(String number) {
        int s1 = 0, s2 = 0;
        String reverse = new StringBuilder(number).reverse().toString();
        for(int i = 0; i < reverse.length(); i++) {
            int digit = Character.digit(reverse.charAt(i), 10);
            if(i % 2 == 0) {//this is for odd digits, they are 1-indexed in the algorithm
                s1 += digit;
            } else { //add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
                s2 += 2 * digit;
                if(digit >= 5){
                    s2 -= 9;
                }
            }
        }
        return (s1 + s2) % 10 == 0;
    }

}
