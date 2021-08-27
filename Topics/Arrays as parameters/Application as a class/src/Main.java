import java.util.Arrays;

class Application {

    String name;

    void run(String[] args) {
        System.out.println(this.name);
        Arrays.stream(args).forEach(System.out::println);
    }
}