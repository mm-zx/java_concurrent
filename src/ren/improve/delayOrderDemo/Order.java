package ren.improve.delayOrderDemo;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/30 16:35
 * @description： 订单的实体类
 */
public class Order {
    private final String ORDER_NO;//订单的编号
    private final double ORDER_MONEY;//订单的金额

    public Order(String ORDER_NO, double ORDER_MONEY) {
        this.ORDER_NO = ORDER_NO;
        this.ORDER_MONEY = ORDER_MONEY;
    }

    public String getORDER_NO() {
        return ORDER_NO;
    }

    public double getORDER_MONEY() {
        return ORDER_MONEY;
    }
}
