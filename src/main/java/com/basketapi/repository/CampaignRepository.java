package com.basketapi.repository;

import com.basketapi.domain.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CampaignRepository extends JpaRepository<Campaign, Integer>
{
}
