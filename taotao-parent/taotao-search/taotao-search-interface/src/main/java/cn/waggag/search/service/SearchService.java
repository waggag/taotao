package cn.waggag.search.service;

import cn.waggag.common.pojo.SearchResult;

/**
 * @author 王港
 * @Date: 2019/3/20 2:52
 * @version: 1.0
 */
public interface SearchService {

    /**
     *根据条件查询结果
     */
    SearchResult search(String query, int page, int rows) throws Exception;

}
