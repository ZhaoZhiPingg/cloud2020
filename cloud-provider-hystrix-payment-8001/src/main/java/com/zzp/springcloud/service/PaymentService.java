package com.zzp.springcloud.service;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author zzp
 * @create 2021-02-25
 */
@Service
public class PaymentService {

    /**
     * 正常访问
     * @param id
     * @return
     */
    public String paymentHystrixOk(Integer id){
        return "线程池： " + Thread.currentThread().getName() + " paymentHystrixOk,id: " + id
                + "\t" + "请求成功";
    }

    /**
     * 超时访问 运行异常 演示
     * @param id value = "2000" : 2秒
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentHystrixTimtoutHandler",
     commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")})
    public String paymentHystrixTimtout(Integer id){
        Long start = System.currentTimeMillis();
//        int timeCount = 3;
        int con = 9/0;
//        try
//        {
//            TimeUnit.SECONDS.sleep(timeCount);
//        } catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
        return "线程池： " + Thread.currentThread().getName() + " paymentHystrixTimtout,id: " + id
                + "\t" + "请求成功";
    }

    public String paymentHystrixTimtoutHandler(Integer id){
        return "线程池： " + Thread.currentThread().getName() + " 系统繁忙，请求稍后重试,id: " +id + " 请求异常";
    }

    /**       -------服务熔断--------          */

    @HystrixCommand(fallbackMethod = "paymentCircuitbreakerFallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"), // 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"), // 失败率达到多少后跳闸
    })
    public String paymentCircuitbreaker(@PathVariable("id") Integer id){
        if(id < 0){
            throw new RuntimeException("*******id  不能为负数");
        }

        // IdUtil.simpleUUID() = UUID.randomUUID().toString().replace("-","")
        String simpleUUID = IdUtil.simpleUUID();
        return "线程名称： " + Thread.currentThread().getName() + "\t" + "调用成功，流水号：" +simpleUUID;
    }
    public String paymentCircuitbreakerFallback(@PathVariable("id") Integer id){
        return "id 不能为负数,请重试，id: " +id;
    }

    //  ========== all HystrixCommandProperties
    @HystrixCommand(fallbackMethod = "str_flallbackMethon", // 定义回退方法的名称, 此方法必须和hystrix的执行方法在相同类中
            groupKey = "strGroupCommand", // HystrixCommand 命令所属的组的名称：默认注解方法类的名称
            commandKey = "strCommand", // HystrixCommand 命令的key值，默认值为注解方法的名称
            threadPoolKey = "strThreadPool",  // 线程池名称，默认定义为groupKey

            commandProperties = { // 配置hystrix命令的参数
                        // 设置隔离策略 THREAD:表示线程池 SEMAPHORE:信号池隔离
                    @HystrixProperty(name = "execution.isolation.strategy",value = "THREAD"),
                        // 但隔离策略选择信号池隔离的时候，用来设置信号池的大小（最大并发数）
                    @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests",value = "10"),
                        // 配置命令执行的超时时间
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "10"),
                        // 是否启用超时时间
                    @HystrixProperty(name = "execution.timeout.enabled",value = "true"),
                        // 执行超时的时候是否中断
                    @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout",value = "true"),
                        // 执行被取消的时候是否中断
                    @HystrixProperty(name = "execution.isolation.thread.interruptOnCancel",value = "true"),
                        // 允许回调方法执行的最大并发数
                    @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests",value = "10"),
                        // 服务降级是否启用，是否执行回调函数
                    @HystrixProperty(name = "fallback.enabled",value = "true"),
                        // 是否启用断路器
                    @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
                        // 该属性用来设置在滚动时间窗中，断路器熔断的最小请求数。例如，默认该值为 20 的时候
                    // 如果滚动时间窗（默认10秒）内仅收到19个请求，即使这19个请求都失败，断路器都不会打开。
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "20"),
                        // 该属性用来设置在滚动时间窗中，表示在滚动时间窗中，在请求数量超过
                    // circuitBreaker.requestVolumeThreshold 的情况下，如果错误请求数的百分比超过50，
                        // 就把断路器设置为 “打开” 状态，否则就设置为 “关闭” 状态
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "50"),
                        // 该属性用来设置当断路器打开之后的休眠时间窗。休眠时间窗结束之后，
                    // 会将断路器设置为 “半开” 状态，尝试熔断的请求命令，如果依然失败就将断路器继续设置为 “打开” 状态，
                    // 如果成功就设置为 “关闭” 状态。
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "5000"),
                        // 断路器强制打开
                    @HystrixProperty(name = "circuitBreaker.forceOpen",value = "false"),
                        // 断路器强制关闭
                    @HystrixProperty(name = "circuitBreaker.forceClosed",value = "false"),
                        // 滚动时间窗设置，该时间用于断路器判断健康度时需要收集信息的持续时间
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value = "10000"),
                        // 该属性用来设置滚动时间窗统计指标信息划分 “桶” 的数量，断路器在收集指标信息的时候会根据
                    // 设置的时间窗长度拆分成多个 “桶” 来累计各度量值，每个 “桶” 记录了一段时间内的采集指标。
                    // 比如 10 秒内拆分成 10 个“桶”收集这样，所以 timeInMilliseconds 必须能被 numBuckets 整除。否则会抛出异常
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets",value = "10"),
                        // 该属性用来设置对命令执行的延迟是否使用百分位数来跟踪和计算。如果设置为 false，那么所有的概念要统计都将返回 -1
                    @HystrixProperty(name = "metrics.rollingPercentile.enabled",value = "false"),
                        // 该属性用来设置百分位数统计滚动窗口的持续时间，单位为毫秒
                    @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds",value = "60000"),
                        // 该属性用来设置百分位数统计滚动窗口中使用 “桶” 的数量。
                    @HystrixProperty(name = "metrics.rollingPercentile.numBuckets",value = "60000"),
                        // 该属性用来设置在执行过程中每个 “桶” 中保留的最大执行次数。如果在滚动时间窗内发生超过该设置的执行次数，
                    // 就会从最初的位置开始重写。例如，将该值设置为100，滚动窗口为10秒，若在10秒内一个 “桶” 中发生了500次执行，
                    // 那么该 “桶” 中只保留 最后的100次执行的统计。另外，增加该值的大小将会增加内存量的消耗，并增加排序百分位数所需的计算时间。
                    @HystrixProperty(name = "metrics.rollingPercentile.bucketSize",value = "100"),
                        // 该属性用来设置采集影响断路器状态的健康快照（请求的成功、错误百分比）的间隔等待时间。
                    @HystrixProperty(name = "metrics.healthSnapshot.intervalInMilliseconds",value = "500"),
                        // 是否开启请求缓存
                    @HystrixProperty(name = "requestCache.enabled",value = "true"),
                        // HystrixCommand的执行和事件是否打印日记到 HystrixRequestLog 中
                    @HystrixProperty(name = "requestLog.enabled",value = "true"),
            },
            threadPoolProperties = { // 配置hystrix依赖的线程池的参数
                        // 该参数用来设置执行命令线程池的核心线程数，该值也就是命令执行的最大并发量
                    @HystrixProperty(name = "coreSize",value = "10"),
                        // 该参数用来设置线程池的最大队列大小。当设置为 -1 时，线程池将使用 SynchronousQueue 实现的队列，
                    // 否则将使用 linkedBlockingQueue 实现的队列.
                    @HystrixProperty(name = "maxQueueSize",value = "-1"),
                        // 该属性用来为队列设置拒绝阀值。通过该参数，即使队列没有达到最大值也能拒绝请求。
                    // 该参数只有对 LinkedBlockingQueue 队列的补充，因为 LinkedBlockingQueue
                        // 队列不能动态修改它的对象大小，而通过该属性就可以调整拒绝请求的队列大小了
                    @HystrixProperty(name = "queueSizeRejectionThreshold",value = "5")
            }
    )
    public String strConsumer(@PathVariable("id") Integer id){
        return "111";
    }

}
