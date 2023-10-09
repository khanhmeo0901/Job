package org.example.Code.entity;

public class ObjectKeyWord {

    private String previousLine;
    private String highlightLine;
    private String nextLine;

    public ObjectKeyWord() {
    }

    public ObjectKeyWord( String previousLine, String highlightLine, String nextLine) {
        this.previousLine = previousLine;
        this.highlightLine = highlightLine;
        this.nextLine = nextLine;
    }

    public String getPreviousLine() {
        return previousLine;
    }

    public void setPreviousLine(String previousLine) {
        this.previousLine = previousLine;
    }

    public String getHighlightLine() {
        return highlightLine;
    }

    public void setHighlightLine(String highlightLine) {
        this.highlightLine = highlightLine;
    }

    public String getNextLine() {
        return nextLine;
    }

    public void setNextLine(String nextLine) {
        this.nextLine = nextLine;
    }
}
