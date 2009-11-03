package cambridge;

import cambridge.model.TemplateDocument;
import cambridge.model.TextNode;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * User: erdinc
 * Date: Oct 13, 2009
 * Time: 11:48:01 AM
 */
public class Cambridge {

   public static void main(String[] args) {
      try {
         final TemplateFactory f = FileTemplateLoader.newTemplateFactory(new File("kitchensink.html"), new TemplateModifier() {
            @Override
            public void modifyTemplate(TemplateDocument doc) {
               doc.getElementById("email").addChild(new TextNode("cambridge rocks"));
            }
         });

         Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
               while (true) {
                  Template t = f.createTemplate();

                  t.setProperty("user", new User("erdinc", "erdinc@yilmazel.com"));
                  t.setProperty("value", false);

                  ArrayList<User> users = new ArrayList<User>();
                  users.add(new User("bahar", "email@email.com"));
                  users.add(new User("melike", "email@email.com"));
                  users.add(new User("ahmet", "email@email.com"));
                  users.add(new User("x", "email@email.com"));
                  users.add(new User("y", "email@email.com"));
                  t.setProperty("users", users);
                  try {
                     t.printTo(System.out);
                  } catch (IOException e) {
                     e.printStackTrace();
                  } catch (TemplateRuntimeException e) {
                     e.printStackTrace();
                  }
                  try {
                     Thread.sleep(5000);
                  } catch (InterruptedException e) {
                     e.printStackTrace();
                  }
               }
            }
         });

         thread.start();

         BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
         reader.readLine();

         System.exit(0);

      } catch (TemplateLoadingException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
