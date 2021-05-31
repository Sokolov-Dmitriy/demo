package com.company.util;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;


public class CustomModel<T> extends AbstractTableModel {

    String[] headers;
    Class<T> cls;
    List<T> data;
    List<T> filteredData;

    public void setPredicates(Predicate<T>[] predicates) {
        this.predicates = predicates;
    }

    public void setComparator(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public Predicate<T>[] getPredicates() {
        return predicates;
    }

    public Comparator<T> getComparator() {
        return comparator;
    }

    public List<T> getFilteredData() {
        return filteredData;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    Predicate<T>[] predicates = new Predicate[]{null,null,null};
    Comparator<T> comparator;

    public CustomModel(String[] headers, Class<T> cls, List<T> data) {
        this.headers = headers;
        this.cls = cls;
        this.data = data;
        filteredData = new ArrayList<>(data);
    }

    public void updateData(){
        filteredData = new ArrayList<>(data);
        for(Predicate<T> predicate : predicates){
            if(predicate!=null){
                filteredData.removeIf(row->!predicate.test(row));
            }
        }

        if(comparator!=null){
            Collections.sort(filteredData,comparator);
        }
        this.onUpdateCustomEvent();
        this.fireTableDataChanged();


    }

    public void onUpdateCustomEvent(){

    }

    @Override
    public String getColumnName(int column) {
        return headers.length<=column?"unknown":headers[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return cls.getDeclaredFields()[columnIndex].getType();
    }

    @Override
    public int getRowCount() {
        return filteredData.size();
    }

    @Override
    public int getColumnCount() {
        return cls.getDeclaredFields().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try{
            Field field = cls.getDeclaredFields()[columnIndex];
            field.setAccessible(true);
            return field.get(this.filteredData.get(rowIndex));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }
}
