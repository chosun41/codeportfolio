DROP TABLE mcho;

CREATE EXTERNAL TABLE mcho(
    user STRING,
    movie STRING,
    rating FLOAT,
    timestamp STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY "\t";

LOAD DATA LOCAL INPATH '/home/public/course/recommendationEngine/u.data' INTO TABLE mcho;

WITH 
    highrtng AS (
    SELECT movie 
    FROM mcho
    WHERE user = '200' AND rating > 3),
    other_users AS (
    SELECT user 
    FROM mcho AS m, highrtng AS hr
    WHERE m.movie = hr.movie AND rating > 3 AND m.user != '196'
    LIMIT 5)
SELECT movie
FROM mcho AS m, other_users AS os
WHERE m.user = os.user AND rating > 3
LIMIT 5;