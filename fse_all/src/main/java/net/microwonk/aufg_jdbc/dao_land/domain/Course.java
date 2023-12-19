package net.microwonk.aufg_jdbc.dao_land.domain;


import lombok.Getter;

import java.sql.Date;

@Getter
public class Course extends BaseEntity {

    private String name;
    private String description;
    private int hours;
    private Date beginDate;
    private Date endDate;
    private CourseType courseType;

    public Course(Long id, String name, String description, int hours, Date beginDate, Date endDate, CourseType courseType) {
        super(id);
        this.setName(name);
        this.setDescription(description);
        this.setHours(hours);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
        this.setCourseType(courseType);
    }

    public Course(String name, String description, int hours, Date beginDate, Date endDate, CourseType courseType) {
        super(null);
        this.setName(name);
        this.setDescription(description);
        this.setHours(hours);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
        this.setCourseType(courseType);
    }

    public void setName(String name) throws InvalidValueException {
        if (name != null && name.length() > 1) {
            this.name = name;
        } else {
            throw new InvalidValueException("Kursname muss min 2 Zeichen lanf sein");
        }
    }

    public void setDescription(String description) throws InvalidValueException {
        if (description != null && description.length() >= 1) {
            this.description = description;
        } else {
            throw new InvalidValueException("Description muss min 10 Zeichnen lang sein");
        }

    }

    public void setHours(int hours) throws InvalidValueException {
        if (hours > 0 && hours < 10) {
            this.hours = hours;
        } else {
            throw new InvalidValueException("Kursstunden müssen zwischen 1 und 10 liegen");
        }

    }

    public void setBeginDate(Date beginDate) throws InvalidValueException {
        if (beginDate != null) {
            if (endDate != null) {
                if (beginDate.before(this.endDate)) {
                    this.beginDate = beginDate;
                } else {
                    throw new InvalidValueException("Der Kursbeginn muss vor dem Kursende sein.");
                }
            } else {
                this.beginDate = beginDate;
            }
        } else {
            throw new InvalidValueException("Der Kursbeginn darf nicht null sein");
        }
    }

    public void setEndDate(Date endDate) throws InvalidValueException {
        if (endDate != null) {
            if (beginDate != null) {
                if (endDate.after(this.beginDate)) {
                    this.endDate = endDate;
                } else {
                    throw new InvalidValueException("Das Kursende muss nach dem Kursbeginn sein.");
                }
            } else {
                this.endDate = endDate;
            }
        } else {
            throw new InvalidValueException("Das Kursende darf nicht null sein");
        }
    }

    public void setCourseType(CourseType courseType) throws InvalidValueException {
        if (courseType != null) {
            this.courseType = courseType;
        } else {
            throw new InvalidValueException("Kurstyp muss angegeben werden.");
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "id ='" + super.getId() + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", hours=" + hours +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", courseType=" + courseType +
                '}';
    }

    public enum CourseType {
        OE, BF, ZA, FF
    }
}
