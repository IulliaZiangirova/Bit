package bitmexbot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table(name = "bots")
public class Bot {

    @Id
    private String id;

    @ManyToOne
    private User user;

    private double step;
    private int level;
    private double coefficient;

    @Column(name = "work_indicator")
    private boolean workingIndicator;
    private int[] sequenceFibonacci;
    private int miniLevel; //уровень контрордеров

    public Bot(double step, int level, double coefficient) {
        this.step = step;
        this.level = level;
        this.coefficient = coefficient;
    }
}
