package cn.newworld.controller;

import cn.newworld.Server;
import cn.newworld.model.ServerConfig;
import cn.newworld.util.Logger;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SSLSocketConnectionHandler implements Runnable{
    private ExecutorService threadPool;
    private SSLServerSocket serverSocket;

    public SSLSocketConnectionHandler(int port){
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            KeyStore keyStore = KeyStore.getInstance("JKS");

            // 加载私钥和证书
            String keystorePassword = ServerConfig.getInstance().getKeystorePassword();
            char[] keyStorePassword = keystorePassword.toCharArray();

            String keystorePath = "keystore/keystore.jks";
            if (!isHasJKSFile(keystorePath)){
                extracted();
            }
            keyStore.load(new FileInputStream(keystorePath), keyStorePassword);
            keyManagerFactory.init(keyStore, keyStorePassword);
            sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

            int connectionMax = ServerConfig.getInstance().getConnectionMax();
            this.serverSocket = (SSLServerSocket) sslContext.getServerSocketFactory().createServerSocket(port);
            this.threadPool = Executors.newFixedThreadPool(connectionMax);

        } catch (NoSuchAlgorithmException | KeyStoreException | IOException | CertificateException |
                 UnrecoverableKeyException | KeyManagementException e) {
            Logger.error(e.getMessage());
        }


    }

    private static void extracted() {
        Logger.info("------------------------------------------------------------------");
        Logger.warning(" ");
        Logger.warning(" 请手动使用 \"keytool\" 生成创建密钥和证书！");
        Logger.warning(" 将已经生成的 .jks文件，命名为\"keystore.jks\"放入\"keystore\"目录中.");
        Logger.warning(" 然后重启服务端!");
        Logger.warning(" ");
        Logger.info("------------------------------------------------------------------");
        System.exit(0);
    }

    @Override
    public void run() {
        try {
            if (serverSocket == null){
                Logger.error("初始化连接失败！请检查后重试！");
                Logger.error(" - 确保 \"keystore.jks \"文件内不为空.");
                Logger.error(" - 确保 \"keystore.jks \"文件是否合法有效.");
                System.exit(0);
            }
            Logger.info("服务器已开放端口: " + serverSocket.getLocalPort());
            Logger.info("已经连接的客户端数量："+ ServerConfig.getInstance().getClientAmount());
            serverSocket.setSoTimeout(5000); // 设置超时时间为5秒
            while (!Server.isShutdownRequested()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    handleClientConnection(clientSocket);
                } catch (SocketTimeoutException e) {
                    // 超时处理
                    //Logger.warning(e.getMessage());
                } catch (IOException e) {
                    Logger.error(e.getMessage());
                }
            }

        } catch (IOException e) {
            Logger.error(e.getMessage());
        } finally {
            close();
        }
    }

    private void handleClientConnection(Socket clientSocket){
        ServerConfig.getInstance().addClientAmount(1);
        Logger.info(" ");
        Logger.info("客户端IP" + clientSocket.getInetAddress() + " 已成功连接！");
        Logger.info("当前已连接的客户端数量: " + ServerConfig.getInstance().getClientAmount());

        // 处理连接，分配线程给客户端连接后处理
        threadPool.submit(new ClientHandler(clientSocket));
    }

    private void close(){
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            if (threadPool != null) {
                threadPool.shutdown();
            }
        } catch (IOException e){
            Logger.error(e.getMessage());
        }
    }
    private boolean isHasJKSFile(String jksPath){
        Path path = Path.of(jksPath);
        return Files.exists(path);
    }
}
