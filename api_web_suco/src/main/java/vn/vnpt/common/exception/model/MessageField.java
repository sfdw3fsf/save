/*******************************************************************************
 * Copyright (c) 2017 ANHTCN.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package vn.vnpt.common.exception.model;

/**
 * @author trananh
 *
 */
public class MessageField {
	private String fieldName;
	private String message;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @param fieldName
	 * @param messages
	 */
	public MessageField(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

}
