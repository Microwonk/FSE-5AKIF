package net.microwonk.aufg_jdbc.dao_land.dataaccess;

import net.microwonk.aufg_jdbc.dao_land.domain.Student;

import java.sql.Date;
import java.util.List;

public interface MyStudentRepository extends BaseRepository<Student,Long> {

    List<Student> getAllStudentsByLastName(String ln);
    List<Student> getAllStudentsByLastNameOrFirstName(String ln);
    List<Student> getAllStudentsByYOB(Date d);
    List<Student> getAllStudentsByPostalCode(int pc);
}
