package com.sanjeev1779.zk.lock;

import org.junit.Test;

import java.util.UUID;

public class ZookeeperLockTest {

    @Test
    public void testZookeeperLock() {
        IDistributedLock distributedLock = new ZookeeperLock("localhost:2181");
        String lockId = UUID.randomUUID().toString();

        try {
            distributedLock.getLock(lockId);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            distributedLock.releaseLock(lockId);
        }
    }
}
