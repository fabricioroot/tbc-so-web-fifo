package thread;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import bean.Process;
import gui.MainScreen;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author Fabrício Reis
 */
public class AlgorithmStepsThread implements Runnable {
    
    MainScreen mainScreen;
    JButton jButtonAlgorithmSteps;
    JButton jButtonReport; 
    Vector<Process> processesList;
    Vector<Process> reportBase;
    Vector<Process> reportBaseTemp;
    int timeCounter;
    JPanel jPanelCPU;
    JProgressBar jProgressBarExecution;
    JLabel jLabelShowBurstTime;
    JLabel jLabelShowCreationTime;
    JLabel jLabelTimeCounter;
    JLabel jLabelCPU;
    boolean isJButtonOkClicked = false;
    JDialog jDialogNextStep;
    JButton jButtonOkNextStep;
    JLabel jLabelAtDialogNextStep;
    int remainingTimeToFinishRunning;

    public AlgorithmStepsThread(MainScreen mainScreen, JButton jButtonAlgorithmSteps, JButton jButtonReport, Vector<Process> processesList, Vector<Process> reportBase,
                                Vector<Process> reportBaseTemp, int timeCounter, JPanel jPanelCPU, JProgressBar jProgressBarExecution,
                                JLabel jLabelShowBurstTime, JLabel jLabelShowCreationTime, JLabel jLabelTimeCounter, JLabel jLabelCPU){
        
        this.mainScreen = mainScreen;
        this.jButtonAlgorithmSteps = jButtonAlgorithmSteps;
        this.jButtonReport = jButtonReport;
        this.processesList = processesList;
        this.reportBase = reportBase;
        this.reportBaseTemp = reportBaseTemp;
        this.timeCounter = timeCounter;
        this.jPanelCPU = jPanelCPU;
        this.jProgressBarExecution = jProgressBarExecution;
        this.jLabelShowBurstTime = jLabelShowBurstTime;
        this.jLabelShowCreationTime = jLabelShowCreationTime;
        this.jLabelTimeCounter = jLabelTimeCounter;
        this.jLabelCPU = jLabelCPU;
    }

    public Vector<Process> getReportBase() {
        return reportBase;
    }

    public Vector<Process> getReportBaseTemp() {
        return reportBaseTemp;
    }

    public int getTimeCounter() {
        return timeCounter;
    }

    public int getRemainingTimeToFinishRunning() {
        return remainingTimeToFinishRunning;
    }
    
    public JDialog getJDialogNextStep() {
        return jDialogNextStep;
    }

    public void setJDialogNextStep(JDialog jDialogNextStep) {
        this.jDialogNextStep = jDialogNextStep;
    }
    
    public void run() {
        this.jButtonAlgorithmSteps.setEnabled(false);
        
        if (!this.processesList.isEmpty()) {
            
            if(this.reportBase == null) {
                this.reportBase = new Vector<Process>();
            }

            this.reportBase.add(this.reportBaseTemp.firstElement());
            this.reportBaseTemp.remove(0);
            Process process = new Process();
            process = this.processesList.firstElement();
            this.processesList.remove(0);
            this.mainScreen.paintProcessesList(this.processesList);

            JTextField block = new JTextField();
            block.setBackground(new java.awt.Color(255, 51, 0));
            block.setForeground(new java.awt.Color(0, 0, 0));
            block.setHorizontalAlignment(javax.swing.JTextField.CENTER);
            block.setEditable(false);
            block.setBounds(35, 20, 30, 30);
            this.jPanelCPU.add(block);
            block.setText("P" + String.valueOf(process.getId()));
            block.setToolTipText("Tempo de burst = " + String.valueOf(process.getLifeTime()));
            this.jProgressBarExecution.setVisible(true);
            this.jLabelShowBurstTime.setVisible(true);
            this.jLabelShowBurstTime.setText("Tempo de burst de P" + String.valueOf(process.getId()) + " = " + String.valueOf(process.getLifeTime()));
            this.jLabelShowCreationTime.setVisible(true);
            this.jLabelShowCreationTime.setText("Tempo na criação de P" + String.valueOf(process.getId()) + " = " + String.valueOf(process.getCreationTime()));

            this.jDialogNextStep = new JDialog();
            this.jDialogNextStep.setModalityType(ModalityType.MODELESS);
            this.jDialogNextStep.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
            //this.jDialogNextStep.setAlwaysOnTop(true);
            this.jDialogNextStep.setResizable(false);
            this.jDialogNextStep.setBounds(600, 520, 231, 118);
            this.jDialogNextStep.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            this.jDialogNextStep.setLayout(null);
            this.jDialogNextStep.setTitle("EXECUÇÃO DE P" + String.valueOf(process.getId()) + " ...");

            this.jButtonOkNextStep = new JButton("OK");
            this.jButtonOkNextStep.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            this.jButtonOkNextStep.setBorderPainted(true);
            this.jButtonOkNextStep.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            this.jButtonOkNextStep.setBounds(80, 35, 60, 30);

            this.jButtonOkNextStep.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    isJButtonOkClicked = true;
                }
            });

            this.jLabelAtDialogNextStep = new JLabel("Clique em 'OK' para o próximo passo");
            this.jLabelAtDialogNextStep.setBounds(5, 3, 500, 30);

            this.jDialogNextStep.add(this.jLabelAtDialogNextStep);
            this.jDialogNextStep.add(this.jButtonOkNextStep);
            
            this.jDialogNextStep.setVisible(true);
            this.remainingTimeToFinishRunning = process.getLifeTime();
            int j = 0;
            int aux = 0;
            while (j <= (process.getLifeTime() - 1)) {
                if (this.isJButtonOkClicked) {
                    this.isJButtonOkClicked = false;
                    this.remainingTimeToFinishRunning--;
                    j++;
                    aux = 100 / process.getLifeTime();
                    this.timeCounter++;
                    this.jLabelTimeCounter.setText(String.valueOf(this.timeCounter));
                    this.jProgressBarExecution.setValue(j*aux);
                    this.jProgressBarExecution.getUI().update(this.jProgressBarExecution.getGraphics(), this.jProgressBarExecution);
                }
            }
            
            // This bit is used to show to the user the last interaction (when 'jProgressBarExecution' is 100%) without increase 'timeCounter'
            while (j == process.getLifeTime()) {
                if (this.isJButtonOkClicked) {
                    this.isJButtonOkClicked = false;
                    aux = 100 / process.getLifeTime();
                    j++;
                    this.jProgressBarExecution.setValue(j*aux);
                    this.jProgressBarExecution.getUI().update(this.jProgressBarExecution.getGraphics(), this.jProgressBarExecution);
                }
            }
            
            this.jDialogNextStep.setVisible(false);

            this.jPanelCPU.removeAll();
            this.jPanelCPU.repaint();
            this.jPanelCPU.add(jLabelCPU);
            this.jButtonReport.setEnabled(true);
            this.jProgressBarExecution.setVisible(false);
            this.jLabelShowBurstTime.setText("");
            this.jLabelShowCreationTime.setText("");
            
            if(this.processesList.size() > 0) {
                this.jButtonAlgorithmSteps.setEnabled(true);
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Não há processos na lista de processos prontos!", "ATENÇÃO", JOptionPane.WARNING_MESSAGE);
        }
    }
}
