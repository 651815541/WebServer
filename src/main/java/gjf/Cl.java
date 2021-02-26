package gjf;

public class Cl {

        public static void main(String[] args) {
            new Thread(new Runnable() {
                public void run() {
                    System.out.print("bar");
                }
            }).start();
        }


}
