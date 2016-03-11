package com.cisco.vehiclesurvey.dataretriever;
/*
 * Interface class for getting the retriver object which will be used to fetch the data..
 */
public interface IDataRetrieverFactory {
   public IDataRetriever getDataRetriever(int type,String resourceURL);
}
