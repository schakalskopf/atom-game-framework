/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.execution;

/**
 * Concept borrow from load balance architecture and financial model. It
 * collapse Resource and Need in an comparable Cost (that's it, Number)
 *
 * <p>T is an interaction, can be runnable, event or what ever. But the
 * computation of T require a Cost. The budget limit the cost into allowance
 * amount.
 *
 * @author cuong.nguyenmanh2
 */
public abstract class OperatingBudget<T> implements Comparable<OperatingBudget>, Iterable<T> {
}
