package com.ntdsoftware.homework.casillas.common.controller.request;

import com.ntdsoftware.homework.casillas.common.enums.SortsEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DataQueryRequest is a generic class that represents a request for querying data.
 * It includes a query object and a map of sorting options.
 *
 * @param <T> the type of the query object
 */
@Data
@Accessors(chain = true)
public class DataQueryRequest<T> {

    /** The query object containing the criteria for querying data */
    private T query;

    /** The page number for pagination, default is 1 */
    private int page = 1;

    /** The page size for pagination, default is 10 */
    private int size = 10;

    /** A map of sorting options where the key is the field name and the value is the sorting order */
    Map<String, SortsEnum> sorts;

    /**
     * Returns the list of sorting orders based on the sorting options provided.
     *
     * @return the list of sorting orders
     */
    public List<Sort.Order> getSorts() {
        List<Sort.Order> sortOrders = new ArrayList<>();

        if (sorts != null) {
            sorts.forEach((key, value) -> sortOrders.add(new Sort.Order(value.getDirection(), key)));
        }

        return sortOrders;
    }
}
