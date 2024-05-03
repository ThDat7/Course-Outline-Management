-- Start transaction
START TRANSACTION;


-- Insert faculties
INSERT INTO facultys (name, alias)
VALUES ('Engineering', 'ENG'),
       ('Information Technology', 'IT'),
       ('Business', 'BUS');

-- Insert majors
INSERT INTO majors (name, alias, faculty_id)
VALUES ('Computer Science', 'CS', 1),
       ('Software Engineering', 'SE', 1),
       ('Information Technology', 'IT', 2),
       ('Business Administration', 'BA', 3),
       ('Marketing', 'MKT', 3);

-- Insert courses
INSERT INTO courses (name, code, credits)
VALUES ('Introduction to Programming', 'CS101', 3),
       ('Database Systems', 'CS202', 3),
       ('Web Development', 'IT303', 3),
       ('Marketing Fundamentals', 'MKT201', 3),
       ('Financial Management', 'BA302', 3);

-- Insert users
INSERT INTO users (firstName, lastName, email, phone, username, password, status, role, image)
VALUES ('John', 'Doe', 'john.doe@example.com', '+1234567890', 'johndoe', 'password123', 'ENABLED', 'ADMIN', 'user.png'),
       ('Jane', 'Smith', 'jane.smith@example.com', '+9876543210', 'janesmith', 'password456', 'TEACHER', 'user2.png'),
       ('Michael', 'Lee', 'michael.lee@example.com', '+5432109876', 'michael', 'password789', 'STUDENT', 'user3.png'),
       ('Olivia', 'Williams', 'olivia.williams@example.com', '+0987654321', 'oliviaw', 'password012', 'STUDENT', 'user4.png'),
       ('David', 'Miller', 'david.miller@example.com', '+1029384756', 'davidm', 'TEACHER', 'user5.png');

-- Insert course outlines
INSERT INTO course_outlines (content, course_id)
SELECT CONCAT('Course outline for ', name, '...'), id
FROM course;

-- Insert assign outlines
INSERT INTO assign_outlines (teacher_id, course_outline_id, assign_date, deadline_date, status)
SELECT user.id, course_outline.id, NOW() - INTERVAL FLOOR(RAND() * 30) DAY, NOW() + INTERVAL FLOOR(RAND() * 10) DAY, TRUE
FROM user
JOIN (SELECT id FROM user WHERE role = 'TEACHER' LIMIT 5) AS teacher_ids ON user.id = teacher_ids.id
JOIN (SELECT id FROM course_outline ORDER BY RAND() LIMIT 1) AS course_outline ON 1=1;

-- Insert course outline details
INSERT INTO course_outline_details (course_outline_id, school_year)
SELECT course_outline.id, 2023
FROM course_outline;

-- Insert course assessments
INSERT INTO course_assessments (type, method, time, clos, weight_percent, course_outline_id)
SELECT 'Assignment', 'Online', '2024-05-10 10:00:00', '2024-05-15 23:59:00', 20, course_outline.id
FROM course_outline
JOIN (SELECT id FROM user WHERE role = 'TEACHER' LIMIT 5) AS teacher_ids ON 1=1
ORDER BY RAND()
LIMIT 1;

INSERT INTO course_assessments (type, method, time, clos, weight_percent, course_outline_id)
SELECT 'Quiz', 'Online', '2024-05-17 14:00:00', '2024-05-17 14:30:00', 10, course_outline.id
FROM course_outlines
JOIN (SELECT id FROM users WHERE role = 'TEACHER' LIMIT 5) AS teacher_ids ON 1=1
ORDER BY RAND()
LIMIT 1;

INSERT INTO course_assessment (type, method, time, clos, weight_percent, course_outline_id)
SELECT 'Midterm Exam', 'In-person', '2024-05-24 09:00:00', '2024-05-24 11:00:00', 30, course_outline.id
FROM course_outline
JOIN (SELECT id FROM user WHERE role = 'TEACHER' LIMIT 5) AS teacher_ids ON 1=1
ORDER BY RAND()
LIMIT 1;

INSERT INTO course_assessment (type, method, time, clos, weight_percent, course_outline_id)
SELECT 'Final Exam', 'In-person', '2024-06-07 09:00:00', '2024-06-07 11:00:00', 40, course_outline.id
FROM course_outline
JOIN (SELECT id FROM user WHERE role = 'TEACHER' LIMIT 5) AS teacher_ids ON 1=1
ORDER BY RAND()
LIMIT 1;

-- Insert comments
INSERT INTO comment (user_id, course_outline_id, cmt, time)
SELECT user.id, course_outline.id, 'Great course outline!', NOW() - INTERVAL FLOOR(RAND() * 60) MINUTE
FROM user
JOIN (SELECT id FROM course_outline ORDER BY RAND() LIMIT 5) AS course_outline ON 1=1
ORDER BY RAND()
LIMIT 10;

-- Commit transaction
COMMIT;
