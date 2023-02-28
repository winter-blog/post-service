package com.devwinter.postservice.adapter.output.persistence.command;

import com.devwinter.postservice.adapter.output.persistence.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostEntityCommandRepository extends JpaRepository<PostEntity, Long> {
}
