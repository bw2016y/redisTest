package com.bbb.redis.service;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.jar.JarEntry;

@Service
public class SecKill {
    JedisPool jedisPool;
    public SecKill(){
        JedisPoolConfig config=new JedisPoolConfig();
        config.setMaxTotal(30);
        config.setMaxIdle(10);
        this.jedisPool=new JedisPool(config,"10.176.24.41",6379);
    }
    public boolean doKill(String uid,String pid){
        //key
        String Pkey="SecK:"+pid+":left";
        String Ukey="SecK:"+pid+":user";

        Jedis jedis=new Jedis("10.176.24.41",6379);
        //监视库存
        jedis.watch(Pkey);
        String left=jedis.get(Pkey);
        if(left==null){
            System.out.println("not started");
            jedis.close();
            return false;
        }
        if(jedis.sismember(Ukey,uid)){
            System.out.println("already get it");
            jedis.close();
            return false;
        }
        if(Integer.parseInt(left)<=0){
            System.out.println("end");
            return false;
        }
        //事务
        Transaction transaction = jedis.multi();
        transaction.decr(Pkey);
        transaction.sadd(Ukey,uid);
        List<Object> res = transaction.exec();

        if(res==null || res.size()==0){
            System.out.println("内卷失败");
            jedis.close();
            return false;
        }


        jedis.close();
        System.out.println("success");
        return true;
    }
    public boolean doKill_script(String uid,String pid){
        String script="local uid=KEYS[1];\r\n"+
                "local pid=KEYS[2];\r\n"+
                "local qkey='SecK:'..pid..':left';\r\n"+
                "local ukey='SecK:'..pid..':user';\r\n"+
                "local uExist=redis.call(\"sismember\",ukey,uid);\r\n"+
                "if tonumber(uExist)==1 then \r\n"+
                " return 2;\r\n"+
                "end\r\n"+
                "local num=redis.call(\"get\",qkey);\r\n"+
                "if tonumber(num)<=0 then \r\n"+
                " return 0; \r\n"+
                "else \r\n"+
                " redis.call(\"decr\",qkey);\r\n"+
                " redis.call(\"sadd\",ukey,uid);\r\n"+
                "end\r\n"+
                "return 1";
        Jedis jedis = this.jedisPool.getResource();
        String sha1 = jedis.scriptLoad(script);
        Object res = jedis.evalsha(sha1, 2, uid, pid);
        String resString=String.valueOf(res);
        if("0".equals(resString)){
            System.out.println("end");
            jedis.close();
            return false;
        }else if("1".equals(resString)){
            System.out.println("success");
            jedis.close();
            return  true;
        }else if("2".equals(resString)){
            System.out.println("already get it");
            jedis.close();
            return false;
        }else{
            System.out.println("error");
            jedis.close();
            return false;
        }


    }
}
