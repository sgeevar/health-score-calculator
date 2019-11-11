package ai.quod.challenge.repo;

import java.util.HashSet;
import java.util.Set;

/* Class to store repo summary. Summary is optimised for given metrics.
EventParser will process different events and populate the summary.
Metrics are computed on the these summaries. */

public class RepoSummary {
    private String repoName;
    //Aggregate values from all the processed events
    private long pullRequests;
    private long mergedPullRequests;
    private long commentsOnPR;
    private long openIssues;
    private long closedIssues;
    private long commits;
    private long releases;
    private long totalIssueOpenDuration;
    private long totalPROpenDuration;
    //Set of unique ids
    private Set<Long> developers;
    private Set<Long> contributors;
    private Set<Long> openPRs;
    private Set<Long> issueOpeners;

    RepoSummary(String repoName) {
        this.repoName = repoName;
        this.pullRequests = 0;
        this.commentsOnPR = 0;
        this.openIssues = 0;
        this.closedIssues = 0;
        this.commits = 0;
        this.releases = 0;
        this.totalIssueOpenDuration = 0;
        this.totalPROpenDuration = 0;
        this.developers = new HashSet<>();
        this.contributors = new HashSet<>();
        this.openPRs = new HashSet<>();
        this.issueOpeners = new HashSet<>();
    }

    public long getMergedPullRequests() {
        return mergedPullRequests;
    }

    public long getTotalIssueOpenDuration() {
        return totalIssueOpenDuration;
    }

    public long getTotalPROpenDuration() {
        return totalPROpenDuration;
    }

    public String getRepoName() {
        return repoName;
    }

    public long getPullRequests() {
        return pullRequests;
    }

    public long getCommentsOnPR() {
        return commentsOnPR;
    }

    public long getOpenIssues() {
        return openIssues;
    }

    public long getClosedIssues() {
        return closedIssues;
    }

    public long getCommits() {
        return commits;
    }

    public long getReleases() {
        return releases;
    }

    public void incReleases() {
        releases++;
    }

    public void incCommits() {
        commits++;
    }

    public void incOpenIssues() {
        openIssues++;
    }

    public void incPullRequests() {
        pullRequests++;
    }

    public void incMergedPullRequests() {
        mergedPullRequests++;
    }

    public void incClosedIssues() {
        closedIssues++;
    }

    public void incCommentsOnPR(long n) {
        commentsOnPR += n;
    }

    public void incTotalIssueOpenDuration(long h) {
        totalIssueOpenDuration += h;
    }

    public void incTotalPROpenDuration(long h) {
        totalPROpenDuration += h;
    }

    public void addDeveloper(long id) {
        developers.add(id);
    }

    public void addIssueOpener(long id) {
        issueOpeners.add(id);
    }

    public long getDevelopers() {
        return developers.size();
    }

    public long getIssueOpeners() {
        return issueOpeners.size();
    }

    public long getContributors() {
        return contributors.size();
    }

    public void addContributor(long id) {
        this.contributors.add(id);
    }

    public void addOpenPR(long id) {
        openPRs.add(id);
    }

    public void removeOpenPR(long id) {
        openPRs.remove(id);
    }

    public long getOpenPRs() {
        return openPRs.size();
    }
}
