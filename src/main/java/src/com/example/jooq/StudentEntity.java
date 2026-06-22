package src.com.example.jooq;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

/************************
 * Made by [MR Ferry™]  *
 * on Juni 2026         *
 ************************/

@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class StudentEntity{
	private long id;
	private String npm;
	private String nik;
	private String studentName;
	private boolean active;
	private LocalDate birthDate;
	private Long studentBatchId;
	private Integer version;

}
