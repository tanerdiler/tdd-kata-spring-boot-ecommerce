package com.basketapi.service.dto;

import java.util.ArrayList;
import java.util.List;

public class BasketDTO
{
    private List<BasketItemDTO> items = new ArrayList<>();

    public BasketDTO add(BasketItemDTO item)
    {
        items.add(item);
        return this;
    }

    public List<BasketItemDTO> getItems()
    {
        return items;
    }

}
