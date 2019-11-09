package ai.quod.challenge.repo;

import java.util.HashSet;
import java.util.Set;

/* Class to store repo summary. Summary is optimised for given metrics.
EventParser will process different events and populate the summary.
Metrics are computed on the these summaries. */

public class RepoSummary {
    private String repo;
    //Aggregate values from all the processed events
    private long numberOfPullRequests;
    private long numberOfCommentsOnPRs;
    private long numberOfOpenIssues;
    private long numberOfClosedIssues;
    private long numberOfCommits;
    private long numberOfReleases;
    private long sumOfIssueOpenTimes;
    private long sumOfPROpenTimes;
    //Set of unique ids
    private Set<Long> developers;
    private Set<Long> contributors;
    private Set<Long> openPRs;
    private Set<Long> issueOpeners;

    public RepoSummary() {
        this.repo = "";
        this.numberOfPullRequests = 0;
        this.numberOfCommentsOnPRs = 0;
        this.numberOfOpenIssues = 0;
        this.numberOfClosedIssues = 0;
        this.numberOfCommits = 0;
        this.numberOfReleases = 0;
        this.sumOfIssueOpenTimes = 0;
        this.sumOfPROpenTimes = 0;
        this.developers = new HashSet<>();
        this.contributors = new HashSet<>();
        this.openPRs = new HashSet<>();
        this.issueOpeners = new HashSet<>();
    }

    public String getRepo() {
        return repo;
    }

    public long getNumberOfPullRequests() {
        return numberOfPullRequests;
    }

    public long getNumberOfCommentsOnPRs() {
        return numberOfCommentsOnPRs;
    }

    public long getNumberOfOpenIssues() {
        return numberOfOpenIssues;
    }

    public long getNumberOfClosedIssues() {
        return numberOfClosedIssues;
    }

    public long getNumberOfCommits() {
        return numberOfCommits;
    }

    public long getNumberOfReleases() {
        return numberOfReleases;
    }

    public void incNumberOfReleases(long count) {
        this.numberOfReleases += count;
    }
}
