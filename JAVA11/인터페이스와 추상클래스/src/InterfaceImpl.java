public class InterfaceImpl extends Base1 implements Interface2, Interface1 {
    @Override
    public void eat() {
        System.out.println("eat");
    }

    @Override
    public void run() {
        System.out.println("run");
    }

    @Override
    public void walk() {
        System.out.println("walk");
    }

}
