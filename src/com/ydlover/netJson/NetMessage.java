package com.ydlover.netJson;

import com.google.gson.Gson;

public class NetMessage {
    public int id;
    public String name;
    public String time;

    public NetMessage() { }
    public NetMessage(int id, String name, String time)
    {
        this.id = id;
        this.name = name;
        this.time = time;
    }
    public void recvMessage(NetJson nj, String data) {
        Gson gson = new Gson();
        NetMessage nm = gson.fromJson(data, this.getClass());
        System.out.printf("NetJson recev msg：id=%d, name=%s\n", nm.id, nm.name);
        proc(nj, nm);
    }
    public String getJsonMessage() {
        Gson gson = new Gson();
        String userJson = gson.toJson(this);
        return userJson;
    }
    public void proc(NetJson nj, NetMessage nm) {}
}


