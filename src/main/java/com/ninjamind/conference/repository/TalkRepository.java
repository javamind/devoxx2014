package com.ninjamind.conference.repository;

import com.ninjamind.conference.domain.Talk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository associé au {@link com.ninjamind.conference.domain.Talk}
 *
 * @author ehret_g
 */
@Repository
public interface TalkRepository extends JpaRepository<Talk, Long> {

}
