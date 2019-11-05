
import java.util.HashMap;
import java.util.TreeMap;

public class sd {
    public static void main(String[] args) {
        //统计一个字符串中每个字符出现的次数
        String str = "aaabbccm123333";
        //1.字符串转数组
        char [] arr = str.toCharArray();
        //2.创建map
        HashMap<Character,Integer> hm = new HashMap<>();
        //3.遍历数组key-value
        for (char c : arr) {
            //4.每取出一个元素，判断key是否存在
            if(!hm.containsKey(c)){
                //5.若不存在，存入key，value=1
                hm.put(c, 1);
            }else{
                //6.若存在，value+1
                hm.put(c, hm.get(c)+1);
            }
        }
        System.out.println(hm);
    }
}
