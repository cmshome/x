### Flink概述
Flink是一个对有界和无界数据流进行状态计算的分布式处理引擎和框架。主要用来处理流式数据。其代码主要是由 Java 实现，部分代码由 Scala实现。  
在Flink的世界里，一切数据都是流式的：  
离线数据(批数据)是有界(bounded)的流；  
实时数据(流数据)是无界(unbounded)的流。  
Flink既可以处理有界的批量数据集，也可以处理无界的实时流数据，为批处理和流处理提供了统一编程模型。  
如果把storm看做第一代流处理框架、把Spark Streaming (微批处理)看做第二代，那么Flink称得上是第三代流处理框架，并且是集大成者。  
Apache Flink 是为分布式、高性能、随时可用以及准确的流处理应用程序打造的开源流处理框架。”Flink 不仅能提供同时支持高吞吐和 exactly-once 语义的实时计算，还能提供批量数据处理。

### 名称由来
起源于德国的科研项目，在德语中，flink 一词表示快速和灵巧。项目采用一只松鼠的彩色图案作为 logo，这不仅因为松鼠具有快速和灵巧的特点，还因为柏林的松鼠有一种迷人的红棕色。

### Flink 安装
官网地址：[官网地址链接][1]  
中文文档：[中文文档链接][2]  
下载地址：[下载地址链接][0]  
下载完之后，解压：tar -zxvf flink-1.12.2-bin-scala_2.12.tgz  
启动命令：./start-cluster.sh 在Linux下，默认就单机启动，集群的再说。  
启动之后，访问Flink的地址，http://1.1.1.1:1/  


### more
more: [这个md写的太不得劲了，换个地方写。][3]



*******************
[0]: https://mirrors.bfsu.edu.cn/apache/flink/
[1]: https://flink.apache.org/
[2]: https://flink.apache.org/zh/flink-architecture.html
[3]: https://flink.apache.org/zh/flink-architecture.html


