

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class SJF {


    public SJF(ArrayList<PCB> list) throws InterruptedException {

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

   /* public static void main(String[] args) {

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

        PCB A = new PCB("A",0,4);
        PCB B = new PCB("B",1,3);
        PCB C = new PCB("C",2,5);
        PCB D = new PCB("D",3,2);
        PCB E = new PCB("E",4,4);

        pcbs.add(A);
        pcbs.add(B);
        pcbs.add(C);
        pcbs.add(D);
        pcbs.add(E);

        */

        PriorityQueue<PCB> backupque = new PriorityQueue<PCB>(10, new Comparator<PCB>() {
            @Override
            public int compare(PCB o1, PCB o2) {

                double d1 = o1.getServeTime();
                double d2 = o2.getServeTime();

                double arrive1 = o1.getArriveTime();
                double arrive2 = o2.getArriveTime();
                if(d1 < d2){
                    return -1;
                }
                else if(d1 > d2){
                    return 1;
                }
                else{
                    if(arrive1 < arrive2){
                        return -1;
                    }
                    else if(arrive1 > arrive2){
                        return 1;
                    }
                    else{
                        return 0;
                    }
                }
            }
        });

        PriorityQueue<PCB> standup = new PriorityQueue<PCB>(10, new Comparator<PCB>() {
            @Override
            public int compare(PCB o1, PCB o2) {

                double d1 = o1.getServeTime();
                double d2 = o2.getServeTime();

                double arrive1 = o1.getArriveTime();
                double arrive2 = o2.getArriveTime();
                if(d1 < d2){
                    return -1;
                }
                else if(d1 > d2){
                    return 1;
                }
                else{
                    if(arrive1 < arrive2){
                        return -1;
                    }
                    else if(arrive1 > arrive2){
                        return 1;
                    }
                    else{
                        return 0;
                    }
                }
            }
        });

        double lastFinishTime = 0;
        System.out.println("进程名     到达时间     服务时间       开始执行时间      完成时间    周转时间");

        PCB tmp = pcbs.peek();
        pcbs.poll();
        tmp.setTimeleft(tmp.getServeTime());
        backupque.add(tmp);

        while(!backupque.isEmpty()){

            NewFrame.processstatus.setText("");
            NewFrame.jTextArea2.setText("");

            for(int i=0;i<list.size();i++){
                NewFrame.processstatus.append(list.get(i).getPname()+"\n");
                NewFrame.jTextArea2.append(list.get(i).getStatus()+"\n");
                NewFrame.processstatus.paintImmediately(NewFrame.processstatus.getBounds());
                NewFrame.jTextArea2.paintImmediately(NewFrame.jTextArea2.getBounds());
            }



            tmp = backupque.peek();

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

            System.out.println(tmp.getPname()+"还需:"+tmp.getTimeleft()+"s");

            if(tmp.getServeTime()==tmp.getTimeleft()) {
                tmp.setStartTime(Math.max(lastFinishTime, tmp.getArriveTime()));
                tmp.setLasttime(tmp.getStartTime());
            }

            lastFinishTime = Math.max(lastFinishTime,tmp.getArriveTime());

            if(!pcbs.isEmpty()){
                PCB pcb = pcbs.peek();
                if(pcb.getArriveTime() < lastFinishTime+tmp.getTimeleft() ){
                    if(pcb.getServeTime() < tmp.getServeTime()) {
                        tmp.setLasttime(pcb.getArriveTime());
                        tmp.setTimeleft(tmp.getServeTime() - (tmp.getLasttime() - lastFinishTime));
                        lastFinishTime = pcb.getArriveTime();
                        backupque.poll();
                        backupque.add(pcb);
                        backupque.add(tmp);
                        pcbs.poll();
                    }
                    else{
                        backupque.add(pcb);
                        pcbs.poll();
                    }
                }
                else{

                    tmp.setLasttime(tmp.getTimeleft()+lastFinishTime);
                    tmp.setTimeleft(0);
                    tmp.setEndTime(tmp.getLasttime());
                    tmp.setTurnaroundTime(tmp.getEndTime()-tmp.getArriveTime());
                    tmp.setWeightedTime(String.format("%.2f",tmp.getTurnaroundTime()/tmp.getServeTime()));
                    lastFinishTime = tmp.getEndTime();
                    backupque.poll();
                }
            }else{
                tmp.setLasttime(tmp.getTimeleft()+lastFinishTime);
                tmp.setTimeleft(0);
                tmp.setEndTime(tmp.getLasttime());
                tmp.setTurnaroundTime(tmp.getEndTime()-tmp.getArriveTime());
                lastFinishTime = tmp.getEndTime();
                tmp.setWeightedTime(String.format("%.2f",tmp.getTurnaroundTime()/tmp.getServeTime()));
                backupque.poll();
            }

                for(int j=0;j<=1000;j++){
                    if(tmp.getUsedtime() >= tmp.getServeTime() || tmp.getUsedtime() > tmp.getServeTime()-tmp.getTimeleft()){
                        break;
                    }
                    Thread.sleep(400);
                    double usedtime = tmp.getUsedtime();
                    usedtime+=1.0;
                    usedtime = Math.round(usedtime);
                    tmp.setUsedtime(usedtime);


                    NewFrame.jTextArea2.setText("");
                    NewFrame.processstatus.setText("");
                    NewFrame.jTextArea3.setText("");
                    NewFrame.jTextArea4.setText("");

                    for(int i=0;i<list.size();i++){
                        if(list.get(i).getUsedtime()>=list.get(i).getServeTime()){
                            list.get(i).setStatus("Finished");
                            list.get(i).setUsedtime(list.get(i).getServeTime());
                        }
                        else if(list.get(i).getUsedtime()>=list.get(i).getServeTime()-list.get(i).getTimeleft()){
                            list.get(i).setStatus("waiting");
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

        }


        /*NewFrame.processstatus.setText("");
        NewFrame.jTextArea2.setText("");

        for(int i=0;i<list.size();i++){
            NewFrame.processstatus.append(list.get(i).getPname()+"\n");
            NewFrame.jTextArea2.append(list.get(i).getStatus()+"\n");
            NewFrame.processstatus.paintImmediately(NewFrame.processstatus.getBounds());
            NewFrame.jTextArea2.paintImmediately(NewFrame.jTextArea2.getBounds());
        }

        double arrivetime = tmp.getArriveTime();
        double servetime = tmp.getServeTime();
        lastFinishTime = arrivetime+servetime;
        tmp.setStartTime(arrivetime);
        tmp.setEndTime(lastFinishTime);
        tmp.setTurnaroundTime(servetime);
        System.out.println(tmp.getPname()+"             "+tmp.getArriveTime()+"            "
                +tmp.getServeTime()+"           "+tmp.getStartTime()+"           "+
                tmp.getEndTime()+"          "+tmp.getTurnaroundTime());


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

        for(int j=0;j<=1000;j++){
            Thread.sleep(400);
            double usedtime = tmp.getUsedtime();
            usedtime+=1.0;
            usedtime = Math.round(usedtime);
            tmp.setUsedtime(usedtime);


            NewFrame.jTextArea2.setText("");
            NewFrame.processstatus.setText("");
            NewFrame.jTextArea3.setText("");
            NewFrame.jTextArea4.setText("");

            for(int i=0;i<list.size();i++){
                if(list.get(i).getUsedtime()>=list.get(i).getServeTime()){
                    list.get(i).setStatus("Finished");
                    list.get(i).setUsedtime(list.get(i).getServeTime());
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

        tmp.setWeightedTime(String.format("%.2f",tmp.getTurnaroundTime()/tmp.getServeTime()));
        backupque.addAll(pcbs);
        while(!backupque.isEmpty()){

            tmp = backupque.peek();
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

            double arriveTime = tmp.getArriveTime();
            double serveTime = tmp.getServeTime();

            double startTime;
            if(arriveTime > lastFinishTime){
                startTime = arriveTime;
            }
            else {
                startTime = lastFinishTime;
            }
            tmp.setStartTime(startTime);

            double endTime;
            endTime = startTime+serveTime;
            tmp.setEndTime(endTime);
            tmp.setTurnaroundTime(endTime-arriveTime);
            lastFinishTime = endTime;
            tmp.setWeightedTime(String.format("%.2f",tmp.getTurnaroundTime()/tmp.getServeTime()));
            lastFinishTime = endTime;

            System.out.println(tmp.getPname()+"             "+tmp.getArriveTime()+"            "
                    +tmp.getServeTime()+"           "+tmp.getStartTime()+"           "+
                    tmp.getEndTime()+"          "+tmp.getTurnaroundTime());
            backupque.poll();

            for(int j=0;j<=10;j++){
                Thread.sleep(400);
                double usedtime = tmp.getUsedtime();
                usedtime+=1.0;
                usedtime = Math.round(usedtime);
                tmp.setUsedtime(usedtime);


                NewFrame.jTextArea2.setText("");
                NewFrame.processstatus.setText("");
                NewFrame.jTextArea3.setText("");
                NewFrame.jTextArea4.setText("");

                for(int i=0;i<list.size();i++){
                    if(list.get(i).getUsedtime()>=list.get(i).getServeTime()){
                        list.get(i).setStatus("Finished");
                        list.get(i).setUsedtime(list.get(i).getServeTime());
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


}*/
