package net.microwonk.aufg_jdbc.dao_land.domain;

import lombok.Getter;

import java.sql.Date;

@Getter
public class Course extends BaseEntity{

    private String name;
    private String description;
    private int hours;
    private Date beginDate;
    private Date endDate;
    private CourseType courseType;

    public Course(
            Long ID,
            String name,
            String description,
            int hours,
            Date beginDate,
            Date endDate,
            CourseType courseType
    ) throws InvalidValueException {
        super(ID);
        this.setName(name);
        this.setDescription(description);
        this.setHours(hours);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
        this.setCourseType(courseType);
    }

    public Course(
            String name,
            String description,
            int hours,
            Date beginDate,
            Date endDate,
            CourseType courseType
    ) throws InvalidValueException {
        this(null, name, description, hours, beginDate, endDate, courseType);
    }


    public void setName(String name) {
        if(name != null && name.length() > 1){
            this.name = name;
        } else {
            throw new InvalidValueException("Kursname muss mindestens 2 Zeichen lang sein");
        }
    }

    public void setDescription(String description) {
        if(description != null && description.length() > 10){
            this.description = description;
        } else {
            throw new InvalidValueException("Kursbeschreibung muss mindestens 10 Zeichen lang sein");
        }
    }

    public void setHours(int hours) {
        if(hours > 0 && hours <= 10){
            this.hours = hours;
        } else {
            throw new InvalidValueException("Anzahl der Kursstunden darf nur zwischen 1 und 10 Stunden liegen");
        }
    }

    public void setBeginDate(Date beginDate) {
        if(beginDate != null){
            if(this.endDate != null){
                if(beginDate.before(this.endDate)){
                    this.beginDate = beginDate;
                } else {
                    throw new InvalidValueException("Kursbeginn muss vor dem Kursende sein");
                }
            } else {
                this.beginDate = beginDate;
            }
        } else {
            throw new InvalidValueException("Startdatum darf nicht leer sein");
        }
    }

    public void setEndDate(Date endDate) {
        if(endDate != null) {
            if(this.beginDate != null) {
                if(endDate.after(this.beginDate)) {
                    this.endDate = endDate;
                } else {
                    throw new InvalidValueException("Kursende muss nach dem Kursbeginn sein");
                }
            } else {
                this.endDate = endDate;
            }
        } else {
            throw new InvalidValueException("Startdatum darf nicht leer sein");
        }
    }

    public void setCourseType(CourseType courseType) {
        if(courseType != null) {
            this.courseType = courseType;
        } else {
            throw new InvalidValueException("Kurstyp darf nicht leer sein");
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", hours=" + hours +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", courseType=" + courseType +
                '}';
    }
}
