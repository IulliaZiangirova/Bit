package bitmexbot.model;

import bitmexbot.repository.OrderRepository;

public class Bot {

    private double step;
    private int level;
    private double coefficient;
    private int[] sequenceFibonacci;
    private OrderRepository orderRepository; //созданные ордера
    private int miniLevel; //уровень контрордеров


}
