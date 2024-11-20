package com.example.vnc;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.util.Properties;
import java.util.Scanner;  // Import Scanner for user input

public class SSHClient {
    // Use user input for the following fields
    private static String JUMP_HOST;  // 中转主机地址
    private static String USERNAME;  // SSH 用户名
    private static String PASSWORD;  // SSH 密码
    private static String TARGET_DEVICE_IP;  // 目标设备的内网IP
    private static int TARGET_PORT;  // 目标设备的端口（例如 nvc 的端口）
    private static int LOCAL_PORT;  // 本地端口用于转发

    private Session session;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);  // Create Scanner object for user input

        // Prompt user for input
        System.out.print("Enter jump host address: ");
        JUMP_HOST = scanner.nextLine();

        System.out.print("Enter SSH username: ");
        USERNAME = scanner.nextLine();

        System.out.print("Enter SSH password: ");
        PASSWORD = scanner.nextLine();

        System.out.print("Enter target device IP: ");
        TARGET_DEVICE_IP = scanner.nextLine();

        System.out.print("Do you want VNC or SSH? (5900/22): ");
        TARGET_PORT = scanner.nextInt();
        switch (TARGET_PORT) {
            case 5900:
                TARGET_PORT = 5900;
                break;
            case 22:
                TARGET_PORT = 22;
                break;
            default:
                System.out.println("Invalid input. Please enter 5900 for VNC or 22 for SSH.");
                return;

        }


        System.out.print("Enter local port for forwarding: ");
        LOCAL_PORT = scanner.nextInt();

        SSHClient sshClient = new SSHClient();
        try {
            // 建立 SSH 连接到中转主机
            sshClient.connectToJumpHost();
            // 设置端口转发，将本地端口转发到目标设备的内网端口
            sshClient.startPortForwarding();

            // 连接后可以访问 localhost:12345 来访问目标设备的端口
            System.out.println("Port forwarding established. You can now connect to localhost:" + LOCAL_PORT + " to access target device.");

            // 保持应用运行，以维持端口转发
            while (true) {
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sshClient.disconnect();
        }
        scanner.close();  // Close the scanner
    }

    // 建立到中转主机的 SSH 连接
    public void connectToJumpHost() throws JSchException {
        JSch jsch = new JSch();

        // 创建会话
        session = jsch.getSession(USERNAME, JUMP_HOST);
        session.setPassword(PASSWORD);

        // 配置 SSH 连接参数
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no"); // 禁用主机密钥检查
        session.setConfig(config);

        // 建立连接
        session.connect();
        System.out.println("Connected to jump host: " + JUMP_HOST);
    }

    // 设置端口转发， 将本地端口转发到目标设备的内网端口
    public void startPortForwarding() throws JSchException {
        // 创建 Channel，设置端口转发
        int localPort = LOCAL_PORT;
        int remotePort = TARGET_PORT;
        String remoteHost = TARGET_DEVICE_IP;

        // 在中转主机上设置端口转发
        session.setPortForwardingL(localPort, remoteHost, remotePort);

        System.out.println("Port forwarding: localhost:" + localPort + " -> " + remoteHost + ":" + remotePort);
    }

    // 断开 SSH 连接
    public void disconnect() {
        if (session != null && session.isConnected()) {
            session.disconnect();
            System.out.println("Disconnected from jump host.");
        }
    }
}