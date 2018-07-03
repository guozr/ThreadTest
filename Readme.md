# 专业课程设计I

## 实验题目
### 教师对学生的项目评分—进程的同步与互斥

在一个班上有S个学生。每个学生都要做一个项目，每一个项目由K个老师一起评分。总共有M个老师。每个老师最多给N个项目评分。其中，S\*K <= M*N。

在项目结束后，老师们提供T分钟用来检查学生们的项目。
检查每一个学生的项目需要用时D分钟。其中，T>D。每一个学生的项目由K个老师共同来检查。
在T分钟的时间段内，学生可以在任何时间进入教室(random)，除了在最后的D分钟内。
所有的老师一直保持工作状态直到他检查完N个项目或者是T分钟过去后。
T分钟过去后，所有的老师和同学都必须离开教室。
另外，在T分钟结束前的D分钟内(即在最后的D分钟内)，如果有任何老师或者是学生都处在没有任务的状态下，
都必须离开教室，因为已经没有时间让他完成任务了(因为一个项目检查的时间是整整D分钟)。

该课题主要考察操作系统中PV操作知识点，涉及到多进程管理和死锁的相关知识。


## 实验要求
### 基本要求
用一个程序来模拟上面描述的作业检查过程。
每一个学生和每一个老师应该用不同的线程来完成。
可以选用C、C++和Java作为开发语言，但是考虑到专业课程设计I的实验大纲，请尽可能使用Java语言。

### 提高要求
(1) 考虑到跨平台的特性，请尽量使用posix线程标准(采用该标准有额外的加分)；

(2) 实现良好的图形用户界面；

(3) 在程序演示过程中能清晰的展示多个学生线程和多个老师线程的同步和互斥流程。

### 设计提示
1、当一个学生进入教室后，他立即开始找K个没有任务的老师
(一次找一个老师，假如没有空闲的老师，则等到有老师为止)，找齐K个老师之后给老师检查，
然后离开教室。刚开始每一个老师都是处于空闲状态直到他被学生找到，被学生找到后只能等待，
直到学生找齐K个老师(在等待学生找其他老师的时间里，他是不能接受其他同学检查作业的请求的)，
当学生找齐K个老师后，老师们执行完检查任务，然后重新变成空闲状态。
每个老师在总共检查了N个学生的作业后，离开教室。

注意，并不保证所有的学生都在T分钟的时间段内完成了检查，还要注意不能发生死锁问题。

2、学生线程和老师线程：
  一个学生线程执行下面的步骤：
  * (1) 选择一个进入的时间(random()%(T-D))minutes ；
  * (2) 进入教室；
  * (3) 选择K个空闲的老师，若找不齐，则等待；
  * (4) 找齐后，做D分钟的检查；
  * (5) 检查完毕离开教室。
  
  一个老师线程执行下面的步骤：
  * (1) 在教室中空闲；
  * (2) 直到被一个学生选择后等待(所有K个老师聚集)；
  * (3) 等待结束项目检查开始；
  * (4) 做D分钟的项目检查；
       * (1)—(4)步骤重复N次……
  * (5) N次检查完毕或者T分钟时间到，离开教室。

3、注意事项
* (1) 在剩余时间小于D时，一个已创建的学生线程只能执行第(5)步，不能再创建新的学生线程；
* (2) 在剩余时间小于D时，一个老师进程只能在执行(3)—(4)步或者直接执行第(5)步；
* (3) 对于所有合理的S, M, K, N, T, D数值(这些数必须都是正整数并且满足条件：S*K<=M*N and T>D)，你的程序都能够运行成功；
* (4) 你的程序对所有的时间安排策略都必须运行成功。例如不管线程的相对速度，例如要把握好sleep的毫秒数；
* (5) 在每一个线程的生命周期内，每一步都要有一个合理的说明信息，来表明这个线程中包括哪个老师，哪个学生，进行到什么程度了；
* (6) 要特别注意防范死锁问题的发生。

以上六点都是最后评分的要点。





