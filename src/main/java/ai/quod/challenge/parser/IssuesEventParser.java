package ai.quod.challenge.parser;

import ai.quod.challenge.repo.RepoSummary;
import ai.quod.challenge.utils.Utilities;
import org.json.JSONObject;

import java.time.Duration;
import java.time.Instant;

public class IssuesEventParser extends EventParser {
    @Override
    public void parse(RepoSummary repoSummary, JSONObject jsonObject) {
        String action = jsonObject.getJSONObject("payload").getString("action");
        if (action.compareTo("opened") == 0) {
            repoSummary.incNumberOfOpenIssues();
            repoSummary.addIssueOpener(jsonObject.getJSONObject("actor").getLong("id"));
        } else if (action.compareTo("closed") == 0) {
            repoSummary.incNumberOfClosedssues();

            JSONObject jo = jsonObject.getJSONObject("payload").getJSONObject("issue");
            Instant oTime = Utilities.parseISOTime(jo.getString("created_at"));
            Instant cTime = Utilities.parseISOTime(jo.getString("closed_at"));
            Duration d = Duration.between(oTime, cTime);
            //counting hours; over days minutes can overflow.
            repoSummary.incSumOfIssueOpenDuration(d.toHours());
        }
    }
}
