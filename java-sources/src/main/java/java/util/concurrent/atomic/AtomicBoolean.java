package java.util.concurrent.atomic;
import sun.misc.Unsafe;

/**
 * @since 1.5
 * @author Doug Lea
 */
public class AtomicBoolean implements java.io.Serializable {
    private static final long serialVersionUID = 4654671469794556979L;
    // setup to use Unsafe.compareAndSwapInt for updates
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long valueOffset;

    static {
        try {
            //获取value在AtomicBoolean中的内存偏移
            valueOffset = unsafe.objectFieldOffset
                (AtomicBoolean.class.getDeclaredField("value"));
        } catch (Exception ex) { throw new Error(ex); }
    }

    private volatile int value;

    //使用boolean初始化
    public AtomicBoolean(boolean initialValue) {
        value = initialValue ? 1 : 0;
    }

    public AtomicBoolean() {
    }

    //get
    public final boolean get() {
        return value != 0;
    }

    /**
     * 比较对象偏移量为valueOffset的value是否等价于expect，
     * 如果是，使用update更新
     * 原子性
     *
     * @param expect the expected value
     * @param update the new value
     * @return 是否等价
     */
    public final boolean compareAndSet(boolean expect, boolean update) {
        int e = expect ? 1 : 0;
        int u = update ? 1 : 0;
        return unsafe.compareAndSwapInt(this, valueOffset, e, u);
    }

    /**
     * 和compareAndSet一模一样
     */
    public boolean weakCompareAndSet(boolean expect, boolean update) {
        int e = expect ? 1 : 0;
        int u = update ? 1 : 0;
        return unsafe.compareAndSwapInt(this, valueOffset, e, u);
    }

    //set
    public final void set(boolean newValue) {
        value = newValue ? 1 : 0;
    }

    /**
     * 将当前对象偏移量为valueOffset的value，更新为newValue
     * 不保证对其他线程立即课件
     */
    public final void lazySet(boolean newValue) {
        int v = newValue ? 1 : 0;
        unsafe.putOrderedInt(this, valueOffset, v);
    }

    /**
     * 原子性地设置值，并且返还原值
     */
    public final boolean getAndSet(boolean newValue) {
        boolean prev;
        do {
            prev = get();
        } while (!compareAndSet(prev, newValue));
        return prev;
    }

    /**
     * Returns the String representation of the current value.
     * @return the String representation of the current value
     */
    public String toString() {
        return Boolean.toString(get());
    }

}
