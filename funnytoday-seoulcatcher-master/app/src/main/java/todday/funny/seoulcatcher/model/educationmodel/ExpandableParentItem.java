package todday.funny.seoulcatcher.model.educationmodel;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;

import java.util.List;

public class ExpandableParentItem implements ParentListItem {
    private String title;
    private int imageResource;
    private List mChilds;

    public ExpandableParentItem(List list){
        this.mChilds = list;
    }


    @Override
    public List<?> getChildItemList() {
        return mChilds;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
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
