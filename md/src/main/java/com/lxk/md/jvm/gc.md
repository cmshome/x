```
-XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:/home/data/log/x/x-gc.log.$(date +%Y%m%d%H%M) 
-XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=1 -XX:GCLogFileSize=64M
```

```
2021-07-02T16:02:50.350+0800: 53.625: [GC pause (G1 Evacuation Pause) (young), 0.0269955 secs]
   [Parallel Time: 22.7 ms, GC Workers: 8]
      [GC Worker Start (ms): Min: 53625.8, Avg: 53627.0, Max: 53628.9, Diff: 3.1]
      [Ext Root Scanning (ms): Min: 0.0, Avg: 1.0, Max: 4.9, Diff: 4.9, Sum: 8.4]
      [Update RS (ms): Min: 0.0, Avg: 0.6, Max: 1.3, Diff: 1.3, Sum: 5.1]
         [Processed Buffers: Min: 0, Avg: 5.9, Max: 19, Diff: 19, Sum: 47]
      [Scan RS (ms): Min: 0.3, Avg: 2.4, Max: 3.1, Diff: 2.7, Sum: 19.4]
      [Code Root Scanning (ms): Min: 0.0, Avg: 3.7, Max: 12.0, Diff: 12.0, Sum: 29.5]
      [Object Copy (ms): Min: 2.7, Avg: 11.9, Max: 15.7, Diff: 13.0, Sum: 95.1]
      [Termination (ms): Min: 0.0, Avg: 1.4, Max: 1.7, Diff: 1.7, Sum: 11.5]
         [Termination Attempts: Min: 1, Avg: 55.8, Max: 111, Diff: 110, Sum: 446]
      [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
      [GC Worker Total (ms): Min: 19.3, Avg: 21.1, Max: 22.4, Diff: 3.1, Sum: 169.2]
      [GC Worker End (ms): Min: 53648.1, Avg: 53648.2, Max: 53648.2, Diff: 0.0]
   [Code Root Fixup: 1.9 ms]
   [Code Root Purge: 0.0 ms]
   [Clear CT: 0.4 ms]
   [Other: 2.1 ms]
      [Choose CSet: 0.0 ms]
      [Ref Proc: 0.7 ms]
      [Ref Enq: 0.0 ms]
      [Redirty Cards: 0.4 ms]
      [Humongous Register: 0.1 ms]
      [Humongous Reclaim: 0.0 ms]
      [Free CSet: 0.5 ms]
   [Eden: 112.0M(112.0M)->0.0B(119.0M) Survivors: 16.0M->9216.0K Heap: 152.2M(1024.0M)->50.5M(1024.0M)]
 [Times: user=0.12 sys=0.01, real=0.02 secs] 
```