package com.sanjeev1779.zk.lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

public class ZookeeperLock implements IDistributedLock {
    private static final String lockBasePath = "/lock";
    private final ZookeeperClient zookeeperClient;

    public ZookeeperLock(String connectString) {
        zookeeperClient = new ZookeeperClient(connectString);
    }

    @Override
    public boolean getLock(String lockId) {
        ZooKeeper client = zookeeperClient.getClient();
        String lockPath = getLockPath(lockId);
        while (true) {
            try {
                String res = client.create(lockPath, "true".getBytes(), OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                return true;
            } catch (KeeperException.NodeExistsException e) {
                System.out.println("someone else has lock. Retrying for " + lockPath);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("unable to getLock for id : " + getLockPath(lockId));
            }
        }
    }

    @Override
    public boolean releaseLock(String lockId) {
        ZooKeeper client = zookeeperClient.getClient();
        try {
            client.delete(getLockPath(lockId), 0);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("unable to releaseLock for id : " + getLockPath(lockId));
        }
    }

    private String getLockPath(String lockId) {
        return lockBasePath + "/" + lockId;
    }

}
