package com.github.bsamartins.dockermanager.services.dockermachine

/**
 * Created by martinsb on 27/06/2016.
 */
class DockerMachine {
    String name;
    String active;
    boolean activeHost;
    boolean activeSwarm;
    String driverName;
    String state;
    String url;
    String swarm;
    String error;
    String dockerVersion;
    long responseTime;
}
