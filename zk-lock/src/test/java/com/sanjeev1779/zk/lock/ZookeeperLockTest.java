package com.sanjeev1779.zk.lock;

import org.junit.Test;

import java.util.UUID;

public class ZookeeperLockTest {

    @Test
    public void testZookeeperLock() {
        IDistributedLock distributedLock = new ZookeeperLock("localhost:2181");
        String lockId = UUID.randomUUID().toString();
        System.out.println(lockId);
        distributedLock.getLock(lockId);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        distributedLock.releaseLock(lockId);
    }
}
