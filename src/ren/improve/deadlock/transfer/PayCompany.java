package ren.improve.deadlock.transfer;

import ren.improve.deadlock.transfer.service.ITransfer;
import ren.improve.deadlock.transfer.service.SafeOperate;
import ren.improve.deadlock.transfer.service.TransferAccount;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/23 13:26
 * @description： 模拟支付公司转账的动作
 */
public class PayCompany {
    /*执行转账动作的线程*/
    private static class TransferThread extends Thread {
        private String name;
        private UserAccount from;//转出账户
        private UserAccount to;//转入账户
        private int amount;//变动金额
        private ITransfer transfer;

        public TransferThread(String name, UserAccount from, UserAccount to, int amount, ITransfer transfer) {
            this.name = name;
            this.from = from;
            this.to = to;
            this.amount = amount;
            this.transfer = transfer;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);
            transfer.transfer(from, to, amount);
        }
    }

    public static void main(String[] args) {
        PayCompany payCompany = new PayCompany();
        UserAccount first = new UserAccount("zhangsan", 20000);
        UserAccount second = new UserAccount("lisi", 20000);
//        ITransfer transfer = new TransferAccount();//线程不安全转账操作
        ITransfer transfer = new SafeOperate();//线程安全转账操作
        TransferThread zhangsanToLisi = new TransferThread("zhangsanToLisi", first, second, 2000, transfer);//账户一向账户二转账2000
        TransferThread lisiToZhangsan = new TransferThread("lisiToZhangsan", second, first, 4000, transfer);//账户二向账户一转账4000
        zhangsanToLisi.start();
        lisiToZhangsan.start();
    }
}
