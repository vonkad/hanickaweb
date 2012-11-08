package com.vonkova;

import java.util.HashMap;
import java.util.Map;

public class StudentFilesAndData {
    private String name;
    private int id;
    private Map<Integer,String> filenames = new HashMap<Integer,String>();
    private Map<Integer,String> data = new HashMap<Integer,String>();

    public StudentFilesAndData(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFilenames(String filename, int i) {
        filenames.put(i,filename);
    }

    public String getFilename(int i) {
        return filenames.get(i);
    }

    public void setData(String value, int i) {
        data.put(i,value);
    }

    public String getData(int i) {
        return data.get(i);
    }


    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentFilesAndData that = (StudentFilesAndData) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "StudentFilesAndData{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", filenames=" + filenames +
                ", data=" + data +
                '}';
    }
}