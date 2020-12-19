package com.sanjeev1779.zk.lock;

public interface IDistributedLock {
    boolean getLock(String lockId);
    boolean releaseLock(String lockId);
}
