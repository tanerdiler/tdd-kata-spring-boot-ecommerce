package com.basketapi.web.rest;

import com.basketapi.service.BasketService;
import com.basketapi.service.dto.BasketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class BasketResource
{
    @Autowired
    private BasketService basketService;

    public BasketResource(BasketService basketService)
    {
        this.basketService = basketService;
    }


    /**
     * POST  /api/v1/campaigns/apply : Apply campaigns to posted basket
     *
     * @param basket the basketDTO to be discounted
     * @return the ResponseEntity with status 200 (Ok) and with body the
     * new basketDTO with discount
     */
    @PostMapping("/api/v1/campaigns/apply")
    public ResponseEntity<BasketDTO> discount(@RequestBody @Valid BasketDTO basket)
    {
        BasketDTO discountedBasket = basketService.applyCampaigns(basket);
        return ResponseEntity.ok(discountedBasket);
    }


}
