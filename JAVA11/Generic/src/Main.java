import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Object형
        Example<String> example1 = new Example<>();
        Example<Integer> example2 = new Example<>();

        example1.setGeneric("String");
        String generic = example1.getGeneric();

        example2.setGeneric(12345);
        Integer generic1 = example2.getGeneric();

        System.out.println(generic);
        System.out.println(generic1);

        //Generic형
        Previous previous1 = new Previous();
        Previous previous2 = new Previous();

        previous1.setGeneric("String");
        String object = (String) previous1.getGeneric();

        previous2.setGeneric(12345);
        Integer object1 = (Integer) previous2.getGeneric();

        System.out.println(object);
        System.out.println(object1);

        //Generic생략
        Example noExample1 = new Example("jihuhwan","hello");
        Example noExample2 = new Example("jihuhwan",12345);

        Object noGeneric1 = noExample1.getGeneric();
        Object noGeneric2 = noExample2.getGeneric();

        //Generic의 제한
//        Limit<Parent> limit1 = new Limit<>();
//        Limit<Child> limit2 = new Limit<>();
//        Limit<String> limit3 = new Limit<>();
//        Limit<Integer> limit4 = new Limit<>();

        //배열
//        ArrayList //Collection<? extends E>
//        Comparator //<T extends Comparable<? super T>>

        //Generic심화
        Extreme<Child> extreme = new Extreme<>();

    }
}
