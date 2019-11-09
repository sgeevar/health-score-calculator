package ai.quod.challenge.parser;

import ai.quod.challenge.repo.RepoSummary;
import ai.quod.challenge.utils.Utilities;
import org.json.JSONObject;

import java.time.Duration;
import java.time.Instant;

public class PullRequestEventParser extends EventParser {
    @Override
    public void parse(RepoSummary repoSummary, JSONObject jsonObject) {
        JSONObject jo = jsonObject.getJSONObject("payload");
        String action = jo.getString("action");
        if (action.compareTo("opened") == 0) {
            repoSummary.addOpenPR(jo.getLong("number"));
        } else if (action.compareTo("closed") == 0) {
            boolean merged = jo.getJSONObject("pull_request").getBoolean("merged");
            if (merged) {
                repoSummary.addContributor(jsonObject.getJSONObject("actor").getLong("id"));
                repoSummary.incMergedPullRequests();

                Instant oTime = Utilities.parseISOTime(jo.getJSONObject("pull_request").getString("created_at"));
                Instant mTime = Utilities.parseISOTime(jo.getJSONObject("pull_request").getString("merged_at"));
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
