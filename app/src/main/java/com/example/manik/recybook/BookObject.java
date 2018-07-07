package com.example.manik.recybook;

public class BookObject {

    private String mBookName;
    private String mAuthorName;
    private String mDescription;
    private String mCategories;
    private String mUrl;
    private double mRating;
    private int mPages;
    private String mImgUrl;


    public BookObject(String mBookName, String mAuthorName, String mDescription, String mCategories, String mUrl, double mRating, int mPages, String mImgUrl) {
        this.mBookName = mBookName;
        this.mAuthorName = mAuthorName;
        this.mDescription = mDescription;
        this.mCategories = mCategories;
        this.mUrl = mUrl;
        this.mRating = mRating;
        this.mPages = mPages;
        if (mImgUrl==null){
            this.mImgUrl="https://github.com/maniksingh10/BookApp-RecyclerView-/blob/master/NoImageFound.jpg";
        }else{
            this.mImgUrl=mImgUrl;
        }

    }

    public String getmBookName() {
        return mBookName;
    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmCategories() {
        return mCategories;
    }

    public String getmUrl() {
        return mUrl;
    }

    public double getmRating() {
        return mRating;
    }

    public int getmPages() {
        return mPages;
    }

    public String getmImgUrl(){
        return mImgUrl;
    }

}
