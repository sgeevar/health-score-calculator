package ai.quod.challenge.parser;

import ai.quod.challenge.repo.RepoSummary;
import org.json.JSONObject;

/* Base class for all EventParsers. In order to support a new event
1. Extend this class and define the abstract method parse
2. Configure the EventParser in AppConfig
3. Extend RepoSummary to store any additional data required */
public abstract class EventParser {
    public abstract void parse(RepoSummary repoSummary, JSONObject jsonObject);
}
