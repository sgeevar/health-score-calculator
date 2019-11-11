package ai.quod.challenge.parser;

import ai.quod.challenge.repo.RepoSummary;
import org.json.JSONObject;

import java.time.Duration;
import java.time.Instant;

public class IssuesEventParser extends EventParser {
    @Override
    public void parse(RepoSummary repoSummary, JSONObject jsonObject) {
        String action = jsonObject.getJSONObject("payload").getString("action");
        if (action.equals("opened")) {
            repoSummary.incOpenIssues();
            repoSummary.addIssueOpener(jsonObject.getJSONObject("actor").getLong("id"));
        } else if (action.equals("closed")) {
            repoSummary.incClosedIssues();

            JSONObject jo = jsonObject.getJSONObject("payload").getJSONObject("issue");
            Instant oTime = Instant.parse(jo.getString("created_at"));
            Instant cTime = Instant.parse(jo.getString("closed_at"));
            Duration d = Duration.between(oTime, cTime);
            //counting hours; over days minutes can overflow.
            repoSummary.incTotalIssueOpenDuration(d.toHours());
        }
    }
}
