package ai.quod.challenge.parser;

import ai.quod.challenge.repo.RepoSummary;
import org.json.JSONObject;

public abstract class EventParser {
    public abstract void parse(RepoSummary repoStatistics, JSONObject jsonObject);
}
