public interface Interface1 {
    void run();

    void eat();

    default void smile() {
        System.out.println("Smile");
    }
}
