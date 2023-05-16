package io.musicStreaming.start.repository;

import io.musicStreaming.start.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongsRepository extends PagingAndSortingRepository<Song, Long> {
    public List<Song> findByTitle(String title);
}
