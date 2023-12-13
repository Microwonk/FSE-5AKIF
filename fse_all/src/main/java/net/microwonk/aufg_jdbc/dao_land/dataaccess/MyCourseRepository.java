package net.microwonk.aufg_jdbc.dao_land.dataaccess;

import net.microwonk.aufg_jdbc.dao_land.domain.Course;
import net.microwonk.aufg_jdbc.dao_land.domain.CourseType;

import java.util.Date;
import java.util.List;

public interface MyCourseRepository extends BaseRepository<Course, Long> {
    List<Course> findAllCoursesByName(String name);
    List<Course> findAllCoursesByDescription(String description);
    List<Course> findAllCoursesByNameOrDescription(String searchText);
    List<Course> findAllCoursesByCourseType(CourseType courseType);
    List<Course> findAllCoursesByStartDate(Date startDate);
    List<Course> findAllRunningCourses();
}
