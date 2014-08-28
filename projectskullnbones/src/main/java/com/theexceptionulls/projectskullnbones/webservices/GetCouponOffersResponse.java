package com.theexceptionulls.projectskullnbones.webservices;

import java.util.List;

/**
 * Created by rohithavatapally on 8/28/14.
 */
public class GetCouponOffersResponse {

    private List<Offers> offersList;

    public List<Offers> getOffersList() {
        return offersList;
    }

    public void setOffersList(List<Offers> offersList) {
        this.offersList = offersList;
    }
}
