package info.simplecloud.core.ng.handlers;

import info.simplecloud.core.ng.MetaData;

import java.util.List;

public interface IHandler {

    /**
     * Decodes JSON data to desired type and returns the result
     * 
     * @param jsonData
     *            data to decode
     * @param me
     *            an instance of my type if necessary
     * @param internalMetaData
     *            in case of plural the meta data for the value has to be
     *            provided
     * @return decoded object, type depends on decoder implementation
     * @throws Exception
     */
    public Object decode(Object jsonData, Object me, MetaData internalMetaData) throws Exception;

    /**
     * Takes an instance of my type and creates a JSON representation of it
     * 
     * @param me
     *            An instance of my type to decode
     * @param internalMetaData
     *            In case of plural meta data for the value has to be provided
     * @param includeAttributes
     *            In case a partial user object is desired a list of attribute
     *            names, if a full use is desired use null
     * @return A JSON data object representing me
     */
    public Object encode(Object me, MetaData internalMetaData, List<String> includeAttributes);

    /**
     * Mashes two objects together and returns the result
     * 
     * @param from
     *            object to pull data from
     * @param to
     *            object to insert data into
     * @return the merge result
     */
    public Object merge(Object from, Object to);

}
