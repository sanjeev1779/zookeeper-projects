package com.sanjeev1779.zk.lock;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Objects;

public class ZookeeperClient implements Watcher {
    private final String connectString;
    private ZooKeeper zooKeeper;

    public ZookeeperClient(String clientString) {
        this.connectString = clientString;
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public void startZK() throws IOException {
        zooKeeper = new ZooKeeper(connectString, 15000, this);
    }

    public ZooKeeper getClient() {
        if (Objects.isNull(zooKeeper) || !zooKeeper.getState().isConnected()) {
            try {
                startZK();
            } catch (IOException e) {
                throw new RuntimeException("unable to start Zk");
            }
        }
        return zooKeeper;
    }
}
