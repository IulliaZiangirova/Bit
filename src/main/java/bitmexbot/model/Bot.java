package bitmexbot.model;

import bitmexbot.repository.OrderRepository;
import lombok.Data;

@Data
public class Bot {

    private double step;
    private int level;
    private double coefficient;
    private int[] sequenceFibonacci;
    private OrderRepository orderRepository; //созданные ордера
    private int miniLevel; //уровень контрордеров

    public Bot(double step, int level, double coefficient) {
        this.step = step;
        this.level = level;
        this.coefficient = coefficient;
    }
}
