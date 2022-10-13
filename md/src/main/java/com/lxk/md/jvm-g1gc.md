## Java内存区域  或  JVM 内存结构
![JVM内存区域](https://img-blog.csdnimg.cn/d58c4ed17bb940668e51f989239a6e39.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA6K-35Y-r5oiR5aSn5biI5YWEXw==,size_20,color_FFFFFF,t_70,g_se,x_16)
`线程共享`的
* 堆
* 方法区
* 直接内存
* (非运行时数据区的一部分)  
`线程私有`的
* 程序计数器
* 虚拟机栈
* 本地方法栈
`JDK 8 版本之后方法区（HotSpot 的永久代）被彻底移除了（JDK7 就已经开始了），取而代之是元空间`    
  ![新的一个图](https://img-blog.csdnimg.cn/7a382135156e4c789f1175e0773f7fb3.jpg?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA6K-35Y-r5oiR5aSn5biI5YWEXw==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)
## 识别垃圾
* 1，引用计数法（Reference Counting）
* 2，可达性分析，又称引用链法（Tracing GC）
## GC算法
* 1，Mark-Sweep（标记-清除）
产生碎片
* 2，Copying（复制）
空间利用率不高的缺点，另外就是存活对象比较大时复制的成本比较高
* 3，Mark-Compact （标记-整理）
好理论，解决1，2 gc算法的不足
* X，分代收集算法
正规的算法，就是1、2、3，这个分代算是灵活运用上面的三种。  
一般将堆分为新生代和老年代。  
新生代使用: 复制算法  
老年代使用: 标记 - 清除 或者 标记 - 整理 算法  

## G1GC
* G1 GC是Java HotSpot虚拟机的`低暂停`，`服务器风格`的`分代`垃圾收集器，适用于具有大内存的多处理器计算机。G1 GC使用`并发`(concurrent)和`并行`(parallel)阶段来实现其目标暂停时间并保持良好的吞吐量。
* 全堆操作（例如全局标记）与应用程序线程并行执行。这样可以防止与堆或活动数据大小成比例的中断。
* G1是一个有整理内存过程的垃圾收集器，在回收垃圾的时候会压缩存活对象。不会产生很多内存碎片。
* G1的Stop The World(STW)更可控，G1在停顿时间上添加了预测机制，用户可以指定期望停顿时间。
## G1GC的设计目标
* 与应用线程同时工作，几乎不需要stop-the-world(与CMS类似);
* 整理剩余空间,不产生内存碎片；（CMS只能在full-GC时，用stop-the-world整理碎片内存）
* GC停顿更加可控；
* 不牺牲系统的吞吐量；
* gc不要求额外的内存空间(CMS需要预留空间存储浮动垃圾)；
## 什么是STW
不管选择哪种GC算法，stop-the-world都是不可避免的。Stop-the-world意味着从应用中停下来并进入到GC执行过程中去。一旦Stop-the-world发生，`除了GC所需的线程外，其他线程都将停止工作，中断了的线程直到GC任务结束才继续它们的任务`。GC调优通常就是为了改善stop-the-world的时间
## G1是要替换掉CMS
CMS算是分代收集器，在JDK9 被标记弃用，JDK14 被删除，G1GC则是分区收集器，在某些方便弥补了CMS的不足，比如，`CMS使用的是mark-sweep算法`，会产生内存碎片；然而`G1基于copying算法`，高效的整理剩余内存,而不需要使用free-list去管理内存碎片。另外，G1提供了更多手段，以达到对gc停顿时间可控。  
![GC收集器大分类](https://img-blog.csdnimg.cn/208f9100fbb44f2b925f76b49783ae5c.jpg?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA6K-35Y-r5oiR5aSn5biI5YWEXw==,size_20,color_FFFFFF,t_70,g_se,x_16)
目前在 Hotspot VM 中主要有分代收集和分区收集两大类，具体可以看下面的这个图，不过未来会逐渐向分区收集发展。
## 并发(concurrent)和并行(parallel)
* 在单CPU系统中，系统调度在某一时刻只能让一个线程运行，通过不断切换需要运行的线程让其运行的方式就叫并发(concurrent)
* 在多CPU系统中，可以让两个以上的线程同时运行，这种可以同时让两个以上线程同时运行的方式叫做并行(parallel)
* 并发：意味着应用程序同时（并发）在一项以上的任务上取得进展，尽力压榨一个CPU的处理能力，抢占更多的执行时间片
* 并行：应用程序将其任务分解为较小的子任务，这些子任务可以并行处理，例如在多个CPU上同时进行。重点是多个CPU。
## G1GC Region
* 传统的GC收集器将连续的内存空间划分为新生代、老年代和永久代（JDK 8去除了永久代/PermGen，引入了元空间Metaspace），这种划分的特点是各代的存储地址（逻辑地址）是连续的。
* G1 GC是一个`区域化`的代垃圾收集器
* G1也是分代管理内存的，他的各代存储地址是不连续的，每一代都使用了n个不连续的大小相同的Region，每个Region占有一块连续的虚拟内存地址。
* G1最大的特点就是高效的执行回收，优先去执行那些大量对象可回收的区域（region）
* G1使用了`停顿预测模型`，来满足用户设定的gc停顿时间，根据用户设定的目标时间，g1会自动的选择哪些region要清除，一次清除多少个region。
* G1从多个region中复制存活的对象，然后集中放入一个region中，同时整理、清除内存(copying收集算法)。
## G1GC 停顿预测模型
* 机器学习听说过吧，这个G1GC里面这个`停顿预测模型`，就类似这个`机器学习`，自行训练，自己判断每次回收谁，回收多少。
* G1 uses a pause prediction model to meet a user-defined pause time target and selects the number of regions to collect based on the specified pause time target.
* G1 GC是一个响应时间优先的GC收集器，它与CMS最大的不同是，用户可以设定整个GC过程的期望停顿时间，参数-XX:MaxGCPauseMillis指定一个G1收集过程目标停顿时间，默认值200ms，不过它不是硬性条件，只是期望值。那么G1怎么满足用户的期望呢？就需要这个停顿预测模型了。G1根据这个模型统计计算出来的历史数据来预测本次收集需要选择的Region数量，从而尽量满足用户设定的目标停顿时间。
## G1GC内部三种形式的GC
### Young GC (STW)
Eden区耗尽的时候就会触发新生代收集，新生代垃圾收集会对整个新生代（E + S）进行回收
* 新生代垃圾收集期间，整个应用`STW`
* 新生代垃圾收集是由`多线程并发`执行的
* 通过控制年轻代的region个数，即年轻代内存大小，来控制young GC的时间开销。
* 新生代收集结束后依然存活的对象，会被疏散evacuation到n(n>=1)个新的Survivor分区，或者是老年代。
### Mixed GC (STW)
* Stop The World
* 选定所有年轻代里的Region，外加根据global concurrent marking统计得出收集收益高的若干老年代Region。在用户指定的开销目标范围内尽可能选择收益高的老年代Region
* Mixed GC不是full GC，它只能回收部分老年代的Region，如果mixed GC实在无法跟上程序分配内存的速度，导致老年代填满无法继续进行Mixed GC，就会使用serial old GC（full GC）来收集整个GC heap
![什么时候Mixed GC](https://img-blog.csdnimg.cn/b12115ae79a746a2ae20aa5e456fe047.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA6K-35Y-r5oiR5aSn5biI5YWEXw==,size_20,color_FFFFFF,t_70,g_se,x_16)
### FULL GC (STW)
* Stop The World
* 如果mixed GC实在无法跟上程序分配内存的速度，导致老年代填满无法继续进行Mixed GC，就会使用serial old GC（full GC）来收集整个GC heap。
* G1是不提供full GC的。这个serial old GC full gc是单线程的（在Java 8中）并且非常慢，因此应避免在G1 gc的时候出现这个full gc 
* 不能觉得用了G1gc收集器之后，Java heap里面的gc不是young gc 就mixed gc，还有这个full gc呢
* `G1GC发生了Full GC，就说明当前程序的运行是问题的`，就得考虑为什么会Full GC 了
## G1GC 支持的配置参数以及默认值
![G1GC 支持的配置参数以及默认值](https://img-blog.csdnimg.cn/88504a1206d44d99a1253fc264e3f676.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA6K-35Y-r5oiR5aSn5biI5YWEXw==,size_20,color_FFFFFF,t_70,g_se,x_16)
![G1GC 支持的配置参数以及默认值](https://img-blog.csdnimg.cn/ac64c8dbab6b46c5becbcf6b67743d92.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA6K-35Y-r5oiR5aSn5biI5YWEXw==,size_20,color_FFFFFF,t_70,g_se,x_16)
## es7的jvm参数配置
-Xms4g -Xmx4g -XX:+UseG1GC -XX:G1ReservePercent=25 -XX:InitiatingHeapOccupancyPercent=30    
-XX:InitiatingHeapOccupancyPercent=45    
设置触发全局并发标记周期的 Java 堆占用率阈值。默认占用率是整个 Java 堆的 45%。es7调小这个默认值，希望提前触发全局标记降低full gc的风险  
-XX:G1ReservePercent=10  
设置作为空闲空间的预留内存百分比，以降低目标空间溢出的风险。默认值是 10%。增加或减少百分比时，请确保对总的 Java 堆调整相同的量，es7调大这个值，扩大预留空间。
## G1 调优建议
* `不要显式的设置新生代的大小`（用Xmn或-XX:NewRatio参数）
* -XX:ConcGCThreads=n 设置并行标记的线程数。将n设置为并行垃圾回收线程数 (ParallelGCThreads) 的 1/4 左右。
* -XX:ParallelGCThreads=n 设置 STW 工作线程数的值。将 n 的值设置为逻辑处理器的数量。如果逻辑处理器不止八个，则将n的值设置为逻辑处理器数的 5/8 左右。
## G1的推荐使用场景
* G1的首要重点是为运行需要大堆且GC延迟有限的应用程序的用户提供解决方案。这意味着堆大小约为6GB或更大，并且稳定且可预测的暂停时间低于0.5秒。
* 如果当前具有CMS或ParallelOld垃圾收集器运行的应用程序具有以下一个或多个特征，则将其切换到G1将非常有益。
* 超过50％的Java堆被实时数据占用。
* 对象分配率或提升率差异很大。
* 不必要的长时间垃圾收集或压缩暂停（长于0.5到1秒）
## CMS 和 G1 对比
![cms和g1对比](https://img-blog.csdnimg.cn/f601e7d449c74418828ee17710b85973.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA6K-35Y-r5oiR5aSn5biI5YWEXw==,size_20,color_FFFFFF,t_70,g_se,x_16#pic_center)
`CMS不能解决Old区的碎片化问题，导致一定会出现长时间停顿的FullGC`
## G1与CMS相比的优势
* 1、并行和并发：G1能充分利用CPU、多核环境下的硬件优势，使用多个CPU（CPU或者CPU核心）来缩短Stop-The-World停顿时间。部分其他收集器原本需要停顿Java线程执行的GC动作，G1收集器仍然可以通过并发的方式让java程序继续执行。
* 2、分代收集：虽然G1可以不需要其他收集器配合就能独立管理整个GC堆，但是还是保留了分代的概念。它能够采用不同的方式去处理新创建的对象和已经存活了一段时间，熬过多次GC的旧对象以获取更好的收集效果。
* 3、空间整合：与CMS的“标记–清理”算法不同**，G1从整体来看是基于“标记-整理”算法实现的收集器；从局部上来看是基于“复制”算法实现的。
* 4、可预测停顿：这是G1相对于CMS的另一个大优势，降低停顿时间是G1和ＣＭＳ共同的关注点，但Ｇ１除了追求低停顿外，还能建立可预测的停顿时间模型，能让使用者明确指定在一个长度为M毫秒的时间片段内。
## 什么时候GC
* 内存不够用了
## 对象何时进入老年代
* 1，迭代年龄判断 (都知道)  
  这个针对的是一个对象长时间存在引用关系，最终在young区来回copy，当年纪大了，就进去old区了。
* 2，大对象直接进入老年代（不常见）  
  超过单个region区域一半大小的对象，为避免在young区来回的copy，在new的时候直接给安排到old区。
* 3，YoungGC之后需要移区的对象放不下（基本想不到）  
  一般推荐使用G1GC，那都是堆比较大的程序，多半都是处理大量数据的程序，既然是大量数据，   
  那么young区满的速度是贼快的，刷刷刷的就满了。若程序运行支持，前脚创建完对象，后脚立马就使用完了，    
  那么YoungGC可以很快的回收young区的内存，Old区也不会出现大量的对象。  
  这个时候Young区和Old区比例，应该是Young占比比较大才对。  
  问题就是对象创建完之后，不是很快的结束引用，这个对象就要在Young区来回的copy，当s区不够的时候，   
  他就会被安排到Old区，对象一旦被copy到old区之后，那就只能被Mixed GC或者Full GC来回收了，  
  但是Mixed GC又不是完全的回收Old区的所有没有被引用的对象，他只是挑选一部分回收价值大的region去回收。  
  问题的关键就在这，  
  Mixed GC
* 1，不是频繁的执行的，
* 2，他还不是回收所有能回收的。当堆内存大量存在可被回收的对象的时候，就会出现浪费的现象。  
  一直憋着，直到触发Full GC，STW，来一次大清理。
## G1GC发生Full GC之后往哪思考
### 堆里的数据是什么
* 写代码的人知道程序中什么数据的对象占比最大
* 现在的问题就是对象积压而不是泄露。因为像内存泄露之类的，是会有OOM的异常的。
* new 对象的速度是不是杠杠滴，而且特定时间段，数据量飙升且巨大的时候才频繁的Full GC。
### 为什么会一直存在引用
* 1，数据写不出去，没办法释放引用  
  比如我这程序是处理完数据之后要写es，写kafka，瞬间数据量就急剧飙升，但是写出的速度不变的。    
  单纯的增加程序的运行内存，程序内部的缓存都是不行的。   
  必须想办法加快数据的流出，比如增加es的data节点，或者es机器换固态硬盘，加机器。  
  我这写es慢的解决是es的data节点换了固态硬盘。  
  写kafka慢，是因为jvm参数设置的不对，添加了 -Xmn，限制了G1GC的黑科技运行。
* 2，思考什么数据、什么情况下会进入到Old区  
  因为一旦到了Old区，相比Young区的回收，Old区的回收，速度就不会很快，量也不会很大。
  我这就是设置了 -Xmn之后，限制了New size的大小，程序在数据量急剧飙升的时候，E区会极其频繁的new对象。  
  但是还没来得及释放，就得被copy到O区了，一旦被copy到O区就发生了上述的问题。
## G1GC实际调优例子
测试程序的jvm参数设置是 -Xmn4G -Xms4G -Xmn64M  -XX:+UseG1GC  
![设置Xmn的Heap情况](https://img-blog.csdnimg.cn/e9fe1ee5327e4a818ce11143dd7d271b.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA6K-35Y-r5oiR5aSn5biI5YWEXw==,size_20,color_FFFFFF,t_70,g_se,x_16)  
分析：  
* 这使用了G1GC的收集器，但是使用了-Xmn 设置了 NewSize的大小
* 看这个进程的gc情况，每秒都进行10次上下的YGC，很频繁。
* 看OU是在默默的一点点的增长着呢，预计一下：长着长着，就会塞不下，然后触发full gc了
  删除掉 -Xmn设置之后的heap情况  
  <img alt="删除掉 -Xmn设置之后的heap情况" height="450" src="https://img-blog.csdnimg.cn/51f70f396f484a25bc1786ae48c1c61e.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA6K-35Y-r5oiR5aSn5biI5YWEXw==,size_16,color_FFFFFF,t_70,g_se,x_16" width="300"/>
分析：  
* 删掉 -Xmn的设置之后的堆内存分布
* newSize最大可以到 2456MB
* 4096(Heap) = 2576(E) + 4(S) + 1516(O)
* 自动分配之后，Y区占比变大，占比 62%
![删除掉 -Xmn设置之后的GC情况](https://img-blog.csdnimg.cn/be5da17e3dee4689950eb3b369ef2bf4.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA6K-35Y-r5oiR5aSn5biI5YWEXw==,size_20,color_FFFFFF,t_70,g_se,x_16)
* 看YGC列，执行次数明显变少几秒钟一次YGCGCT也增长的很慢
* 再观察OU，有增有减。说明有Mixed GC在执行
* SEO各区的大小是浮动的
* 为啥 S0C 和 S0U一直是0？  
`结论`：  
用这个例子，实际验证了 G1GC的调优建议中的`不要设置New size的大小`，因为这个会限制G1GC内部的`停顿预测模型`的工作，他牛就牛在这个模型上。
## 对比一下CMS GC
![删除掉 -Xmn设置之后的GC情况](https://img-blog.csdnimg.cn/e079fab6e95e43cf9860d824852dfe5a.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA6K-35Y-r5oiR5aSn5biI5YWEXw==,size_20,color_FFFFFF,t_70,g_se,x_16)
S0 和S1是交替在使用，在YGC的时候，有个互相copy的过程  
