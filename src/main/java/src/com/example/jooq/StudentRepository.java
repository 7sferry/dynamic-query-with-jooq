package src.com.example.jooq;

import java.util.List;

/************************
 * Made by [MR Ferry™]  *
 * on Juni 2026         *
 ************************/

public interface StudentRepository{
	<C> List<C> findByAdvancedFilterProjections(StudentFilter filter, Class<C> clazz);

	<C> List<C> findByFilterProjections(StudentFilter filter, Class<C> clazz);

	List<StudentEntity> findSpecificStudents();

	List<StudentEntity> findByFilter(StudentFilter filter);
}
