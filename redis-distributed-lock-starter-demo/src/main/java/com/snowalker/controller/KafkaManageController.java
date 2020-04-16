package com.snowalker.controller;

import com.snowalker.lock.redisson.RedissonLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class KafkaManageController
{
    private static final Logger logger = LoggerFactory.getLogger(KafkaManageController.class);
    @Autowired
    RedissonLock redissonLock;

    @RequestMapping("/start")
    public void start()
    {
        if (redissonLock.lock("redisson", 10))
        {
            logger.info("[ExecutorRedisson]--执行定时任务开始，休眠三秒");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("=======================业务逻辑=============================");
            logger.info("[ExecutorRedisson]--执行定时任务结束，休眠三秒");
            redissonLock.release("redisson");
        } else {
            logger.info("[ExecutorRedisson]获取锁失败");
        }
    }

}
