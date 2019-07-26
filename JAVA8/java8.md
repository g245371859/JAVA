# 一、lambda

## 1. 简单的例子

|       使用案例        |                          lambda示例                          |
| :-------------------: | :----------------------------------------------------------: |
|      布尔表达式       |           `(List<String> list) -> list.isEmpty()`            |
|       创建对象        |                    `() -> new Apple(10)`                     |
|     消费一个对象      |     `(Apple a) -> {System.out.println(a.getWeight());}`      |
| 从一个对象中选择/抽取 |                  `(String s) -> s.length()`                  |
|      组合两个值       |                  `(int a, int b) -> a * b`                   |
|     比较两个对象      | `(Apple a1, Apple a2) ->a1.getWeight().compareTo(a2.getWeight())` |

## 2. 函数式接口

函数式接口就是只定义一个抽象方法的接口。

```java
测验3.2：函数式接口
下面哪些接口是函数式接口？
public interface Adder{
int add(int a, int b);
}
public interface SmartAdder extends Adder{
int add(double a, double b);
}
public interface Nothing{
}
答案：只有Adder是函数式接口。
SmartAdder不是函数式接口，因为它定义了两个叫作add的抽象方法（其中一个是从
Adder那里继承来的）。
Nothing也不是函数式接口，因为它没有声明抽象方法。
```

> @FunctionalInterface又是怎么回事？
> 如果你去看看新的Java API，会发现函数式接口带有@FunctionalInterface的标注。这个标注用于表示该接口会设计成一个函数式接口。如果你用@FunctionalInterface定义了一个接口，而它却不是函数式接口的话，编译器将返回一个提示原因的错误。例如，错误消息可能是“Multiple non-overriding abstract methods found in interface Foo”，表明存在多个抽象方法。请注意， @FunctionalInterface不是必需的，但对于为此设计的接口而言，使用它是比较好的做法。 它就像是@Override标注表示方法被重写了。

## 3. java.util.Function

- Consumer

    Consumer

    BiConsumer

    DoubleConsumer

    IntConsumer

    LongConsumer

    ObjDoubleConsumer

    ObjIntConsumer

    ObjLongConsumer

- Predicate

    Predicate

    BiPredicate

    DoublePredicate

    IntPredicate

    LongPredicate

- Function

    Function

    BiFunction

    DoubleFunction

    DoubleToIntFunction

    DoubleToLongFunction

    IntFunction

    IntToDoubleFunction

    IntToLongFunction

    LongFunction

    LongToDoubleFunction

    LongToIntFunction

    ToDoubleBiFunction

    ToDoubleFunction

    ToIntBiFunction

    ToIntFunction

    ToLongBiFunction

    ToLongFunction

- Supplier

    Supplier

    BooleanSupplier

    DoubleSupplier

    IntSupplier

    LongSupplier

- Operator

    BinaryOperator

    DoubleBinaryOperator

    DoubleUnaryOperator

    IntBinaryOperator

    IntUnaryOperator

    LongBinaryOperator

    LongUnaryOperator

    UnaryOperator