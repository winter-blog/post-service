package com.devwinter.postservice.adapter.output.persistence;

import com.devwinter.postservice.adapter.output.persistence.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostEntityRepository extends JpaRepository<PostEntity, Long> {
}
