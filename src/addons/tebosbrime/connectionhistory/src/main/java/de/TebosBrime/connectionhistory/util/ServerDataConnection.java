package de.TebosBrime.connectionhistory.util;

import java.io.Serializable;

public class ServerDataConnection implements Serializable {

    private long start;
    private long end;

    public ServerDataConnection(long start){

        this.start = start;
        this.end = -1;

    }

    public ServerDataConnection(String data){

        String[] split = data.split(";");

        start = Long.valueOf(split[0]);
        end = Long.valueOf(split[1]);

    }

    public long getTimePlayed(){

        if(end == -1 || start == -1){
            return 0;
        }

        return Math.abs(end - start);

    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getData(){
        return start + ";" + end;
    }

    public boolean isCompleted() {
        return end != -1;
    }

    public String getTimePlayedAsTimeStamp() {
        if(isCompleted()){
            return TimeFormat.get(getTimePlayed(), false);
        }
        return "unknown";
    }

    public String getStartTimeStamp() {
        return TimeFormat.getTimeStamp(start);
    }

    public String getDetailsString(){
        return getStartTimeStamp() + " > " + getTimePlayedAsTimeStamp();
    }
}
