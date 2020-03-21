public class HashTest {
    public static void main(String[] args) {
        HashTable<String> p = new HashTable<>();
        p.add("Hello");

        System.out.println(p.getLoad());

        p.setLoad(0.5);

        System.out.println(p.getLoad());

        System.out.println(p.toArray());

        System.out.println(p.contains("Hello"));
        System.out.println(p.contains("bosfjkkslgkgsdgo"));

    }
}
