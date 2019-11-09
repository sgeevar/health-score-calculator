package ai.quod.challenge.parser;

import ai.quod.challenge.repo.RepoSummary;
import org.json.JSONObject;

public class ReleaseEventParser extends EventParser {
    @Override
    public void parse(RepoSummary repoSummary, JSONObject jsonObject) {
        if (jsonObject.getJSONObject("payload").getString("action").equals("published")) {
            repoSummary.incNumberOfReleases();
        }
    }
}
