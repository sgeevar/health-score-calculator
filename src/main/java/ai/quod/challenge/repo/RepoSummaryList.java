package ai.quod.challenge.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RepoSummaryList {
    private Map<Long, RepoSummary> repoSummaryMap;

    public RepoSummaryList() {
        this.repoSummaryMap = new HashMap<>();
    }

    public RepoSummary getOrCreate(Long repoId, String repoName) {
        RepoSummary rs = get(repoId);
        if (rs == null) {
            rs = new RepoSummary(repoName);
            repoSummaryMap.put(repoId, rs);
        }
        return rs;
    }

    public RepoSummary get(Long repoId) {
        return repoSummaryMap.get(repoId);
    }

    public ArrayList<Long> getRepoIds() {
        return new ArrayList<>(repoSummaryMap.keySet());
    }
}
