package org.formation.coffee.domain.model;

import org.formation.coffee.domain.exception.DomainValidationException;

import java.util.Objects;

/**
 * Avis d'un client sur un café. Fait partie de l'agrégat {@link Coffee}.
 */
public record Review(ReviewId id, String author, String comment, int rating) {

    public static final int MIN_RATING = 1;
    public static final int MAX_RATING = 5;

    public Review {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(comment, "comment must not be null");
        if (author == null || author.isBlank()) {
            throw new DomainValidationException("Review author must not be blank");
        }
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new DomainValidationException(
                "Rating must be between %d and %d, got %d".formatted(MIN_RATING, MAX_RATING, rating));
        }
    }
}
