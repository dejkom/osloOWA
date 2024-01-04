/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.odelowebapp.HR.beans;

import com.google.common.collect.Maps;
import com.odelowebapp.HR.entity.HRCourseAttendance;
import com.odelowebapp.HR.entity.VADCODEKSUsers;
import com.odelowebapp.HR.facade.HRCourseAttendanceFacade;
import com.odelowebapp.HR.facade.VADCODEKSUsersFacade;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

/**
 *
 * @author dematjasic
 */
@Named
@ViewScoped
public class HRCourseAttendanceLazyLoadBean implements Serializable {

    @EJB
    private HRCourseAttendanceFacade attendanceDAO;
    private LazyDataModel<HRCourseAttendance> lazyModel;

    @EJB
    private VADCODEKSUsersFacade codeksusersFacade;

    @PostConstruct
    public void init() {
        pridobiOsebeVse();
        lazyModel = new LazyDataModel<HRCourseAttendance>() {

            @Override
            public int count(Map<String, FilterMeta> filterBy) {
                //return 0;
                // Convert FilterMeta map to a format that your DAO can understand
                Map<String, Object> customFilters = filterBy.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                e -> e.getValue().getFilterValue()
                        ));

                // Call a method in your DAO that counts the number of rows matching these filters
                return attendanceDAO.countWithFilters(customFilters);

            }

            @Override
            public String getRowKey(HRCourseAttendance object) {
                return object.getAttendanceID() + "";
                //return super.getRowKey(object); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public HRCourseAttendance getRowData(String rowKey) {
                for (HRCourseAttendance att : lazyModel.getWrappedData()) {
                    if (att.getAttendanceID() == Integer.parseInt(rowKey)) {
                        return att;
                    }
                }

                return null;
            }

            @Override
            public List<HRCourseAttendance> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                // Custom filter handling
                System.out.println("Number of filters used:"+filterBy.size());
                Map<String, Object> customFilters = new HashMap<>();
                for (String key : filterBy.keySet()) {
                    FilterMeta meta = filterBy.get(key);
                    if (key.contains(".")) { // Nested property
                        customFilters.put(key, meta.getFilterValue());
                    } else {
                        // Handle regular filters
                        customFilters.putAll(filterBy.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getFilterValue())));
                    }
                }

                // Extract sort field and order
                String sortField = null;
                SortOrder sortOrder = SortOrder.UNSORTED; // Adjust this based on your SortOrder enum
                if (!sortBy.isEmpty()) {
                    SortMeta sortMeta = sortBy.values().iterator().next(); // Assuming single sort column for simplicity
                    sortField = sortMeta.getField();
                    sortOrder = convertPrimeFacesSortOrder(sortMeta.getOrder()); // Convert PrimeFaces SortOrder to your SortOrder, method is written down in code
                }

                // Call DAO with custom filters
                //List<HRCourseAttendance> findWithFilters = attendanceDAO.findWithFilters(first, pageSize, customFilters);
                List<HRCourseAttendance> findWithFilters = attendanceDAO.findWithFiltersAndSorting(first, pageSize, customFilters, sortField, sortOrder);
                System.out.println("--Sem pred setRowCount v load metodi, findWithFilters.size() je:"+findWithFilters.size());
                int count = count(filterBy);
                setRowCount(count);
                System.out.println("--Sem za setRowCount v load metodi, nastavil sem count:"+count);

                if (findWithFilters.size() > 0 && first >= findWithFilters.size()) {
                    int numberOfPages = (int) Math.ceil(findWithFilters.size() * 1d / pageSize);
                    first = Math.max((numberOfPages - 1) * pageSize, 0);
                }

                return findWithFilters;
            }
        };
    }

    private Map<Integer, VADCODEKSUsers> vrnjeniUserjiMap = new HashMap();

    public void pridobiOsebeVse() {
        System.out.println("Sem v pridobiOsebeVse()");
        vrnjeniUserjiMap = Maps.uniqueIndex(codeksusersFacade.findAll(), VADCODEKSUsers::getId);

    }

    public LazyDataModel<HRCourseAttendance> getLazyModel() {
        return lazyModel;
    }

    private SortOrder convertPrimeFacesSortOrder(org.primefaces.model.SortOrder pfSortOrder) {
        if (pfSortOrder == org.primefaces.model.SortOrder.ASCENDING) {
            return SortOrder.ASCENDING;
        } else if (pfSortOrder == org.primefaces.model.SortOrder.DESCENDING) {
            return SortOrder.DESCENDING;
        } else {
            return SortOrder.UNSORTED;
        }
    }

    public Map<Integer, VADCODEKSUsers> getVrnjeniUserjiMap() {
        return vrnjeniUserjiMap;
    }

    public void setVrnjeniUserjiMap(Map<Integer, VADCODEKSUsers> vrnjeniUserjiMap) {
        this.vrnjeniUserjiMap = vrnjeniUserjiMap;
    }

}
