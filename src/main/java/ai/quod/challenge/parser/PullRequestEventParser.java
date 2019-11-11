package ai.quod.challenge.parser;

import ai.quod.challenge.repo.RepoSummary;
import org.json.JSONObject;

import java.time.Duration;
import java.time.Instant;

public class PullRequestEventParser extends EventParser {
    @Override
    public void parse(RepoSummary repoSummary, JSONObject jsonObject) {
        JSONObject jo = jsonObject.getJSONObject("payload");
        String action = jo.getString("action");
        if (action.equals("opened")) {
            repoSummary.addOpenPR(jo.getLong("number"));
        } else if (action.equals("closed")) {
            boolean merged = jo.getJSONObject("pull_request").getBoolean("merged");
            if (merged) {
                repoSummary.addContributor(jsonObject.getJSONObject("actor").getLong("id"));
                repoSummary.incMergedPullRequests();

                Instant oTime = Instant.parse(jo.getJSONObject("pull_request").getString("created_at"));
                Instant mTime = Instant.parse(jo.getJSONObject("pull_request").getString("merged_at"));
                Duration d = Duration.between(oTime, mTime);
                //counting hours; over days minutes can overflow.
                repoSummary.incTotalPROpenDuration(d.toHours());
            }
            repoSummary.removeOpenPR(jo.getLong("number"));
        }
        repoSummary.incCommentsOnPR(jo.getJSONObject("pull_request").getLong("comments"));
        repoSummary.incPullRequests();
    }
}