package com.cc1021.service.impl;

import com.cc1021.service.IAccountService;
import org.springframework.stereotype.Service;

/**
 *  账户业务层实现类
 */
@Service("accountService")
public class AccountServiceImpl implements IAccountService {
    /**
     * 模拟保存账户
     */
    public void saveAccount() {
        System.out.println("执行了保存");
        //int i=1/0;
    }

    /**
     * 模拟更新账户
     *
     * @param i
     */
    public void updateAccount(int i) {
        System.out.println("执行了更新" + i);
    }

    /**
     * 模拟删除账户
     */
    public int deleteAccount() {
        System.out.println("执行了删除");
        return 0;
    }
}
