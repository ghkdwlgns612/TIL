import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String[] arr1 = {"aaa","bbb","ccc","aaa"};
        Integer[] arr2 = {1,2,3,4,1};
        List<Integer> arr3 = Arrays.asList(arr2);

        Stream<String> stream1 = Arrays.stream(arr1);
//        HashSet<String> collect = stream1.collect(Collectors.toCollection(HashSet::new));//HashSet<String>
//        ArrayList<String> collect = stream1.collect(Collectors.toCollection(ArrayList::new));//ArrayList<String>
//        List<String> list = stream1.filter(o -> !o.equals("bbb")).collect(Collectors.toList());
        ///list : ["aaa","ccc","aaa"]
    }
}
