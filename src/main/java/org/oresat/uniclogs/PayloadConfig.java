package org.oresat.uniclogs;

import org.yamcs.Spec;

public class PayloadConfig extends Spec {
    PayloadConfig() {
        addOption("secret", OptionType.STRING);
        addOption("sequenceNumber", OptionType.INTEGER);
    }
}
