# 一个Java程序的所有JVM参数 VM Flags

## 怎么看

* 使用的是jdk 1.8
* ps -ef|grep xxx 找到pid
* jinfo pid  
  就会展示出这个Java程序好多的信息，输出结果的最后面，有VM的参数，两行，VM Flags。下面是kafka服务服务的jvm参数，我给它切到两个列表里面展示一下。

## VM Flags:

### Non-default VM flags:

|非默认VM标志  |对应参数的理解       |
|:-----|:---                 |
|-XX:CICompilerCount=4|设置最大并行编译数,使用-XX:CICompilerCount=N来调整编译线程的数目|
|-XX:ConcGCThreads=3|并发线程数|
|-XX:+ExplicitGCInvokesConcurrent|字面直译显式GC调用并发，通常我们不会显式地调用 System.gc()。但是一些情况下，比如使用了 Direct 内存，为了使得其（堆外内存）能够被及时回收，我们会通过显式调用 System.gc() 触发 full gc。但是 full gc 又会导致 stw，所以我们希望显式的对 GC 的触发也是并发执行的，这便是 -XX:ExplicitGCInvokesConcurrent 的意义|
|-XX:G1HeapRegionSize=1048576|1048576 = 1024 * 1024 , G1的region size是1M|
|-XX:GCLogFileSize=104857600|gc日志文件大小，1048576是1M，再带俩0就是100M|
|-XX:InitialHeapSize=2147483648| 2147483648  = 1024 * 1024 * 1024 * 2 = 2048M = 2G，堆heap的初始大小|
|-XX:InitiatingHeapOccupancyPercent=35|设置触发全局并发标记周期的 Java 堆占用率阈值。默认占用率是整个 Java 堆的 45%。调小这个默认值，希望提前触发全局标记降低full gc的风险|
|-XX:+ManagementServer|服务器管理器|
|-XX:MarkStackSize=4194304|标记堆栈大小，4194304 = 1024 * 1024 * 4 = 4M|
|-XX:MaxGCPauseMillis=20|设置回收器的最大停顿时间，这是一个比较简单的目标，JVM将尽最大努力去实现它，默认是200毫秒，它竟然设置20，要求很高啊|
|-XX:MaxHeapSize=2147483648|2147483648  = 1024 * 1024 * 1024 * 2 = 2048M = 2G，堆heap的最大值|
|-XX:MaxNewSize=1287651328|堆中最大New size 1287651328 = 1024 * 1024 * 1228 = 1228M = 1.12G|
|-XX:MinHeapDeltaBytes=1048576|1048576 = 1024 * 1024 表示当我们要扩容或者缩容的时候，决定是否要做或者尝试扩容的时候最小扩/缩多少，默认为192K。使用G1时，在当新生代无法存储新创建的对象时，会先做一次扩容，扩容大小就是MinHeapDeltaBytes的值，如果还存不下就做GC|
|-XX:NumberOfGCLogFiles=10|滚动日志的数量|
|-XX:+PrintGC|+PrintGC（或者-verbose:gc）开启了简单GC日志模式，为每一次新生代（young generation）的GC和每一次的Full GC打印一行信息。|
|-XX:+PrintGCDateStamps|输出GC的时间戳（以日期的形式，如 2013-05-04T21:53:59.234+0800|
|-XX:+PrintGCDetails|打印GC详细信息|
|-XX:+PrintGCTimeStamps|输出GC的时间戳（以JVM启动到当期的总时长的时间戳形式）|
|-XX:+UseCompressedClassPointers|类指针压缩,会压缩klass pointer 这部分的大小，由8字节压缩至4字节，间接的提高内存的利用率。|
|-XX:+UseCompressedOops|开启普通对象指针压缩,高内存的利用率|
|-XX:+UseFastUnorderedTimeStamps|实验,使用只支持时间戳的平台不稳定时间|
|-XX:+UseG1GC|使用垃圾优先(G1)回收器|
|-XX:+UseGCLogFileRotation|开启滚动日志|

### Command line:

|命令行  |对应参数的理解       |
|:-----|:---                 |
|-Xms2g|设置初始 Java 堆大小,单位默认是字节，可以使用k,m,g|
|-Xmx2g|设置最大 Java 堆大小,单位默认是字节，可以使用k,m,g|
|-XX:+UseG1GC|使用垃圾优先(G1)回收器|
|-XX:MaxGCPauseMillis=20|设置回收器的最大停顿时间，这是一个比较简单的目标，JVM将尽最大努力去实现它，默认是200毫秒，它竟然设置20，要求很高啊|
|-XX:InitiatingHeapOccupancyPercent=35|设置触发全局并发标记周期的 Java 堆占用率阈值。默认占用率是整个 Java 堆的 45%。调小这个默认值，希望提前触发全局标记降低full gc的风险|
|-XX:+ExplicitGCInvokesConcurrent|字面直译显式GC调用并发，通常我们不会显式地调用 System.gc()。但是一些情况下，比如使用了 Direct 内存，为了使得其（堆外内存）能够被及时回收，我们会通过显式调用 System.gc() 触发 full gc。但是 full gc 又会导致 stw，所以我们希望显式的对 GC 的触发也是并发执行的，这便是 -XX:ExplicitGCInvokesConcurrent 的意义|
|-Xloggc:/xxx/log/kafka-node/kafkaServer-gc.log|日志文件的输出路径|
|-verbose:gc|表示输出虚拟机中GC的详细情况|
|-XX:+PrintGCDetails|打印GC详细信息|
|-XX:+PrintGCDateStamps|输出GC的时间戳（以日期的形式，如 2013-05-04T21:53:59.234+0800|
|-XX:+PrintGCTimeStamps|输出GC的时间戳（以JVM启动到当期的总时长的时间戳形式）|
|-XX:+UseGCLogFileRotation|开启滚动日志|
|-XX:NumberOfGCLogFiles=10|滚动日志的数量|
|-XX:GCLogFileSize=100M|gc日志文件大小|