package org.bm.modules.about.frame;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.bm.modules.shared.IModule;
import org.bm.modules.shared.ModuleFrame;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

public class AboutModuleFrame extends ModuleFrame {

   JScrollPane scrollPane;

   JTextArea textArea;

   JButton buttonCopyToClipBoard;

   JButton buttonOk;

   public AboutModuleFrame() {
      this.setClosable(true);

      this.setIconifiable(false);
      this.setMaximizable(false);
      this.setResizable(false);
      this.setSize(640, 480);
   }

   @Override
   public void initComponents() {

      buttonCopyToClipBoard = new JButton("Copy to clipboard");
      buttonCopyToClipBoard.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            String text = textArea.getText();
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
         }
      });

      buttonOk = new JButton("Ok");
      buttonOk.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            AboutModuleFrame.this.setVisible(false);
            AboutModuleFrame.this.dispose();

         }
      });

      textArea = new JTextArea();
      textArea.setEditable(false);
      textArea.setWrapStyleWord(true);

      scrollPane = new JScrollPane(textArea);
      scrollPane.setPreferredSize(new Dimension(600, 400));

      StringBuilder sb = new StringBuilder();

      Collection<IModule> modulesList = this.getComponentContainer().getModulesList();
      Properties properties = System.getProperties();

      sb.append("System properties:\n");
      AboutModulesDatas.appendSystemProperties(sb, properties, "  ");
      sb.append('\n');

      AboutModulesDatas.appentLoadedModules(sb, modulesList, "  ");

      textArea.setText(sb.toString());
      textArea.setCaretPosition(0);

      FormLayout layout = new FormLayout("3dlu:g, f:p, 7dlu, f:p, 3dlu", "f:p:g, 3dlu, p");
      layout.setColumnGroups(new int[][] { { 2, 4 } });
      PanelBuilder pb = new PanelBuilder(layout);

      pb.border(Borders.DIALOG);

      pb.add(scrollPane, CC.rcw(1, 1, 5));
      pb.add(buttonOk, CC.rc(3, 2));
      pb.add(buttonCopyToClipBoard, CC.rc(3, 4));

      JPanel p = pb.build();
      p.setSize(630, 470);
      //      this.setContentPane(new JScrollPane(p));
      this.setContentPane(p);

   }
}
