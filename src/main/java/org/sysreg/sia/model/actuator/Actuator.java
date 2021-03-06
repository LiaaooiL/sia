package org.sysreg.sia.model.actuator;

import org.sysreg.sia.model.Board;
import org.sysreg.sia.model.Enclosure;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by jose on 08/02/14.
 */
@Entity
@Table(name = "ACTUATORS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ACTUATOR_TYPE")
@DiscriminatorValue("ACTUATOR")
public class Actuator implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column
    private boolean enabled;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    public Actuator() {
    }

    public Actuator(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String toString() {
            String res = "";
            Class sensorType = this.getClass();
            res += sensorType.getSimpleName() + ": " + '\t';
            res += printAttributes(sensorType, this);
            return res;
    }

    static String printAttributes(Class<?> clazz, Actuator sensor) {
        String res = "";
        try {
            if (clazz.equals(Actuator.class)) {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    res += field.getName() + ": " + field.get(sensor) + '\t';
                }
                return res;
            } else {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    res += field.getName() + ": " + field.get(sensor) + '\t';
                }
                return printAttributes(clazz.getSuperclass(), sensor) + '\t' + res;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
