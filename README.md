Usage
-----
ai.quod.challenge.HealthScoreCalculator [ISO_8601_datetime_start] [ISO_8601_datetime_end]  
  
ai.quod.challenge.HealthScoreCalculator 2019-08-01T00:00:00Z 2019-09-01T00:00:00Z  
Health score between 2019-08-01T00:00:00Z and 2019-09-01T00:00:00Z  
  
ai.quod.challenge.HealthScoreCalculator 2019-08-01T00:00:00Z  
Health score between 2019-08-01T00:00:00Z and now  
  
ai.quod.challenge.HealthScoreCalculator  
Health score for the last one hour  

Technical Discussions
---------------------
What frameworks/libraries did you use? What are the benefits of those libraries?  
How would you improve your code for performance?  
What code would you refactor for readability and maintainability?  
1. Make Metrics loosely coupled with RepoSummery  

Assumptions
-----------
1. There is a delay for new hourly files to be uploaded and to prevent 404
errors using a 10min offset with default times.  
2. Since the whole 1 hour file is download and available to process, not
discarding the events before the start time and after end time.  

Metrics Used
------------
1. Average number of commits (push) per day (to any branch)  
Given the duration of collecting data is same for all projects the average/day 
is not significan't unless its a new trending repo. Using number of commits in 
the given time window for now and will add the per day criteria as a future enhancement E1.  
CommitCommentEvent  
repo/id,name -> name split by / gives org and repo  
actor/id,login -> user  

2. Number of contributors  
Considering a contributor as one who has pull request closed with merge  
PullRequestEvent  
actor/id,login when payload/action=closed,payload/pullrequst/merged=true  
repo/id,name  

3. Average time that an issue remains opened  
IssuesEvent  
repo/id,name  
payload/issue/created_at,closed_at  

4. Average time for a pull request to get merged  
PullRequestEvent  
actor/id,login when payload/action=closed,payload/pull_requst/merged=true  
repo/id,name  
payload/pull_requst/created_at,merged_at  

5. Ratio of commit per developer  
Consider developer as anyone who commits (contributor only the one who merged)  
CommitCommentEvent  
repo/id,name -> name split by / gives org and repo  
actor/id,login -> user  

6. Ratio of closed to open issues  
IssuesEvent  
repo/id,name  
payload/action=opened or closed  

7. Average number of comments per pull requests  
PullRequestEvent  
payload/pull_requst/comments  

8. Number of releases  
Assuming number of release published  
ReleaseEvent  
payload/action=published  
  
9. Number of people opening new issues  
IssuesEvent  
repo/id,name  
payload/action=opened  
actor/id,login  

10. Number of open pull requests  
PullRequestEvent  
actor/id,login when payload/action=opened/reopened and remove when action is closed  