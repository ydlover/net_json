package com.ydlover.netJson;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class NetJson extends Thread {
    private int localPort;
    private int peerPort;
    private DatagramSocket sendSocket;
    private InetAddress peerAddress;
    private Map<String, NetMessage> handles;
    public NetJson(int localPort, int peerPort){
        super("NetJson");
        this.localPort = localPort;
        this.peerPort = peerPort;
        handles = new HashMap<String, NetMessage>();
        try {
            this.peerAddress = InetAddress.getByName("127.0.0.1");
            this.sendSocket = new DatagramSocket(this.localPort);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    public void regMsgHandle(NetMessage message) {
        handles.put(message.name, message);
    }
    public void sendMessage(String data) {
        byte[] dataBytes = data.getBytes();
        DatagramPacket packet = new DatagramPacket(dataBytes, dataBytes.length, peerAddress, peerPort);
        try {
            sendSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            byte[] data = new byte[1024];// 创建字节数组，指定接收的数据包的大小
            System.out.printf("Plug net json service is started,localPort = %d, peerPort = %d\n", localPort, peerPort);
            while(true) {
                DatagramPacket packet = new DatagramPacket(data, data.length);
                sendSocket.receive(packet);// 此方法在接收到数据报之前会一直阻塞
                // 4.读取数据
                String message = new String(data, 0, packet.getLength());
                Gson gson = new Gson();
                NetMessage nm = gson.fromJson(message, NetMessage.class);
                NetMessage handleMessage = handles.get(nm.name);
                if ( handleMessage != null) {
                    handleMessage.recvMessage(this, message);
                } else {
                    System.out.println("not found msg handle：" + nm.name);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
