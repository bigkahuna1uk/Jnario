/*******************************************************************************
 * Copyright (c) 2012 BMW Car IT and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.jnario.doc

import static extension org.jnario.util.XtendTypes.*
import static extension org.jnario.util.Strings.*
import org.eclipse.xtext.generator.IFileSystemAccess
 
import static org.jnario.doc.DocOutputConfigurationProvider.*
import org.eclipse.xtend.core.xtend.XtendTypeDeclaration

class HtmlFileBuilder {
	
	def generate(XtendTypeDeclaration context, IFileSystemAccess fsa, HtmlFile htmlFile){
		if(htmlFile.name == null) return
		val content = htmlFile.toText
		fsa.generateFile(filePath(context, htmlFile), DOC_OUTPUT, content)
	}
	
	def toHtmlFileName(CharSequence nameWithoutExtension){
		var result = nameWithoutExtension?.toString
		return result.trim("_") + ".html"
	}
	
	def private filePath(XtendTypeDeclaration xtendClass, HtmlFile htmlFile){
		val fileName = "/" + htmlFile.name?.toHtmlFileName
		if(xtendClass.packageName == null){
			return fileName
		}
		return "/" + xtendClass.packageName.replaceAll("\\.", "/") + fileName
	}
	
}