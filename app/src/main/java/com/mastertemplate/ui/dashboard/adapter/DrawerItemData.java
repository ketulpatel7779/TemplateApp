package com.mastertemplate.ui.dashboard.adapter;
/**
 * Model Class for Navigation drawer item.
 */
public class DrawerItemData {
    int position;
    String title;
    int iconSelected;
    int iconDeselected;
    boolean isSelected = false;

    private DrawerItemData(Builder builder) {
        this.position = builder.position;
        this.title = builder.title;
        this.iconSelected = builder.iconSelected;
        this.iconDeselected = builder.iconDeselected;
        this.isSelected = builder.isSelected;
    }

    public int getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public int getIconSelected() {
        return iconSelected;
    }

    public int getIconDeselected() {
        return iconDeselected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public static Builder newDrawerItemData() {
        return new Builder();
    }


    public static final class Builder {
        private int position;
        private String title;
        private int iconSelected;
        private int iconDeselected;
        private boolean isSelected;

        private Builder() {
        }

        public DrawerItemData build() {
            return new DrawerItemData(this);
        }

        public Builder position(int position) {
            this.position = position;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder iconSelected(int iconSelected) {
            this.iconSelected = iconSelected;
            return this;
        }

        public Builder iconDeselected(int iconDeselected) {
            this.iconDeselected = iconDeselected;
            return this;
        }

        public Builder isSelected(boolean isSelected) {
            this.isSelected = isSelected;
            return this;
        }
    }
}