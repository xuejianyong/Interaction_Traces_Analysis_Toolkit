package mdp.search;

import java.util.Map; 
import java.util.Set; 
 
import agent.Action; 
import mdp.MarkovDecisionProcess; 
import util.Util; 
 
/**
 * Artificial Intelligence A Modern Approach (3rd Edition): page 653.<br> 
 * <br> 
 *  
 * <pre> 
 * function VALUE-ITERATION(mdp, ¦Å) returns a utility function 
 *   inputs: mdp, an MDP with states S, actions A(s), transition model P(s' | s, a), 
 *             rewards R(s), discount ¦Ã 
 *           ¦Å the maximum error allowed in the utility of any state 
 *   local variables: U, U', vectors of utilities for states in S, initially zero 
 *                    ¦Ä the maximum change in the utility of any state in an iteration 
 *                     
 *   repeat 
 *       U <- U'; ¦Ä <- 0 
 *       for each state s in S do 
 *           U'[s] <- R(s) + ¦Ã  max<sub>a ¡Ê A(s)</sub> ¦²<sub>s'</sub>P(s' | s, a) U[s'] 
 *           if |U'[s] - U[s]| > ¦Ä then ¦Ä <- |U'[s] - U[s]| 
 *   until ¦Ä < ¦Å(1 - ¦Ã)/¦Ã 
 *   return U 
 * </pre> 
 *  
 * Figure 17.4 The value iteration algorithm for calculating utilities of 
 * states. The termination condition is from Equation (17.8):<br> 
 *  
 * <pre> 
 * if ||U<sub>i+1</sub> - U<sub>i</sub>|| < ¦Å(1 - ¦Ã)/¦Ã then ||U<sub>i+1</sub> - U|| < ¦Å 
 * </pre> 
 *  
 * @param <S> 
 *            the state type. 
 * @param <A> 
 *            the action type. 
 *  
 * @author Ciaran O'Reilly 
 * @author Ravi Mohan 
 *  
 */ 
public class ValueIteration<S, A extends Action> { 
 // discount ¦Ã to be used. 
 private double gamma = 0; 
 
 /**
  * Constructor. 
  *  
  * @param gamma 
  *            discount ¦Ã to be used. 
  */ 
 public ValueIteration(double gamma) { 
  if (gamma > 1.0 || gamma <= 0.0) { 
   throw new IllegalArgumentException("Gamma must be > 0 and <= 1.0"); 
  } 
  this.gamma = gamma; 
 } 
 
 // function VALUE-ITERATION(mdp, ¦Å) returns a utility function 
 /**
  * The value iteration algorithm for calculating the utility of states. 
  *  
  * @param mdp 
  *            an MDP with states S, actions A(s), <br> 
  *            transition model P(s' | s, a), rewards R(s) 
  * @param epsilon 
  *            the maximum error allowed in the utility of any state 
  * @return a vector of utilities for states in S 
  */ 
 public Map<S, Double> valueIteration(MarkovDecisionProcess<S, A> mdp, 
   double epsilon) { 
  // 
  // local variables: U, U', vectors of utilities for states in S, 
  // initially zero 
  Map<S, Double> U = Util.create(mdp.states(), new Double(0)); 
  Map<S, Double> Udelta = Util.create(mdp.states(), new Double(0)); 
  // ¦Ä the maximum change in the utility of any state in an 
  // iteration 
  double delta = 0; 
  // Note: Just calculate this once for efficiency purposes: 
  // ¦Å(1 - ¦Ã)/¦Ã 
  double minDelta = epsilon * (1 - gamma) / gamma; 
 
  // repeat 
  do { 
   // U <- U'; ¦Ä <- 0 
   U.putAll(Udelta); 
   delta = 0; 
   // for each state s in S do 
   for (S s : mdp.states()) { 
    // max<sub>a ¡Ê A(s)</sub> 
    Set<A> actions = mdp.actions(s); 
    // Handle terminal states (i.e. no actions). 
    double aMax = 0; 
    if (actions.size() > 0) { 
     aMax = Double.NEGATIVE_INFINITY; 
    } 
    for (A a : actions) { 
     // ¦²<sub>s'</sub>P(s' | s, a) U[s'] 
     double aSum = 0; 
     for (S sDelta : mdp.states()) { 
      aSum += mdp.transitionProbability(sDelta, s, a) 
        * U.get(sDelta); 
     } 
     if (aSum > aMax) { 
      aMax = aSum; 
     } 
    } 
    // U'[s] <- R(s) + ¦Ã 
    // max<sub>a ¡Ê A(s)</sub> 
    Udelta.put(s, mdp.reward(s) + gamma * aMax); 
    // if |U'[s] - U[s]| > ¦Ä then ¦Ä <- |U'[s] - U[s]| 
    double aDiff = Math.abs(Udelta.get(s) - U.get(s)); 
    if (aDiff > delta) { 
     delta = aDiff; 
    } 
   } 
   // until ¦Ä < ¦Å(1 - ¦Ã)/¦Ã 
  } while (delta > minDelta); 
 
  // return U 
  return U; 
 } 
}
