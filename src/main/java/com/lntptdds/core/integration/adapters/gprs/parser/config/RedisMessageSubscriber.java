package com.lntptdds.core.integration.adapters.gprs.parser.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class RedisMessageSubscriber implements MessageListener {




    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    RedisMessagePublisher redisPublisher;


    static final HashMap<String,String> jsonMap = JsonLoader.jsonMap();
    public static List<String> messageList = new ArrayList<String>();


    public RedisMessageSubscriber(){}
    public RedisMessageSubscriber(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;

    }

    public RedisMessageSubscriber(RedisMessagePublisher redisMessagePublisher) {
        this.redisPublisher = redisMessagePublisher;

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
                // System.out.println(s2);
                int hexValueToInt = Integer.parseInt(s2, 16);


                if (rcvedValue.endsWith(Constants.U1) || rcvedValue.endsWith(Constants.S1)) {
//                    log.info(String.valueOf(hexValueToInt));
                    double hexValueToDouble = (double) hexValueToInt / 10.0;


                    DecimalFormat df = new DecimalFormat("0.0");

                    sendMap.put(jsonMap.get(s1),df.format(hexValueToDouble));
                } else if (rcvedValue.endsWith(Constants.U0) || rcvedValue.endsWith(Constants.S0)) {
                    double hexValueToDouble = hexValueToInt / 1.0;

                    sendMap.put(jsonMap.get(s1), String.valueOf(hexValueToDouble));

                } else if (rcvedValue.endsWith(Constants.D0)) {

                    sendMap.put(jsonMap.get(s1), String.valueOf(hexValueToInt));

                } else if (rcvedValue.endsWith(Constants.B0)) {

                    if (rcvedValue.startsWith("BF")) {
                        Boolean bankFaultStatus = Boolean.valueOf(String.valueOf(hexValueToInt));
                        if (bankFaultStatus)
                            log.info("Bank is Healthy");

                        else
                            log.error("Bank is Faulty");



                    } else if (rcvedValue.startsWith("ER3")) {

                        int bankFaultVar1 =hexValueToInt;
                        if (bankFaultVar1 == 0)
                            log.info("Bank Fault Var 1: Fault Absent");
                        else {
                            log.error("Bank Fault Var 1: Fault Present");
                            log.info("Reported Faults are: " + FaultLogic.parseFaultVariable1(bankFaultVar1).toString());


                            //To DO for all fault vars


                        }

                        sendMap.put(jsonMap.get(s1), FaultLogic.parseFaultVariable1(bankFaultVar1).toString());


                    } else if (rcvedValue.startsWith("ER4")) {

                        int bankFaultVar2 = hexValueToInt;
                        if (bankFaultVar2 == 0)
                            log.info("Bank Fault Var 2: Fault Absent");
                        else {
                            log.error("Bank Fault Var 2: Fault Present");
                            log.info("Reported Faults are: " + String.valueOf(FaultLogic.parseFaultVariable2(bankFaultVar2)));


                            //To DO for all fault vars


                        }

                        sendMap.put(jsonMap.get(s1), FaultLogic.parseFaultVariable2(bankFaultVar2).toString());


                    } else if (rcvedValue.startsWith("ER5")) {

                        int bankFaultVar3 = hexValueToInt;
                        if (bankFaultVar3 == 0)
                            log.info("Bank Fault Var 3: Fault Absent");
                        else {
                            log.error("Bank Fault Var 3: Fault Present");
                            log.error("Reported Faults are: " + FaultLogic.parseFaultVariable3(bankFaultVar3).toString());


                            //To DO for all fault vars


                        }

                        sendMap.put(jsonMap.get(s1), FaultLogic.parseFaultVariable1(bankFaultVar3).toString());


                    }


                }


                LocalDateTime tim = LocalDateTime.now();
                sendMap.put("time", tim.toString());

            }
        }
        log.info("--------------------------------------------------");
        log.info(String.valueOf(sendMap.size()));


        Iterator it = sendMap.entrySet().iterator();
        StringBuilder s = new StringBuilder();
        int i =0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
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
