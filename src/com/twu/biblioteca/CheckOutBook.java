package com.twu.biblioteca;

public class CheckOutBook implements MenuAction {
    private Books books;
    private ConsoleView consoleView;

    public CheckOutBook(ConsoleView consoleView, Books books) {
        this.consoleView = consoleView;
        this.books = books;
    }

    @Override
    public void performAction() {

    }
}
