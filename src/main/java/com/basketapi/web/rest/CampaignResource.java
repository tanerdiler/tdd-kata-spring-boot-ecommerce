package com.basketapi.web.rest;

import com.basketapi.service.CampaignService;
import com.basketapi.service.dto.CampaignDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.basketapi.service.ApplicationConstants.ENTITY_NAME_CATEGORY;
import static com.basketapi.web.rest.util.HeaderUtil.createEntityCreationAlert;

@Controller
public class CampaignResource
{
    private final Logger log = LoggerFactory.getLogger(CampaignResource.class);

    private CampaignService campaignService;

    public CampaignResource(CampaignService campaignService)
    {
        this.campaignService = campaignService;
    }

    /**
     * POST  /api/v1/campaigns : Create a new campaign.
     *
     * @param campaignDTO the campaignDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new campaignDTO, or with status 400 (Bad Request) if the campaign has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api/v1/campaigns")
    public ResponseEntity<CampaignDTO> createCampaign(@RequestBody CampaignDTO
                                                            campaignDTO)
            throws URISyntaxException
    {
        log.debug("REST request to save Campaign : {}",
                campaignDTO);

        CampaignDTO result = campaignService.save(campaignDTO);

        return ResponseEntity
                .created(new URI("/api/v1/campaigns/"+result.getId()))
                .headers(createEntityCreationAlert(
                        ENTITY_NAME_CATEGORY,
                        result.getId().toString()))
                .body(result);
    }

    /**
     * GET  /api/v1/campaigns : get all campaigns.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the
     * campaigns, or with status 404 (Not Found)
     */
    @GetMapping("/api/v1/campaigns")
    public ResponseEntity<List<CampaignDTO>> getAllCategories()
    {
        return ResponseEntity.ok(campaignService.findAll());
    }

    /**
     * GET  /api/v1/campaigns : get the "id" campaign.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the
     * campaign, or with status 404 (Not Found)
     */
    @GetMapping("/api/v1/campaigns/{id}")
    public ResponseEntity<CampaignDTO> getCampaign(@PathVariable Integer id)
    {
        return ResponseEntity.ok(campaignService.find(id));
    }
}
