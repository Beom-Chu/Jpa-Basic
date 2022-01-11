package com.kbs.datajpa.repository;

import com.kbs.datajpa.entity.Header;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeaderRepository extends JpaRepository<Header, Long> {
    Header findByName(String name);
}
