/**/// DO NOT MODIFY THIS FILE MANUALLY
/**/// This class was automatically generated by "GeneratePatternClasses.java",
/**/// modify the "unique_patterns.json" and re-generate instead.

package org.schabi.newpipe.extractor.timeago.patterns;

import org.schabi.newpipe.extractor.timeago.PatternsHolder;

public class no extends PatternsHolder {
    private static final String WORD_SEPARATOR = " ";
    private static final String[]
            SECONDS  /**/ = {"sekund", "sekunder"},
            MINUTES  /**/ = {"minutt", "minutter"},
            HOURS    /**/ = {"time", "timer"},
            DAYS     /**/ = {"dag", "dager"},
            WEEKS    /**/ = {"uke", "uker"},
            MONTHS   /**/ = {"md."},
            YEARS    /**/ = {"år"};

    private static final no INSTANCE = new no();

    public static no getInstance() {
        return INSTANCE;
    }

    private no() {
        super(WORD_SEPARATOR, SECONDS, MINUTES, HOURS, DAYS, WEEKS, MONTHS, YEARS);
    }
}