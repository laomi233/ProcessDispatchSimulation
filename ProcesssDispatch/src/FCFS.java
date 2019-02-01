import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class FCFS {


    public FCFS(ArrayList<PCB> list) throws InterruptedException {

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

        double lastFinishTime = 0;
     //   System.out.println("进程名     到达时间     服务时间       开始执行时间      完成时间    周转时间");

        while(!pcbs.isEmpty()){

            NewFrame.processstatus.setText("");
            NewFrame.jTextArea2.setText("");
            PCB tmp = pcbs.peek();
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
            //Thread.sleep(2000);

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

            tmp.setWeightedTime(String.format("%.2f",tmp.getTurnaroundTime()/tmp.getServeTime()));
            lastFinishTime = endTime;

//
//            System.out.println(tmp.getPname()+"             "+tmp.getArriveTime()+"            "
//                    +tmp.getServeTime()+"           "+tmp.getStartTime()+"           "+
//                    tmp.getEndTime()+"          "+tmp.getTurnaroundTime());

            pcbs.poll();
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
           // Thread.sleep(2000);



        }


    }

}
