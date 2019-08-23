package ren.improve.deadlock.transfer.service;

import ren.improve.deadlock.transfer.UserAccount;

/**
 * @author ：zhou (https://improve.ren)
 * @date ：Created in 2019/8/23 13:20
 * @description： 银行转账动作接口
 */
public interface ITransfer {
    void transfer(UserAccount from, UserAccount to, int amount);
}
