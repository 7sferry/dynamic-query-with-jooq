package src.com.example.jooq;

import org.jooq.impl.DSL;

import java.util.List;
import java.util.Set;

/************************
 * Made by [MR Ferry™]  *
 * on Juni 2026         *
 ************************/

public class App{
	public static void main(String[] args){
		StudentRepository studentRepository = new StudentJooqRepository(DSL.using("jdbc:postgresql://localhost:5432/dynamic_query?currentSchema=dynamic_query_example", "postgres", "12345"));
		List<StudentEntity> students = studentRepository.findSpecificStudents();
		for(StudentEntity student : students){
//			System.out.println("student.getStudentName() = " + student.getStudentName());
		}
		StudentFilter filter = StudentFilter.builder()
				.npms(Set.of("12322","111"))
//				.batchYear(2025)
				.build();
		List<StudentNameAndBatchNameProjection> specificStudents = studentRepository.findByAdvancedFilterProjections(filter, StudentNameAndBatchNameProjection.class);
		for(StudentNameAndBatchNameProjection specificStudent : specificStudents){
//			System.out.println(specificStudent.studentName() + ' ' + specificStudent.batchName());
		}
		List<StudentNameAndBirthDateProjection> advancedFilterProjections = studentRepository.findByAdvancedFilterProjections(filter, StudentNameAndBirthDateProjection.class);
		for(StudentNameAndBirthDateProjection specificStudent : advancedFilterProjections){
			System.out.println(specificStudent.studentName() + ' ' + specificStudent.birthDate());
		}
	}
}
