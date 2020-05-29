package DB;

import AssociationAssets.Field;

import java.util.HashMap;

/**
 * this class is the fieldDB class
 * Aouthors: Tair Cohen
 */
public class FieldDB {
    HashMap<String, Field> allFields;

    public FieldDB() {
        this.allFields = new HashMap<>();
    }
    public void addField(Field newField,String fieldName){
        allFields.put(fieldName,newField);
    }
    public void removeField(String fieldName){
        this.allFields.remove(fieldName);
    }

    public HashMap<String, Field> getAllFields() {
        return allFields;
    }
}