package sample.Controllers;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class RestartController {
    public static void main(String[] args){
        StringBuilder cmd = new StringBuilder();
        cmd.append(System.getProperty("java.home")).append(File.separator).
                append("bin").append(File.separator).append("java ");
        for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
            cmd.append(jvmArg).append(" ");
        }
        cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
        cmd.append(RestartController.class.getName()).append(" ");
        try {
            Thread.sleep(10000); // 10 seconds delay before restart
            Runtime.getRuntime().exec(cmd.toString());
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
