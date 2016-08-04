package com.github.bsamartins.dockermanager.services.dockermachine

import org.springframework.stereotype.Service

/**
 * Created by martinsb on 27/06/2016.
 */
@Service
class DockerMachineService {

    List<DockerMachine> listMachines() {
        DockerMachineClient.ls();
    }

    class DockerMachineClient {
        static List<DockerMachine> ls() {
            Process p = Runtime.getRuntime().exec("docker-machine ls --format {{.Name}};{{.Active}};{{.ActiveHost}};{{.ActiveSwarm}};{{.DriverName}};{{.State}};{{.URL}};{{.Swarm}};{{.Error}};{{.DockerVersion}};{{.ResponseTime}}")
            p.waitFor()
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))

            List<DockerMachine> result = [];
            String line = null;
            while((line = reader.readLine()) != null) {
                String[] splitLine = line.split(";")
                DockerMachine dm = new DockerMachine(
                        name: splitLine[0],
                        active: splitLine[1],
                        activeHost: splitLine[2],
                        activeSwarm: splitLine[3],
                        driverName: splitLine[4],
                        state: splitLine[5],
                        url: splitLine[6],
                        swarm: splitLine[7],
                        error: splitLine[8],
                        dockerVersion: splitLine[9],
                        responseTime: splitLine[10].replaceAll("ms", "").toLong()
                );
                result.add(dm)
            }
            result
        }
    }
}
