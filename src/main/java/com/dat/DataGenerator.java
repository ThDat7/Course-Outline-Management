package com.dat;

import com.dat.pojo.*;
import com.github.javafaker.Faker;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Transactional
public class DataGenerator {

    @Autowired
    private LocalSessionFactoryBean factory;

    public DataGenerator(LocalSessionFactoryBean factory) {
        this.factory = factory;
        userList = new ArrayList<>();
        majorList = new ArrayList<>();
        teacherList = new ArrayList<>();
        facultyList = new ArrayList<>();
        courseList = new ArrayList<>();
        courseOutlineList = new ArrayList<>();
        assignOutlineList = new ArrayList<>();
    }

    private Session s;
    private final List<User> userList;
    private final List<Teacher> teacherList;
    private final List<Major> majorList;
    private final List<Faculty> facultyList;
    private final List<Course> courseList;
    private final List<CourseOutline> courseOutlineList;
    private final List<AssignOutline> assignOutlineList;

    public void FakeData() {
        s = factory.getObject().getCurrentSession();

        generateUser(5);
        generateUserPending(5);
        generateFaculty(5);
        generateMajor(10);
        generateTeacher(3);
        generateCourse(30);
        generateEducationProgram();
        generateAssignOutline();
        generateCourseOutline();
        generateCourseOutlineDetail();
        generateCourseAssessment();
        generateComment();
    }

//    user fac major teacher course assign_outline
//    course_outline course_outline_detail course_assessment comment

    private final Faker faker = new Faker();

    private void generateUser(int number) {
        for (int i = 0; i < number; i++) {
            User user = new User();
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmail(faker.internet().emailAddress());
            user.setPhone(faker.phoneNumber().cellPhone());
            user.setUsername(faker.name().username());
            user.setPassword(faker.internet().password());
            user.setStatus(UserStatus.values()[faker.random().nextInt(UserStatus.values().length)]);
            user.setRole(UserRole.values()[faker.random().nextInt(UserRole.values().length)]);
            user.setImage(faker.internet().avatar());

            userList.add(user);
            s.save(user);
        }
    }

    private void generateUserPending(int number) {
        for (int i = 0; i < number; i++) {
            User user = new User();
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setEmail(faker.internet().emailAddress());
            user.setPhone(faker.phoneNumber().cellPhone());
            user.setUsername(faker.name().username());
            user.setStatus(UserStatus.PENDING);
            user.setRole(UserRole.values()[faker.random().nextInt(UserRole.values().length)]);

            userList.add(user);
            s.save(user);
        }
    }

    private void generateFaculty(int number) {
        for (int i = 0; i < number; i++) {
            Faculty faculty = new Faculty();
            faculty.setName(faker.university().name());
            faculty.setAlias(faker.lorem().word());

            facultyList.add(faculty);
            s.save(faculty);
        }
    }

    private void generateMajor(int number) {
        for (int i = 0; i < number; i++) {
            Major major = new Major();
            major.setName(faker.educator().course());
            major.setAlias(faker.lorem().word());
            Faculty factoryRandom = facultyList.get(faker.random().nextInt(facultyList.size()));
            major.setFaculty(factoryRandom);

            majorList.add(major);
            s.save(major);
        }
    }

    private void generateTeacher(int number) {
        List<User> available = new ArrayList<>(userList);

        for (int i = 0; i < number; i++) {
            Teacher teacher = new Teacher();
            User userRandom = available.get(faker.random().nextInt(available.size()));
            available.remove(userRandom);

            userRandom.setRole(UserRole.TEACHER);
            userRandom.setStatus(UserStatus.ENABLED);
            s.save(userRandom);

            teacher.setUser(userRandom);

            Major majorRandom = majorList.get(faker.random().nextInt(majorList.size()));
            teacher.setMajor(majorRandom);

            teacherList.add(teacher);
            s.save(teacher);
        }
    }


    private void generateCourse(int number) {
        for (int i = 0; i < number; i++) {
            Course course = new Course();
            course.setName(faker.educator().course());
            course.setCode(faker.code().isbn10());
            course.setCredits(faker.number().numberBetween(1, 5));

            Major majorRandom = majorList.get(faker.random().nextInt(majorList.size()));

            courseList.add(course);
            s.save(course);
            s.update(majorRandom);
        }
    }

    private void generateEducationProgram() {
        for (Major major : majorList) {

            List<Course> available = new ArrayList<>(courseList);
            for (int i = 1; i < 9; i++) {
                for (int j = 0; j < 3; j++) {
                    Course courseRandom = available.get(faker.random().nextInt(available.size()));
                    if (courseRandom == null)
                        break;
                    available.remove(courseRandom);

                    EducationProgramId id = new EducationProgramId();

                    EducationProgram educationProgram = new EducationProgram();
                    educationProgram.setSemester(i);
                    educationProgram.setMajor(major);
                    educationProgram.setCourse(courseRandom);
                    educationProgram.setId(id);

                    s.save(educationProgram);
                }
            }
        }
    }

    private void generateAssignOutline() {
        for (Course course : courseList) {
            AssignOutline assignOutline = new AssignOutline();
            assignOutline.setAssignDate(new Date(faker.date().past(10, TimeUnit.DAYS).getTime()));
            assignOutline.setDeadlineDate(new Date(faker.date().future(10, 3, TimeUnit.DAYS).getTime()));

            Teacher teacherRandom = teacherList.get(faker.random().nextInt(teacherList.size()));
            assignOutline.setTeacher(teacherRandom);

            assignOutline.setCourse(course);

            assignOutlineList.add(assignOutline);
            s.save(assignOutline);
        }
    }

    private void generateCourseOutline() {
        for (AssignOutline assignOutline : assignOutlineList) {
            CourseOutline courseOutline = new CourseOutline();
            courseOutline.setContent(faker.lorem().paragraph(1));
            courseOutline.setStatus(OutlineStatus.values()[faker.random().nextInt(OutlineStatus.values().length)]);

            courseOutline.setAssignOutline(assignOutline);

            courseOutlineList.add(courseOutline);
            s.save(courseOutline);
        }

    }

    private void generateCourseOutlineDetail() {
        for (CourseOutline courseOutline : courseOutlineList) {
            CourseOutlineDetail courseOutlineDetail = new CourseOutlineDetail();
            CourseOutlineDetailId id = new CourseOutlineDetailId();
            id.setSchoolYear(2023);
            courseOutlineDetail.setId(id);
            courseOutlineDetail.setCourseOutline(courseOutline);

            CourseOutlineDetail courseOutlineDetail1 = new CourseOutlineDetail();
            CourseOutlineDetailId id1 = new CourseOutlineDetailId();
            id1.setSchoolYear(2022);
            courseOutlineDetail1.setId(id1);
            courseOutlineDetail1.setCourseOutline(courseOutline);

            s.save(courseOutlineDetail);
            s.save(courseOutlineDetail1);
        }
    }

    private void generateCourseAssessment() {
        for (CourseOutline courseOutline : courseOutlineList) {
            CourseAssessment assessment = new CourseAssessment();
            assessment.setType("Quá trình");
            assessment.setMethod("Tham gia học trên lớp");
            assessment.setTime("Suốt khóa học");
            assessment.setClos("CLO1, CLO2, CLO3");
            assessment.setWeightPercent(30);
            assessment.setCourseOutline(courseOutline);
            s.save(assessment);

            assessment = new CourseAssessment();
            assessment.setType("Giữa kỳ");
            assessment.setMethod("Bài kiểm tra tự luận");
            assessment.setTime("Buổi học cuối");
            assessment.setClos("CLO1, CLO3");
            assessment.setWeightPercent(20);
            assessment.setCourseOutline(courseOutline);
            s.save(assessment);

            assessment = new CourseAssessment();
            assessment.setType("Cuối kỳ");
            assessment.setMethod("Bài kiểm tra tự luận");
            assessment.setTime("Thi tập trung");
            assessment.setClos("CLO1, CLO3");
            assessment.setWeightPercent(50);
            assessment.setCourseOutline(courseOutline);
            s.save(assessment);
        }
    }


    private void generateComment() {
        for (CourseOutline courseOutline : courseOutlineList) {
            Comment comment = new Comment();
            comment.setCourseOutline(courseOutline);
            comment.setCmt(faker.lorem().sentence());
            comment.setTime(LocalTime.now());

            User userRandom = userList.get(faker.random().nextInt(userList.size()));
            comment.setUser(userRandom);

            comment.setCourseOutline(courseOutline);

            s.save(comment);
        }
    }
}
