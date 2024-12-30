SELECT
    student.name,
    student.age,
    faculty.name AS faculty_name
FROM
    student
JOIN
    faculty
ON
    student.faculty_id = faculty.id;

SELECT
    student.name,
    student.age
FROM
    student
JOIN
    faculty ON student.faculty_id = faculty.id
JOIN
    avatar ON student.id = avatar.student_id;