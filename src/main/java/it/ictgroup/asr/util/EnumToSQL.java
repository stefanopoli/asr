package it.ictgroup.asr.util;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Created by stefano on 31/10/14.
 */
public class EnumToSQL
{
   private static Logger logger = Logger.getLogger(EnumToSQL.class.getSimpleName());

   public static void enumToSQL(String packageName, String sqlFileName)
   {
      try
      {
         Class[] classes = ReflectionUtils.getClasses(packageName);
         PrintStream printStream = new PrintStream(sqlFileName);
         enumToSql(classes, printStream);
         printStream.close();
      }
      catch (ClassNotFoundException e)
      {
         e.printStackTrace();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   public static void enumToSql(Class[] classes, PrintStream printStream)
   {
      for(Class c : classes)
      {
         enumToSQL(c, printStream);
      }
   }
   
   public static void enumToSQL(Class clz, PrintStream printStream)
   {
      String val = null;
      String desc = null;
      logger.info("//------------------- " + clz.getSimpleName() + " -------------------");
      printStream.println();
      printStream.println("//------------------- " + clz.getSimpleName() + " -------------------");
      String createTableSql = "CREATE TABLE " + clz.getSimpleName() + " (name varchar(255), value varchar(255), description varchar(255));";
      logger.info(createTableSql);
      printStream.println(createTableSql);
      for(Object constant : Arrays.asList(clz.getEnumConstants()))
      {
         Class<?> sub = constant.getClass();
         try
         {
            Method getValue = sub.getDeclaredMethod("getValue");
            val = (String) getValue.invoke(constant);
         }
         catch (NoSuchMethodException e)
         {
            e.printStackTrace();
         }
         catch (InvocationTargetException e)
         {
            e.printStackTrace();
         }
         catch (IllegalAccessException e)
         {
            e.printStackTrace();
         }

         try
         {
            Method getDescription = sub.getDeclaredMethod("getDescription");
            desc = (String) getDescription.invoke(constant);
         }
         catch (NoSuchMethodException e)
         {
            e.printStackTrace();
         }
         catch (InvocationTargetException e)
         {
            e.printStackTrace();
         }
         catch (IllegalAccessException e)
         {
            e.printStackTrace();
         }
         String insertSql = "INSERT INTO " + clz.getSimpleName() + " VALUES (" +
                  "'" + ((Enum)constant).name() + "'" +
                  ", " + ((val != null) ? "'" + val + "'" : null) +
                  ", " + ((desc != null) ? "'" + desc + "'" : null) +
                  ")";
         logger.info(insertSql);
         printStream.println(insertSql);
         val = null;
         desc = null;
      }
   }
}
