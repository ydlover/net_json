package com.ydlover.netJson;

class TestSendMessage extends NetMessage {
    public int t1;

    public TestSendMessage(int id, String name, String time)
    {
        super(id, name, time);
    }
    public void proc(NetJson nj, NetMessage nm) {
        System.out.printf("recev T1=%d,time=%s\n",((TestSendMessage)nm).t1, nm.time);
        // 简单测试直接回本消息，正常清空下应该回复响应消息
        this.time = "回个中文试试";
        nj.sendMessage(getJsonMessage());
    }
}
public class Test {

    public static void main(String[] args) {
        NetJson nj = new NetJson(8002, 8001);
        nj.start();
        nj.regMsgHandle(new TestSendMessage(0,"TestSenderMessage","" ));
    }
}
