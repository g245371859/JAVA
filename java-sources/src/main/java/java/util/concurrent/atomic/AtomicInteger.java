package java.util.concurrent.atomic;

import sun.misc.Unsafe;

import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

/**
 * @author Doug Lea
 * @since 1.5
 */
public class AtomicInteger extends Number implements java.io.Serializable {
    private static final long serialVersionUID = 6214790243416807050L;

    // setup to use Unsafe.compareAndSwapInt for updates
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long valueOffset;

    static {
        try {
            //获取value在AtomicBoolean中的内存偏移
            valueOffset = unsafe.objectFieldOffset
                    (AtomicInteger.class.getDeclaredField("value"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    private volatile int value;

    //使用int初始化
    public AtomicInteger(int initialValue) {
        value = initialValue;
    }

    public AtomicInteger() {
    }

    //get
    public final int get() {
        return value;
    }

    //set
    public final void set(int newValue) {
        value = newValue;
    }

    //最终设置为给定值
    public final void lazySet(int newValue) {
        unsafe.putOrderedInt(this, valueOffset, newValue);
    }

    /**
     * 获取当前对象中偏移量为valueOffset的变量volatile语义的当前值
     * 设置变量volatile语义的值为newValue
     */
    public final int getAndSet(int newValue) {
        return unsafe.getAndSetInt(this, valueOffset, newValue);
    }

    /**
     * 比较对象偏移量为valueOffset的value是否等价于expect，
     * 如果是，使用update更新
     * 原子性
     */
    public final boolean compareAndSet(int expect, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }

    /**
     * 和compareAndSet一模一样
     */
    public final boolean weakCompareAndSet(int expect, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }

    /**
     * 将当前对象偏移量为valueOffset的value，更新为newValue
     * 不保证对其他线程立即可见
     */
    public final int getAndIncrement() {
        return unsafe.getAndAddInt(this, valueOffset, 1);
    }

    /**
     * 获取当前对象中偏移量为valueOffset的volatile语义的当前值
     * 并设置为原始值-1
     * 原子性
     */
    public final int getAndDecrement() {
        return unsafe.getAndAddInt(this, valueOffset, -1);
    }

    /**
     * 获取当前对象中偏移量为valueOffset的volatile语义的当前值
     * 并设置为原始值+delta
     * 原子性
     */
    public final int getAndAdd(int delta) {
        return unsafe.getAndAddInt(this, valueOffset, delta);
    }

    /**
     * 先原子性+1
     * 再返回原始值+1
     */
    public final int incrementAndGet() {
        return unsafe.getAndAddInt(this, valueOffset, 1) + 1;
    }

    /**
     * 先原子性-1
     * 再返回原始值-1
     */
    public final int decrementAndGet() {
        return unsafe.getAndAddInt(this, valueOffset, -1) - 1;
    }

    /**
     * 先原子性+delta
     * 再返回原始值+delta
     */
    public final int addAndGet(int delta) {
        return unsafe.getAndAddInt(this, valueOffset, delta) + delta;
    }

    /**
     * 原子性处理值
     * 返回原始值
     */
    public final int getAndUpdate(IntUnaryOperator updateFunction) {
        int prev, next;
        do {
            prev = get();
            next = updateFunction.applyAsInt(prev);
        } while (!compareAndSet(prev, next));
        return prev;
    }

    /**
     * 原子性处理值
     * 返回处理后的值
     */
    public final int updateAndGet(IntUnaryOperator updateFunction) {
        int prev, next;
        do {
            prev = get();
            next = updateFunction.applyAsInt(prev);
        } while (!compareAndSet(prev, next));
        return next;
    }

    /**
     * 原子性使用x和原始值计算
     * 返回原始值
     */
    public final int getAndAccumulate(int x,
                                      IntBinaryOperator accumulatorFunction) {
        int prev, next;
        do {
            prev = get();
            next = accumulatorFunction.applyAsInt(prev, x);
        } while (!compareAndSet(prev, next));
        return prev;
    }

    /**
     * 原子性使用x和原始值计算
     * 返回计算结果
     */
    public final int accumulateAndGet(int x,
                                      IntBinaryOperator accumulatorFunction) {
        int prev, next;
        do {
            prev = get();
            next = accumulatorFunction.applyAsInt(prev, x);
        } while (!compareAndSet(prev, next));
        return next;
    }

    public String toString() {
        return Integer.toString(get());
    }

    //get
    public int intValue() {
        return get();
    }

    //转换为float
    public long longValue() {
        return (long) get();
    }

    //转换为float
    public float floatValue() {
        return (float) get();
    }

    //转换为double
    public double doubleValue() {
        return (double) get();
    }

}
