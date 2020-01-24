package mdp.impl;
 
import java.util.Set; 
 
import agent.Action; 
import mdp.ActionsFunction; 
import mdp.MarkovDecisionProcess; 
import mdp.RewardFunction; 
import mdp.TransitionProbabilityFunction; 
 
/**
 * Default implementation of the MarkovDecisionProcess<S, A> interface. 
 *  
 * @param <S> 
 *            the state type. 
 * @param <A> 
 *            the action type. 
 *  
 * @author Ciaran O'Reilly 
 * @author Ravi Mohan 
 */ 
public class MDP<S, A extends Action> implements MarkovDecisionProcess<S, A> { 
 private Set<S> states = null; 
 private S initialState = null; 
 private ActionsFunction<S, A> actionsFunction = null; 
 private TransitionProbabilityFunction<S, A> transitionProbabilityFunction = null; 
 private RewardFunction<S> rewardFunction = null; 
 
 public MDP(Set<S> states, S initialState, 
   ActionsFunction<S, A> actionsFunction, 
   TransitionProbabilityFunction<S, A> transitionProbabilityFunction, 
   RewardFunction<S> rewardFunction) { 
  this.states = states; 
  this.initialState = initialState; 
  this.actionsFunction = actionsFunction; 
  this.transitionProbabilityFunction = transitionProbabilityFunction; 
  this.rewardFunction = rewardFunction; 
 } 
 
 // 
 // START-MarkovDecisionProcess 
 @Override 
 public Set<S> states() { 
  return states; 
 } 
 
 @Override 
 public S getInitialState() { 
  return initialState; 
 } 
 
 @Override 
 public Set<A> actions(S s) { 
  return actionsFunction.actions(s); 
 } 
 
 @Override 
 public double transitionProbability(S sDelta, S s, A a) { 
  return transitionProbabilityFunction.probability(sDelta, s, a); 
 } 
 
 @Override 
 public double reward(S s) { 
  return rewardFunction.reward(s); 
 } 
 
 // END-MarkovDecisionProcess 
 // 
}