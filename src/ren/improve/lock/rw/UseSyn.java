package ren.improve.lock.rw;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/23 17:17
 * @description： 使用synchronized实现商品服务接口
 */
public class UseSyn implements GoodsService {
    private GoodsInfo goodsInfo;

    public UseSyn(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    @Override
    public synchronized GoodsInfo getNum() {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.goodsInfo;
    }

    @Override
    public synchronized void setNum(int number) {
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        goodsInfo.chageNumber(number);

    }
}
