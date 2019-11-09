package ai.quod.challenge.parser;

import ai.quod.challenge.repo.RepoSummary;
import org.json.JSONObject;

public class CommitCommentEventParser extends EventParser {
    @Override
    public void parse(RepoSummary repoSummary, JSONObject jsonObject) {
        repoSummary.incNumberOfCommits();
        repoSummary.addDeveloper(jsonObject.getJSONObject("actor").getLong("id"));
    }
}
