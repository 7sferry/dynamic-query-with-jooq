package src.com.example.jooq;

import com.example.soclean.repository.user.generated.tables.Student;
import com.example.soclean.repository.user.generated.tables.StudentBatch;
import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.Record;
import org.jooq.impl.DSL;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/************************
 * Made by [MR Ferry™]  *
 * on Juni 2026         *
 ************************/

@RequiredArgsConstructor
public class StudentJooqRepository implements StudentRepository{
	private final DSLContext dsl;
	private static final Student STUDENT = Student.STUDENT;
	private static final StudentBatch STUDENT_BATCH = StudentBatch.STUDENT_BATCH;

	private static final Map<Class<?>, Collection<TableField<?, ?>>> SELECTION_BY_PROJECTION = Map.ofEntries(
			Map.entry(StudentNameAndBirthDateProjection.class, List.of(STUDENT.STUDENT_NAME, STUDENT.BIRTH_DATE)),
			Map.entry(StudentNameAndBatchNameProjection.class, List.of(STUDENT.STUDENT_NAME, STUDENT_BATCH.BATCH_NAME))
	);

	@Override
	public <C> List<C> findByAdvancedFilterProjections(StudentFilter filter, Class<C> clazz){
		SelectJoinStep<Record> query = dsl.select(SELECTION_BY_PROJECTION.get(clazz))
				.from(STUDENT);
		if(filter.batchYear() != null || containsBatchSelection(clazz)){
			query.innerJoin(STUDENT_BATCH)
					.on(STUDENT_BATCH.ID.eq(STUDENT.STUDENT_BATCH_ID));
		}
		SelectConditionStep<Record> finalQuery = query.where(constructWhereClause(filter));
		System.out.println("finalQuery.getSQL() = " + finalQuery.getSQL());
		return finalQuery
				.fetchInto(clazz);
	}

	private static <C> boolean containsBatchSelection(Class<C> clazz){
		return SELECTION_BY_PROJECTION.getOrDefault(clazz, Set.of())
				.stream()
				.anyMatch(field -> STUDENT_BATCH.equals(field.getTable()));
	}

	@Override
	public <C> List<C> findByFilterProjections(StudentFilter filter, Class<C> clazz){
		SelectJoinStep<Record> query = dsl.select(SELECTION_BY_PROJECTION.get(clazz))
				.from(STUDENT);
		if(filter.batchYear() != null){
			query.innerJoin(STUDENT_BATCH)
					.on(STUDENT_BATCH.ID.eq(STUDENT.STUDENT_BATCH_ID));
		}
		return query.where(constructWhereClause(filter))
				.fetchInto(clazz);
	}

	@Override
	public List<StudentEntity> findSpecificStudents(){
		return dsl.select()
				.from(STUDENT)
				.where(STUDENT.IS_ACTIVE.isTrue()
						.and(STUDENT.STUDENT_NAME.contains("ferry"))
						.and(STUDENT.ID.between(1L, 100L)))
				.fetchInto(StudentEntity.class);
	}

	@Override
	public List<StudentEntity> findByFilter(StudentFilter filter){
		SelectJoinStep<Record> query = dsl.select()
				.from(STUDENT);
		if(filter.batchYear() != null){
			query = query.innerJoin(STUDENT_BATCH)
					.on(STUDENT_BATCH.ID.eq(STUDENT.STUDENT_BATCH_ID));
		}
		SelectConditionStep<Record> where = query.where(constructWhereClause(filter));
		System.out.println("where.getSQL() = " + where.getSQL());
		return where
				.fetchInto(StudentEntity.class);
	}

	private Condition constructWhereClause(StudentFilter filter){
		Condition condition = DSL.noCondition();
		if(filter.active() != null){
			condition = condition.and(STUDENT.IS_ACTIVE.eq(filter.active()));
		}
		if(filter.npm() != null){
			condition = condition.and(STUDENT.NPM.contains(filter.npm()));
		}
		if(filter.npms() != null && !filter.npms().isEmpty()){
			condition = condition.and(STUDENT.NPM.in(filter.npms()));
		}
		if(filter.studentName() != null){
			condition = condition.and(DSL.lower(STUDENT.STUDENT_NAME).contains(filter.studentName().toLowerCase()));
		}
		if(filter.nik() != null){
			condition = condition.and(STUDENT.NIK.eq(filter.nik()));
		}
		if(filter.birthDateRangeStart() != null && filter.birthDateRangeEnd() != null){
			condition = condition.and(STUDENT.BIRTH_DATE.between(filter.birthDateRangeStart(), filter.birthDateRangeEnd()));
		}
		if(filter.batchYear() != null){
			condition = condition.and(STUDENT_BATCH.BATCH_YEAR.eq(filter.batchYear()));
		}
		return condition;
	}

}
