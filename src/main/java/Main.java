import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Tuple;

public class Main {

    public static void main(String[] args) {
        RWayTrie rwt = new RWayTrie();
        Tuple t = new Tuple("hetio", 5);
        Tuple t1 = new Tuple("sex", 3);
        Tuple t2 = new Tuple("hilka", 5);


//        System.out.println('h' == 'h');
//        rwt.delete("hetio");
        rwt.add(t);
        rwt.add(t2);


//        rwt.add(t1);
//        rwt.wordsWithPrefix("");
        System.out.println(rwt.contains("hetio"));
        System.out.println(rwt.wordsWithPrefix(""));
    }
}
