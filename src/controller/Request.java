package controller;

public class Request {
    public int senderID;
    public int currentCluster;

    public Request(int sender, int cCluster) {
        senderID = sender;
        currentCluster = cCluster;
    }
}