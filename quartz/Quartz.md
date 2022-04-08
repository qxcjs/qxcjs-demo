# Quartz

## 项目说明

- spring-quartz-01
  > 1. 自定义 quartz.properties 配置文件

- spring-quartz-02
  > 1. application.yml 中直接配置 quartz

## 配置说明

- org.quartz.jobStore.useProperties : 默认(false)
  ，用来指示JDBCJobStore，在JobDataMaps里的所有值都应该是String，这样在能作为name-value方式储存，而不是在BLOB列里以序列化的格式储存复杂的对象。从长远看，这样做会很安全，因为你可以避免将非String的类序列化到BLOB里的类版本问题。

- org.quartz.scheduler.idleWaitTime : 在调度程序处于空闲状态时，调度程序将在重新查询可用触发器之前等待的时间量（以毫秒为单位）

## 方法说明

- 获取JobDetail通过JobDataMap传递的参数信息

context.getJobDetail().getJobDataMap()

- 获取Trigger通过JobDataMap传递的信息

context.getTrigger().getJobDataMap()

- 获取JobDetail与Trigger合并的JobDataMap的方法，当获取两者有相同的key值的时候，优先获取的是Trigger的值，也就是JobDatail的会被覆盖

context.getMergedJobDataMap()
