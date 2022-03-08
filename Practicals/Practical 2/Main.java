public class Main {
    public static void main(String[] args) {
        OrganisingList list = new OrganisingList();

        list.insert(5);
        list.insert(5);
        list.insert(2);
        list.insert(9);
        list.insert(5);
        list.insert(20);
        list.insert(20);
        list.insert(9);
        //list.insert(69);
        System.out.println(list.toStringKeysOnly());
        System.out.println(list.toString());
        System.out.println("=====================================\n");

        list.delete(20);
        list.delete(3453);
        System.out.println(list.toStringKeysOnly());
        System.out.println(list.toString());
        System.out.println("=====================================\n");

        list.delete(9);
        System.out.println(list.toStringKeysOnly());
        System.out.println(list.toString());
        System.out.println("=====================================\n");

        list.delete(2);
        System.out.println(list.toStringKeysOnly());
        System.out.println(list.toString());
        System.out.println("=====================================\n");

        list.delete(5);
        System.out.println(list.toStringKeysOnly());
        System.out.println(list.toString());
        System.out.println("=====================================\n");

        list.getData(456);
        list.insert(456);
        System.out.println(list.toStringKeysOnly());
        System.out.println(list.toString());
        System.out.println("=====================================\n");

        list.getData(456);
        System.out.println(list.toStringKeysOnly());
        System.out.println(list.toString());
        System.out.println("=====================================\n");

        list.insert(597773);
        System.out.println(list.toStringKeysOnly());
        System.out.println(list.toString());
        System.out.println("=====================================\n");

        list.insert(3890);
        System.out.println(list.toStringKeysOnly());
        System.out.println(list.toString());
        System.out.println("=====================================\n");

        list.getData(3890);
        System.out.println(list.toStringKeysOnly());
        System.out.println(list.toString());
        System.out.println("=====================================\n");

        list.getData(597773);
        System.out.println(list.toStringKeysOnly());
        System.out.println(list.toString());
        System.out.println("=====================================\n");

        list.getData(456);
        System.out.println(list.toStringKeysOnly());
        System.out.println(list.toString());
        System.out.println("=====================================\n");

        list.getData(98677);
        System.out.println(list.toStringKeysOnly());
        System.out.println(list.toString());
        System.out.println("=====================================\n");

        list.delete(3890);
        System.out.println(list.toStringKeysOnly());
        System.out.println(list.toString());
        System.out.println("=====================================\n");

        list.delete(456);
        System.out.println(list.toStringKeysOnly());
        System.out.println(list.toString());
        System.out.println("=====================================\n");

        System.out.println(list.contains(2));
        System.out.println(list.contains(456));
        System.out.println(list.contains(597773));
    }
}

/* Output:

5, 2, 9, 20
[K: 5, D: 359] [K: 2, D: -179] [K: 9, D: 241] [K: 20, D: 20]
=====================================

5, 2, 9
[K: 5, D: 67] [K: 2, D: 13] [K: 9, D: 9]
=====================================

5, 2
[K: 5, D: 33] [K: 2, D: 2]
=====================================

5
[K: 5, D: 5]
=====================================

List is empty
List is empty
=====================================

456
[K: 456, D: 456]
=====================================

456
[K: 456, D: 456]
=====================================

456, 597773
[K: 456, D: 272194651] [K: 597773, D: 597773]
=====================================

456, 597773, 3890
[K: 456, D: 1393926519] [K: 597773, D: -1119360255] [K: 3890, D: 3890]
=====================================

456, 3890, 597773
[K: 456, D: -2065305033] [K: 3890, D: -1955095999] [K: 597773, D: 597773]
=====================================

456, 597773, 3890
[K: 456, D: 1393926519] [K: 597773, D: -1119360255] [K: 3890, D: 3890]
=====================================

456, 597773, 3890
[K: 456, D: 1393926519] [K: 597773, D: -1119360255] [K: 3890, D: 3890]
=====================================

456, 597773, 3890
[K: 456, D: 1393926519] [K: 597773, D: -1119360255] [K: 3890, D: 3890]
=====================================

456, 597773
[K: 456, D: 272194651] [K: 597773, D: 597773]
=====================================

597773
[K: 597773, D: 597773]
=====================================

false
false
true

*/