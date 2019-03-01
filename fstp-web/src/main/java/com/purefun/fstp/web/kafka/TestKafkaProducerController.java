package com.purefun.fstp.web.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purefun.fstp.core.bo.TestBO;
import com.purefun.fstp.core.bo.otw.TestBO_OTW;
import com.purefun.fstp.core.bo.tool.BoFactory;

/**
 * 测试kafka生产者
 */
@RestController
@RequestMapping("kafka")
public class TestKafkaProducerController {

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @RequestMapping("send")
    public String send(String msg){
    	TestBO_OTW bo = (TestBO_OTW) BoFactory.createBo(TestBO.class);
    	bo.setMsg("5555");
    	System.out.println(bo.getBuilder().build().toByteArray());
        kafkaTemplate.send(bo.getDestination(), bo.getBuilder().build().toByteArray());
        return "success";
    }

}
