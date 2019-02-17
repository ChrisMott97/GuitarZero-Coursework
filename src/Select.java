import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Select {

    public static void main(String[] args) {
        List<MusicItem> someList = new ArrayList<>();
        someList.add(new MusicItem("Song 1"));
        someList.add(new MusicItem("Song 2"));
        someList.add(new MusicItem("Song 3"));
        someList.add(new MusicItem("Song 4"));
        someList.add(new MusicItem("Song 5"));
        someList.add(new MusicItem("Song 6"));

        Carousel select = new Carousel(someList);

        Scanner in = new Scanner(System.in);

        while(true){
            String input = in.nextLine();
            if(input.equals("next")){
                select.next();
            } else if(input.equals("previous")){
                select.previous();
            } else{
                continue;
            }
            System.out.println(select.getAll());
            System.out.println(select.getVisible());
            System.out.println(select.getIntended());
        }
    }
}
