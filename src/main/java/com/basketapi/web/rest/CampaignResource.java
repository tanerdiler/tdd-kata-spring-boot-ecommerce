package com.basketapi.web.rest;

import com.basketapi.service.CampaignService;
import com.basketapi.service.dto.CampaignDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.basketapi.service.ApplicationConstants.ENTITY_NAME_CAMPAIGN;
import static com.basketapi.service.ApplicationConstants.ENTITY_NAME_CATEGORY;
import static com.basketapi.web.rest.util.HeaderUtil.createEntityCreationAlert;
import static com.basketapi.web.rest.util.HeaderUtil.createEntityDeletionAlert;
import static com.basketapi.web.rest.util.HeaderUtil.createEntityUpdateAlert;

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
     * PUT  /api/v1/campaigns : Updates an existing campaign.
     *
     * @param id the identifier of campaign to update
     * @param campaignDTO the campaignDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated campaignDTO,
     * or with status 400 (Bad Request) if the campaignDTO is not valid,
     * or with status 500 (Internal Server Error) if the campaignDTO couldn't be updated
     */
    @PutMapping("/api/v1/campaigns/{id}")
    public ResponseEntity updateCampaign(@PathVariable("id") Integer id,
                                         @RequestBody @Valid CampaignDTO campaignDTO)
    {
        log.debug("REST request to update Category : {}", campaignDTO);

        campaignService.update(id, campaignDTO);

        return ResponseEntity
                .noContent()
                .headers(createEntityUpdateAlert(ENTITY_NAME_CAMPAIGN, id.toString()))
                .build();
    }

    /**
     * DELETE  /api/v1/campaigns : Delete the specified Campaign.
     *
     * @param id the campaign identifier
     * @return the ResponseEntity with status 204 (NoContent),
     * or with status 404 (Not Fount) if the campaign has
     * not been found
     */
    @DeleteMapping("/api/v1/campaigns/{id}")
    public ResponseEntity deleteCampaign(@PathVariable("id") Integer id)
    {
        log.debug("REST request to delete Campaign : {}",
                id);

        campaignService.delete(id);

        return ResponseEntity
                .noContent()
                .headers(createEntityDeletionAlert(ENTITY_NAME_CAMPAIGN, id.toString()))
                .build();
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
    public ResponseEntity<CampaignDTO> createCampaign(@RequestBody CampaignDTO campaignDTO)
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
