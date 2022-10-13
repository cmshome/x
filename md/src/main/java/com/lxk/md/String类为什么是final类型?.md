# String类为什么是final类型?
首先，先得清楚 final 这个关键字。  
final的出现就是为了为了不想改变，而不想改变的理由有两点：设计(安全)或者效率。
final 修饰的类是不被能继承的，所以 final 修饰的类是不能被篡改的。  
了解了这一点，我们再看看问题：

## 从设计安全上讲
* 确保它们不会在子类中改变语义。String类是final类，这意味着不允许任何人定义String的子类。
换言之，
如果有一个String的引用，它引用的一定是一个String对象，而不可能是其他类的对象。
* String 一旦被创建是不能被修改的。类加载器要用到字符串，不可变性提供了安全性，以便正确的类被加载。   

因为 java 设计者将 String 为可以共享的，下面这段是源码中的注释：  
```java
/*
 * The {@code String} class represents character strings. All
 * string literals in Java programs, such as {@code "abc"}, are
 * implemented as instances of this class.
 * <p>
 * Strings are constant; their values cannot be changed after they
 * are created. String buffers support mutable strings.
 * Because String objects are immutable they can be shared. For example:
 *     String str = "abc";
 * is equivalent to:
 *     char data[] = {'a', 'b', 'c'};
 *     String str = new String(data);
 *
   对应翻译：
 * 字符串类表示字符串。所有
 * 在java程序中的字符串，如“ABC”，是
 * 实现为这个类的实例。
 *
 * 字符串是常量，它们的值在它们创建之后不能更改。
 * 支持可变字符串字符串缓冲区。
 * 因为字符串对象是不可改变的，它们可以共享。
 */
```

## 从效率上讲
* 设计成final，JVM才不用对相关方法在虚函数表中查询，而直接定位到String类的相关方法上，提高了执行效率。  
* Java设计者认为共享带来的效率更高，字符串常量池的出现实现共享，节约空间，提高效率。   


总而言之，就是要保证 java.lang.String 引用的对象一定是 java.lang.String的对象，而不是引用它的子孙类，这样才能保证它的效率和安全。

## native方法
native方法：该方法的实现由非java语言实现  
String.class 类内部就一个native方法--intern()  

## String类的属性
* char value[]  
The value is used for character storage.  
* int hash   
Cache the hash code for the string  


## 为啥Hashmap的key经常是String类型
因为字符串是不可变的，所以在它创建的时候hashcode就被缓存了，不需要重新计算。这就使得字符串很适合作为Map中的键，字符串的处理速度要快过其它的键对象。这就是HashMap中的键往往都使用字符串。  









- [Java常量池的大概理解][0]  
- [Java常量池的面试题][1]  
- [JavaString中理解起来模糊的东西，我来给我扫扫盲。(String类的intern(),equal(). == )][2]
- [link 2 csdn][4]
- [link 2 github][3]  

*******************
[0]: https://blog.csdn.net/qq_27093465/article/details/52033327
[1]: https://blog.csdn.net/qq_27093465/article/details/52033409
[2]: https://blog.csdn.net/qq_27093465/article/details/52032666
[3]: https://github.com/cmshome/x/tree/master/md
[4]: https://blog.csdn.net/qq_27093465