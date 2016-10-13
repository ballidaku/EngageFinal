package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(2, "com.soft.adi.engage");
        Entity users = schema.addEntity("Users");
        users.addIdProperty();
        users.addStringProperty("imageUrl");
        users.addStringProperty("username");
        users.addStringProperty("password");
        users.addStringProperty("coins");

        Entity apps = schema.addEntity("Apps");
        apps.addIdProperty();
        apps.addStringProperty("Pack");
        apps.addStringProperty("Installed");
        apps.addStringProperty("Single");
        apps.addStringProperty("Cost");


        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }
}
