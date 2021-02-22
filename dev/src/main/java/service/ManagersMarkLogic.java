package service;

import dal.MarkLogicUtility;

public class ManagersMarkLogic {

    public MarkLogicUtility utility;
    public PersonManager personManager;
    public VendorManager vendorManager;
    public LinkPersonManager linkManager;
    public FeedbackManager feedbackManager;

    public ManagersMarkLogic(){
        utility = new MarkLogicUtility("localhost",8003, "Admin", "Admin");
        personManager = new PersonManager(utility);
        vendorManager = new VendorManager(utility);
        linkManager = new LinkPersonManager(utility);
        feedbackManager = new FeedbackManager(utility);
    }

    public void release(){
        personManager.release();
        vendorManager.release();
        linkManager.release();
        feedbackManager.release();
        utility.releaseConnection();
    }

    public void deleteAll(){
        feedbackManager.deleteAll();
        linkManager.deleteAll();
        vendorManager.deleteAll();
        personManager.deleteAll();
    }

}
