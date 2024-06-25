SELECT * FROM "Homework";

SELECT * FROM "Lesson" JOIN "Homework" ON "Homework".id = homework_id;

SELECT * FROM "Lesson" JOIN "Homework" ON "Homework".id = homework_id ORDER BY "updatedAt";

SELECT * FROM "Schedule"
	JOIN "Schedule_Lesson" ON "Schedule".id = "Schedule_Lesson".schedule_id
	JOIN "Lesson" ON "Schedule_Lesson".lesson_id = "Lesson".id;

SELECT s.id, s.name, COUNT(sl.lesson_id)
	FROM "Schedule" AS s
	LEFT JOIN "Schedule_Lesson" AS sl ON s.id = sl.schedule_id
	GROUP BY s.id, s.name;
