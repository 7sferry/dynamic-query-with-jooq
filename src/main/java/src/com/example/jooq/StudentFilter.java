package src.com.example.jooq;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

/************************
 * Made by [MR Ferry™]  *
 * on Juni 2026         *
 ************************/

@Builder
public record StudentFilter(String npm, Set<String> npms, String nik, String studentName, Boolean active,
                            LocalDate birthDateRangeStart, LocalDate birthDateRangeEnd, Integer batchYear){
}
