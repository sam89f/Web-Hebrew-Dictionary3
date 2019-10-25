package org.launchcode.models.data;

import org.launchcode.models.English;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface EnglishDao extends CrudRepository<English, Integer> {
}
