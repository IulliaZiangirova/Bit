package bitmexbot.client;

import bitmexbot.repository.OrderRepository;

public class Bot {

    private double step;
    private int level;
    private double coefficient;
    private int[] sequenceFibonacci;
    private OrderRepository orderRepository; //созданные ордера
    private int miniLevel; //уровень контрордеров

    public void start(){}
    public void stop(){}

    public void getSequenceFibonacci(){
        //на основе лвл генерируем уровни фибоначи
    }
}
