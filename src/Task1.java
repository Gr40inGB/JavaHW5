public class Task1 {
    public static void main(String[] args) {
        PhoneBook pb = new PhoneBook();
        pb.add("Richard", "mobile", "12121212121212");
        pb.add("AARichard", "mobile", "232323232323");
        pb.add("AARichard", "work", "1223232323");
        pb.add("Gulya", "mobile", "54545454545454");
        pb.add("Azamat ", "home", "6767676767");
        pb.run();
    }
}