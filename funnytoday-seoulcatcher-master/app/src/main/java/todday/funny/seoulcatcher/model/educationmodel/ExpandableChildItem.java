package todday.funny.seoulcatcher.model.educationmodel;

import java.util.List;

public class ExpandableChildItem {
    private String title;
    private int imageResource;

    public ExpandableChildItem(String title, int imageResource){
        this.title = title;
        this.imageResource = imageResource;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
