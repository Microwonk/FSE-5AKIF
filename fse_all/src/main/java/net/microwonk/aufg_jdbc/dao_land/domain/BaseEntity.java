package net.microwonk.aufg_jdbc.dao_land.domain;

import lombok.Getter;

@Getter
public abstract class BaseEntity {
    public Long ID;

    public BaseEntity(Long ID){
        setId(ID);
    }

    public void setId(Long ID) {
        if (ID == null || ID > 0) {
            this.ID = ID;
        } else {
            throw new InvalidValueException("Kurs-ID muss größer gleich 0 sein!");
        }
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "ID=" + ID +
                '}';
    }
}
