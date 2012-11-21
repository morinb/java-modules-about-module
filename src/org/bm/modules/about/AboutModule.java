package org.bm.modules.about;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

import org.bm.modules.about.frame.AboutModuleFrame;
import org.bm.modules.shared.IModule;
import org.bm.modules.shared.ModuleFrame;

public class AboutModule implements IModule {

   private final ModuleFrame frame = new AboutModuleFrame();

   @Override
   public void attach() {}

   @Override
   public void deattach() {}

   @Override
   public int getMenuIndex() {
      return IModule.MENU_HELP;
   }

   @Override
   public int getMenuItemIndex() {
      return 0;
   }

   @Override
   public String getName() {
      return "About";
   }

   @Override
   public boolean hasMnemonic() {
      return true;
   }

   @Override
   public int getMnemonic() {
      // TODO Auto-generated method stub
      return KeyEvent.VK_A;
   }

   @Override
   public boolean hasAccelerator() {
      return true;
   }

   @Override
   public KeyStroke getAccelerator() {

      return KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, InputEvent.CTRL_DOWN_MASK);
   }

   @Override
   public ModuleFrame getModuleFrame() {
      return frame;
   }

}
