CREATE TABLE "Homework"
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name text NOT NULL,
    description text,
    PRIMARY KEY (id)
);

CREATE TABLE "Lesson"
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name text NOT NULL,
    "updatedAt" date NOT NULL,
    homework_id bigint NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (homework_id),
    FOREIGN KEY (homework_id)
        REFERENCES "Homework" (id) MATCH SIMPLE
);

CREATE TABLE "Schedule"
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY,
    name text NOT NULL,
    "updatedAt" date NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE "Schedule_Lesson"
(
	schedule_id bigint NOT NULL,
    lesson_id bigint NOT NULL,
    PRIMARY KEY (schedule_id, lesson_id),
    FOREIGN KEY (schedule_id) 
		REFERENCES "Schedule" (id) MATCH SIMPLE,
    FOREIGN KEY (lesson_id) 
		REFERENCES "Lesson" (id) MATCH SIMPLE
);

