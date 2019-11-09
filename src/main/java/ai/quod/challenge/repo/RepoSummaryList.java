package ai.quod.challenge.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RepoSummaryList {
    private Map<Long, RepoSummary> repoSummaryMap;

    public RepoSummaryList() {
        this.repoSummaryMap = new HashMap<>();
    }

    public RepoSummary get(Long repoId) {
        RepoSummary rs = repoSummaryMap.get(repoId);
        if (rs == null) {
            rs = new RepoSummary();
            repoSummaryMap.put(repoId, rs);
        }
        return rs;
    }

    public ArrayList<Long> getRepoIds() {
        ArrayList<Long> sl = new ArrayList<>(repoSummaryMap.keySet());
        return sl;
    }
}
