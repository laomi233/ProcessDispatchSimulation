
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class RoundRobin {


    public RoundRobin(ArrayList<PCB> list) throws InterruptedException {

        PriorityQueue<PCB> pcbs = new PriorityQueue<PCB>(10, new Comparator<PCB>() {
            @Override
            public int compare(PCB o1, PCB o2) {
                double timea = o1.getArriveTime();
                double timeb = o2.getArriveTime();

                if(timea < timeb){
                    return -1;
                }
                else if(timea > timeb){
                    return 1;
                }
                else{
                    return 0;
                }

            }
        });

        pcbs.addAll(list);

        double timeslice = NewFrame.timeslice;

        double lastFinishTime = 0;
        System.out.println("进程名     到达时间     服务时间       开始执行时间      完成时间    周转时间");
        Queue<PCB> ready = new ArrayBlockingQueue<PCB>(10);
        ready.addAll(pcbs);


        while(!ready.isEmpty()){

            PCB tmp = ready.peek();
            System.out.println("现在正在执行"+tmp.getPname());
            System.out.println("该任务还需"+tmp.getTimeleft());
            System.out.println("----------------------------");
            NewFrame.processstatus.setText("");
            NewFrame.jTextArea2.setText("");

            for(int i=0;i<list.size();i++){
                NewFrame.processstatus.append(list.get(i).getPname()+"\n");
                NewFrame.jTextArea2.append(list.get(i).getStatus()+"\n");
                NewFrame.processstatus.paintImmediately(NewFrame.processstatus.getBounds());
                NewFrame.jTextArea2.paintImmediately(NewFrame.jTextArea2.getBounds());
            }

            Thread.sleep(1000);
            tmp.setStatus("Running");
            NewFrame.processstatus.setText("");
            NewFrame.jTextArea2.setText("");

            for(int i=0;i<list.size();i++){
                NewFrame.processstatus.append(list.get(i).getPname()+"\n");
                NewFrame.jTextArea2.append(list.get(i).getStatus()+"\n");
                NewFrame.processstatus.paintImmediately(NewFrame.processstatus.getBounds());
                NewFrame.jTextArea2.paintImmediately(NewFrame.jTextArea2.getBounds());
            }
            double lasttime = tmp.getLasttime();
            double timeleft = tmp.getTimeleft();

            double starttime;
            if(lasttime==0){
                if(tmp.getArriveTime()>lastFinishTime) {
                    tmp.setStartTime(tmp.getArriveTime());
                }
                else{
                    tmp.setStartTime(lastFinishTime);
                }

                tmp.setLasttime(tmp.getStartTime());
            }
            starttime = tmp.getStartTime();
            System.out.println(lasttime);
            System.out.println("当前进程开始执行时间："+starttime);
            if(lastFinishTime==0){
                lastFinishTime = starttime;
            }
            if(timeleft < timeslice){
                lastFinishTime += timeleft;
                ready.poll();
                tmp.setLasttime(lastFinishTime);
                tmp.setTimeleft(0);
                tmp.setEndTime(lastFinishTime);

            }
            else{
                tmp.setUsedtime(tmp.getUsedtime()+timeslice);
                tmp.setTimeleft(tmp.getServeTime()-tmp.getUsedtime());
                lastFinishTime += timeslice;

            }
            if(tmp.getTimeleft() > 0){
                tmp.setLasttime(lastFinishTime);
                ready.poll();
                ready.add(tmp);
            }

            System.out.println(lastFinishTime);

                Thread.sleep(500);
                double usedtime = tmp.getUsedtime();

               // usedtime+=timeslice;
                usedtime = Math.round(usedtime);
                tmp.setUsedtime(usedtime);


                NewFrame.jTextArea2.setText("");
                NewFrame.processstatus.setText("");
                NewFrame.jTextArea3.setText("");
                NewFrame.jTextArea4.setText("");

                for(int i=0;i<list.size();i++){
                    if(list.get(i).getTimeleft()<=0){
                        list.get(i).setStatus("Finished");
                        list.get(i).setUsedtime(list.get(i).getServeTime());
                        list.get(i).setTurnaroundTime(list.get(i).getEndTime()-list.get(i).getArriveTime());
                        list.get(i).setWeightedTime(String.format("%.2f",list.get(i).getTurnaroundTime()/
                                list.get(i).getServeTime()));
                    }

                    NewFrame.processstatus.append(list.get(i).getPname()+"\n");
                    NewFrame.jTextArea2.append(list.get(i).getStatus()+"\n");
                    NewFrame.processstatus.paintImmediately(NewFrame.processstatus.getBounds());
                    NewFrame.jTextArea2.paintImmediately(NewFrame.jTextArea2.getBounds());
                    NewFrame.jTextArea3.append(String.valueOf(list.get(i).getUsedtime()+"\n"));
                    NewFrame.jTextArea3.paintImmediately(NewFrame.jTextArea3.getBounds());
                    double rate = list.get(i).getUsedtime()/list.get(i).getServeTime();
                    rate *= 100;
                    if(rate>=100.0){
                        rate = 100;
                    }

                    NewFrame.jTextArea4.append(String.valueOf(Math.round(rate))+"%\n");
                    NewFrame.jTextArea4.paintImmediately(NewFrame.jTextArea4.getBounds());

            }

        }
    }
}
