import java.util.Comparator;

public class PCB {

    private int pid; // 进程ID
    private int priority; //进程优先级
    private double arriveTime;//到达时间
    private double serveTime;//服务时间
    private double startTime;//开始时间
    private double endTime;//完成时间
    private double turnaroundTime;//周转时间
    private String pname;//进程名
    private String status;
    private double timeleft;//还需要的时间
    private double usedtime;//已被CPU执行时间
    private double lasttime;//上次完成时间
    private int DynamicPriority; //动态优先级
    private String WeightedTime;//带权周转时间

    public String getWeightedTime() {
        return WeightedTime;
    }

    public void setWeightedTime(String weightedTime) {
        WeightedTime = weightedTime;
    }

    public PCB(String pname, double arriveTime, double serveTime){

        this.pname = pname;
        this.arriveTime = arriveTime;
        this.serveTime = serveTime;
        this.timeleft = serveTime;
        this.usedtime = 0;
        this.lasttime = 0;

    }

    public PCB(String pname, double arriveTime, double serveTime, int priority){
        this.pname = pname;
        this.arriveTime = arriveTime;
        this.serveTime = serveTime;
        this.priority = priority;
        this.timeleft = serveTime;
    }



    public int getDynamicPriority() {
        return DynamicPriority;
    }

    public void setDynamicPriority(int dynamicPriority) {
        DynamicPriority = dynamicPriority;
    }

    public double getLasttime() {
        return lasttime;
    }

    public void setLasttime(double lasttime) {
        this.lasttime = lasttime;
    }

    public double getTimeleft() {
        return timeleft;
    }

    public void setTimeleft(double timeleft) {
        this.timeleft = timeleft;
    }

    public double getUsedtime() {
        return usedtime;
    }

    public void setUsedtime(double usedtime) {
        this.usedtime = usedtime;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }


    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public double getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(double arriveTime) {
        this.arriveTime = arriveTime;
    }

    public double getServeTime() {
        return serveTime;
    }

    public void setServeTime(double serveTime) {
        this.serveTime = serveTime;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public double getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(double turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
