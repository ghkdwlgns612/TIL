import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        long N = 10000000;
        ArrayList<String> arr1 = new ArrayList<>();
        LinkedList<String> arr2 = new LinkedList<>();

        for (int i = 0; i < N; i++) {
            arr1.add("String" + i);
            arr2.add("String" + i);
        }

        long startTime1 = System.nanoTime();
        arr1.get(50);
        long endTime1 = System.nanoTime();
        System.out.println("ArrayList의 조회 : " + (endTime1-startTime1));

        long startTime2 = System.nanoTime();
        arr2.get(50);
        long endTime2 = System.nanoTime();
        System.out.println("LinkedList의 조회 : " + (endTime2-startTime2));



        long startTime3 = System.nanoTime();
        arr1.remove(500000);
        long endTime3 = System.nanoTime();
        System.out.println("ArrayList의 제거 : " + (endTime3-startTime3));

        long startTime4 = System.nanoTime();
        arr2.remove(500000);
        long endTime4 = System.nanoTime();
        System.out.println("LinkedList의 제거 : " + (endTime4-startTime4));

    }
}
