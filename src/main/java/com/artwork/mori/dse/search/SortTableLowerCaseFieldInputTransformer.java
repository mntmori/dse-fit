package com.artwork.mori.dse.search;

import java.util.Arrays;
import java.util.HashSet;

public class SortTableLowerCaseFieldInputTransformer extends AbstractLowerCaseFieldInputTransformer {

    public SortTableLowerCaseFieldInputTransformer() {
        super(new HashSet<>(Arrays.asList("name", "shortname")));
    }
}
