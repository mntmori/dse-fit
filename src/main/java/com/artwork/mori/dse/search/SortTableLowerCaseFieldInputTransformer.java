package com.artwork.mori.dse.search;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SortTableLowerCaseFieldInputTransformer extends AbstractLowerCaseFieldInputTransformer {

    public SortTableLowerCaseFieldInputTransformer() {
        super(new HashSet<>(Arrays.asList("name", "shortname")));
    }
}
