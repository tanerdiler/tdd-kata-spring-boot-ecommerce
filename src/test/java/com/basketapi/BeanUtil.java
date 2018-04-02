package com.basketapi;

import com.basketapi.domain.model.*;
import com.basketapi.repository.CampaignRepository;
import com.basketapi.repository.CategoryRepository;
import com.basketapi.repository.ProductRepository;
import com.basketapi.service.dto.BasketDTO;
import com.basketapi.service.dto.BasketItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.basketapi.domain.model.DiscountTargetType.CATEGORY;
import static com.basketapi.domain.model.DiscountTargetType.PRODUCT;

@Component
public class BeanUtil
{
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CampaignRepository campaignRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteAll()
    {
        productRepository.deleteAll();
        campaignRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    public static Category category1 = createCategory(200, "Gömlek");
    public static Category category2 = createCategory(100, "Pantalon");

    public static Product getRuganShoe() {
        return createProduct(20, "Rugan", 500d, category2);
    }

    public static Product getBlackPant() {
        return createProduct(5, "Pantul", 200d, category2);
    }

    public static Product getBlueShirt() {
        return createProduct(10, "Mavi Gömlek", 100.99d, category1);
    }


    public static BasketItemDTO createBasketItemDTO (Integer id, String name,
                                                     Double price, Double
                                                             discountedPrice,
                                                     Integer categoryI)
    {
        return new BasketItemDTO(id,
                name, price, discountedPrice, categoryI);
    }

    public static BasketDTO fillAndGetBasketDTO()
    {
        BasketItemDTO itemDTO1 = createBasketItemDTO(10,
                "Mavi Gömlek", 100.99d, 100.99d, 200);
        BasketItemDTO itemDTO2 = createBasketItemDTO(5,
                "Pantul", 200d, 200d, 100);
        BasketItemDTO itemDTO3 = createBasketItemDTO(20,
                "Rugan", 500d, 500d, 100);
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.add(itemDTO1);
        basketDTO.add(itemDTO2);
        basketDTO.add(itemDTO3);
        return basketDTO;
    }

    public static Basket fillAndGetBasket() {


        Product p1 = getBlueShirt();
        Product p2 = getBlackPant();
        Product p3 = getRuganShoe();

        Basket basket = new Basket();
        basket.add(p1);
        basket.add(p2);
        basket.add(p3);
        return basket;
    }



    public static Product createProduct(Integer id, String name, Double price,
                                  Category category)
    {
        return Product.aNew()
                .withId(id)
                .withName(name)
                .withPrice(price)
                .relateTo(category)
                .get();
    }



    public static Category createRandomCategoryWithId()
    {
        return Category.aNew()
                .withId(Math.abs(new Random().nextInt()))
                .withName("IT-CATEGORY"+UUID.randomUUID())
                .get();
    }

    public static Category createRandomCategory()
    {
        return Category.aNew()
                .withName("IT-CATEGORY"+UUID.randomUUID())
                .get();
    }

    public static Category createCategory(Integer id, String name)
    {
        return Category.aNew()
                .withId(id)
                .withName(name)
                .get();
    }

    public static List<Campaign> createCampaigns()
    {
        List<Campaign> campaigns = new ArrayList<>();
        campaigns.addAll(createProductCampaigns());
        campaigns.addAll(createCategoryCampaigns());
        return  campaigns;
    }

    public static List<Campaign> createProductCampaigns() {
        Campaign c1 = Campaign.aNew()
                .withName("Campaign 1")
                .withDiscount(Discount.withRate(5d).withLimit(100d).get())
                .target(5, PRODUCT)
                .get();
        Campaign c2 = Campaign.aNew()
                .withName("Campaign 2")
                .withDiscount(Discount.withPrice(50d).get())
                .target(10, PRODUCT)
                .get();
        List<Campaign> campaigns = Arrays.asList(c1, c2);
        return campaigns;
    }

    public static List<Campaign> createCategoryCampaigns() {
        Campaign c3 = Campaign.aNew()
                .withName("Campaign 3")
                .withDiscount(Discount.withRate(10d).withLimit(30d).get())
                .target(100, CATEGORY)
                .get();
        Campaign c4 = Campaign.aNew()
                .withName("Campaign 3")
                .withDiscount(Discount.withPrice(20d).get())
                .target(200, CATEGORY)
                .get();
        List<Campaign> campaigns = Arrays.asList(c3, c4);
        return campaigns;
    }

    public static Category createRandomCategoryAndSave(CategoryRepository repository) {
        return
        repository.save(createCategory(null, "IT-CATEGORY"+ UUID.randomUUID()
                .toString()));
    }

    public static Product createRandomProduct(Category category)
    {
        return createProduct(null, "IT-PRODUCT-"+UUID
                        .randomUUID()
                        .toString(),
                100d, category);
    }

    public static Product createRandomProductAndSave(Category category,
                                                  ProductRepository
                                                          repository) {
        return repository.save(createRandomProduct(category));
    }
}
