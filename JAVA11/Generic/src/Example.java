public class Example<T>{
    private String name;
    private T generic;

    public String getName() {
        return name;
    }

    public T getGeneric() {
        return generic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGeneric(T generic) {
        this.generic = generic;
    }

    public Example(String name, T generic) {
        this.name = name;
        this.generic = generic;
    }

    public Example() {
    }
}
