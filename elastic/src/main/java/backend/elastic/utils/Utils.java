package backend.elastic.utils;

import java.util.Arrays;
import java.util.List;

public class Utils {
    public static final String INDEX = "ckan_index";
    public static final String TYPE = "catalog_type";
    static final List<String> african_repositories = Arrays.asList(
            "https://africaopendata.org",
            "http://data.edostate.gov.ng",
            "http://catalog.data.ug/",
            "http://data.gov.bf/"
    );

}
