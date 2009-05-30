package thread;

import java.util.Vector;
import javax.swing.JButton;
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
    JButton jButtonOkNextStep;
    JLabel jLabelAtDialogNextStep;
    float remainingTimeToFinishRunning;

    public AlgorithmStepsThread(MainScreen mainScreen, JButton jButtonAlgorithmSteps, JButton jButtonReport, Vector<Process> processesList, Vector<Process> reportBase,
                                Vector<Process> reportBaseTemp, int timeCounter, JPanel jPanelCPU, JProgressBar jProgressBarExecution,
                                JLabel jLabelShowBurstTime, JLabel jLabelShowCreationTime, JLabel jLabelTimeCounter, JLabel jLabelCPU, JButton jButtonOkNextStep){
        
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
        this.jButtonOkNextStep = jButtonOkNextStep;
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

    public float getRemainingTimeToFinishRunning() {
        return remainingTimeToFinishRunning;
    }
    
    public JButton getJButtonOkNextStep() {
        return jButtonOkNextStep;
    }

    public void setJButtonOkNextStep(JButton jButtonOkNextStep) {
        this.jButtonOkNextStep = jButtonOkNextStep;
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
            block.setToolTipText("Tempo de burst = " + String.valueOf((int)process.getLifeTime()));
            this.jProgressBarExecution.setVisible(true);
            this.jLabelShowBurstTime.setVisible(true);
            this.jLabelShowBurstTime.setText("Tempo de burst de P" + String.valueOf(process.getId()) + " = " + String.valueOf((int)process.getLifeTime()));
            this.jLabelShowCreationTime.setVisible(true);
            this.jLabelShowCreationTime.setText("Tempo na criação de P" + String.valueOf(process.getId()) + " = " + String.valueOf((int)process.getCreationTime()));

            this.jButtonOkNextStep.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    isJButtonOkClicked = true;
                }
            });

            this.jButtonOkNextStep.setVisible(true);
            this.remainingTimeToFinishRunning = process.getLifeTime();
            int j = 0;
            int aux = 0;
            while (j <= (process.getLifeTime() - 1)) {
                if (this.isJButtonOkClicked) {
                    this.isJButtonOkClicked = false;
                    this.remainingTimeToFinishRunning--;
                    j++;
                    aux = 100 / (int)process.getLifeTime();
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
                    aux = 100 / (int)process.getLifeTime();
                    j++;
                    this.jProgressBarExecution.setValue(j*aux);
                    this.jProgressBarExecution.getUI().update(this.jProgressBarExecution.getGraphics(), this.jProgressBarExecution);
                }
            }
            
            this.jButtonOkNextStep.setVisible(false);

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
