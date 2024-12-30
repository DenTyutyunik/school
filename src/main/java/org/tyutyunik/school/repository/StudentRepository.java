package org.tyutyunik.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tyutyunik.school.model.Student;

import java.util.Collection;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(int ageMin, int ageMax);

    @Query("SELECT count(s) FROM Student s")
    long countAll();

    @Query("SELECT AVG(s.age) FROM Student s")
    long ageAvg();

    @Query("SELECT s FROM Student s ORDER BY id DESC LIMIT 5")
    Collection<Student> readLast5();
}
