package ge.tbc.testautomation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResponse {

    public Data data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {
        public String title;
        public List<Section> sections;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Section {
        public List<Item> items;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        public String label;
    }
}