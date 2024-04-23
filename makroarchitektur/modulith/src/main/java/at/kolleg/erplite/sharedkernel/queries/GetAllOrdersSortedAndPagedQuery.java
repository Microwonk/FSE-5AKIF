package at.kolleg.erplite.sharedkernel.queries;

public record GetAllOrdersSortedAndPagedQuery(int page, int pagesize, String sortedBy) {
}
