package manager;

import java.util.Vector;
import bean.Process;

/**
 *
 * @author Fabricio Reis
 */
public class Calculator {
    
    public Calculator() {
    }
    
    /* This method calculates the sum of the burst times.
     */
    public float burstTimeSum (Vector<Process> processes) {
        float aux = 0;
        for (int i = 0; i <= (processes.size() - 1); i++) {
            aux += processes.elementAt(i).getLifeTime();
        }
        return aux;
    }

    /* This method calculates the average of the waiting times.
     */
    public float averageWaitingTime (Vector<Process> processes) {
        float aux = 0;
        for (int i = 0; i <= (processes.size() - 1); i++) {
            aux += processes.elementAt(i).getWaitingTime();
        }
        aux = aux / processes.size();
        return aux;
    }
    
    /* This method calculates the average of the turns around.
     */
    public float averageTurnAround (Vector<Process> processes) {
        float aux = 0;
        for (int i = 0; i <= (processes.size() - 1); i++) {
            aux += processes.elementAt(i).getTurnAround();
        }
        aux = aux / processes.size();
        return aux;
    }
}