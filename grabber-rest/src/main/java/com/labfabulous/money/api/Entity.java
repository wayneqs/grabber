package com.labfabulous.money.api;

import com.labfabulous.money.Identifier;
import lombok.Data;

import java.util.List;

@Data
public class Entity {
    /*
    {
        "name": "name of entity",
        "probability": 0.98,
        "identifiers": [
            { }
        ]
     */
    private final String name;
    private final double probability;
    private final List<Identifier> identifiers;
}
