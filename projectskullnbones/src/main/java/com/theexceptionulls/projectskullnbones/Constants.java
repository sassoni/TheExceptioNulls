package com.theexceptionulls.projectskullnbones;

public class Constants {

    public static final String OFFERS_FILE_PREFIX = "cardOffers";
    public static final String CARD_POSITION = "cardPosition";
    public static final String CARD_NUMBER = "cardNumber";
    public static final String RETAILER_ID = "retailerId";
    public static final int DEFAULT_RETAILER_ID = -1;
    public static final int DEFAULT_CARD_POSITION = -1;
    public static final int OFFERS_LIST_SIZE = 2;

    public static final int NOTIFICATION_NEW_OFFERS = 1321;

    public static final int INTENT_RESULT_FINISH_HOME = 12312;
    public static final int INTENT_REQUEST_CODE_CARD = 678;

    public static final int RESULT_SCAN_NO_BARCODE = 21;
    public static final int RESULT_MANUAL_ENTRY = 22;
    public static final int SCAN_CARD_REQUEST = 121;

    public static final String INTENT_FROM = "intentFrom";
    public static final String INTENT_FROM_REGISTRATION = "intentFromRegistration";
    public static final String INTENT_FROM_GRID = "intentFromGrid";
    public static final String INTENT_FROM_NOTIFICATION = "intentFromNotification";

    public static final String WEB_SERVICE_URL = "http://ec2-54-242-142-175.compute-1.amazonaws.com:8080";

    public static final String REGISTRATION_BROADCAST_INTENT = "com.woffers.register";
    public static final String BARCODE_VALUE_KEY = "barcode";
    public static final String SYMBOLOGY_VALUE_KEY = "symbology";
}
