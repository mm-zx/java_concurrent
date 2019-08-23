package ren.improve.lock.rw;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/23 17:16
 * @description： 商品服务的接口
 */
public interface GoodsService {
    public GoodsInfo getNum();//获取商品的信息

    public void setNum(int number);//设置商品的数量
}
