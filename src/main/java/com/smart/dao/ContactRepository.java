package com.smart.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.smart.entities.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Integer> {
    //PAGINATION.......

    @Query("from Contact as c where c.user.id:=userId")
    //Pageable will have currentPage-page
    //Contact per page-5
    public Page<Contact> findContactsByUser(@Param("userId") int userId, Pageable pageable);
}
