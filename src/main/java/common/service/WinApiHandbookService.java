package common.service;

import common.exception.HandbookException;
import model.WinApiClass;
import model.WinApiFunction;
import model.WinApiParameter;

import java.util.List;

public interface WinApiHandbookService {

    /**
     * Find class instance by id
     *
     * @param id class id
     * @return founded instance
     * @throws HandbookException if instance was not founded or else errors
     */
    WinApiClass getWinApiClass(long id) throws HandbookException;

    /**
     * Find classes by part of their name
     *
     * @param keyword part of name
     * @return list of founded classes
     * @throws HandbookException
     */
    List<WinApiClass> findClasses(String keyword) throws HandbookException;

    /**
     * Save class instance or update if class's id not equal 0
     *
     * @param topic
     * @return return saved instance with all autogenerated fields
     * @throws HandbookException
     */
    WinApiClass saveOrUpdate(WinApiClass topic) throws HandbookException;

//    /**
//     * Update existence class
//     *
//     * @param topic
//     * @throws HandbookException
//     */
//    void updateClass(WinApiClass topic) throws HandbookException;

    /**
     * Remove existence class and all nested instances
     *
     * @param id
     * @throws HandbookException
     */
    void removeClass(long id) throws HandbookException;

    /**
     * Update function
     *
     * @param function
     * @throws HandbookException
     */
    void updateFunction(WinApiFunction function) throws HandbookException;

    /**
     * Remove existence function and all nested instances
     *
     * @param id
     * @throws HandbookException
     */
    void removeWinApiFunction(long id) throws HandbookException;

    /**
     * Update parameter
     *
     * @param parameter
     * @throws HandbookException
     */
    void updateParam(WinApiParameter parameter) throws HandbookException;

    /**
     * Remove existence parameter and all nested instances
     *
     * @param id
     * @throws HandbookException
     */
    void removeWinApiParameter(long id) throws HandbookException;
}
