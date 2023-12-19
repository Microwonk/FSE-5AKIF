package net.microwonk.aufg_jdbc.dao_land.domain;

public abstract class BaseEntity {
    public Long id;
    public BaseEntity(Long id){
        setId(id);
    }

    public long getId(){
        return this.id;
    }

    public void setId(Long id){
        if(id == null || id >= 0){
            this.id = id;
        } else {
            throw new InvalidValueException("Kurs-ID muss größer 0 sein");
        }
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}
