/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.bmw.carit.jnario.common;

import org.eclipse.xtext.xbase.XExpression;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Matcher</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.bmw.carit.jnario.common.Matcher#getClosure <em>Closure</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.bmw.carit.jnario.common.CommonPackage#getMatcher()
 * @model
 * @generated
 */
public interface Matcher extends XExpression {
	/**
	 * Returns the value of the '<em><b>Closure</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Closure</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Closure</em>' containment reference.
	 * @see #setClosure(XExpression)
	 * @see de.bmw.carit.jnario.common.CommonPackage#getMatcher_Closure()
	 * @model containment="true"
	 * @generated
	 */
	XExpression getClosure();

	/**
	 * Sets the value of the '{@link de.bmw.carit.jnario.common.Matcher#getClosure <em>Closure</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Closure</em>' containment reference.
	 * @see #getClosure()
	 * @generated
	 */
	void setClosure(XExpression value);

} // Matcher
