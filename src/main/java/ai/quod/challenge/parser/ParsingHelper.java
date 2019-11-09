package ai.quod.challenge.parser;

import ai.quod.challenge.repo.RepoSummary;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ParsingHelper {
    private Map<String, EventParser> parsers;

    public ParsingHelper() {
        this.parsers = new HashMap<>();
    }

    public void addEventParser(String type, EventParser parser) {
        this.parsers.put(type, parser);
    }

    public void parse(RepoSummary rs, JSONObject jo) {
        EventParser ep = parsers.get(jo.getString("type"));
        if (ep != null) {
            ep.parse(rs, jo);
        }
    }
}
