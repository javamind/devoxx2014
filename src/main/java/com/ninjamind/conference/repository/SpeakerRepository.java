package com.ninjamind.conference.repository;

import com.ninjamind.conference.domain.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository associé au {@link com.ninjamind.conference.domain.Speaker}
 *
 * @author ehret_g
 */
@Repository
public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
}
