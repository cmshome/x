总结常用的Linux下的命令，不论简单与否，弄个记事本备忘录啥的，忘记了，也有个在线的文档可以看，我这就简单一个table到底了。 

## 常用命令列表

| 命令                                                                                         |含义|
|:-------------------------------------------------------------------------------------------|:-----|
| ln -s 源文件s 目的文件d                                                                           |创建软链接，d相当于s的快捷方式，ln 相当于link的缩写|
| ln -snf 另一个源文件f 目的文件d                                                                      |修改软链接，d由s的快捷方式变成指向f文件  nf 即 new file|
| cd -                                                                                       |回退到之前所在到目录位置|
| cat /etc/redhat-release                                                                    |查看Linux 服务器的内核版本信息|
| ulimit -a                                                                                  |显示当前的各种用户进程限制|
| adduser elasticsearch                                                                      |新增用户|
| passwd elasticsearch                                                                       |给用户 elasticsearch 设置密码，还不能太简单，不然系统还不同意呢！|
| chown -R elasticsearch:elasticsearch elasticsearch-7.8.0/                                  |将对应的文件夹权限赋给该用户|
| su elasticsearch                                                                           |切换用户，之后输入密码验证成功之后，切换用户。|
| ./elasticsearch -d                                                                         |使用后台启动方式|
| top -Hp pid                                                                                |可以查看某个进程的线程信息<br>-H 显示线程信息，-p指定pid|
| printf "%x\n" 5010 5092 5093                                                               |将 PID 转为 16进制|
| grep 关键词 -A2 -B2 fileName                                                                  |在fileName文件中过滤关键词，并展示关键词所在行的 after 2行和 before 2行，直接-5,就是展示关键词所在行的前后n行|
| jstack pid &#124; grep '0x13e4' -A10                                                       |过滤程序的堆栈信息， 0x13e4 是16进制的线程id|
| top -c                                                                                     |shift + m 按照内存排序<br>shift + p  按照cpu排序<br>b + x 正在运行的和排序的那一列加黑<br>按键盘数字“1”，可监控每个逻辑CPU的状况<br>通过”shift + >”或”shift + <”可以向右或左改变排序列|
| ntpdate 1.cn.pool.ntp.org                                                                  |校正服务器时间同网络时间一致|
| netstat -tunlp &#124; grep 8088                                                            |查看端口被占用否|
| cd /proc/pid                                                                               |然后ls -al 就看到exe和cwd，2个软连接指向运行关键|
| ps aux                                                                                     |查看全部多个的|
| ps aux &#124; grep -E 'kthreadd &#124; migration/0'                                        |查找多个字符串的匹配（grep -E相当于egrep）|
| ps -ef &#124; grep xxxx &#124; grep -v "grep" &#124; wc -l                                 | 只有 ps -ef &#124; grep xxxx会出2个结果，一个是目的pid，还有一个就是grep这个pid，<br>后面再接 &#124; 继续grep -v 就是剔除掉，grep这个pid。只留下我们想ps的目的pid。<br>后面又继续接管道，wc -l，就是统计个数。|
| ps -ef &#124; grep collector.cluster.xml &#124; grep -v "grep" &#124; awk '{print$2}'      |awk 后面跟的是英文单引号，这个md，会自动变成中文的，直接复制的时候还的改改<br>输出这个目的pid，$1 是用户 $2是pid 。。。。，依次是ps的结果列|
| ps -ef &#124; grep storm &#124; grep -v "grep" &#124; awk '{print$2}' &#124; xargs kill -9 |批量关闭ps出来的进程 xargs 把前面管道的输出当作后面命令的输入|
| echo $JAVA_HOME                                                                            |查看Java的环境变量|
| less /etc/profile                                                                          |查看linux服务器环境变量|
| less /etc/hosts                                                                            |查看linux hosts文件中映射的配置|
| hostname                                                                                   |显示主机名称|
| hostname -i                                                                                |显示主机IP|
| export JAVA_HOME=/usr/java/jdk1.8.0_131                                                    |设置环境变量|
| scp license.dat 1.1.1.1:/home/lxk <br>scp -r root@ip:/home/lxk/                            |不同服务器之间复制东西<br>复制个文件夹及其所有子 命令中间  -r  就OK了|
| crontab -e                                                                                 |设置定时任务，执行指定的脚本<br>然后使用那个表达式就可以执行了，比如每分钟一次    */1 * * * * sh /home/a.sh|
| tar zcvf lxk.zip lxk                                                                       |压缩文件夹lxk，生成lxk.zip|
| tar xzvf lxk.zip                                                                           |解压文件|
| find dir "\*kafka_\*" &#124; head -3  &#124; grep -o '\kafka[^\n]*'                        |在目录下，根据名称查找文件，<br>grep 过滤，-n，就是展示结果前n行<br>grep -o 只输出匹配的具体字符串,匹配行中其他内容不会输出|
| vi 之 ^                                                                                     |移动到光标所在行的"行首"|
| vi 之 $                                                                                     |移动到光标所在行的"行尾"|
| vi 之 ctrl + g                                                                              |列出光标所在行的行号|
| vi 之#G                                                                                     |（先输入15，再 shift + g = 15+G）就跳转到15行了<br>shift G跳到文章尾部，   1+ shift +g就跳到第一行了。首尾互跳|
| vi 之esc再冒号再set nu 或set number                                                              |文件中的每一行前面列出行号|
| vi 之esc再冒号再set nonu                                                                        |关闭行号展示|
| vi 之esc再冒号再#                                                                               |如输入数字15，再回车，就会跳到文章的第15行|
| vi 之esc再/关键字                                                                               |搜索关键字，n 下一个，N 上一个|
| vi 之o 小写字母 欧 o                                                                             |插入新的一行，从行首开始输入|
| vi 之x                                                                                      |每按一次，删除光标所在位置的"后面"一个字符|
| vi 之X                                                                                      |删除光标所在位置的"前面"一个字符|
| vi 之dd                                                                                     |删除光标所在行|
| vi 之u                                                                                      |回退，类似ctrl + z|
| vi 之yy                                                                                     |复制光标所在行到缓冲区|
| vi 之yw                                                                                     |将光标所在之处到字尾的字符复制到缓冲区|
| vi 之p                                                                                      |将缓冲区内的字符贴到光标所在位置|
| vi 之h、j、k、l                                                                                |控制光标左、下、上、右移一格|
| vi 之ctrl + b                                                                               |屏幕往"上"移动一页|
| vi 之ctrl + f                                                                               |屏幕往"下"移动一页|
| vi 之ctrl + u                                                                               |屏幕往"上"移动半页|
| vi 之ctrl + d                                                                               |屏幕往"下"移动半页|
| cd ~                                                                                       |到用户的目录  /Users/fang/|
| cd /                                                                                       |到根目录下   /|
| ls -al                                                                                     |直接ls，就是查看当前文件夹下的可见文件，<br>-a 查看所有，包括隐藏文件  <br>-l   就是文件的详细情况|
| ll -alrt                                                                                   |-t 最新的文件会排在上面 <br> -r 输出倒序|
| rm -rf xx                                                                                  |强制删除所有及其子文件|
| rm -rf . *                                                                                 |强制删除所有隐藏的文件|
| clear 或者  control + L                                                                      |清屏|
| > sda.log                                                                                  |清空文件|
| >> sda.log                                                                                 |追加到文件末尾|
| cp -rf dir1 dir2                                                                           |强制复制目录及其子目录所有|
| ps -ef &#124; grep mongo                                                                   |筛选单个线程|
| chmod u+x file                                                                             |执行权限|
| mkdir -p /home/lxk/test                                                                    |直接创建目录，即使父目录不存在|
| mkdir -p /home/lxk2/test1{1,2,3}                                                           |一下子创建3个目录出来|
| tail -20f instance.log                                                                     |动态看最后n行|
| head -n instance.log                                                                       |看最开始的n行|
| sed -n '1,100p' file1  >> newfile                                                          |从第几行到第几行的信息，输出到新文件去|
| sed -i "s/old/new/g" file                                                                  |批量替换文件中的字符的命令<br>这儿不能直接复制，引号得改成英文的<br>例如：sed -i "s/old_ip/new_ip/g" *.sh|
| du -sh dir                                                                                 |看某个文件的大小|
| df -h                                                                                      |查看整个系统的存储使用情况|
| free -g                                                                                    |查看系统的内存使用情况|
| egrep -i '(free)' zxc*                                                                     |在当前目录下搜素括号内的内容，后面是跟着文件名称|
| for i in {1..10}; do cat *.xxx >> /home/test/lx3k.xxx; done                                |一个简单到脚本for循环复制一个文件|
| date -s 11/03/2019 <br>date -s 10:33:00                                                    |修改服务器时间|
| date -d "2020-11-23 10:44:00" +%s                                                          |把日期转时间戳|
| date -d @1606099440                                                                        |时间戳转日期|
| ls -l  &#124;  wc -l<br> ls -l &#124; grep “^-” &#124; wc -l                               |统计文件个数|
| shift + g                                                                                  |命令行下，直接跳转到文件的末尾|
| sl                                                                                         |本来要ls的，一不小sl了，就在页面上动态打印一个小火车从右边跑到左边，就完事儿了。|



## top 命令每列含义

|列的名称        |含义|
|:----          |:-----|
|PID            |进程id|
|USER           |进程所有者|
|PR             |进程优先级|
|NI             |nice值。负值表示高优先级，正值表示低优先级|
|VIRT           |进程使用的虚拟内存总量，单位kb。VIRT=SWAP+RES|
|RES            |进程使用的、未被换出的物理内存大小，单位kb。RES=CODE+DATA
|SHR            |共享内存大小，单位kb|
|S              |进程状态。<br>D=不可中断的睡眠状态 <br>R=运行 <br>S=睡眠 <br>T=跟踪/停止 <br>Z=僵尸进程|
|%CPU           |上次更新到现在的CPU时间占用百分比|
|%MEM           |进程使用的物理内存百分比|
|TIME+          |进程使用的CPU时间总计，单位1/100秒|
|COMMAND        |进程名称（命令名/命令行）|
|top命令详解链接 |[top命令详解链接][2]|


- [link 2 github][0]  
- [link 2 csdn][1]

*******************
[0]: https://github.com/cmshome/x/blob/master/md/src/main/java/com/lxk/md/Linux%E5%B8%B8%E7%94%A8%E5%91%BD%E4%BB%A4.md
[1]: https://lixuekai.blog.csdn.net/article/details/112243730
[2]: https://lixuekai.blog.csdn.net/article/details/105044501