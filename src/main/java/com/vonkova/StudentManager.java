package com.vonkova;

import java.io.InputStream;
import java.sql.ResultSet;
import java.util.*;

public class StudentManager {
    public List<StudentFilesAndData> getStudentsFromGroup(final int groupId) {
        List<StudentFilesAndData> result = new ArrayList<StudentFilesAndData>(50);
        final DbManager dbManager = new DbManager();
        try {
            ResultSet rs = dbManager.query("select users.id,users.username,types_groups.typeid,files.filename\n" +
                    "     from users\n" +
                    "     inner join users_groups on (users_groups.userid=users.id)\n" +
                    "     inner join types_groups on (types_groups.groupid=users_groups.groupid)\n" +
                    "     left join files on (users.id=files.userid and files.current=1 and files.typeid=types_groups.typeid)\n" +
                    "     where users_groups.groupid="+groupId+"\n" +
                    "     order by users.username,types_groups.typeid");

            TreeMap<Integer, StudentFilesAndData> zaznamy = new TreeMap<Integer, StudentFilesAndData>();
            while (rs.next()) {
                Integer id = rs.getInt("id");
                StudentFilesAndData sf = zaznamy.get(id);
                if (sf == null) {
                    sf = new StudentFilesAndData(id);
                    zaznamy.put(id, sf);
                }
                String name = rs.getString("username");
                sf.setName(name);
                Integer type = rs.getInt("typeid");
                if (type != null && type>0) {
                    String filename = rs.getString("filename");
                    sf.setFilenames(filename, type);
                }
            }

            dbManager.finalizeQuery();

            rs = dbManager.query("select users.id,ugd.klic,ugd.hodnota\n" +
                    "  from users\n" +
                    "  inner join users_groups on (users_groups.userid=users.id)\n" +
                    "  inner join user_group_data as ugd on (ugd.userid=users.id and ugd.groupid=users_groups.groupid)\n" +
                    "  where users_groups.groupid="+groupId);

            while (rs.next()) {
                Integer id = rs.getInt("id");
                StudentFilesAndData sf = zaznamy.get(id);
                if (sf == null) {
                    continue;
                }
                Integer klic = rs.getInt("klic");
                if (klic!=null) {
                    String hodnota = rs.getString("hodnota");
                    sf.setData(hodnota,klic);
                }
            }

            rs.close();



            for (Integer id:zaznamy.descendingKeySet()) {
                result.add(0,zaznamy.get(id));
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException("failed at getting students", e);
        } finally {
            dbManager.finalizeQuery();
            dbManager.closeConnection();
        }
    }
    
    public Map<Integer,String> getTypesForGroup(int groupId){
        Map<Integer,String> result = new TreeMap<Integer, String>();
        final DbManager dbManager = new DbManager();
        try{
            ResultSet rs = dbManager.query("select types.id,types.title\n" +
                    "    from types\n" +
                    "    inner join types_groups on (types.id=types_groups.typeid)\n" +
                    "    where types_groups.groupid="+groupId+"\n" +
                    "    order by types.id");
            while (rs.next()){
                result.put(rs.getInt("id"),rs.getString("title"));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("failed at getting types for groupId="+groupId, e);
        } finally {
            dbManager.finalizeQuery();
            dbManager.closeConnection();
        }
    }

    public Map<Integer,String> getKliceForGroup(int groupId){
        Map<Integer,String> result = new TreeMap<Integer, String>();
        final DbManager dbManager = new DbManager();
        try{
            ResultSet rs = dbManager.query("select klice.klic,klice.title\n" +
                    "    from klice\n" +
                    "    inner join klice_groups on (klice.klic=klice_groups.klicid)\n" +
                    "    where klice_groups.groupid="+groupId+"\n" +
                    "    order by klice.klic");
            while (rs.next()){
                result.put(rs.getInt("klic"),rs.getString("title"));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("failed at getting types for groupId="+groupId, e);
        } finally {
            dbManager.finalizeQuery();
            dbManager.closeConnection();
        }
    }


    public Map<Integer,String> getGroups(){
        Map<Integer,String> result = new TreeMap<Integer, String>();
        final DbManager dbManager = new DbManager();
        try{
            ResultSet rs = dbManager.query("select id,title from groups");
            while (rs.next()){
                result.put(rs.getInt("id"),rs.getString("title"));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("failed at getting the list of groups", e);
        } finally {
            dbManager.finalizeQuery();
            dbManager.closeConnection();
        }
    }

    public InputStream getFileContent(int user, int filenr) {
        final DbManager dbManager = new DbManager();
        try {
            ResultSet rs = dbManager.query("select files.content " +
                    "from files where userid='" + user + "' and typeid='" + filenr + "' and current=1");
            if (rs.next()) {
                return rs.getClob("content").getAsciiStream();
            } else {
                throw new RuntimeException("rs.next() is empty");
            }
        } catch (Exception e) {
            throw new RuntimeException("failed at getting file content for user " + user + " file number " + filenr, e);
        } finally {
            dbManager.finalizeQuery();
            dbManager.closeConnection();
        }

    }
}
