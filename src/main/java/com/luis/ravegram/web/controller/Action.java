package com.luis.ravegram.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Action {

	private String name = null;
	
	public Action(String name) {
		setName(name);
	}
	
	public final void doAction(HttpServletRequest request, HttpServletResponse response) {
		try {
			System.out.println("Ejecutando "+getName()+"...");
			long t0 = System.currentTimeMillis();
			String targetView = doIt(request, response);
			long t1 = System.currentTimeMillis();
			System.out.println(getName()+" ejecutada en "+ (t1-t0)+"ms! ");
			request.getRequestDispatcher(targetView).forward(request, response);
			
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
	}
	
	
	protected abstract String doIt(HttpServletRequest request, HttpServletResponse response);
	
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return getName();
	}
}
