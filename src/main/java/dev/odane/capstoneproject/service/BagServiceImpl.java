package dev.odane.capstoneproject.service;

import dev.odane.capstoneproject.model.Book;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BagServiceImpl implements BagService {
    private static final  String MEMBER_BAG = "BAG";

    @Override
    public Book addToBag(Book book, HttpSession session) {
        List<Book> bag = getBag(session);
        bag.add(book);
        session.setAttribute(MEMBER_BAG, bag);
        return book;
    }

    private static List<Book> getBag(HttpSession session) { // helper method to fetch bag from session
        return session.getAttribute(MEMBER_BAG) == null ? new ArrayList<>() :
                (List<Book>) session.getAttribute(MEMBER_BAG);
    }

    @Override
    public Book removeFromBag(Book book, HttpSession session) {
        List<Book> bag = getBag(session);
        bag.remove(book);
        session.setAttribute(MEMBER_BAG,bag);
        return book;
    }

    @Override
    public List<Book> viewBag(long id, HttpSession session) {
        return getBag(session);
    }

    @Override
    public String borrowBooks(long id, HttpSession session) {
        // TODO: 02/08/2023 implement borrow books
        return "";
    }
}
