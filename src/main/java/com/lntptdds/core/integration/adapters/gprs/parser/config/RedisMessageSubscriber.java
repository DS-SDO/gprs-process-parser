package com.lntptdds.core.integration.adapters.gprs.parser.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@Slf4j
public class RedisMessageSubscriber implements MessageListener {




    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    RedisMessagePublisher redisPublisher;


    static final HashMap<String,String> jsonMap = JsonLoader.jsonMap();
    public static List<String> messageList = new ArrayList<>();


    public RedisMessageSubscriber(){}
    public RedisMessageSubscriber(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;

    }

    public RedisMessageSubscriber(RedisMessagePublisher redisMessagePublisher) {
        this.redisPublisher = redisMessagePublisher;

    }


    public static void calcutil(HashMap<String, String> sendMap){
        DecimalFormat df = new DecimalFormat("0.0");
        try{
        BiFunction<String,String,String> pf = (a1, b1)-> {  if (a1 ==null || b1 == null) return "0.0";
            if (!a1.isEmpty() && !b1.isEmpty()) {



               if(Double.parseDouble(b1) != 0.0) return df.format(Double.parseDouble(a1) / Double.parseDouble(b1));
                else
                    return String.valueOf(0.0);}
        return "0.0";
        };
        BiFunction<String,String,String> apprPower = (a1,b1)->{
            if (a1 ==null || b1 == null) return "0.0";
            if(!a1.isEmpty() && !b1.isEmpty()){
                var val = Double.parseDouble(a1) + Double.parseDouble(b1);
                return String.valueOf(val);

            }
           return "0.0";
        };

        sendMap.put("R-KVA",apprPower.apply(sendMap.get("R-KW"),sendMap.get("R-Kvar")));
        sendMap.put("Y-KVA",apprPower.apply(sendMap.get("Y-KW"),sendMap.get("Y-Kvar")));
        sendMap.put("B-KVA",apprPower.apply(sendMap.get("B-KW"),sendMap.get("B-Kvar")));
        sendMap.put("R-PF", pf.apply(sendMap.get("R-KW"),sendMap.get("R-KVA")));
        sendMap.put("Y-PF", pf.apply(sendMap.get("R-KW"),sendMap.get("Y-KVA")));
        sendMap.put("B-PF", pf.apply(sendMap.get("R-KW"),sendMap.get("B-KVA")));

        String tPf = df.format((Double.parseDouble(sendMap.get("R-PF")) +
                        Double.parseDouble(sendMap.get("Y-PF")) +
                        Double.parseDouble(sendMap.get("B-PF"))
        )/3.0);
        sendMap.put("T-PF",tPf);
        String tKw = String.valueOf(Double.parseDouble(sendMap.get("R-KW")) +
                Double.parseDouble(sendMap.get("Y-KW")) +
                Double.parseDouble(sendMap.get("B-KW"))) ;
        String tKvar = String.valueOf(Double.parseDouble(sendMap.get("R-Kvar")) +
                Double.parseDouble(sendMap.get("Y-Kvar")) +
                Double.parseDouble(sendMap.get("B-Kvar")));
        sendMap.put("T-KvAR", tKvar);
        sendMap.put("T-KvA", String.valueOf(Double.parseDouble(tKw ) + Double.parseDouble(tKvar)));
        }
        catch(Exception e){
            e.printStackTrace();
            log.error("Calculatio failed");
        }



    }



    public void onMessage(Message message, byte[] pattern) {

        messageList.add(message.toString());
        String data = message.toString();
        System.out.println("Message received: " + data);




        List<String> rcvdData = Arrays.asList(data.split(","));
        System.out.println(rcvdData);

        HashMap<String, String> sendMap = new HashMap<>();


        for (String rcvedValue : rcvdData) {

            if (rcvedValue.length() > 5 && !rcvedValue.startsWith("EPC") && rcvdData.indexOf(rcvedValue) > 12) {


                // System.out.println(r.substring(0, 3));

                String s1 = rcvedValue.substring(0, 3);
                String s2 = rcvedValue.substring(3, rcvedValue.length() - 2);
                System.out.println(s2);
//                System.out.println(Long.parseLong(s2,Character.MAX_RADIX));
                int hexValueToInt =  s2.startsWith("FFFF") ? 0 : Integer.parseInt(s2,Character.MAX_RADIX);
//                 hexValueToInt = Integer.parseInt(s2,Character.MAX_RADIX);


                if (rcvedValue.endsWith(Constants.U1) || rcvedValue.endsWith(Constants.S1)) {
//                    log.info(String.valueOf(hexValueToInt));
                    double hexValueToDouble = (double) hexValueToInt / 10.0;


                    DecimalFormat df = new DecimalFormat("0.0");

                    sendMap.put(jsonMap.get(s1),df.format(hexValueToDouble));
                } else if (rcvedValue.endsWith(Constants.U0) || rcvedValue.endsWith(Constants.S0)) {

                    sendMap.put(jsonMap.get(s1), String.valueOf((double) hexValueToInt));

                } else if (rcvedValue.endsWith(Constants.D0)) {

                    sendMap.put(jsonMap.get(s1), String.valueOf(hexValueToInt));

                } else if (rcvedValue.endsWith(Constants.B0)) {

                    if (rcvedValue.startsWith("BF")) {
                        boolean bankFaultStatus = Boolean.parseBoolean(String.valueOf(hexValueToInt));
                        if (bankFaultStatus)
                            log.info("Bank is Healthy");

                        else
                            log.error("Bank is Faulty");



                    } else if (rcvedValue.startsWith("ER3")) {

                        if (hexValueToInt == 0)
                            log.info("Bank Fault Var 1: Fault Absent");
                        else {
                            log.error("Bank Fault Var 1: Fault Present");
                            log.info("Reported Faults are: " + FaultLogic.parseFaultVariable1(hexValueToInt));


                            //To DO for all fault vars


                        }

                        String faultStr1 = String.join(";",FaultLogic.parseFaultVariable1(hexValueToInt));
                        sendMap.put(jsonMap.get(s1), faultStr1);


                    } else if (rcvedValue.startsWith("ER4")) {

                        if (hexValueToInt == 0)
                            log.info("Bank Fault Var 2: Fault Absent");
                        else {
                            log.error("Bank Fault Var 2: Fault Present");
                            log.info("Reported Faults are: " + FaultLogic.parseFaultVariable2(hexValueToInt));


                            //To DO for all fault vars


                        }
                        String faultStr2 = String.join(";",FaultLogic.parseFaultVariable1(hexValueToInt));
                        sendMap.put(jsonMap.get(s1), faultStr2);
//                        sendMap.put(jsonMap.get(s1), FaultLogic.parseFaultVariable2(bankFaultVar2).toString());


                    } else if (rcvedValue.startsWith("ER5")) {

                        if (hexValueToInt == 0)
                            log.info("Bank Fault Var 3: Fault Absent");
                        else {
                            log.error("Bank Fault Var 3: Fault Present");
                            log.error("Reported Faults are: " + FaultLogic.parseFaultVariable3(hexValueToInt));


                            //To DO for all fault vars


                        }
                        String faultStr3 = String.join(";",FaultLogic.parseFaultVariable1(hexValueToInt));
                        sendMap.put(jsonMap.get(s1), faultStr3);
//                        sendMap.put(jsonMap.get(s1), FaultLogic.parseFaultVariable1(bankFaultVar3).toString());


                    }


                }


                LocalDateTime tim = LocalDateTime.now();

                sendMap.put("time", tim.toString());

            }
        }
        log.info("--------------------------------------------------");
        log.info(String.valueOf(sendMap.size()));
        calcutil(sendMap);

        Iterator<Map.Entry<String, String>> it = sendMap.entrySet().iterator();
        StringBuilder s = new StringBuilder();
        int i =0;
        while (it.hasNext()) {
            Map.Entry<String, String> pair = it.next();
            s.append(pair.getKey());
            s.append(" ");
            i = i+1;
            s.append(pair.getValue());
            if(it.hasNext()){
            s.append(",");}
            // it.remove(); // avoids a ConcurrentModificationExceptio

        }


        redisPublisher.publish(s.toString());
        log.info("message published");
        log.info(redisPublisher.topic.getTopic());
        log.info(String.valueOf(i));
        log.info(sendMap.toString());













    }
}
