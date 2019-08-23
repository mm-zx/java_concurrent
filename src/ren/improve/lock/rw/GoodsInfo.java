package ren.improve.lock.rw;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/23 17:10
 * @description： 商品的实体类
 */
public class GoodsInfo {
    private final String name;
    private double totalMoney;//总销售额
    private int storeNumber;//库存数
    public GoodsInfo(String name,int totalMoney,int storeNumber){
        this.name = name;
        this.totalMoney = totalMoney;
        this.storeNumber = storeNumber;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public int getStoreNumber() {
        return storeNumber;
    }
    public void chageNumber(int sellNumber){
        this.totalMoney += sellNumber * 25;
        this.storeNumber -= sellNumber;
    }
}
