package service;

import dao.ReviewDAO;

public class ReviewService {
    private ReviewDAO dao = new ReviewDAO();

    public void add(int pid, int buyerId, int rating, String comment) {
        dao.add(pid, buyerId, rating, comment);
    }

    public void view(int pid) {
        dao.viewByProduct(pid);
    }
}
