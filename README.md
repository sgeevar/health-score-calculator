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

Assumptions
-----------
1. There is a delay for new hourly files to be uploaded and to prevent 404
errors using a 10min offset with default times.
2. Since the whole 1 hour file is download and available to process, not
discarding the events before the start time and after end time.